package com.gharin.sharedprefence;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout tInputName, tImpunPass, tImputEmail, tIConfrimPass;
    AutoCompleteTextView etName, etEmail, etPass, etConfirmpass;
    RadioButton rbMale, rbFemale;
    RadioGroup rgGender;
    Button btRegister;
    CheckBox cbAgger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etConfirmpass = findViewById(R.id.etpassconfrim);
        etConfirmpass.setOnClickListener(this);

        btRegister = findViewById(R.id.btRegister);
        btRegister.setOnClickListener(this);

        etName = findViewById(R.id.etusername);
        etName.setOnClickListener(this);

        rgGender = findViewById(R.id.rggender);
        rgGender.setOnClickListener(this);

        rbFemale = findViewById(R.id.Female);
        rbFemale.setOnClickListener(this);

        cbAgger = findViewById(R.id.cbAggre);
        cbAgger.setOnClickListener(this);

        etEmail = findViewById(R.id.etEmail);
        etEmail.setOnClickListener(this);

        etPass = findViewById(R.id.etpass);
        etPass.setOnClickListener(this);

        rbMale = findViewById(R.id.male);
        rbMale.setOnClickListener(this);

        cbAgger = findViewById(R.id.cbAggre);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btRegister){
            String nama =etName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPass.getText().toString();
            String kelamin = String.valueOf(rgGender.getCheckedRadioButtonId());
            String agree = cbAgger.getText().toString();
            String confirm = etConfirmpass.getText().toString();

            if (TextUtils.isEmpty(nama)){
                etName.setError("nama tidak boleh kosong");
                return;
            }
            if (TextUtils.isEmpty(email)){
                etEmail.setError("Email tidak boleh kosong");
                return;
            }

            if (TextUtils.isEmpty(password)){
                etPass.setError("Password tidak boleh kosong");
                return;
            }

            if (TextUtils.isEmpty(confirm)){
                etConfirmpass.setError("confirm Password tidak boleh kosong");
                return;
            }

            if (!password.equals(confirm)){
                Toast.makeText(this, "password tidak sama", Toast.LENGTH_SHORT).show();
                return;
            }


            invalidUser(nama, email, password,kelamin,agree );


        }
    }

    private void invalidUser( String nama, String email, String password, String kelamin, String aggre){
        SaveShared userShared =new SaveShared(this);
        UserModel userModel = userShared.getUser();

        String saveEmail = userModel.getEmail();
        if (email.equals(saveEmail)){
            Toast.makeText(this, "Email sudah terdaftar,silahkan gunakan email lain", Toast.LENGTH_SHORT).show();
        }else {
            saveUser(nama, email, password, kelamin, aggre);
        }
    }

    private void saveUser(String nama, String email, String password, String kelamin, String aggre){
        SaveShared userShared = new SaveShared(this);
        UserModel userModel = new UserModel();
        userModel.setName(nama);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setKelamin(kelamin);
        userModel.setAgrew(aggre);

        userShared.setUser(userModel);
 //Toast.makeText(this, nama+email, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
        alert.setTitle(getString(R.string.succesRegister));
        alert.setTitle(getString(R.string.cautionSuscces));
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(RegisterActivity.this,LoginAct.class));
                finish();
            }
        });
        alert.show();
    }
}
