package com.example.andrealfarysie.warungdanaspv200.AktivitasFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.Adapter.ListActivityScheduleAdapter;
import com.example.andrealfarysie.warungdanaspv200.Helper.SharedPrefManager;
import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.Response.ListActivitySchedule;
import com.example.andrealfarysie.warungdanaspv200.Response.RespActivitySchedule;
import com.example.andrealfarysie.warungdanaspv200.TambahJadwalActivity;

import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class JadwalFragment extends Fragment {
    HorizontalCalendar horizontalCalendar;
    String selectedDate;
    ProgressBar progress;
    RecyclerView recyclerView;
    ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;
    LinearLayout emptyLayout;
    Button tambahAktivitas;

    public JadwalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jadwal, container, false);
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(getContext());
        progress = view.findViewById(R.id.progress);
        recyclerView = view.findViewById(R.id.recylerView);
        emptyLayout = view.findViewById(R.id.emptyLayout);
        emptyLayout.setVisibility(View.GONE);
        tambahAktivitas = view.findViewById(R.id.tambahAktivitas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        final Calendar defaultSelectedDate = Calendar.getInstance();
        horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .sizeMiddleText(20)
                .showBottomText(true)
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .build();
        selectedDate = android.text.format.DateFormat.format("yyyy-MM-dd",defaultSelectedDate).toString();
        getList();
        return view;
    }

    private void getList() {
        mApiService.listActivitySchedule(sharedPrefManager.getSpId(),selectedDate).enqueue(new Callback<RespActivitySchedule>() {
            @Override
            public void onResponse(Call<RespActivitySchedule> call, Response<RespActivitySchedule> response) {
                if (response.isSuccessful()){
                    progress.setVisibility(View.GONE);
                    if (response.body().getData()!=null){
                        List<ListActivitySchedule> list = response.body().getData();
                        recyclerView.setAdapter(new ListActivityScheduleAdapter(getContext(),list));
                        if (list.isEmpty()){
                            emptyLayout.setVisibility(View.VISIBLE);

                        }else {
                            emptyLayout.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RespActivitySchedule> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
            horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
                @Override
                public void onDateSelected(Calendar date, int position) {
                    selectedDate = android.text.format.DateFormat.format("yyyy-MM-dd",date).toString();
                    getList();
                }
            });

            tambahAktivitas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(),TambahJadwalActivity.class));
                }
            });
    }
}
