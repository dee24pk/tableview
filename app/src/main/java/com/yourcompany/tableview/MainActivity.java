package com.yourcompany.tableview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    EditText edit_email;
    final Context context = this;

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

                                /** Creating a TextView to add to the row for user_id **/
                                id = new TextView(MainActivity.this);
                                id.setText(firstName);
                                id.setTextColor(Color.GRAY);
                                id.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                                id.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                id.setPadding(10, 18, 18, 18);
                                tableRow.addView(id);  // Adding textView to tablerow.

                                /** Creating another textview for email address **/
                                email = new TextView(MainActivity.this);
                                email.setText(lastName);
                                email.setTextColor(Color.GRAY);
                                email.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                email.setPadding(14, 18, 18, 18);
                                email.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                                tableRow.addView(email); // Adding textView to tablerow.

                                final Button button = new Button(MainActivity.this);
                                button.setBackgroundResource(R.drawable.delete);
                                button.setWidth(1);
                                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final TableRow parent = (TableRow) v.getParent();

                                        TextView items = (TextView) parent.getChildAt(0);
                                        TextView items2 = (TextView) parent.getChildAt(1);

                                        String myText = items.getText().toString();
                                        String myText2 = items2.getText().toString();
                                        tableLayout.removeView(parent);
                                        Log.e("value",myText+myText2 );

                                    }
                                });

                                tableRow.addView(button);

                                final Button button1 = new Button(MainActivity.this);
                                button1.setBackgroundResource(R.drawable.edit);
                                button1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                button1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final TableRow parent = (TableRow) v.getParent();

                                        TextView items = (TextView) parent.getChildAt(0);
                                        TextView items2 = (TextView) parent.getChildAt(1);

                                        String user_id = items.getText().toString();
                                        String user_email = items2.getText().toString();
                                        tableLayout.removeView(parent);
                                       // Log.e("value",myText+myText2 );

                                    }
                                });

                                tableRow.addView(button1);

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

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.add_user);
            final Button submit = (Button) dialog.findViewById(R.id.save);
            ImageView close = (ImageView) dialog.findViewById(R.id.imageView_close);
            edit_email = (EditText) dialog.findViewById(R.id.email);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add_user();
                }
            });
            dialog.show();
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

        }
        return super.onOptionsItemSelected(item);
    }

    private void add_user() {

        final String email = this.edit_email.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edit_email.setError("Enter a valid email");
            edit_email.requestFocus();
            return;
        }


    }

}
