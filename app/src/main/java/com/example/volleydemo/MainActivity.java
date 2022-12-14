package com.example.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.volleydemo.model.ImageDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<ImageDetails> list = new ArrayList<ImageDetails>();

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

            }
        });
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
                list.add(imageDetails);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //create adapter for listview
            listView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return list.size();
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
                    ImageDetails data = list.get(position);
                    ImageView imageView = view.findViewById(R.id.image_view);
                    TextView name = view.findViewById(R.id.tv_text);


                    return null;
                }
            });


        }
    }
}