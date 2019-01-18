package com.example.andrealfarysie.warungdanaspv200.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.OnLoadMoreListener;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.BottomSheet.BsPreviewOrderFragment;
import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.Response.ListOrder;
import com.example.andrealfarysie.warungdanaspv200.Response.ListOutlet;
import com.example.andrealfarysie.warungdanaspv200.Response.RespCounterLead;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ListLeadHolder> {
    List<ListOrder> listOutlets;
    Context context;
    ApiEndPoint mApiService;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public ListOrderAdapter(){

    }

    public ListOrderAdapter(Context context, List<ListOrder> list) {
        this.context = context;
        this.listOutlets = list;


    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }
    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_order_home, parent, false);
        mApiService = UtilsApi.getAPIService();
        return new ListOrderAdapter.ListLeadHolder(view);
    }

    private String convertTimeDay(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy");
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
        final ListOrder list = listOutlets.get(position);

        if (list.getContactLastName()!=null){
            holder.konsumen.setText(list.getContactFirstName()+" "+list.getContactLastName());
        }else {
            holder.konsumen.setText(list.getContactFirstName());
        }

        holder.cfa.setText(list.getCmsUsersName());
        holder.outlet.setText(list.getMstOutletOutletName());
        holder.tanggal.setText(convertTimeDay(list.getCreatedAt()));
        holder.status.setText(list.getOrderMstStatusStatus());



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BsPreviewOrderFragment bsPreviewOrderFragment = new BsPreviewOrderFragment();
                Bundle bundle = new Bundle();
                bundle.putString("konsumen", holder.konsumen.getText().toString());
                bundle.putInt("idOrder", list.getId());
                bundle.putInt("idContact", list.getIdContact());
                bundle.putInt("idUnit", list.getIdMstUnit());
                bundle.putString("status", list.getOrderMstStatusStatus());
                bundle.putString("reason", list.getOrderMstReasonReason());
                bundle.putString("tanggal", holder.tanggal.getText().toString());
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                bsPreviewOrderFragment.setArguments(bundle);
                bsPreviewOrderFragment.show(fragmentManager,bsPreviewOrderFragment.getTag());
            }
        });




    }

    @Override
    public int getItemCount() {
        return listOutlets == null ? 0 :listOutlets.size();
    }


    public class ListLeadHolder extends RecyclerView.ViewHolder {
        TextView konsumen,cfa,outlet,tanggal,status;
        CardView cardView;

        public ListLeadHolder(View itemView) {
            super(itemView);
            konsumen = itemView.findViewById(R.id.konsumen);
            cfa = itemView.findViewById(R.id.cfa);
            outlet = itemView.findViewById(R.id.outlet);
            tanggal = itemView.findViewById(R.id.tanggal);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

}
