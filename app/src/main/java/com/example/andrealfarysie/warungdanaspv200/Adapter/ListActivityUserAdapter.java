package com.example.andrealfarysie.warungdanaspv200.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.Response.ActivitySchedule;
import com.example.andrealfarysie.warungdanaspv200.Response.ListActivitySchedule;
import com.example.andrealfarysie.warungdanaspv200.Response.ListActivityUser;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListActivityUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivityUserAdapter extends RecyclerView.Adapter<ListActivityUserAdapter.ListLeadHolder> {
    List<ListActivityUser> listOutlets;
    Context context;
    ApiEndPoint mApiService;

    public ListActivityUserAdapter(Context context, List<ListActivityUser> list) {
        this.context = context;
        this.listOutlets = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nama_cfa, parent, false);
        mApiService = UtilsApi.getAPIService();
        return new ListActivityUserAdapter.ListLeadHolder(view);
    }



    @Override
    public void onBindViewHolder(final ListLeadHolder holder, int position) {
        final ListActivityUser list = listOutlets.get(position);

        holder.nama.setText(list.getCmsUsersName());
        if (list.getIdMstStatus()!=null){
            holder.status.setText(list.getStatus());
            holder.tanggal.setText(list.getTanggal());
        }else {
            holder.status.setText("Belum");
            holder.tanggal.setVisibility(View.GONE);
        }
        if (list.getNote()!=null){
            holder.note.setText(list.getNote());
        }else {
            holder.note.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return listOutlets.size();
    }



    public class ListLeadHolder extends RecyclerView.ViewHolder {
        TextView nama,status,tanggal,note;

        public ListLeadHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            status = itemView.findViewById(R.id.status);
            tanggal = itemView.findViewById(R.id.tanggal);
            note = itemView.findViewById(R.id.note);
        }
    }

}
