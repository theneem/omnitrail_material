package com.thenneem.omnitrail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.thenneem.omnitrail.response.SocialLoginResponse;
import com.thenneem.omnitrail.retrofit.ApiInterface;
import com.thenneem.omnitrail.retrofit.ApiClient;
import com.thenneem.omnitrail.utils.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private LoginButton loginButton;
    PreferenceManager preferenceManager;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.e("DBERROR", "before calling facebook button handler" );

        loginButton = findViewById(R.id.facebook_login);
        preferenceManager = new PreferenceManager(LoginActivity.this);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));


        checkLoginStatus();

        Log.e("DBERROR", "after check login" );

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.e("DBERROR", "on success of faceboo" );

            }

            @Override
            public void onCancel() {

                Log.e("DBERROR", "canceld facebook" );

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("DBERROR", "" + error);
                Toast.makeText(LoginActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("DBERROR", "on activii result" );

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


    }


    AccessTokenTracker tokenTracker = new AccessTokenTracker() {


        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            Log.e("DBERROR", "on activii result" );
            if (currentAccessToken == null) {
				/*txtName.setText("");
				txtEmail.setText("");
				circleImageView.setImageResource(0);
				Toast.makeText(MainActivity.this, "User Logged out", Toast.LENGTH_LONG).show();*/
            } else
                loadUserProfile(currentAccessToken);
        }
    };



    private void checkLoginStatus() {
        Log.e("DBERROR", "inside check login" );

        if (AccessToken.getCurrentAccessToken() != null) {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }


    private void loadUserProfile(AccessToken newAccessToken) {
        Log.e("DBERROR", "inside load user profile " );

        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                    Log.e("DBERROR", "string url" + image_url );

                    // FB
                    SocialLogin("2", first_name + "" + last_name, email, id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    public void SocialLogin(String isSocial, String username, String email, String userId) {

        final ApiInterface api = ApiClient.getApiService();
        Call<SocialLoginResponse> call = api.getSocialLogin(isSocial, username, email, userId);

        call.enqueue(new Callback<SocialLoginResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<SocialLoginResponse> call, @NonNull Response<SocialLoginResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().isSuccess()) {

                            preferenceManager.setuserName(response.body().getSocialLogin().getUsername());
                            preferenceManager.setemailAddress(response.body().getSocialLogin().getEmail());
                            preferenceManager.setUserId(response.body().getSocialLogin().getUserId());
                            preferenceManager.setLoginSession(true);

                            Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, FullscreenActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "response body() is null", Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 401) {
                    // Handle unauthorized
                } else if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "Not Found (From Serverside Error)", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(LoginActivity.this, "Server Broken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SocialLoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "onFailer:  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}