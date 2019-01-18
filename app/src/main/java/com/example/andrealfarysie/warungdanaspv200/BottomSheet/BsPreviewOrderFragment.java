package com.example.andrealfarysie.warungdanaspv200.BottomSheet;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.Adapter.ListOrderAdapter;
import com.example.andrealfarysie.warungdanaspv200.Adapter.ListOrderAdapter2;
import com.example.andrealfarysie.warungdanaspv200.DetailOrderActivity;
import com.example.andrealfarysie.warungdanaspv200.Helper.NumberTextWatcher;
import com.example.andrealfarysie.warungdanaspv200.Helper.SharedPrefManager;
import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.Response.DetailLoan;
import com.example.andrealfarysie.warungdanaspv200.Response.DetailUnit;
import com.example.andrealfarysie.warungdanaspv200.Response.ListOrderStatus;
import com.example.andrealfarysie.warungdanaspv200.Response.ListType;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListOrderStatus;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BsPreviewOrderFragment extends BottomSheetDialogFragment {

    Button btnDetail,btnEdit;
    TextView nama,unit,tanggal;
    Integer idUnit,idOrder,idContact;
    ApiEndPoint mApiService;
    EditText otr,plafond,angsuran,tenor,dp;
    String strPlafond,strAngsuran,strtenor,strDp,status,reason=null;
    Integer idLoan, jumlah = 0,idStatus,idReason1=null;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    NumberFormat formatter = new DecimalFormat("#,###");
    Spinner spinnerStatus,spinnerReason;


    public BsPreviewOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bs_preview_order,container,false);
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(getContext());
        btnDetail = view.findViewById(R.id.btnDetail);
        btnEdit = view.findViewById(R.id.btnEdit);
        nama = view.findViewById(R.id.nama);
        tanggal = view.findViewById(R.id.tanggal);
        unit = view.findViewById(R.id.motor);
        spinnerStatus = view.findViewById(R.id.spinnerStatus);

        spinnerReason = view.findViewById(R.id.spinnerReason);
        spinnerReason.setVisibility(View.GONE);


        Bundle bundle = getArguments();
        nama.setText(bundle.getString("konsumen"));
        tanggal.setText(bundle.getString("tanggal"));
        status = bundle.getString("status");
        reason = bundle.getString("reason");
        idUnit = bundle.getInt("idUnit");
        idOrder = bundle.getInt("idOrder");
        idContact = bundle.getInt("idContact");
        otr = view.findViewById(R.id.otr);
        plafond = view.findViewById(R.id.plafond);
        angsuran = view.findViewById(R.id.angsuran);
        tenor = view.findViewById(R.id.tenor);
        dp = view.findViewById(R.id.dp);
        plafond.addTextChangedListener(autoTextWatcher);
        otr.addTextChangedListener(autoTextWatcher);
        otr.addTextChangedListener(new NumberTextWatcher(otr));
        plafond.addTextChangedListener(new NumberTextWatcher(plafond));
        angsuran.addTextChangedListener(new NumberTextWatcher(angsuran));
        dp.addTextChangedListener(new NumberTextWatcher(dp));

        initSpinnerStatus();



        mApiService.detailUnit(idUnit).enqueue(new Callback<DetailUnit>() {
            @Override
            public void onResponse(Call<DetailUnit> call, Response<DetailUnit> response) {
                if (response.isSuccessful()){
                    unit.setText(response.body().getMerk()+" "+response.body().getModel());
                    otr.setText(response.body().getOtr().toString());
                }
            }

            @Override
            public void onFailure(Call<DetailUnit> call, Throwable t) {

            }
        });
        mApiService.detailLoan(idOrder).enqueue(new Callback<DetailLoan>() {
            @Override
            public void onResponse(Call<DetailLoan> call, Response<DetailLoan> response) {
                if (response.isSuccessful()){
                    idLoan = response.body().getId();
                    strPlafond = response.body().getPlafond().toString();
                    strAngsuran = response.body().getInstallment().toString();
                    strtenor = response.body().getTenor().toString();
                    strDp = response.body().getDownPayment().toString();
                    plafond.setText(strPlafond);
                    angsuran.setText(strAngsuran);
                    tenor.setText(strtenor);
                    dp.setText(strDp);
                }
            }

            @Override
            public void onFailure(Call<DetailLoan> call, Throwable t) {
                Toast.makeText(getContext(),"Internet Bermasalah !",Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

    private void initSpinnerStatus() {
        mApiService.orderStatus().enqueue(new Callback<RespListOrderStatus>() {
            @Override
            public void onResponse(Call<RespListOrderStatus> call, Response<RespListOrderStatus> response) {
                if (response.isSuccessful()){
                    final List<ListOrderStatus> list = response.body().getData();
                    final List<String> list1 = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        list1.add(list.get(i).getStatus());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_2, list1);
                    spinnerStatus.setAdapter(adapter);
                    int intSource = adapter.getPosition(status);
                    spinnerStatus.setSelection(intSource);
                    if (reason!=null){
                        spinnerReason.setVisibility(View.VISIBLE);
                        initSpinnerReason(intSource);
                    }
                    spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            idStatus = list.get(position).getId();

                            if (idStatus==3||idStatus==4){
                                spinnerReason.setVisibility(View.VISIBLE);
                                initSpinnerReason(idStatus);
                            }else {
                                spinnerReason.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<RespListOrderStatus> call, Throwable t) {

            }
        });
    }


    private void initSpinnerReason(final int idReason){
        mApiService.spv_order_reason_listing(idReason).enqueue(new Callback<RespListOrderStatus>() {
            @Override
            public void onResponse(Call<RespListOrderStatus> call, Response<RespListOrderStatus> response) {
                if (response.isSuccessful()){
                    final List<ListOrderStatus> list = response.body().getData();
                    final List<String> list1 = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        list1.add(list.get(i).getReason());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_3, list1);
                    spinnerReason.setAdapter(adapter);
                    int intSource = adapter.getPosition(reason);
                    spinnerReason.setSelection(intSource);
                    spinnerReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            idReason1 = list.get(position).getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<RespListOrderStatus> call, Throwable t) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),DetailOrderActivity.class);
                intent.putExtra("idContact",idContact);
                intent.putExtra("idOrder",idOrder);
                startActivity(intent);
                dismiss();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiService.orderUpdate(idOrder,idStatus,idReason1,sharedPrefManager.getSpId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            mApiService.orderLoanUpdate(idLoan,idOrder,Integer.parseInt(plafond.getText().toString().replaceAll(",", "").replaceAll("\\.", "")),
                                    Integer.parseInt(dp.getText().toString().replaceAll(",", "").replaceAll("\\.", "")),
                                    Integer.parseInt(angsuran.getText().toString().replaceAll(",", "").replaceAll("\\.", "")),
                                    Integer.parseInt(tenor.getText().toString()),sharedPrefManager.getSpId(),sharedPrefManager.getSpId(),null)
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            Toast.makeText(getContext(),"Berhasil mengubah data Order !",Toast.LENGTH_LONG).show();
                                            dismiss();
                                            ListOrderAdapter2 listOrderAdapter2 = new ListOrderAdapter2();
                                            listOrderAdapter2.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getContext(),"Internet Bermasalah !",Toast.LENGTH_LONG).show();

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(),"Internet Bermasalah !",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }
    private TextWatcher autoTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            sumDp();
            if (!plafond.getText().toString().isEmpty() && !otr.getText().toString().isEmpty() && jumlah >= 0) {
                dp.setText(formatter.format(jumlah));
            } else {
                dp.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void sumDp() {

        if (!otr.getText().toString().isEmpty() && !plafond.getText().toString().isEmpty()) {
            Integer MinOtr;
            Integer MinPlafond;
            MinOtr = Integer.parseInt(otr.getText().toString().replaceAll(",", "").replaceAll("\\.", ""));
            MinPlafond = Integer.parseInt(plafond.getText().toString().replaceAll(",", "").replaceAll("\\.", ""));
            jumlah = MinOtr - MinPlafond;
        }

    }

}
