package com.example.dorei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginManagerActivity extends AppCompatActivity {

    private EditText mEmail,mPassword;
    private Button registerBtn,loginBtn,managerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        registerBtn = findViewById(R.id.btn_signup);
        loginBtn = findViewById(R.id.btn_login);
        managerBtn = findViewById(R.id.btn_manager);


        registerBtn.setVisibility(View.GONE);
        managerBtn.setText("Not a manager?Click here");
        managerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginManagerActivity.this,LoginActivity.class));
            }
        });

        loginBtn.setText("LOGIN AS A MANAGER");
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                DataBaseHelper dataBaseHelper = new DataBaseHelper(LoginManagerActivity.this);
                boolean isManager =dataBaseHelper.getManager(email,password);
                if(!isManager)
                {
                    Toast.makeText(LoginManagerActivity.this,"Incorrect Email or Password!", Toast.LENGTH_LONG).show();
                    mEmail.setText("");
                    mPassword.setText("");
                }
                else
                {
                    startActivity(new Intent(LoginManagerActivity.this,ManagerActivity.class));
                }




            }
        });
    }
}
