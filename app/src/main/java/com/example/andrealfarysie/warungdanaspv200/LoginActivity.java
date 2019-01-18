package com.example.andrealfarysie.warungdanaspv200;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.Helper.SharedPrefManager;
import com.example.andrealfarysie.warungdanaspv200.Response.DetailUserAgreement;
import com.example.andrealfarysie.warungdanaspv200.Response.Login;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText npm, password;
    TextView link;
    CheckBox eula;
    ProgressDialog loading;
    Context mContext;
    ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;
    Integer uId, time;
    String userAgent, strNpm, strPass;
    TextInputLayout inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //findview
        btnLogin = findViewById(R.id.btnLogin);
        npm = findViewById(R.id.loginNpm);
        password = findViewById(R.id.loginPassword);
        eula = findViewById(R.id.cbEula);
        link = findViewById(R.id.llink);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);
        inputPassword = findViewById(R.id.inputPassword);

    }

    @Override
    protected void onStart() {
        super.onStart();
        link.setText(Html.fromHtml("<a href=https://lmu2018.github.io/WarungDana/> Syarat dan Ketentuan"));
        link.setMovementMethod(LinkMovementMethod.getInstance());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(npm.getText())) {
                    npm.setError("Wajib Diisi !!!");
                } else if (TextUtils.isEmpty(password.getText())) {
                    password.setError("Wajib Diisi !!!");
                } else if (!eula.isChecked()) {
                    Toast toast = Toast.makeText(mContext, "Ceklist Syarat dan Ketentuan", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                    Calendar calendar = Calendar.getInstance();
                    time = calendar.get(Calendar.MINUTE);
                    strNpm = npm.getText().toString();
                    strPass = password.getText().toString();
                    requestlogin();
                }
            }
        });
        if (sharedPrefManager.getSPSudahLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Keperluan UI

    }

    private void requestlogin() {
        mApiService.loginrequest(strNpm,strPass).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()){
                    if (response.body().getApiStatus()==1 && response.body().getIdCmsPrivileges()==4){
                        loading.dismiss();
                        Integer userId = response.body().getId();
                        uId = userId;
                        eulaCheck();
                        userLogs();
                        String nama = response.body().getName();
                        String roleName = response.body().getCmsPrivilegesName();
                        Integer roleId = response.body().getIdCmsPrivileges();
                        String npm = response.body().getNpm();
                        Integer outletId = response.body().getIdMstBranch();
                        String outletName = response.body().getBranch();

                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_ID, userId);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAME, nama);
                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_ROLES, roleId);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NPM, npm);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_ROLES_NAMES, roleName);
                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_BRANCH_ID, outletId);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_OUTLET_NAME, outletName);
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                        startActivity(new Intent(mContext, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();

                    }else {
                        loading.dismiss();
                        Toast.makeText(mContext, "Username atau Password anda Salah !!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                loading.dismiss();
                Log.d("gagal",call.toString(),t);
                Toast.makeText(mContext, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eulaCheck() {
        mApiService.eulaCheck(uId).enqueue(new Callback<DetailUserAgreement>() {
            @Override
            public void onResponse(Call<DetailUserAgreement> call, Response<DetailUserAgreement> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() == 0) {

                        mApiService.eulaCreate("Y", uId).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(mContext, "Not Responding", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } else {
                    Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailUserAgreement> call, Throwable t) {
                Toast.makeText(mContext, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userLogs() {
        mApiService.userLogs(userAgent, "Login", uId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
