package com.thenneem.omnitrail.ui.templedetail;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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


    ImageView templeThumb;
    TextView txtTempleName,  txtFullAddress, txtTempleStory, txtPrimaryDeity, txtGoverningBody, txtContactPerson, txtCreator, txtCompletionPeriod;
    String strWiki, strName, templeAdd, templeStory, templeDeity, templeGoverning, templeContact, templeCreator, templeCompletion,
            img, myTID;
    MaterialButton btnPhotos;

    FloatingActionButton fabImageGallary;
    FloatingActionButton fabShare;
    FloatingActionButton fabWiki;


    ShareButton btnShare;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private static final String TAG = TempleDetailFragment.class.getName();


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
            strName = bundle.getString("TempleName");
            templeAdd = bundle.getString("templeAdd") + "" + bundle.getString("templeCity")
                    + "," + bundle.getString("templeState") + "," + bundle.getString("templeCountry") + ".";
            templeStory = bundle.getString("templeStory");
            templeDeity = bundle.getString("templeDeity");
            templeGoverning = bundle.getString("templeGoverning");
            templeContact = bundle.getString("templeContactPerson") + ":" + bundle.getString("templeContactNumber");
            templeCreator = bundle.getString("templeCreator");
            templeCompletion = bundle.getString("templeCompletionPeriod");
            strWiki = bundle.getString("templeWiki");
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        final View root = inflater.inflate(R.layout.temple_detail_fragment, container, false);

        txtTempleName = root.findViewById(R.id.txtTempleName);
        txtFullAddress = root.findViewById(R.id.txtFullAddress);
        txtTempleStory = root.findViewById(R.id.txtTempleStory);
        txtPrimaryDeity = root.findViewById(R.id.txtPrimaryDeity);
        txtGoverningBody = root.findViewById(R.id.txtGoverningBody);
        txtContactPerson = root.findViewById(R.id.txtContactPerson);
        txtCreator = root.findViewById(R.id.txtCreator);
        txtContactPerson = root.findViewById(R.id.txtContactPerson);
        txtCompletionPeriod = root.findViewById(R.id.txtCompletionPeriod);

        templeThumb = root.findViewById(R.id.templeThumb);


        fabWiki = root.findViewById(R.id.fabWiki);
        fabImageGallary = root.findViewById(R.id.fabImageGallary);
        fabShare = root.findViewById(R.id.fabShare);

        fabWiki.setOnClickListener(this);
        fabImageGallary.setOnClickListener(this);
        fabShare.setOnClickListener(this);

        BindTempleDetail();

        return root;
    }

    public void BindTempleDetail() {

        //txtTempleName.setText(strName);
        txtFullAddress.setText(templeAdd);
        txtTempleStory.setText(templeStory);
        txtPrimaryDeity.setText(templeDeity);
        txtGoverningBody.setText(templeGoverning);
        txtContactPerson.setText(templeContact);
        txtCreator.setText(templeCreator);
        txtCompletionPeriod.setText(templeCompletion);
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
        //fabShare = (ShareButton) view.findViewById(R.id.share);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fabImageGallary:
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("bundleId", "0");
                bundle.putString("templeId", myTID);
                bundle.putString("templeName", strName);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.fabWiki:
                // Do something
                Toast.makeText(this.getContext(), "wiki link crossed" + strWiki, Toast.LENGTH_SHORT).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strWiki));
                startActivity(browserIntent);
                break;
            case R.id.fabShare:
                btnShare.performClick();
                break;

        }
    }


}
