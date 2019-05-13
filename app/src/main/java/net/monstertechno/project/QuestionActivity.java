package net.monstertechno.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import net.monstertechno.ImageShowActivity;
import net.monstertechno.project.adapter.DataAdapter;
import net.monstertechno.project.model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Data> dataList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        mList = findViewById(R.id.main_list);

        dataList = new ArrayList<>();
        adapter = new DataAdapter(getApplicationContext(),dataList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        User user = SharedPrefManager.getInstance(this).getUser();

        final String email = user.getEmail();

        getData();

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(URLs.yes) {
                    saveData(email);
                }else {

                    Toast.makeText(getApplicationContext(), "Congratulation you don't have any problem", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(QuestionActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    ProgressDialog progressDialog;
    private void saveData(final String email) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        class UpdateUser extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("checking", "true");


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
                //starting the profile activity
                startActivity(new Intent(QuestionActivity.this, ImageShowActivity.class));
                finish();
            }
        }

        UpdateUser updateUser = new UpdateUser();
        updateUser.execute();

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Loading...");


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.URL_QUESTIONS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("JsonData", String.valueOf(response));

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Data data = new Data();
                        data.setQuestions(jsonObject.getString("question"));
                        data.setAnswer_yes(jsonObject.getString("answer_yes"));
                        data.setAnswer_no(jsonObject.getString("answer_no"));
                        data.setAnswer_one(jsonObject.getString("answer_one"));
                        data.setAnswer_two(jsonObject.getString("answer_two"));
                        data.setAnswer_three(jsonObject.getString("answer_three"));
                        data.setAnswer_four(jsonObject.getString("answer_four"));

                        dataList.add(data);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
