package com.yourcompany.tableview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TableLayout tableLayout;
    TableRow tableRow;
    TextView id, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = (TableLayout) findViewById(R.id.table_layout);

        GET_Table();
    }

    private void GET_Table() {

        String URL = "https://devfrontend.gscmaven.com/wmsweb/webapi/email/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("MyLog", error.getMessage()+ "");
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    public void parseData(String response) {

        try {

            JSONArray jsonarray = new JSONArray();
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String name = jsonobject.getString("idtableEmail");
                String url = jsonobject.getString("tableEmailEmailAddress");


                Log.d("MyLog", name+url+ "");

                    }




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
