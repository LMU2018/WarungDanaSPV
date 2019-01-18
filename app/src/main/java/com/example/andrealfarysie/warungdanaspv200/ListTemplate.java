package com.example.andrealfarysie.warungdanaspv200;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.Adapter.ListActivityTemplate2Adapter;
import com.example.andrealfarysie.warungdanaspv200.Adapter.ListActivityTemplateAdapter;
import com.example.andrealfarysie.warungdanaspv200.Helper.SharedPrefManager;
import com.example.andrealfarysie.warungdanaspv200.Response.ListActivityTemplate;
import com.example.andrealfarysie.warungdanaspv200.Response.ListOutlet;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListActivityTemplate;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListOutlet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTemplate extends AppCompatActivity {
    Spinner spinner;
    RecyclerView recyclerView;
    Context context;
    SharedPrefManager sharedPrefManager;
    ApiEndPoint mApiService;
    Integer idOutlet;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_template);
        context = this;
        sharedPrefManager = new SharedPrefManager(context);
        mApiService = UtilsApi.getAPIService();
        spinner = findViewById(R.id.spinner);
        progress = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        initSpinner();
    }

    private void initSpinner() {
        mApiService.listOutlet(sharedPrefManager.getSpBranchId()).enqueue(new Callback<RespListOutlet>() {
            @Override
            public void onResponse(Call<RespListOutlet> call, Response<RespListOutlet> response) {
                if (response.isSuccessful()) {
                    final List<ListOutlet> list = response.body().getData();
                    if (list!=null||!list.isEmpty()){
                        progress.setVisibility(View.GONE);
                    }else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(context,"Data template outlet ini kosong",Toast.LENGTH_LONG).show();
                    }
                    final List<String> list1 = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        list1.add(list.get(i).getOutletName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item_2, list1);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            idOutlet = list.get(position).getId();


                            getListTemplate();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<RespListOutlet> call, Throwable t) {

            }
        });
    }

    private void getListTemplate() {
        mApiService.activityTemplateList(sharedPrefManager.getSpId(), idOutlet).enqueue(new Callback<RespListActivityTemplate>() {
            @Override
            public void onResponse(Call<RespListActivityTemplate> call, Response<RespListActivityTemplate> response) {
                if (response.isSuccessful()) {
                    List<ListActivityTemplate> list = response.body().getData();
                    recyclerView.setAdapter(new ListActivityTemplate2Adapter(context, list));
                }
            }

            @Override
            public void onFailure(Call<RespListActivityTemplate> call, Throwable t) {

            }
        });
    }
}
