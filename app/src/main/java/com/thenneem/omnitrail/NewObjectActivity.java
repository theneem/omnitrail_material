package com.thenneem.omnitrail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.model.Religion;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;
import com.thenneem.omnitrail.rest.UploadResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RuntimePermissions
public class NewObjectActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void checkLocation(){
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if(location == null){
                new AlertDialog.Builder(this)
                        .setMessage("Could not determine location")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }
            currentLocation = location;
            currentLocationRadioButton.setEnabled(true);
            currentLocationRadioButton.setChecked(true);
        });
    }

    MaterialToolbar topToolBar;
    Picasso picasso;
    Religion religion;

    MaterialRadioButton currentLocationRadioButton;
    MaterialRadioButton manualAddressRadioButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        topToolBar = findViewById(R.id.toptoolbar);

        topToolBar.setNavigationIcon(R.drawable.ic_back);
        topToolBar.animate();

        topToolBar.setNavigationOnClickListener(view -> {
            finish();

        });

        findViewById(R.id.browseButton).setOnClickListener(v -> {
            Intent browseIntent = new Intent(Intent.ACTION_GET_CONTENT)
                    .setType("image/*");
            startActivityForResult(Intent.createChooser(browseIntent, "Select a file"), 123);
        });

        religion = (Religion) getIntent().getSerializableExtra("religion");
        int type = getIntent().getIntExtra("type", -1);
        if(type == -1)return;

        topToolBar.setTitle(type == 0 ? "Suggest adding a Temple" : "Suggest adding a Saint");

        ImageView imgRThumb = findViewById(R.id.imgReligionThumb);
        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        picasso = builder.build();
        picasso.load(religion.getHeaderimg())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_foreground)
                .into(imgRThumb);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        View addressContainer = findViewById(R.id.addressContainer);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.manualAddress:
                        addressContainer.setVisibility(View.VISIBLE);
                    break;
                    case R.id.currentLocation:
                        addressContainer.setVisibility(View.GONE);
                        break;

                }
            }
        });
        NewObjectActivityPermissionsDispatcher.checkLocationWithPermissionCheck(this);
        manualAddressRadioButton = findViewById(R.id.manualAddress);
        currentLocationRadioButton = findViewById(R.id.currentLocation);
        templeName = findViewById(R.id.title);
        deity = findViewById(R.id.deity);
        story = findViewById(R.id.story);


        findViewById(R.id.sendButton).setOnClickListener(v -> {
            if(validation()) {
                uploadAndSave();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        NewObjectActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private Uri uploadImage;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 123){
            if(data == null) return;
            Uri uri = data.getData();
            uploadImage = uri;
            ImageView view = findViewById(R.id.templeThumb);
            picasso.load(uri)
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_foreground)
                    .fit()
                    .into(view);

        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    void uploadAndSave(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        if(uploadImage == null){
            return;
        }
        try {
            ContentResolver resolver = getContentResolver();
            InputStream inputStream = resolver.openInputStream(uploadImage);

            RequestBody requestFile = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file","file.jpg",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    IOUtils.toByteArray(inputStream)))
                    .build();

            MultipartBody.Part body =
                    MultipartBody.Part.create(requestFile);
            apiService.uploadImage("temple", requestFile).enqueue(new Callback<UploadResult>() {
                @Override
                public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {

                    String imageUrl = response.body().getData().getFileUrl();
                    String name = templeName.getText().toString();
                    String deityString = deity.getText().toString();
                    String storyString = story.getText().toString();
                    apiService.templeAdd(String.valueOf(religion.getReligionID()),
                            name, imageUrl, deityString, storyString,
                            currentLocation.getLongitude(), currentLocation.getLatitude())
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });

                }


                @Override
                public void onFailure(Call<UploadResult> call, Throwable t) {
                    Log.d("Andrey", "Error");
                }
            });
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    EditText templeName;
    EditText deity;
    EditText story;
    boolean validation(){
        boolean result = true;
        if(templeName.getText().toString().isEmpty()){
            templeName.setError("");
            result = false;
        }
        if(deity.getText().toString().isEmpty()){
            deity.setError("");
            result = false;
        }
        if(story.getText().toString().isEmpty()){
            story.setError("");
            result = false;
        }
        if(currentLocationRadioButton.isSelected() && currentLocation == null){
            Toast.makeText(this, "Could not determine location", Toast.LENGTH_LONG).show();
            result = false;
        }
        if(uploadImage == null){
            Toast.makeText(this, "Select image", Toast.LENGTH_LONG).show();
            result = false;
        }
        return result;
    }
}
