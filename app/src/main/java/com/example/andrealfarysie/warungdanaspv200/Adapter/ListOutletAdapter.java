package com.example.andrealfarysie.warungdanaspv200.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.Response.ListOutlet;
import com.example.andrealfarysie.warungdanaspv200.Response.RespCounterLead;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOutletAdapter extends RecyclerView.Adapter<ListOutletAdapter.ListLeadHolder> {
    List<ListOutlet> listOutlets;
    Context context;
    ApiEndPoint mApiService;


    public ListOutletAdapter(Context context, List<ListOutlet> list) {
        this.context = context;
        this.listOutlets = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kpi_outlet, parent, false);
        mApiService = UtilsApi.getAPIService();
        return new ListOutletAdapter.ListLeadHolder(view);
    }

    private String convertTimeDay(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd-yyyy");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = format1.format(date);
        return convertedDate;
    }
    private String convertTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("MM-yyyy");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = format1.format(date);
        return convertedDate;
    }


    @Override
    public void onBindViewHolder(final ListLeadHolder holder, int position) {
        final ListOutlet list = listOutlets.get(position);
        final String tanggal,bulans,hariini,bulanini;
        SimpleDateFormat format = new SimpleDateFormat("dd-yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("MM-yyyy");
        Date today = Calendar.getInstance().getTime();

        Calendar calendar = Calendar.getInstance();
        hariini = format.format(today);
        bulanini = format2.format(today);
        tanggal = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)+1);
        bulans = String.valueOf(calendar.get(Calendar.MONTH)+1);

        holder.nama.setText(list.getOutletName());

        mApiService.orderCounterOutlet(list.getId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()){
                    List<String> hari = new ArrayList<String>();
                    List<String> bulan = new ArrayList<String>();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        hari.add(convertTimeDay(response.body().getData().get(i).getCreatedAt()));
                    }
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        bulan.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                    }

                    int countHari = 0;
                    int countMonth = 0;
                    for (String cfa : hari) {
                        if (cfa.equals(hariini)) {
                            countHari++;
                        }

                    }
                    for (String cfa : bulan) {
                        if (cfa.equals(bulanini)) {
                            countMonth++;
                        }

                    }
                    holder.orderHari.setText(String.valueOf(countHari));
                    holder.orderBulan.setText(String.valueOf(countMonth));
                }

            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {

            }
        });

        mApiService.bookingCounterOutlet(list.getId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()){
                    List<String> hari = new ArrayList<String>();
                    List<String> bulan = new ArrayList<String>();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        hari.add(convertTimeDay(response.body().getData().get(i).getCreatedAt()));
                    }
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        bulan.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                    }
                    int countHariBook = 0;
                    int countMonthBook = 0;
                    for (String cfa : hari) {
                        if (cfa.equals(hariini)) {
                            countHariBook++;
                        }

                    }
                    for (String cfa : bulan) {
                        if (cfa.equals(bulanini)) {
                            countMonthBook++;
                        }

                    }
                    holder.bookingHari.setText(String.valueOf(countHariBook));
                    holder.bookingBulan.setText(String.valueOf(countMonthBook));
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {

            }
        });

//        mApiService.spv_order_hari_counter(list.getId(),hariini,5).enqueue(new Callback<RespCounterLead>() {
//            @Override
//            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
//                if (response.isSuccessful()){
//                    holder.bookingHari.setText(String.valueOf(response.body().getData().size()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespCounterLead> call, Throwable t) {
//
//            }
//        });
//
//        mApiService.spv_order_hari_counter(list.getId(),hariini,null).enqueue(new Callback<RespCounterLead>() {
//            @Override
//            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
//                if (response.isSuccessful()){
//                    holder.orderHari.setText(String.valueOf(response.body().getData().size()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespCounterLead> call, Throwable t) {
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return listOutlets.size();
    }



    public class ListLeadHolder extends RecyclerView.ViewHolder {
        TextView nama,orderHari,orderBulan,bookingHari,bookingBulan;

        public ListLeadHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namaOutlet);
            orderHari = itemView.findViewById(R.id.orderHari);
            orderBulan = itemView.findViewById(R.id.orderBulan);
            bookingHari = itemView.findViewById(R.id.bookingHari);
            bookingBulan = itemView.findViewById(R.id.bookingBulan);
        }
    }

}
