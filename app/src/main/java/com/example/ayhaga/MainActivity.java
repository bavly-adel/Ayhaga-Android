package com.example.ayhaga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button forgetPassBtn;
    private Button registerBtn;
    private Button login;
    private Button skipLogin;
    private EditText email;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=(EditText) findViewById(R.id.emailEtext) ;
        password=(EditText) findViewById(R.id.passwordEditTxt) ;

        forgetPassBtn = (Button) findViewById(R.id.forgetPassBtn);
        forgetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);

            }
        });

        skipLogin = (Button) findViewById(R.id.skipLoginBtn);
        skipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
                startActivity(intent);

            }
        });

        registerBtn = (Button) findViewById(R.id.regBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        login = (Button) findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailValue=email.getText().toString();
                String passwordValue=password.getText().toString();
                if (emailValue.equals("") || passwordValue.equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "you should fill email and password", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                    login(emailValue,passwordValue);


            }
        });

        findViewById(R.id.faceLoginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent);
            }
        });
    }



    public void login(String email,String password){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("email",email);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url ="https://dashboard.ayhaga.app/api/auth/login";
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST,url,jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            addTokenToSharedPreference(response.getString("access_token"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("loginRes", "onResponse: "+ response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast toast = Toast.makeText(getApplicationContext(), "on error "+error.toString(), Toast.LENGTH_SHORT);
                        toast.show();
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        queue.add(loginRequest);
    }


    public void addTokenToSharedPreference (String token)
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("auth_token", token);
        editor.apply();   // instead of commit
       // Log.d("SP", pref.getString("auth_token", "NoToken") + "");
    }
}
