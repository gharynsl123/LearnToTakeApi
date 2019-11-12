package com.gharin.sharedprefence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gharin.sharedprefence.service.ApiClient;
import com.gharin.sharedprefence.service.BaseApiService;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    UserModel userModel;
    SaveShared saveShared;

    TextView tvFajr, tvSyuruk, tvDhuruh, tvAsar, tvMagrib, tvIsya, tvTanggal, tvLocation, tvTime, tvBadinternet;
    BaseApiService apiService;

    TextView txtfajar, txtSunrice, txtduhr, txtAsar, txtMagrib, txtIsya;

    ImageView ivBackground;

    MKLoader mkloader;
    ScrollView svMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        saveShared = new SaveShared(this);
        setData();

        tvFajr = findViewById(R.id.tvFajr);
        tvSyuruk = findViewById(R.id.tvSunrise);
        tvDhuruh = findViewById(R.id.tvDhuhr);
        tvAsar = findViewById(R.id.tvAsr);
        tvMagrib = findViewById(R.id.tvMaghrib);
        tvIsya = findViewById(R.id.tvIsha);
        tvTanggal = findViewById(R.id.tvCalendar);
        tvLocation = findViewById(R.id.tvLocation);
        tvTime = findViewById(R.id.tvTime);
        ivBackground = findViewById(R.id.ivBg);
        tvBadinternet = findViewById(R.id.tvBadConetction);
        svMain = findViewById(R.id.svMain);

        txtfajar = findViewById(R.id.fajr);
        txtSunrice = findViewById(R.id.sunrise);
        txtduhr = findViewById(R.id.dhuhr);
        txtAsar = findViewById(R.id.asr);
        txtMagrib = findViewById(R.id.maghrib);
        txtIsya = findViewById(R.id.isha);

        mkloader  = findViewById(R.id.Mkloader);

        apiService = ApiClient.getJadwal();
        getJadwalSholatMethiod("Bekasi");

        ImageView btChange = findViewById(R.id.btOk);
        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText etcityName = new EditText(MainActivity.this);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("City Name").setMessage("Masukkan nama kota yang di inginkan ").setView(etcityName);
                alert.setPositiveButton("Change City", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String city = etcityName.getText().toString();
                        getJadwalSholatMethiod(city);
                    }
                });
                alert.show();
            }
        });

        showDynamisTime();


    }



    private void showDynamisTime() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMM yyy");
        tvTanggal.setText(simpleDateFormat.format(new Date()));

        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);

        if (time >= 4 && time <= 5) {
            tvTime.setText(getString(R.string.fajr));
            ivBackground.setImageResource(R.drawable.pagi_bg);
            txtfajar.setTextColor(getResources().getColor(R.color.hijautua));
            tvFajr.setTextColor(getResources().getColor(R.color.hijautua));
        } else if (time >= 7 && time <= 11) {
            tvTime.setText(R.string.Morning);
            ivBackground.setImageResource(R.drawable.pagi);
            txtSunrice.setTextColor(getResources().getColor(R.color.hijautua));
            tvSyuruk.setTextColor(getResources().getColor(R.color.hijautua));
        } else if (time >= 12 && time <= 15) {
            tvTime.setText(R.string.Zuhur);
            ivBackground.setImageResource(R.drawable.siang);
            txtduhr.setTextColor(getResources().getColor(R.color.hijautua));
            tvDhuruh.setTextColor(getResources().getColor(R.color.hijautua));
        } else if (time >= 18 && time <= 19) {
            tvTime.setText(R.string.Mangrib);
            ivBackground.setImageResource(R.drawable.siang);
            txtMagrib.setTextColor(getResources().getColor(R.color.hijautua));
            tvMagrib.setTextColor(getResources().getColor(R.color.hijautua));
        } else if (time >= 19 && time <= 24) {
            tvTime.setText(R.string.isyaa);
            ivBackground.setImageResource(R.drawable.malam);
            txtIsya.setTextColor(getResources().getColor(R.color.hijautua));
            tvIsya.setTextColor(getResources().getColor(R.color.hijautua));
        }


    }



    private void getJadwalSholatMethiod(String namaKota) {
        Call<ResponseBody> requestApi = apiService.getJadwalShalat(namaKota);
        requestApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("OK")) {
                            mkloader.setVisibility(View.GONE);
                            svMain.setVisibility(View.VISIBLE);
                            String fajr = jsonObject.getJSONObject("data").getString("Fajr");
                            String syuruk = jsonObject.getJSONObject("data").getString("Sunrise");
                            String zuhur = jsonObject.getJSONObject("data").getString("Dhuhr");
                            String asar = jsonObject.getJSONObject("data").getString("Asr");
                            String magrib = jsonObject.getJSONObject("data").getString("Maghrib");
                            String isya = jsonObject.getJSONObject("data").getString("Isha");
                            String adrres = jsonObject.getJSONObject("location").getString("address");

                            tvFajr.setText(fajr + " AM");
                            tvSyuruk.setText(syuruk + " AM");
                            tvDhuruh.setText(zuhur + " AM");
                            tvAsar.setText(asar + " AM");
                            tvMagrib.setText(magrib + " AM");
                            tvIsya.setText(isya + " AM");
                            tvLocation.setText(adrres);

//                            set data waktu from sever

                                SimpleDateFormat DateFormat = new SimpleDateFormat("hh:mm");
                            try {
                                Date date = DateFormat.parse(fajr);


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "respone gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                tvBadinternet.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Keluar");
        builder.setMessage("Apakah anda ingin keluar??");
        builder.setPositiveButton("Ya !!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("Tidak !!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @SuppressLint("SetTextI18n")
    private void setData() {
        userModel = saveShared.getUser();

        String nama = userModel.getName();

        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);

        if (time >= 4 && time <= 5) {
            setTitle("Subuh, " + nama + "");
        } else if (time >= 5 && time <= 9) {
            setTitle("pagi, " + nama + "");
        } else if (time >= 12 && time <= 15) {
            setTitle("siang, " + nama + "");
        } else if (time >= 18 && time <= 19) {
            setTitle("Magrib, " + nama + "");
        } else if (time >= 19 && time <= 24) {
            setTitle("Malam, " + nama + "");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                userModel = saveShared.getUser();
                userModel.setStatusLogin(false);
                saveShared.setUser(userModel);
                startActivity(new Intent(MainActivity.this, LoginAct.class));
                finish();
                break;

            case R.id.setting:
                startActivity(new Intent(MainActivity.this, SettingAct.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
