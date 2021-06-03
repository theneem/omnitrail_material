package com.thenneem.omnitrail.ui.saintdetail;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
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
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thenneem.omnitrail.GalleryActivity;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;
import com.thenneem.omnitrail.ui.templedetail.TempleDetailFragment;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaintDetailFragment extends Fragment implements View.OnClickListener {

    private SaintDetailViewModel mViewModel;

    TextView txtSect;
    TextView txtSamudai;
    TextView txtParentSaint;
    TextView txtSaintDate;
    TextView txtFullAddress;
    TextView txtSaintStory;
    TextView txtContactPerson;
    MaterialButton btnWikiLink;
    MaterialButton btnPhotos;
    TextView txtSaintDeatilName;
    TextView txtLifeSpan;
    ShareButton btnShare;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    String strWiki, strName, img, mySID;
    Integer intId;


    FloatingActionButton fabImageGallary;
    FloatingActionButton fabShare;
    FloatingActionButton fabWiki;


    private static String TAG = SaintDetailFragment.class.getName();

    public static SaintDetailFragment newInstance() {
        return new SaintDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, callback);
        mySID = "";
        img = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            mySID = bundle.getString("SaintId");
            img = bundle.getString("SaintImg");
            //Toast.makeText(this.getContext(), "TempleID : " + myTID, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.saint_detail_fragment, container, false);


        txtSaintDeatilName = root.findViewById(R.id.txtSaintDetailName);
        txtLifeSpan = root.findViewById(R.id.txtLifeSpan);

        txtSect = root.findViewById(R.id.txtSect);

        txtSamudai = root.findViewById(R.id.txtSamudai);
        txtParentSaint = root.findViewById(R.id.txtParentSaint);
        txtSaintDate = root.findViewById(R.id.txtSaintDate);
        txtFullAddress = root.findViewById(R.id.txtFullAddress);
        txtSaintStory = root.findViewById(R.id.txtSaintStory);
        txtContactPerson = root.findViewById(R.id.txtContactPerson);




        fabWiki = root.findViewById(R.id.fabWiki);
        fabImageGallary = root.findViewById(R.id.fabImageGallary);
        fabShare = root.findViewById(R.id.fabShare);

        fabWiki.setOnClickListener(this);
        fabImageGallary.setOnClickListener(this);
        fabShare.setOnClickListener(this);


        // calling json retrofit
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Saint>> call = apiService.getSaint(mySID);
        //Call<List<Temple>> call = apiService.getTempleList("1");

        //
        call.enqueue(new Callback<List<Saint>>() {
            @Override
            public void onResponse(Call<List<Saint>> call, Response<List<Saint>> response) {


                List<Saint> sl = response.body();
                ///recyclerView.setAdapter(new TempleAdaptor(rl,R.layout.templelist_layout,getContext()));
                BindSaintDetail(sl);


            }

            @Override
            public void onFailure(Call<List<Saint>> call, Throwable t) {
                //Log.d(TAG , t.toString());
                Toast.makeText(getContext(), "Error" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        //

        return root;
    }


    public void BindSaintDetail(List<Saint> sl) {
        if (sl.size() > 0) {

            intId = sl.get(0).getSaintID();
            strName = sl.get(0).getSaintName();
            txtSaintDeatilName.setText(sl.get(0).getSaintName());
            txtSect.setText(sl.get(0).getSectName());
            txtSamudai.setText(sl.get(0).getSamudai());
            txtParentSaint.setText(sl.get(0).getParentSaintName());
            txtSaintDate.setText(sl.get(0).getSaintDate());
            txtSaintStory.setText(sl.get(0).getSaintStory());
            txtFullAddress.setText(sl.get(0).getCurrentAddress());
            strWiki = sl.get(0).getWiki_link();
            txtContactPerson.setText(sl.get(0).getChiefFollower() + " : " + sl.get(0).getChiefFollowerContact());


            // TextView txtSaintName = (TextView) findViewById(R.id.txtSaintName);
            // txtSaintName.setText(saint.getSaintName());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date BirthDate = null;
            Date DeathDate = null;
            String strBirthDate = "";
            String strDeathDate = "";


            try {


                if (sl.get(0).getBirthDate() != null)
                    BirthDate = format.parse(sl.get(0).getBirthDate());
                if (sl.get(0).getDeathDate() != null)
                    DeathDate = format.parse(sl.get(0).getDeathDate());

                format = new SimpleDateFormat("MMM dd, yyyy");

                if (sl.get(0).getBirthDate() != null)
                    strBirthDate = format.format(BirthDate);

                if (sl.get(0).getDeathDate() != null)
                    strDeathDate = format.format(DeathDate);


            } catch (ParseException e) {
                e.printStackTrace();
                //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }


            if (strDeathDate != "")
                txtLifeSpan.setText(strBirthDate + " to " + strDeathDate);
            else
                txtLifeSpan.setText(strBirthDate);


        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SaintDetailViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabShare:
                btnShare.performClick();
                break;
            case R.id.fabWiki:
                // Do something
                Toast.makeText(this.getContext(), "wiki link crossed" + strWiki, Toast.LENGTH_SHORT).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strWiki));
                startActivity(browserIntent);
                break;
            case R.id.fabImageGallary:
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("bundleId", "1");
                bundle.putString("SaintId", intId.toString());
                bundle.putString("SaintName", strName);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
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
}
