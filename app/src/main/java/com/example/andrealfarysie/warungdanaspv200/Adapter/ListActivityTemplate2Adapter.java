package com.example.andrealfarysie.warungdanaspv200.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.Response.ListActivityTemplate;
import com.example.andrealfarysie.warungdanaspv200.TambahJadwalActivity;

import java.util.List;

public class ListActivityTemplate2Adapter extends RecyclerView.Adapter<ListActivityTemplate2Adapter.ListLeadHolder> {
    List<ListActivityTemplate> listOutlets;
    Context context;
    ApiEndPoint mApiService;

    public ListActivityTemplate2Adapter(Context context, List<ListActivityTemplate> list) {
        this.context = context;
        this.listOutlets = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_activity_template, parent, false);
        mApiService = UtilsApi.getAPIService();
        return new ListActivityTemplate2Adapter.ListLeadHolder(view);
    }



    @Override
    public void onBindViewHolder(final ListLeadHolder holder, int position) {
        final ListActivityTemplate list = listOutlets.get(position);

        holder.lokasi.setText(list.getLocation());
        holder.outlet.setText(list.getMstOutletOutletName());
        holder.tipe.setText(list.getActivityMstTypeType());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TambahJadwalActivity.class);
                intent.putExtra("idJadwal",list.getId());
                intent.putExtra("type",list.getActivityMstTypeType());
                intent.putExtra("lokasi",list.getLocation());
                intent.putExtra("outlet",list.getMstOutletOutletName());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        return listOutlets.size();
    }



    public class ListLeadHolder extends RecyclerView.ViewHolder {
        TextView outlet,tipe,lokasi;
        CardView cardView;

        public ListLeadHolder(View itemView) {
            super(itemView);
            outlet = itemView.findViewById(R.id.outlet);
            tipe = itemView.findViewById(R.id.tipe);
            lokasi = itemView.findViewById(R.id.lokasi);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}
