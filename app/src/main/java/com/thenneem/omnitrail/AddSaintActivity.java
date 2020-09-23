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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.model.City;
import com.thenneem.omnitrail.model.Country;
import com.thenneem.omnitrail.model.Religion;
import com.thenneem.omnitrail.model.State;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;
import com.thenneem.omnitrail.rest.UploadResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RuntimePermissions
public class AddSaintActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;
    private ApiInterface apiService;

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void checkLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location == null) {
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
        setContentView(R.layout.activity_add_saint);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        topToolBar = findViewById(R.id.toptoolbar);
        apiService = ApiClient.getClient().create(ApiInterface.class);

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
        if (type == -1) return;

        topToolBar.setTitle("Suggest adding a Saint");

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
                switch (checkedId) {
                    case R.id.manualAddress:
                        addressContainer.setVisibility(View.VISIBLE);
                        initSpinners();
                        break;
                    case R.id.currentLocation:
                        addressContainer.setVisibility(View.GONE);
                        break;

                }
            }
        });
        AddSaintActivityPermissionsDispatcher.checkLocationWithPermissionCheck(this);
        manualAddressRadioButton = findViewById(R.id.manualAddress);
        currentLocationRadioButton = findViewById(R.id.currentLocation);
        templeName = findViewById(R.id.title);
        samudai = findViewById(R.id.samudai);
        story = findViewById(R.id.story);
        adress = findViewById(R.id.address);
        zipCode = findViewById(R.id.zipCode);


        findViewById(R.id.sendButton).setOnClickListener(v -> {
            if (validation()) {
                uploadAndSave();
            }
        });
    }

    void initSpinners(){
        AppCompatSpinner spinner = findViewById(R.id.countrySpinner);
        apiService.countryList().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                List<Country> countries = response.body();
                if(countries == null) return;
                ArrayAdapter<Country> adapter = new ArrayAdapter<>(AddSaintActivity.this, android.R.layout.simple_spinner_item, countries);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinner.setAdapter( adapter );
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        initStates(countries.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });
    }

    void initStates(Country country){
        AppCompatSpinner spinner = findViewById(R.id.stateSpinner);
        apiService.stateList(country.getCountryCode())
                .enqueue(new Callback<List<State>>() {
                    @Override
                    public void onResponse(Call<List<State>> call, Response<List<State>> response) {
                        List<State> states = response.body();
                        if(states == null) return;
                        if(states.isEmpty()){
                            initCity(null, null);
                        }
                        ArrayAdapter<State> adapter = new ArrayAdapter<>(AddSaintActivity.this, android.R.layout.simple_spinner_item, states);
                        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                        spinner.setAdapter( adapter );
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                initCity(country, states.get(position));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<State>> call, Throwable t) {

                    }
                });
    }

    City currentCity;
    void initCity(Country country, State state){
        AppCompatSpinner spinner = findViewById(R.id.citySpinner);
        apiService.getCityList(state == null ? null:state.getStateCode(),
                country == null ? null : country.getCountryCode())
                .enqueue(new Callback<List<City>>() {
                    @Override
                    public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                        List<City> cities = response.body();
                        if(cities == null) return;
                        ArrayAdapter<City> adapter = new ArrayAdapter<>(AddSaintActivity.this, android.R.layout.simple_spinner_item, cities);
                        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                        spinner.setAdapter( adapter );
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                currentCity = cities.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<City>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AddSaintActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private Uri uploadImage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123) {
            if (data == null) return;
            Uri uri = data.getData();
            uploadImage = uri;
            ImageView view = findViewById(R.id.templeThumb);
            picasso.load(uri)
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_foreground)
                    .fit()
                    .into(view);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    AlertDialog saveDialog;

    void uploadAndSave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Send data")
                .setView(new ProgressBar(this));

        saveDialog = builder.show();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        if (uploadImage == null) {
            return;
        }
        try {
            ContentResolver resolver = getContentResolver();
            InputStream inputStream = resolver.openInputStream(uploadImage);

            RequestBody requestFile = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", "file.jpg",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    IOUtils.toByteArray(inputStream)))
                    .build();

            MultipartBody.Part body =
                    MultipartBody.Part.create(requestFile);
            apiService.uploadImage("temple", requestFile).enqueue(new Callback<UploadResult>() {
                @Override
                public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
                    Callback<Void> callback = new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            saveDialog.dismiss();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            saveDialog.dismiss();
                        }
                    };
                    String imageUrl = response.body().getData().getFileUrl();
                    String name = templeName.getText().toString();
                    String samudaiString = samudai.getText().toString();
                    String storyString = story.getText().toString();
                    if(manualAddressRadioButton.isChecked()){
                        String addressString = adress.getText().toString();
                        String zip = zipCode.getText().toString();
                        apiService.saintAdd(String.valueOf(religion.getReligionID()),
                                name, imageUrl, samudaiString, storyString,
                                String.valueOf(currentCity.getCityId()), addressString, zip)
                                .enqueue(callback);
                    }else{
                        apiService.saintAdd(String.valueOf(religion.getReligionID()),
                                name, imageUrl, samudaiString, storyString,
                                currentLocation.getLongitude(), currentLocation.getLatitude())
                                .enqueue(callback);
                    }
                }


                @Override
                public void onFailure(Call<UploadResult> call, Throwable t) {
                    saveDialog.dismiss();
                    Toast.makeText(AddSaintActivity.this, "Upload image error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    EditText templeName;
    EditText samudai;
    EditText story;
    EditText adress;
    EditText zipCode;

    boolean validation() {
        boolean result = true;
        if (templeName.getText().toString().isEmpty()) {
            templeName.setError("");
            result = false;
        }
        if (samudai.getText().toString().isEmpty()) {
            samudai.setError("");
            result = false;
        }
        if (story.getText().toString().isEmpty()) {
            story.setError("");
            result = false;
        }
        if (currentLocationRadioButton.isChecked() && currentLocation == null) {
            Toast.makeText(this, "Could not determine location", Toast.LENGTH_LONG).show();
            result = false;
        }
        if (uploadImage == null) {
            Toast.makeText(this, "Select image", Toast.LENGTH_LONG).show();
            result = false;
        }

        if(manualAddressRadioButton.isChecked()){
            if(currentCity == null) result = false;
            if(adress.getText().toString().isEmpty()){
                adress.setError("");
                result = false;
            }
            if(zipCode.getText().toString().isEmpty()){
                zipCode.setError("");
                result = false;
            }
        }
        return result;
    }
}
