package com.example.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.volleydemo.model.ImageDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<ImageDetails> arrayList = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        //Url
        String url = "https://picsum.photos/v2/list";

        //Progress dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        //message for progress dialog
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null){

                    //when response is not null (response contains value)
                    //dismiss dialog
                    progressDialog.dismiss();

                    try {

                        //Initialize response json array
                        JSONArray jsonArray = new JSONArray(response);
                        //parseArray
                        parseArray(jsonArray);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //initialize requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //add request
        requestQueue.add(stringRequest);
    }

    private void parseArray(JSONArray jsonArray) {
        //use for loop
        for (int i = 0; i < jsonArray.length(); i++){

            try {
                //json object
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //imageDetails
                ImageDetails imageDetails = new ImageDetails();
                imageDetails.setName(jsonObject.getString("author"));
                imageDetails.setImage(jsonObject.getString("download url"));  //download url is   { String url = "https://picsum.photos/v2/list" }
                arrayList.add(imageDetails);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //create adapter for listview
            listView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return arrayList.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = getLayoutInflater().inflate(R.layout.item_main,null);
                    //intialize model class
                    ImageDetails data = arrayList.get(position);
                    ImageView imageView = view.findViewById(R.id.image_view);
                    TextView name = view.findViewById(R.id.tv_text);

                    //set image on imageview
                    Glide.with(context)
                            .load(data.getImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                    //set name on textview
                    name.setText(data.getName());


                    return view;
                }
            });


        }
    }
}