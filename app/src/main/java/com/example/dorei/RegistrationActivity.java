package com.example.dorei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mUsername,mEmail,mPassword,mRepeatpassword,mHouseNo,mStreetNo,mStreetName,mCity,mState,mPinCode;
    private Button registerBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUsername = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mRepeatpassword = findViewById(R.id.repeat_password);
        mHouseNo = findViewById(R.id.house_no);
        mStreetNo = findViewById(R.id.street_no);
        mStreetName = findViewById(R.id.street_name);
        mCity = findViewById(R.id.city_name);
        mState = findViewById(R.id.state_name);
        mPinCode = findViewById(R.id.postal_code);

        registerBtn = findViewById(R.id.register);
        loginBtn = findViewById(R.id.login);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkDataEntered())
                {
                    String username = mUsername.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();
                    String email = mEmail.getText().toString().trim();
                    String house_no = mHouseNo.getText().toString().trim();
                    String street_no = mStreetNo.getText().toString().trim();
                    String street_name = mStreetName.getText().toString().trim();
                    String city = mCity.getText().toString().trim();
                    String state = mState.getText().toString().trim();
                    String  pin_code = mPinCode.getText().toString().trim();

                    UserModel userModel = new UserModel(-1,username,password,email,house_no,street_no,street_name,city,state,pin_code);
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RegistrationActivity.this);
                    boolean success = dataBaseHelper.addUser(userModel);
                    if(success)
                    {
                        Toast.makeText(RegistrationActivity.this, "Registered!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                    }

                    else Toast.makeText(RegistrationActivity.this, "Couldn't register :(",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isConfirmedPassword(EditText e1, EditText e2){
        String pwd = e1.getText().toString().trim();
        String pwd2 = e2.getText().toString().trim();

        return !pwd.equals(pwd2);
    }

    boolean checkDataEntered() {
        if (isEmpty(mUsername)) {
            mUsername.setError("Your name is required");
            return false;
        }
        else if (isEmail(mEmail) == false) {
            mEmail.setError("Enter valid email!");
        }
        else if (isEmpty(mPassword) || mPassword.getText().toString().trim().length()< 4) {
            mPassword.setError("Minimum 4 characters required!");
            return false;
        }
        else if(isConfirmedPassword(mPassword,mRepeatpassword)){
            mRepeatpassword.setError("Password not matching");
            return false;
        }
       else if (isEmpty(mHouseNo)) {
            mHouseNo.setError("Required");
            return false;
        }
        else if (isEmpty(mStreetNo)) {
            mStreetNo.setError("Required");
            return false;
        }
        else if (isEmpty(mStreetName)) {
            mStreetName.setError("Required");
            return false;
        }
        else if (isEmpty(mCity)) {
            mCity.setError("Required");
            return false;
        }
        else if (isEmpty(mState)) {
            mState.setError("Required");
            return false;
        }
        else if (isEmpty(mPinCode)) {
            mPinCode.setError("Required");
            return false;
        }
        return true;
    }
}


