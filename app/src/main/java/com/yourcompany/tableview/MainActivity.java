package com.yourcompany.tableview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

        String URL = "http://devfrontend.gscmaven.com/wmsweb/webapi/email/";


        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject student = response.getJSONObject(i);

                                // Get the current student (json object) data
                                String firstName = student.getString("idtableEmail");
                                String lastName = student.getString("tableEmailEmailAddress");

                                /** Create a TableRow dynamically **/
                                tableRow = new TableRow(MainActivity.this);
                                tableRow.setLayoutParams(new TableRow.LayoutParams(
                                        TableRow.LayoutParams.FILL_PARENT,
                                        TableRow.LayoutParams.WRAP_CONTENT));

                                /** Creating a TextView to add to the row **/
                                id = new TextView(MainActivity.this);
                                id.setText(firstName);
                                id.setTextColor(Color.GRAY);
                                id.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                                id.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                id.setPadding(16, 16, 16, 16);
                                tableRow.addView(id);  // Adding textView to tablerow.

                                /** Creating another textview **/
                                email = new TextView(MainActivity.this);
                                email.setText(lastName);
                                email.setTextColor(Color.GRAY);
                                email.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                email.setPadding(26, 16, 16, 16);
                                email.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                                tableRow.addView(email); // Adding textView to tablerow.

                                // Add the TableRow to the TableLayout
                                tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                                        TableRow.LayoutParams.FILL_PARENT,
                                        TableRow.LayoutParams.WRAP_CONTENT));

                                Log.d("volley", "error : " + firstName+lastName);

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.d("volley1", "error : " + error.getMessage());

                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}
