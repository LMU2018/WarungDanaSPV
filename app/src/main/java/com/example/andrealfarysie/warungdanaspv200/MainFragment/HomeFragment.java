package com.example.andrealfarysie.warungdanaspv200.MainFragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.Adapter.ListActivityScheduleAdapter;
import com.example.andrealfarysie.warungdanaspv200.Adapter.ListOrderAdapter;
import com.example.andrealfarysie.warungdanaspv200.Adapter.ListOutletAdapter;
import com.example.andrealfarysie.warungdanaspv200.AktivitasFragment.TemplateFragment;
import com.example.andrealfarysie.warungdanaspv200.BottomSheet.BsPreviewOrderFragment;
import com.example.andrealfarysie.warungdanaspv200.Helper.SharedPrefManager;
import com.example.andrealfarysie.warungdanaspv200.LoginActivity;
import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.Response.ListActivitySchedule;
import com.example.andrealfarysie.warungdanaspv200.Response.ListOrder;
import com.example.andrealfarysie.warungdanaspv200.Response.ListOutlet;
import com.example.andrealfarysie.warungdanaspv200.Response.RespActivitySchedule;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListOrder;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListOutlet;
import com.example.andrealfarysie.warungdanaspv200.TambahJadwalActivity;

import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements android.support.v7.widget.Toolbar.OnMenuItemClickListener {

    private android.support.v7.widget.Toolbar toolbar;
    LinearLayout orderContoh;
    TextView semangatPagi,hari,tanggalLokasi;
    SharedPrefManager sharedPrefManager;
    ProgressBar progressKPI,progressAktivitas,progressOrder;
    RecyclerView recylerViewKPIOutlet,recylerViewAktivitas,recylerViewOrder;
    ApiEndPoint mApiService;
    String userAgent;
    Integer userId, lastLogin;
    LinearLayout emptyLayout;
    Button tambahAktivitas;
    ImageView koneksi,koneksi2;
    View view;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPrefManager = new SharedPrefManager(getContext());
        mApiService = UtilsApi.getAPIService();
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_home);
        toolbar.setOnMenuItemClickListener(this);
        semangatPagi = view.findViewById(R.id.semangatPagi);
        hari = view.findViewById(R.id.hari);
        tanggalLokasi = view.findViewById(R.id.tanggalLokasi);
        progressKPI = view.findViewById(R.id.progressKPI);
        progressOrder = view.findViewById(R.id.progressOrder);
        progressAktivitas = view.findViewById(R.id.progressAktivitas);
        recylerViewKPIOutlet = view.findViewById(R.id.recylerViewKPIOutlet);
        recylerViewOrder = view.findViewById(R.id.recylerViewOrder);
        recylerViewAktivitas = view.findViewById(R.id.recylerViewAktivitas);
        userAgent = System.getProperty("http.agent");
        userId = sharedPrefManager.getSpId();
        emptyLayout = view.findViewById(R.id.emptyLayout);
        emptyLayout.setVisibility(View.GONE);
        tambahAktivitas = view.findViewById(R.id.tambahAktivitas);
        koneksi = view.findViewById(R.id.koneksi);
        koneksi2 = view.findViewById(R.id.koneksi2);
        koneksi.setVisibility(View.GONE);
        koneksi2.setVisibility(View.GONE);
