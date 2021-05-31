package com.thenneem.omnitrail.ui.templedetail;

import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Share;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.thenneem.omnitrail.GalleryActivity;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.adapter.TempleAdaptor;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;
import com.thenneem.omnitrail.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TempleDetailFragment extends Fragment implements View.OnClickListener {

    private TempleDetailViewModel mViewModel;


    ImageView templeThumb, imgTest;
    // TextView txtTempleName;
    // TextView txtLocation;
    TextView txtFullAddress;
    TextView txtTempleStory;
    TextView txtPrimaryDeity;
    TextView txtGoverningBody;
    TextView txtContactPerson;
    TextView txtCreator;
    TextView txtCompletionPeriod;
    String strWiki, strName, img, myTID;
    MaterialButton btnPhotos;
    Integer intId;
    ShareButton btnShare;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private static String TAG = TempleDetailFragment.class.getName();


    public static TempleDetailFragment newInstance() {
        return new TempleDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, callback);
        myTID = "";
        img = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            myTID = bundle.getString("TempleId");
            img = bundle.getString("templeImg");

//            Toast.makeText(this.getContext(), "TempleID : " + myTID, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        final View root = inflater.inflate(R.layout.temple_detail_fragment, container, false);


//        txtTempleName = root.findViewById(R.id.txtTempleName);
        //       txtLocation = root.findViewById(R.id.txtMyLocation);
        txtFullAddress = root.findViewById(R.id.txtFullAddress);
        txtTempleStory = root.findViewById(R.id.txtTempleStory);
        txtPrimaryDeity = root.findViewById(R.id.txtPrimaryDeity);
        txtGoverningBody = root.findViewById(R.id.txtGoverningBody);
        txtContactPerson = root.findViewById(R.id.txtContactPerson);
        txtCreator = root.findViewById(R.id.txtCreator);
        txtContactPerson = root.findViewById(R.id.txtContactPerson);
        txtCompletionPeriod = root.findViewById(R.id.txtCompletionPeriod);

        templeThumb = root.findViewById(R.id.templeThumb);
        MaterialButton btnWiki = root.findViewById(R.id.btnWikiLink);
        btnPhotos = root.findViewById(R.id.btnPhotos);
        btnWiki.setOnClickListener(this);
        btnPhotos.setOnClickListener(this);

        // calling json retrofit
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Temple>> call = apiService.getTemple(myTID);
        //Call<List<Temple>> call = apiService.getTempleList("1");


        //
        call.enqueue(new Callback<List<Temple>>() {
            @Override
            public void onResponse(Call<List<Temple>> call, Response<List<Temple>> response) {

                Utils.showLoader(getActivity());
                List<Temple> tl = response.body();
                ///recyclerView.setAdapter(new TempleAdaptor(rl,R.layout.templelist_layout,getContext()));
                BindTempleDetail(tl);


            }

            @Override
            public void onFailure(Call<List<Temple>> call, Throwable t) {
                //Log.d(TAG , t.toString());
                Toast.makeText(getContext(), "Error" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });


        return root;
    }


    public void BindTempleDetail(List<Temple> tl) {
        if (tl.size() > 0) {
            strName = tl.get(0).getTempleName();
            intId = tl.get(0).getTempleID();
            txtFullAddress.setText(tl.get(0).getAddress() + " " + tl.get(0).getCity_name() + ", " + tl.get(0).getState_name() + ", " + tl.get(0).getCountry_name());
            txtTempleStory.setText(tl.get(0).getTempleStory());

            txtPrimaryDeity.setText(tl.get(0).getPrimaryDeity());
            txtGoverningBody.setText(tl.get(0).getGoverningBody());
            txtContactPerson.setText(tl.get(0).getContactPerson() + " : " + tl.get(0).getContactNumber());
            txtCreator.setText(tl.get(0).getCreator());
            txtCompletionPeriod.setText(tl.get(0).getCompletionPerios());
            //img = tl.get(0).getTempleIMG();

            strWiki = tl.get(0).getWiki_link();
            //UrlToBitmap(img);
            Utils.hideLoader(getActivity());

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TempleDetailViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setImageShare(view);

    }

    private void setImageShare(View view) {

        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setImageUrl(Uri.parse(img))
                .setCaption("I am here!")
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build();

        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setQuote("I am at!")
                .setContentUrl(Uri.parse(img))
                .build();

        btnShare = (ShareButton) view.findViewById(R.id.share);
        btnShare.setShareContent(content);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private FacebookCallback<Sharer.Result> callback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onSuccess(Sharer.Result result) {
            Log.v(TAG, "Successfully posted");
            // Write some code to do some operations when you shared content successfully.
        }

        @Override
        public void onCancel() {
            Log.v(TAG, "Sharing cancelled");
            // Write some code to do some operations when you cancel sharing content.
        }

        @Override
        public void onError(FacebookException error) {
            Log.v(TAG, error.getMessage());
            // Write some code to do some operations when some error occurs while sharing content.
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnWikiLink:
                // Do something
                Toast.makeText(this.getContext(), "wiki link crossed" + strWiki, Toast.LENGTH_SHORT).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strWiki));
                startActivity(browserIntent);
                break;
            case R.id.btnPhotos:
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("templeid", intId.toString());
                bundle.putString("templename", strName);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }


}
