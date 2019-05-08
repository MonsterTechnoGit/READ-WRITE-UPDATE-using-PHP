package net.monstertechno.project.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.monstertechno.project.R;
import net.monstertechno.project.helper.RequestHandler;
import net.monstertechno.project.helper.SharedPrefManager;
import net.monstertechno.project.helper.URLs;
import net.monstertechno.project.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewId, textViewUsername, textViewEmail, textViewGender,fullname,phone,emphone;

    @Override
    protected void onStart() {
        super.onStart();
        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        final String email = user.getEmail();

        getUserDetails(email);
    }

    private void getUserDetails(final String email) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking...");
        progressDialog.show();

        class UpdateUser extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_USERDETAILS, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressDialog.dismiss();

                try {
                    //converting response to json object
                    JSONArray obj = new JSONArray(s);
                    JSONObject object = obj.getJSONObject(0);

                    textViewId.setText(String.valueOf(object.getInt("id")));
                    textViewUsername.setText(object.getString("username"));
                    textViewEmail.setText(object.getString("email"));
                    textViewGender.setText(object.getString("gender"));
                    fullname.setText(object.getString("fullname"));
                    phone.setText(object.getString("phone"));
                    emphone.setText(object.getString("emphone"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        UpdateUser updateUser = new UpdateUser();
        updateUser.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewGender = (TextView) findViewById(R.id.textViewGender);
        fullname = findViewById(R.id.textViewFullName);
        phone = findViewById(R.id.textViewFullPhone);
        emphone = findViewById(R.id.textViewEmPhone);


        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        final String email = user.getEmail();


        //setting the values to the textviews


        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });

        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UpdateProfile.class));
            }
        });

        findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getResult(email);
                //startActivity(new Intent(ProfileActivity.this,QuestionActivity.class));
            }
        });
    }

    private void getResult(final String email) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking...");
        progressDialog.show();

        class UpdateUser extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_CHECKING, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressDialog.dismiss();

                try {
                    //converting response to json object
                    JSONArray obj = new JSONArray(s);
                    JSONObject object = obj.getJSONObject(0);
                    String checking = object.getString("checking");
                    if (checking.equals("0")) {
                        startActivity(new Intent(ProfileActivity.this, QuestionActivity.class));
                    } else {
                        Intent intent = new Intent(ProfileActivity.this, ImageShowActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        UpdateUser updateUser = new UpdateUser();
        updateUser.execute();
    }
}