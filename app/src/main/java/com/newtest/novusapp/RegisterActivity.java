package com.newtest.novusapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.newtest.novusapp.util.AppConfig;
import com.newtest.novusapp.util.AppController;
import com.newtest.novusapp.util.SQLiteHandler;
import com.newtest.novusapp.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ROOT-PC on 26.09.2015.
 */
public class RegisterActivity extends Activity {

    int DIALOG_DATE = 1;
    int birthYear = 2011;
    int birthMonth = 01;
    int birthDay = 03;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputName;
    private EditText inputPatrName;
    private EditText inputLastName;

    private EditText inputEmail;
    private EditText inputPassword;
    private TextView birthdayView;
    private Button birthdayBtn;
    private RadioGroup genderGroup;
    private EditText phoneText;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputName = (EditText) findViewById(R.id.nameEdit);
        inputPatrName = (EditText) findViewById(R.id.patrNameEdit);
        inputLastName = (EditText) findViewById(R.id.lastNameEdit);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        birthdayView = (TextView) findViewById(R.id.birthdayText);
        birthdayBtn = (Button) findViewById(R.id.birthdayBtn);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        phoneText = (EditText) findViewById(R.id.phone);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        birthdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_DATE);

            }
        });

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputName.getText().toString().trim();
                String patrName = inputPatrName.getText().toString().trim();
                String lastName = inputLastName.getText().toString().trim();
                String birthDate = birthdayView.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String gender = new String();
                int genderId = genderGroup.getCheckedRadioButtonId();
                    if (genderId == R.id.maleButton){
                         gender = "М";
                    }
                else if (genderId == R.id.femaleButton){
                        gender = "Ж";
                    }

                String phone = phoneText.getText().toString().trim();

                if (!name.isEmpty() &&  !lastName.isEmpty() && !birthDate.isEmpty()
                        && !gender.isEmpty() && !phone.isEmpty() &&!email.isEmpty() && !password.isEmpty()) {
                    registerUser(name,patrName,lastName,gender, birthDate,phone,email, password);
                } else {
                    Toast.makeText(getApplicationContext(),R.string.no_reg_data,
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, birthYear, birthMonth, birthDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            birthYear = year;
            birthMonth = monthOfYear+1;
            birthDay = dayOfMonth;
            birthdayView.setText(birthYear + "/" + birthMonth + "/" + birthDay);
        }
    };

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String patrName,final String lastName, final String gender, final String birthDate,final String phone, final String email,
                              final String password) {
        // Tag used to cancel the request
//        String tag_string_req = "req_register";

        pDialog.setMessage(" ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String email = user.getString("email");
                        String phone = user.getString("phone");
                        String created_at = user
                                .getString("created_at");
//                        String firstName = user.getString("first_name");
//                        String patrName = user.getString("patr_name");
//                        String lastName = user.getString("last_name");
//                        String gender = user.getString("gender");
//                        String bday = user.getString("birthday");

                        // Inserting row in users table
                        db.addUser( email, uid, created_at);

                        Toast.makeText(getApplicationContext(), "!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();


                params.put("first_name", name);
                params.put("patr_name",patrName);
                params.put("last_name",lastName);
                params.put("gender",gender);
                params.put("birthday",birthDate);
                params.put("phone",phone);
                params.put("email", email);
                //params.put("");
                params.put("password", password);

                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
