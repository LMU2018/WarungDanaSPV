package com.example.andrealfarysie.warungdanaspv200.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.AktivitasFragment.TemplateFragment;
import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.Response.ListActivityTemplate;
import com.example.andrealfarysie.warungdanaspv200.Response.ListActivityUser;
import com.example.andrealfarysie.warungdanaspv200.TambahJadwalActivity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivityTemplateAdapter extends RecyclerView.Adapter<ListActivityTemplateAdapter.ListLeadHolder> {
    List<ListActivityTemplate> listOutlets;
    Context context;
    ApiEndPoint mApiService;

    public ListActivityTemplateAdapter(Context context, List<ListActivityTemplate> list) {
        this.context = context;
        this.listOutlets = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_activity_template, parent, false);
        mApiService = UtilsApi.getAPIService();
        return new ListActivityTemplateAdapter.ListLeadHolder(view);
    }




    @Override
    public void onBindViewHolder(final ListLeadHolder holder, final int position) {
        final ListActivityTemplate list = listOutlets.get(position);

        holder.lokasi.setText(list.getLocation());
        holder.outlet.setText(list.getMstOutletOutletName());
        holder.tipe.setText(list.getActivityMstTypeType());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_template, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.delete){
                            AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
                            alertdialog.setMessage("Hapus Template ?").setCancelable(true)
                                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mApiService.spv_activity_template_delete(list.getId()).enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    if (response.isSuccessful()){
                                                        Toast.makeText(context,"Berhasil menghapus template",Toast.LENGTH_LONG).show();
                                                        TemplateFragment templateFragment = new TemplateFragment();
                                                        templateFragment.onAttach(context);

                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                }
                                            });


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
                        if (item.getItemId()==R.id.tambah){
                            Intent intent = new Intent(context,TambahJadwalActivity.class);
                            intent.putExtra("idJadwal",list.getId());
                            intent.putExtra("type",list.getActivityMstTypeType());
                            intent.putExtra("lokasi",list.getLocation());
                            intent.putExtra("outlet",list.getMstOutletOutletName());
                            context.startActivity(intent);
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
