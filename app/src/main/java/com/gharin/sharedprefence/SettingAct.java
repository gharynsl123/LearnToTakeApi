package com.gharin.sharedprefence;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingAct extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText etNama, etEmail, etPass, etConfirmPass;
    Button btConfirm;
    UserModel userModel;
    SaveShared saveShared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        etNama = findViewById(R.id.tiEditNama);
        etEmail = findViewById(R.id.tiEditEmail);
        etPass = findViewById(R.id.tiEditPassword);
        etConfirmPass = findViewById(R.id.tiEditConfirmPass);

        btConfirm = findViewById(R.id.btConfirm);
        btConfirm.setOnClickListener(this);

        saveShared = new SaveShared(this);
        setData();

        etEmail.setFocusable(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }




    private void setData() {
        userModel = saveShared.getUser();

        String nama = userModel.getName();
        String email = userModel.getEmail();
        String password = userModel.getPassword();

        etNama.setText(nama);
        etEmail.setText(email);
        etPass.setText(password);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btConfirm:
                confirmSetting();
                break;

        }
    }

    private void confirmSetting() {
        userModel = saveShared.getUser();
        String confirmPassword = etConfirmPass.getText().toString();
        String olPassword = userModel.getPassword();
        if (confirmPassword.equals(olPassword)) {
            userModel.setName(etNama.getText().toString());
            userModel.setPassword(etPass.getText().toString());
            saveShared.setUser(userModel);
            Toast.makeText(this, "data berhasil di ubah", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingAct.this, MainActivity.class));
        }

        else {
            Toast.makeText(this, "pasword beda guys", Toast.LENGTH_SHORT).show();
        }

    }
}
