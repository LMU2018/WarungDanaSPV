package com.example.andrealfarysie.warungdanaspv200;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.Helper.SharedPrefManager;
import com.example.andrealfarysie.warungdanaspv200.Response.AdditionalContactDetail;
import com.example.andrealfarysie.warungdanaspv200.Response.ContactDetail;
import com.example.andrealfarysie.warungdanaspv200.Response.DetailLoan;
import com.example.andrealfarysie.warungdanaspv200.Response.ListAddress;
import com.example.andrealfarysie.warungdanaspv200.Response.ListPhone;
import com.example.andrealfarysie.warungdanaspv200.Response.OrderDetail;
import com.example.andrealfarysie.warungdanaspv200.Response.OrderProductDetail;
import com.example.andrealfarysie.warungdanaspv200.Response.OrderSuretyDetail;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListAddress;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListPhone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderActivity extends AppCompatActivity {

    private ApiEndPoint mApiService;
    private Integer idContact, idOrder;
    TextView namaCfa, tglPooling, jamPooling, sumberOrder, stsKonsumen, namaOutlet, cabangFif, posFif, tglSurvei, jamSurvei,
            namaPemohon, noHp, noHp2, tmpLhr, tglLhr, pNama, pTmpLhr, pTglLhr, namaIbu, jmlTanggungan,
            pekerjaan, stsKaryawan, jabatan, lamaKerja, perusahaan, penghasilan, pengeluaran,
            pPekerjaan, pStatusKaryawan, pJabatan, pLamaKerja, pPerusahaan, pPenghasilan, pPengeluaran,
            otr, plafond, dp, angsuran, tenor, keperluan,
            merk, type, vehicle, tahunKendaraan, nopol, sPajak, owner,
            alamat, rtrw, kel, kec, kab, prov, sRumah, sAlamat;
    FloatingActionButton btnScrrenShot;
    String model, tahun;
    NumberFormat formatter = new DecimalFormat("#,###");
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        sharedPrefManager = new SharedPrefManager(this);

        namaCfa = findViewById(R.id.tv_hsl_nama_cfa);
        tglPooling = findViewById(R.id.tv_hsl_tgl_pooling);
        jamPooling = findViewById(R.id.tv_hsl_jam_pooling);
        sumberOrder = findViewById(R.id.tv_hsl_sumber_order);
        stsKonsumen = findViewById(R.id.tv_hsl_status_konsumen);
        namaOutlet = findViewById(R.id.tv_hsl_nama_outlet);
        cabangFif = findViewById(R.id.tv_hsl_cabang_fif);
        posFif = findViewById(R.id.tv_hsl_pos_fif);
        tglSurvei = findViewById(R.id.tv_hsl_tgl_survey);
        jamSurvei = findViewById(R.id.tv_hsl_jam_survey);

        namaPemohon = findViewById(R.id.tv_hsl_nama_pemohon);
        noHp = findViewById(R.id.tv_hsl_no_hp);
        noHp2 = findViewById(R.id.tv_hsl_no_hp_2);
        tmpLhr = findViewById(R.id.tv_hsl_tempat_lahir);
        tglLhr = findViewById(R.id.tv_hsl_tgl_lahir);
        pNama = findViewById(R.id.tv_hsl_nama_penjamin);
        pTmpLhr = findViewById(R.id.tv_hsl_ptempat_lahir);
        pTglLhr = findViewById(R.id.tv_hsl_ptgl_lahir);
        namaIbu = findViewById(R.id.tv_hsl_ibu_kandung);
        jmlTanggungan = findViewById(R.id.tv_hsl_jml_tanggungan);

        pekerjaan = findViewById(R.id.tv_hsl_pekerjaan);
        stsKaryawan = findViewById(R.id.tv_hsl_status_karyawan);
        jabatan = findViewById(R.id.tv_hsl_jabatan);
        lamaKerja = findViewById(R.id.tv_hsl_lama_kerja);
        perusahaan = findViewById(R.id.tv_hsl_perusahaan);
        penghasilan = findViewById(R.id.tv_hsl_penghasilan);
        pengeluaran = findViewById(R.id.tv_hsl_pengeluaran);

        pPekerjaan = findViewById(R.id.tv_hsl_pekerjaan_pasangan);
        pStatusKaryawan = findViewById(R.id.tv_hsl_status_pasangan);
        pJabatan = findViewById(R.id.tv_hsl_jabatan_pasangan);
        pLamaKerja = findViewById(R.id.tv_hsl_lama_kerja_pasangan);
        pPerusahaan = findViewById(R.id.tv_hsl_perusahaan_pasangan);
        pPenghasilan = findViewById(R.id.tv_hsl_penghasilan_pasangan);
        pPengeluaran = findViewById(R.id.tv_hsl_pengeluaran_pasangan);

        otr = findViewById(R.id.tv_hsl_otr);
        plafond = findViewById(R.id.tv_hsl_plafond);
        dp = findViewById(R.id.tv_hsl_dp);
        angsuran = findViewById(R.id.tv_hsl_angsuran);
        tenor = findViewById(R.id.tv_hsl_tenor);
        keperluan = findViewById(R.id.tv_hsl_keperluan);

        merk = findViewById(R.id.tv_hsl_merk_kendaraan);
        type = findViewById(R.id.tv_hsl_type_kendaraan);
        vehicle = findViewById(R.id.tv_hsl_vehicle);
        tahunKendaraan = findViewById(R.id.tv_hsl_thn_kendaraan);
        nopol = findViewById(R.id.tv_hsl_nopol);
        sPajak = findViewById(R.id.tv_hsl_pajak);
        owner = findViewById(R.id.tv_hsl_owner);

        alamat = findViewById(R.id.tv_hsl_alamat);
        rtrw = findViewById(R.id.tv_hsl_rtrw);
        kel = findViewById(R.id.tv_hsl_kelurahan);
        kec = findViewById(R.id.tv_hsl_kecamatan);
        kab = findViewById(R.id.tv_hsl_kabupaten);
        prov = findViewById(R.id.tv_hsl_provinsi);
        sRumah = findViewById(R.id.tv_hsl_sts_rumah);
        sAlamat = findViewById(R.id.tv_hsl_sts_alamat);

        btnScrrenShot = findViewById(R.id.btnScreenShot);
//        btnScrrenShot.setVisibility(View.GONE);

        mApiService = UtilsApi.getAPIService();
        idContact = getIntent().getIntExtra("idContact", 0);
        idOrder = getIntent().getIntExtra("idOrder", 0);


        getDataPrint();


    }

    private String convertTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd MMMM yyyy");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = format1.format(date);
        return convertedDate;
    }

    private String convertTime2(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate2 = format1.format(date);
        return convertedDate2;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        btnScrrenShot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(DetailOrderActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
//                }
//
//                Bitmap bitmap = takeScreenshot();
//                saveBitmap(bitmap);
//                finish();
//                Toast.makeText(getApplicationContext(), "Berhasil screenshot data pooling !", Toast.LENGTH_LONG).show();
//            }
//        });


    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/" + model + tahun + ".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void getDataPrint() {

        mApiService.spv_order_detail(idOrder).enqueue(new Callback<OrderDetail>() {
            @Override
            public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        if (response.body().getCreatedAt() != null) {
                            tglPooling.setText(convertTime(response.body().getCreatedAt()));
                        } else {
                            tglPooling.setText("Empty");
                        }

                        if (response.body().getCreatedAt() != null) {
                            jamPooling.setText(convertTime2(response.body().getCreatedAt()));
                        } else {
                            jamPooling.setText("Empty");
                        }

                        if (response.body().getMstDataSourceDatasource() != null) {
                            sumberOrder.setText(response.body().getMstDataSourceDatasource());
                        } else {
                            sumberOrder.setText("Empty");
                        }

                        if (response.body().getCategory() != null) {
                            stsKonsumen.setText(response.body().getCategory());
                        } else {
                            stsKonsumen.setText("Empty");
                        }

                        if (response.body().getMstCabangFifBranchName() != null) {
                            cabangFif.setText(response.body().getMstCabangFifBranchName());
                        } else {
                            cabangFif.setText("Empty");
                        }

                        if (response.body().getMstCabangFifPosName() != null) {
                            posFif.setText(response.body().getMstCabangFifPosName());
                        } else {
                            posFif.setText("Empty");
                        }

                        if (response.body().getSurvey() != null) {
                            tglSurvei.setText(convertTime(response.body().getSurvey()));
                        } else {
                            tglSurvei.setText("Empty");
                        }

                        if (response.body().getSurvey() != null) {
                            jamSurvei.setText(convertTime2(response.body().getSurvey()));
                        } else {
                            jamSurvei.setText("Empty");
                        }
                        namaCfa.setText(response.body().getCmsUsersName());
                        namaOutlet.setText(response.body().getMstOutletOutletName());

//                        if (response.body().getOrderMstStatusStatus() != null) {
//                            if (response.body().getStatusAddress().equalsIgnoreCase("S")) {
//                                sAlamat.setText("SESUAI");
//                            } else {
//                                sAlamat.setText("TIDAK SESUAI");
//                            }
//                        } else {
//                            sAlamat.setText("Empty");
//                        }

                    } else {
                        Toast.makeText(DetailOrderActivity.this, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailOrderActivity.this, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderDetail> call, Throwable t) {
                Toast.makeText(DetailOrderActivity.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.spv_contact_detail(idContact).enqueue(new Callback<ContactDetail>() {
            @Override
            public void onResponse(Call<ContactDetail> call, Response<ContactDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        if (response.body().getFirstName() != null & response.body().getLastName() != null) {
                            namaPemohon.setText(response.body().getFirstName().toUpperCase() + " " + response.body().getLastName().toUpperCase());
                        } else if (response.body().getFirstName() != null & response.body().getLastName() == null) {
                            namaPemohon.setText(response.body().getFirstName().toUpperCase());
                        } else if (response.body().getFirstName() == null & response.body().getLastName() != null) {
                            namaPemohon.setText(response.body().getLastName().toUpperCase());
                        } else {
                            namaPemohon.setText("Empty");
                        }

                        if (response.body().getBirthPlace() != null) {
                            tmpLhr.setText(response.body().getBirthPlace().toUpperCase());
                        } else {
                            tmpLhr.setText("Empty");
                        }

                        if (response.body().getBirthDate() != null) {
                            tglLhr.setText(convertTime(response.body().getBirthDate()));
                        } else {
                            tglLhr.setText("Empty");
                        }

                        if (response.body().getMstJobJob() != null) {
                            pekerjaan.setText(response.body().getMstJobJob());
                        } else {
                            pekerjaan.setText("Empty");
                        }

                    } else {
                        Toast.makeText(DetailOrderActivity.this, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailOrderActivity.this, "Not Responding", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ContactDetail> call, Throwable t) {
                Toast.makeText(DetailOrderActivity.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.spv_additional_contact_detail(idContact).enqueue(new Callback<AdditionalContactDetail>() {
            @Override
            public void onResponse(Call<AdditionalContactDetail> call, Response<AdditionalContactDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        if (response.body().getMother() != null) {
                            namaIbu.setText(response.body().getMother().toUpperCase());
                        } else {
                            namaIbu.setText("Empty");
                        }

                        if (response.body().getFamily() != null) {
                            jmlTanggungan.setText(response.body().getFamily().toString() + " Orang");
                        } else {
                            jmlTanggungan.setText("Empty");
                        }

                        if (response.body().getContactMstStatusEmployeeStatus() != null) {
                            stsKaryawan.setText(response.body().getContactMstStatusEmployeeStatus());
                        } else {
                            stsKaryawan.setText("Empty");
                        }

                        if (response.body().getPosition() != null) {
                            jabatan.setText(response.body().getPosition().toUpperCase());
                        } else {
                            jabatan.setText("Empty");
                        }

                        if (response.body().getWorkingTime() != null) {
                            lamaKerja.setText(response.body().getWorkingTime().toString() + " Tahun");
                        } else {
                            lamaKerja.setText("Empty");
                        }

                        if (response.body().getCompany() != null) {
                            perusahaan.setText(response.body().getCompany().toUpperCase());
                        } else {
                            perusahaan.setText("Empty");
                        }

                        if (response.body().getIncome() != null) {
                            penghasilan.setText(formatter.format(response.body().getIncome()));
                        } else {
                            penghasilan.setText("Empty");
                        }

                        if (response.body().getOutlay() != null) {
                            pengeluaran.setText(formatter.format(response.body().getOutlay()));
                        } else {
                            pengeluaran.setText("Empty");
                        }

                        if (response.body().getContactMstStatusPlaceStatus() != null) {
                            sRumah.setText(response.body().getContactMstStatusPlaceStatus().toString());
                        } else {
                            sRumah.setText("Empty");
                        }

                    } else {
                        Toast.makeText(DetailOrderActivity.this, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailOrderActivity.this, "Not Responding", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AdditionalContactDetail> call, Throwable t) {
                Toast.makeText(DetailOrderActivity.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.spv_order_surety_detail(idOrder).enqueue(new Callback<OrderSuretyDetail>() {
            @Override
            public void onResponse(Call<OrderSuretyDetail> call, Response<OrderSuretyDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        if (response.body().getName() != null) {
                            pNama.setText(response.body().getName().toUpperCase());
                        } else {
                            pNama.setText("Empty");
                        }

                        if (response.body().getBirthPlace() != null) {
                            pTmpLhr.setText(response.body().getBirthPlace().toUpperCase());
                        } else {
                            pTmpLhr.setText("Empty");
                        }

                        if (response.body().getBirthDate() != null) {
                            pTglLhr.setText(convertTime(response.body().getBirthDate()));
                        } else {
                            pTglLhr.setText("Empty");
                        }

                        if (response.body().getMstJobJob() != null) {
                            pPekerjaan.setText(response.body().getMstJobJob());
                        } else {
                            pPekerjaan.setText("Empty");
                        }

                        if (response.body().getContactMstStatusEmployeeStatus() != null) {
                            pStatusKaryawan.setText(response.body().getContactMstStatusEmployeeStatus());
                        } else {
                            pStatusKaryawan.setText("Empty");
                        }

                        if (response.body().getPosition() != null) {
                            pJabatan.setText(response.body().getPosition().toUpperCase());
                        } else {
                            pJabatan.setText("Empty");
                        }

                        if (response.body().getWorkingTime() != null) {
                            pLamaKerja.setText(response.body().getWorkingTime() + " Tahun");
                        } else {
                            pLamaKerja.setText("Empty");
                        }

                        if (response.body().getCompany() != null) {
                            pPerusahaan.setText(response.body().getCompany().toUpperCase());
                        } else {
                            pPerusahaan.setText("Empty");
                        }

                        if (response.body().getIncome() != null) {
                            pPenghasilan.setText(formatter.format(response.body().getIncome()));
                        } else {
                            pPenghasilan.setText("Empty");
                        }

                        if (response.body().getOutlay() != null) {
                            pPengeluaran.setText(formatter.format(response.body().getOutlay()));
                        } else {
                            pPengeluaran.setText("Empty");
                        }

                    } else {
                        Toast.makeText(DetailOrderActivity.this, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailOrderActivity.this, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderSuretyDetail> call, Throwable t) {
                Toast.makeText(DetailOrderActivity.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.spv_order_product_detail(idOrder).enqueue(new Callback<OrderProductDetail>() {
            @Override
            public void onResponse(Call<OrderProductDetail> call, Response<OrderProductDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        model = response.body().getMstUnitModel();
                        tahun = response.body().getMstUnitYear().toString();

                        if (response.body().getMstUnitOtr() != null) {
                            otr.setText(formatter.format(response.body().getMstUnitOtr()));
                        } else {
                            otr.setText("Empty");
                        }

                        if (response.body().getMstUnitMerk() != null) {
                            merk.setText(response.body().getMstUnitMerk().trim());
                        } else {
                            merk.setText("Empty");
                        }

                        if (response.body().getMstUnitType() != null) {
                            type.setText(response.body().getMstUnitType().trim());
                        } else {
                            type.setText("Empty");
                        }

                        if (response.body().getMstUnitKodeUnit() != null) {
                            vehicle.setText(response.body().getMstUnitKodeUnit());
                        } else {
                            vehicle.setText("Empty");
                        }

                        if (response.body().getMstUnitYear() != null) {
                            tahunKendaraan.setText(response.body().getMstUnitYear().toString());
                        } else {
                            tahunKendaraan.setText("Empty");
                        }

                        if (response.body().getNopol() != null) {
                            nopol.setText(response.body().getNopol());
                        } else {
                            nopol.setText("Empty");
                        }

                        if (response.body().getTaxStatus() != null) {
                            if (response.body().getTaxStatus().equalsIgnoreCase("Y")) {
                                sPajak.setText("HIDUP");
                            } else {
                                sPajak.setText("MATI");
                            }
                        } else {
                            sPajak.setText("Empty");
                        }

                        if (response.body().getOwner() != null) {
                            owner.setText("MILIK " + response.body().getOwner().toUpperCase());
                        } else {
                            owner.setText("Empty");
                        }

                    } else {
                        Toast.makeText(DetailOrderActivity.this, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailOrderActivity.this, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderProductDetail> call, Throwable t) {
                Toast.makeText(DetailOrderActivity.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.detailLoan(idOrder).enqueue(new Callback<DetailLoan>() {
            @Override
            public void onResponse(Call<DetailLoan> call, Response<DetailLoan> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        if (response.body().getPlafond() != null) {
                            plafond.setText(formatter.format(response.body().getPlafond()));
                        } else {
                            plafond.setText("Empty");
                        }

                        if (response.body().getDownPayment() != null) {
                            dp.setText(formatter.format(response.body().getDownPayment()));
                        } else {
                            dp.setText("Empty");
                        }

                        if (response.body().getInstallment() != null) {
                            angsuran.setText(formatter.format(response.body().getInstallment()));
                        } else {
                            angsuran.setText("Empty");
                        }

                        if (response.body().getTenor() != null) {
                            tenor.setText(response.body().getTenor().toString() + " Bulan");
                        } else {
                            tenor.setText("Empty");
                        }

                        if (response.body().getNeed() != null) {
                            keperluan.setText(response.body().getNeed());
                        } else {
                            keperluan.setText("Empty");
                        }

                    } else {
                        Toast.makeText(DetailOrderActivity.this, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailOrderActivity.this, "Not Responding", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DetailLoan> call, Throwable t) {
                Toast.makeText(DetailOrderActivity.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.spv_contact_address_listing(idContact).enqueue(new Callback<RespListAddress>() {
            @Override
            public void onResponse(Call<RespListAddress> call, Response<RespListAddress> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        List<ListAddress> listLeads = response.body().getData();

                        if (listLeads.get(0).getAddress() != null) {
                            alamat.setText(listLeads.get(0).getAddress().trim().toUpperCase());
                        } else {
                            alamat.setText("Empty");
                        }

                        if (listLeads.get(0).getRt() != null & listLeads.get(0).getRw() != null) {
                            rtrw.setText(listLeads.get(0).getRt() + " / " + listLeads.get(0).getRw());
                        } else if (listLeads.get(0).getRt() != null & listLeads.get(0).getRw() == null) {
                            rtrw.setText(listLeads.get(0).getRt() + " / " + "-");
                        } else if (listLeads.get(0).getRt() == null & listLeads.get(0).getRw() != null) {
                            rtrw.setText("-" + " / " + listLeads.get(0).getRw());
                        } else {
                            rtrw.setText("Empty");
                        }

                        if (listLeads.get(0).getMstAddressKelurahan() != null) {
                            kel.setText(listLeads.get(0).getMstAddressKelurahan().trim().toUpperCase());
                        } else {
                            kel.setText("Empty");
                        }

                        if (listLeads.get(0).getMstAddressKecamatan() != null) {
                            kec.setText(listLeads.get(0).getMstAddressKecamatan().trim().toUpperCase());
                        } else {
                            kec.setText("Empty");
                        }

                        if (listLeads.get(0).getMstAddressKabupaten() != null) {
                            kab.setText(listLeads.get(0).getMstAddressKabupaten().trim().toUpperCase());
                        } else {
                            kab.setText("Empty");
                        }

                        if (listLeads.get(0).getMstAddressProvinsi() != null) {
                            prov.setText(listLeads.get(0).getMstAddressProvinsi().trim().toUpperCase());
                        } else {
                            prov.setText("Empty");
                        }

                    } else {
                        Toast.makeText(DetailOrderActivity.this, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailOrderActivity.this, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListAddress> call, Throwable t) {
                Toast.makeText(DetailOrderActivity.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.spv_contact_phone_listing(idContact).enqueue(new Callback<RespListPhone>() {
            @Override
            public void onResponse(Call<RespListPhone> call, Response<RespListPhone> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        List<ListPhone> listLeads = response.body().getData();

                        if (listLeads.get(0).getNumber() != null) {
                            noHp.setText(listLeads.get(0).getNumber());
                        } else {
                            noHp.setText("Empty");
                        }

                        if (listLeads.get(1).getNumber() != null) {
                            noHp2.setText(listLeads.get(1).getNumber());
                        } else {
                            noHp2.setText("Empty");
                        }

                    } else {
                        Toast.makeText(DetailOrderActivity.this, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailOrderActivity.this, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListPhone> call, Throwable t) {
                Toast.makeText(DetailOrderActivity.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
