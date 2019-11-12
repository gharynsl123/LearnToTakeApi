package com.gharin.sharedprefence;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginAct extends AppCompatActivity implements View.OnClickListener {

        AutoCompleteTextView etusername, etpassword;
        TextView tvregister, tvforgot;
        Button btlogin;
        SaveShared saveShared;
        UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etusername = findViewById(R.id.etusername);
        etusername.setOnClickListener(this);

        etpassword = findViewById(R.id.etpass);
        etpassword.setOnClickListener(this);

        tvregister =findViewById(R.id.tvregister);
        tvregister.setOnClickListener(this);

        tvforgot = findViewById(R.id.forgotpass);
        tvforgot.setOnClickListener(this);

        btlogin = findViewById(R.id.btlogin);
        btlogin.setOnClickListener(this);

        saveShared = new SaveShared(this);
        checkLogin();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btlogin:

                String userName = etusername.getText().toString();
                String password = etpassword.getText().toString();

                if (TextUtils.isEmpty(userName)) {
                    etusername.setError("User name tidak boleh kosong");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etpassword.setError("Password tidak boleh kosong");
                    return;
                }
                if (!isValidEmail(userName)) {
                    Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                validateLogin(userName, password);
                //Toast.makeText(this, "Email tidak terdaftar Silahkan Register Terlebih dahulu", Toast.LENGTH_SHORT).show();

                break;
            case R.id.tvregister:

                startActivity(new Intent(this, RegisterActivity.class));

                break;
            case R.id.forgotpass:
                validateForgot();
                //Toast.makeText(this, "helo forgot", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void validateForgot(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        userModel = saveShared.getUser();
        String showPasword = userModel.getPassword();
        alert.setTitle("password kamu adalah :"+showPasword);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void validateLogin(String email, String password){
        userModel = saveShared.getUser();
        String saveEmail = userModel.getEmail();
        String savepassword = userModel.getPassword();
        if (email.equals(saveEmail)&& password.equals(savepassword)){
            startActivity(new Intent(LoginAct.this,MainActivity.class));
            userModel.setStatusLogin(true);
            saveShared.setUser(userModel);
            finish();
        }else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(R.string.acountNotRegist));
            alert.setTitle(getString(R.string.pleaseRegister));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(LoginAct.this,RegisterActivity.class));
                    finish();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        }
    }

    private void checkLogin () {
        userModel = saveShared.getUser();
        boolean statusLogin = userModel.isStatusLogin();
        if (statusLogin == true) {
            startActivity(new Intent(LoginAct.this, MainActivity.class));
            finish();
        }
    }

    private boolean isValidEmail(CharSequence email){
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
