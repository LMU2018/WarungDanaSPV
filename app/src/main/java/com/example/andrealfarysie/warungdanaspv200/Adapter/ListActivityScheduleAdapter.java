package com.example.andrealfarysie.warungdanaspv200.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.Response.ActivitySchedule;
import com.example.andrealfarysie.warungdanaspv200.Response.ListActivitySchedule;
import com.example.andrealfarysie.warungdanaspv200.Response.ListActivityUser;
import com.example.andrealfarysie.warungdanaspv200.Response.ListOutlet;
import com.example.andrealfarysie.warungdanaspv200.Response.RespCounterLead;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListActivityUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivityScheduleAdapter extends RecyclerView.Adapter<ListActivityScheduleAdapter.ListLeadHolder> {
    List<ListActivitySchedule> listOutlets;
    Context context;
    ApiEndPoint mApiService;
    ArrayList<Integer> idActivityUser = new ArrayList<Integer>();

    public ListActivityScheduleAdapter(Context context, List<ListActivitySchedule> list) {
        this.context = context;
        this.listOutlets = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aktivitas_hari_ini, parent, false);
        mApiService = UtilsApi.getAPIService();
        return new ListActivityScheduleAdapter.ListLeadHolder(view);
    }



    @Override
    public void onBindViewHolder(final ListLeadHolder holder, int position) {
        final ListActivitySchedule list = listOutlets.get(position);
        holder.lokasi.setText(list.getActivityLocation());
        holder.jam.setText(list.getStarted()+" - "+list.getEnded());
        mApiService.ActivitySchedule(list.getIdActivity()).enqueue(new Callback<ActivitySchedule>() {
            @Override
            public void onResponse(Call<ActivitySchedule> call, Response<ActivitySchedule> response) {
                holder.tipe.setText(response.body().getActivityMstTypeType());
                holder.outlet.setText(response.body().getMstOutletOutletName());
            }

            @Override
            public void onFailure(Call<ActivitySchedule> call, Throwable t) {

            }
        });
        mApiService.ListActivityUser(list.getId()).enqueue(new Callback<RespListActivityUser>() {
            @Override
            public void onResponse(Call<RespListActivityUser> call, Response<RespListActivityUser> response) {
                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context){
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                holder.cfa.setLayoutManager(layoutManager1);
                List<ListActivityUser> list = response.body().getData();
                holder.cfa.setAdapter(new ListActivityUserAdapter(context,list));
                for (int i=0;i< list.size();i++){
                    idActivityUser.add(list.get(i).getId());
                }
            }

            @Override
            public void onFailure(Call<RespListActivityUser> call, Throwable t) {

            }
        });
        if (list.getNote()!=null){
            holder.note.setText(list.getNote());
        }else {
            holder.note.setVisibility(View.GONE);
        }
        holder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_aktivitas_hari, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.delete){
                            AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
                            alertdialog.setMessage("Hapus Jadwal ?").setCancelable(true)
                                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mApiService.spv_activity_user_delete(list.getId()).enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    if (response.isSuccessful()){

                                                        notifyDataSetChanged();
                                                        mApiService.spv_activity_schedule_delete(list.getId()).enqueue(new Callback<ResponseBody>() {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                if (response.isSuccessful()){
                                                                    notifyDataSetChanged();
                                                                    Toast.makeText(context,"Berhasil menghapus jadwal",Toast.LENGTH_LONG).show();

                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                            }
                                                        });
                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                }
                                            });

                                            notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return listOutlets.size();
    }



    public class ListLeadHolder extends RecyclerView.ViewHolder {
        TextView tipe,lokasi,outlet,jam,note;
        RecyclerView cfa;
        ImageView option;

        public ListLeadHolder(View itemView) {
            super(itemView);
            tipe = itemView.findViewById(R.id.tipe);
            lokasi = itemView.findViewById(R.id.lokasi);
            outlet = itemView.findViewById(R.id.outlet);
            jam = itemView.findViewById(R.id.jam);
            cfa = itemView.findViewById(R.id.cfa);
            note = itemView.findViewById(R.id.note);
            option = itemView.findViewById(R.id.option);
        }
    }

}
