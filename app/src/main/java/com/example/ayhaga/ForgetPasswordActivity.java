package com.example.ayhaga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Button registerBtn;
    private Button forgetPassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        registerBtn = (Button) findViewById(R.id.regBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        forgetPassBtn = (Button) findViewById(R.id.loginBtn);
        forgetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this, SendLinkActivity.class);
                startActivity(intent);

            }
        });
    }
}