//        final SwipeRefreshLayout pull = view.findViewById(R.id.pull);
//        pull.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                onResume();
//                pull.setRefreshing(false);
//            }
//        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        String name = sharedPrefManager.getSPName();
        String firstName = "";
        if (name.split("\\w+").length>1){
            firstName = name.substring(0,name.lastIndexOf(' '));
        }
        else {
            firstName = name;
        }
        semangatPagi.setText("Semangat Pagi ! "+ firstName);

        getHari();

        getRVKPIOutlet();

        getRvAktivitas();

        getRvOrder();

        tambahAktivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),TambahJadwalActivity.class));

            }
        });



    }

    private void getRvOrder() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recylerViewOrder.setLayoutManager(layoutManager);
        mApiService.listOrder(sharedPrefManager.getSpBranchId(),null,5).enqueue(new Callback<RespListOrder>() {
            @Override
            public void onResponse(Call<RespListOrder> call, Response<RespListOrder> response) {
                if (response.isSuccessful()){
                    List<ListOrder> list = response.body().getData();
                    if (!list.isEmpty()){
                        progressOrder.setVisibility(View.GONE);
                    }
                    recylerViewOrder.setAdapter(new ListOrderAdapter(getContext(),list));
                }
            }

            @Override
            public void onFailure(Call<RespListOrder> call, Throwable t) {
                progressOrder.setVisibility(View.GONE);
                koneksi2.setVisibility(View.VISIBLE);

            }
        });
    }

    private void getRvAktivitas() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recylerViewAktivitas.setLayoutManager(layoutManager);
        Calendar calendar = Calendar.getInstance();
        String tanggal = android.text.format.DateFormat.format("yyyy-MM-dd", calendar).toString();
        mApiService.listActivitySchedule(sharedPrefManager.getSpId(),tanggal).enqueue(new Callback<RespActivitySchedule>() {
            @Override
            public void onResponse(Call<RespActivitySchedule> call, Response<RespActivitySchedule> response) {
                if (response.isSuccessful()){

                    List<ListActivitySchedule> list = response.body().getData();
                    if (!list.isEmpty()){
                        progressAktivitas.setVisibility(View.VISIBLE);
                    }else {
                        emptyLayout.setVisibility(View.VISIBLE);
                        progressAktivitas.setVisibility(View.GONE);
                    }
                    recylerViewAktivitas.setAdapter(new ListActivityScheduleAdapter(getContext(),list));
                }
            }

            @Override
            public void onFailure(Call<RespActivitySchedule> call, Throwable t) {
                progressAktivitas.setVisibility(View.GONE);
                koneksi.setVisibility(View.VISIBLE);

            }
        });
    }

    private void getRVKPIOutlet() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recylerViewKPIOutlet.setLayoutManager(layoutManager);
        mApiService.listOutlet(sharedPrefManager.getSpBranchId()).enqueue(new Callback<RespListOutlet>() {
            @Override
            public void onResponse(Call<RespListOutlet> call, Response<RespListOutlet> response) {
                if (response.isSuccessful()){
                    progressKPI.setVisibility(View.GONE);
                    List<ListOutlet> list = response.body().getData();
                    recylerViewKPIOutlet.setAdapter(new ListOutletAdapter(getContext(),list));
                }
            }

            @Override
            public void onFailure(Call<RespListOutlet> call, Throwable t) {

            }
        });

    }



    private void getHari() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String tanggal;

        tanggal = android.text.format.DateFormat.format("dd MMMM yyyy", calendar).toString();

        tanggalLokasi.setText(tanggal + " | "+ sharedPrefManager.getSPOutletName());

        switch (day){
            case Calendar.SUNDAY:
                hari.setText("Minggu");
                break;
            case Calendar.MONDAY:
                hari.setText("Senin");
                break;
            case Calendar.TUESDAY:
                hari.setText("Selasa");
                break;
            case Calendar.WEDNESDAY:
                hari.setText("Rabu");
                break;
            case Calendar.THURSDAY:
                hari.setText("Kamis");
                break;
            case Calendar.FRIDAY:
                hari.setText("Jumat");
                break;
            case Calendar.SATURDAY:
                hari.setText("Sabtu");
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.logout:
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
                alertdialog.setMessage("Log Out ?").setCancelable(true)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                                mApiService.userLogs(userAgent, "Logout", userId).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;

        }
        return false;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        MenuInflater menuInflater =
//    }

    private void popup() {
        PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(),view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_home_2, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.logout){

                }

                return true;
            }
        });
        popupMenu.show();
    }


}
