package net.monstertechno.project.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.monstertechno.project.MainActivity;
import net.monstertechno.project.R;
import net.monstertechno.project.helper.RequestHandler;
import net.monstertechno.project.helper.SharedPrefManager;
import net.monstertechno.project.helper.URLs;
import net.monstertechno.project.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateProfile extends AppCompatActivity {

    EditText fullname,phonenumber,emphone;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        progressDialog = new ProgressDialog(this);
        fullname = findViewById(R.id.editTextName);
        phonenumber = findViewById(R.id.editTextPhone);
        emphone = findViewById(R.id.editTextEmPhone);

        User user = SharedPrefManager.getInstance(this).getUser();

        final String email = user.getEmail();


        findViewById(R.id.complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateData(email);

            }
        });
    }

    private void updateData(final String email) {

        progressDialog.setMessage("Updating...");
        progressDialog.show();
        final String name = fullname.getText().toString().trim();
        final String phone = phonenumber.getText().toString().trim();
        final String emphonenumber = emphone.getText().toString().trim();

        class UpdateUser extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("fullname", name);
                params.put("phone", phone);
                params.put("emphone", emphonenumber);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_UPDATE, params);
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
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("username"),
                                userJson.getString("email"),
                                userJson.getString("gender")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        startActivity(new Intent(UpdateProfile.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
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
