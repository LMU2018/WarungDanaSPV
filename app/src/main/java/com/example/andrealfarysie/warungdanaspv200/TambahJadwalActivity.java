package com.example.andrealfarysie.warungdanaspv200;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.Helper.SharedPrefManager;
import com.example.andrealfarysie.warungdanaspv200.Response.ListCfa;
import com.example.andrealfarysie.warungdanaspv200.Response.ListOutlet;
import com.example.andrealfarysie.warungdanaspv200.Response.ListType;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListCfa;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListOutlet;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListType;
import com.example.andrealfarysie.warungdanaspv200.Response.RespPost;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahJadwalActivity extends AppCompatActivity {
    Spinner tipe, outlet;
    EditText lokasi, mulai, selesai, catatan, cfa;
    Button pilihTemplate, tambahSchedule;
    ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;
    ProgressDialog loading;
    Context context;
    Integer idOutlet, idTipe, idActivity, idSchedule;
    Integer[] idUser;
    Calendar calendar;
    String tglMulai, jamMulai, tglSelesai, jamSelesai, gagu, note, strTipe,strOutlet;
    List<ListCfa> listCfas;
    List<String> mantap;

    boolean[] checkedItems;
    String[] list, kombo, maut;
    Integer[] idcfa, item;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal);
        context = this;
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);
        tipe = findViewById(R.id.tipe);
        outlet = findViewById(R.id.outlet);
        lokasi = findViewById(R.id.lokasi);
        mulai = findViewById(R.id.mulai);
        selesai = findViewById(R.id.selesai);
        catatan = findViewById(R.id.catatan);
        pilihTemplate = findViewById(R.id.pilihTemplate);
        cfa = findViewById(R.id.cfa);
        tambahSchedule = findViewById(R.id.tambahSchedule);
        calendar = Calendar.getInstance();

        initTipe();
        initOutlet();
    }

    public class Sort implements Comparator<ListCfa> {
        public int compare(ListCfa left, ListCfa right) {
            return left.getName().compareTo(right.getName());
        }
    }

    private void getListUser() {
        mApiService.listCFA(3, idOutlet).enqueue(new Callback<RespListCfa>() {
            @Override
            public void onResponse(Call<RespListCfa> call, Response<RespListCfa> response) {
                if (response.isSuccessful()) {
                    listCfas = response.body().getData();
                    Collections.sort(listCfas, new Sort());
                    List<Integer> listId = new ArrayList<>();
                    List<String> listnama = new ArrayList<>();
                    for (int i = 0; i < listCfas.size(); i++) {
                        listnama.add(listCfas.get(i).getName());
                        listId.add(listCfas.get(i).getId());
                        item = listId.toArray(new Integer[0]);
                        kombo = listnama.toArray(new String[0]);
                        checkedItems = new boolean[item.length];
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListCfa> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        pilihTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ListTemplate.class));
                finish();
            }
        });
        idActivity = getIntent().getIntExtra("idJadwal", 0);
        strTipe = getIntent().getStringExtra("type");
        strOutlet = getIntent().getStringExtra("outlet");
        lokasi.setText(getIntent().getStringExtra("lokasi"));

        mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();


            }

            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    final String tanggal = dateFormat.format(calendar.getTime());
                    tglMulai = dateFormat1.format(calendar.getTime());
                    new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            String jam = new SimpleDateFormat("HH:mm", Locale.US).format(calendar.getTime());
                            mulai.setText(tanggal + " " + jam);
                            jamMulai = new SimpleDateFormat("HH:mm:ss", Locale.US).format(calendar.getTime());
                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                }
            };
        });
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        String jam = new SimpleDateFormat("HH:mm", Locale.US).format(calendar.getTime());
                        selesai.setText(jam);
                        jamSelesai = new SimpleDateFormat("HH:mm:ss", Locale.US).format(calendar.getTime());
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }

        });

        cfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihCFA();
            }
        });

        tambahSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note = null;
                if (catatan.getText().toString() != null) {
                    note = catatan.getText().toString();
                }
                if (TextUtils.isEmpty(lokasi.getText())) {
                    lokasi.setError("Wajib Diisi !!!");
                } else if (TextUtils.isEmpty(mulai.getText())) {
                    mulai.setError("Wajib Diisi !!!");
                } else if (TextUtils.isEmpty(selesai.getText())) {
                    selesai.setError("Wajib Diisi !!!");
                } else if (TextUtils.isEmpty(cfa.getText())) {
                    cfa.setError("Wajib Diisi !!!");
                } else {
                    loading = ProgressDialog.show(context, null, "Harap Tunggu...", true, false);
                    tambahAktivitas();
                }

            }
        });
    }

    private void tambahAktivitas() {
        if (idActivity == 0) {
            mApiService.addActivityTemplate(idOutlet, idTipe, sharedPrefManager.getSpId(), null, null, lokasi.getText().toString())
                    .enqueue(new Callback<RespPost>() {
                        @Override
                        public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                            if (response.isSuccessful()) {
                                idActivity = response.body().getId();
                                mApiService.addActivitySchedule(idActivity, tglMulai, tglMulai, jamMulai, jamSelesai, sharedPrefManager.getSpId(), note)
                                        .enqueue(new Callback<RespPost>() {
                                            @Override
                                            public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                                                if (response.isSuccessful()) {
                                                    idSchedule = response.body().getId();
                                                    for (int i = 0; i < idcfa.length; i++) {
                                                        mApiService.activityUserCreate(idSchedule, idcfa[i]).enqueue(new Callback<ResponseBody>() {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                            }

                                                            @Override
                                                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                            }
                                                        });
                                                    }
                                                    finish();
                                                    loading.dismiss();
                                                    Toast.makeText(context, "Berhasil menambah aktivitas !", Toast.LENGTH_LONG).show();

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<RespPost> call, Throwable t) {
                                                loading.dismiss();
                                                Toast.makeText(context, "Gagal menambah aktivitas !", Toast.LENGTH_LONG).show();

                                            }
                                        });
                            }
                        }

                        @Override
                        public void onFailure(Call<RespPost> call, Throwable t) {

                        }
                    });


        } else {
            mApiService.addActivitySchedule(idActivity, tglMulai, tglMulai, jamMulai, jamSelesai, sharedPrefManager.getSpId(), note)
                    .enqueue(new Callback<RespPost>() {
                        @Override
                        public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                            if (response.isSuccessful()) {
                                idSchedule = response.body().getId();
                                for (int i = 0; i < idcfa.length; i++) {
                                    mApiService.activityUserCreate(idSchedule, idcfa[i]).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });
                                }
                                finish();
                                loading.dismiss();
                                Toast.makeText(context, "Berhasil menambah aktivitas !", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<RespPost> call, Throwable t) {
                            loading.dismiss();
                            Toast.makeText(context, "Gagal menambah aktivitas !", Toast.LENGTH_LONG).show();

                        }
                    });


        }
    }

    private void pilihCFA() {


        AlertDialog.Builder builder = new AlertDialog.Builder(TambahJadwalActivity.this);
        builder.setTitle("Pilih CFA");
        builder.setMultiChoiceItems(kombo, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    mUserItems.add(which);
                } else {
                    mUserItems.remove((Integer.valueOf(which)));
                }
            }
        });
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String gaga = "";
                gagu = "";
                mantap = new ArrayList<String>();

                for (int i = 0; i < mUserItems.size(); i++) {
                    gaga = gaga + kombo[mUserItems.get(i)];
                    gagu = gagu + item[mUserItems.get(i)];
                    if (i != mUserItems.size() - 1) {
                        gaga = gaga + ",   ";
                        gagu = gagu + ",";

                    }
                }
                cfa.setText(gaga);
                list = gagu.split(",");
                idcfa = new Integer[list.length];
                for (int i = 0; i < idcfa.length; i++) {
                    idcfa[i] = Integer.parseInt(list[i]);// Parsing from string to int
                    System.out.println(idcfa[i]);
                }

            }
        });
        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        AlertDialog mDialog = builder.create();
        mDialog.show();

    }

    private void initOutlet() {
        mApiService.listOutlet(sharedPrefManager.getSpBranchId()).enqueue(new Callback<RespListOutlet>() {
            @Override
            public void onResponse(Call<RespListOutlet> call, Response<RespListOutlet> response) {
                if (response.isSuccessful()) {
                    final List<ListOutlet> list = response.body().getData();
                    final List<String> list1 = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        list1.add(list.get(i).getOutletName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, list1);
                    outlet.setAdapter(adapter);
                    int intSource = adapter.getPosition(strOutlet);
                    outlet.setSelection(intSource);
                    outlet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            idOutlet = list.get(position).getId();
                            if (listCfas != null) {
                                listCfas.clear();
                                cfa.setText("");
                                if (item != null) {
                                    item = null;
                                }
                                if (kombo != null) {
                                    kombo = null;
                                }
                                if (checkedItems != null) {
                                    checkedItems = null;
                                }

                                if (mUserItems != null) {
                                    mUserItems.clear();
                                }
                            }

                            getListUser();
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

    private void initTipe() {
        mApiService.activityType().enqueue(new Callback<RespListType>() {
            @Override
            public void onResponse(Call<RespListType> call, Response<RespListType> response) {
                if (response.isSuccessful()) {
                    final List<ListType> list = response.body().getData();
                    final List<String> list1 = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        list1.add(list.get(i).getType());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, list1);
                    tipe.setAdapter(adapter);
                    int intSource = adapter.getPosition(strTipe);
                    tipe.setSelection(intSource);
                    tipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            idTipe = list.get(position).getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<RespListType> call, Throwable t) {

            }
        });
    }
}
