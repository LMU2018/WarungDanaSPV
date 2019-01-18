package com.example.andrealfarysie.warungdanaspv200.OrderFragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.andrealfarysie.warungdanaspv200.API.ApiEndPoint;
import com.example.andrealfarysie.warungdanaspv200.API.UtilsApi;
import com.example.andrealfarysie.warungdanaspv200.Adapter.ListOrderAdapter;
import com.example.andrealfarysie.warungdanaspv200.Adapter.ListOrderAdapter2;
import com.example.andrealfarysie.warungdanaspv200.Helper.SharedPrefManager;
import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.Response.ListOrder;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListOrder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderRejectFragment extends Fragment {
    ProgressBar progress;
    RecyclerView recyclerView;
    SharedPrefManager sharedPrefManager;
    ApiEndPoint mApiService;
    ArrayList<ListOrder> listOrders;
    ListOrderAdapter2 listOrderAdapter;

    private Integer offset = 10;
    private boolean itShouldLoadMore = true;

    public OrderRejectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_reject, container, false);
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.pull);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onAttach(getContext());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        listOrders = new ArrayList<>();
        listOrderAdapter = new ListOrderAdapter2(getContext(),listOrders);
        sharedPrefManager = new SharedPrefManager(getContext());
        mApiService = UtilsApi.getAPIService();
        progress = view.findViewById(R.id.progress);
        recyclerView = view.findViewById(R.id.recylerView);

        listOrderAdapter = new ListOrderAdapter2(getContext(),listOrders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listOrderAdapter);
        firstLoad();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Recycle view scrolling downwards...
                    // this if statement detects when user reaches the end of recyclerView, this is only time we should load more
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        // remember "!" is the same as "== false"
                        // here we are now allowed to load more, but we need to be careful
                        // we must check if itShouldLoadMore variable is true [unlocked]
                        if (itShouldLoadMore) {
                            loadMore();
                        }
                    }
                }
            }
        });
        return view;
    }

    private void loadMore() {
        itShouldLoadMore = false;
        progress.setVisibility(View.VISIBLE);
        mApiService.listOrder2(sharedPrefManager.getSpBranchId(),4,10,offset).enqueue(new Callback<RespListOrder>() {
            @Override
            public void onResponse(Call<RespListOrder> call, Response<RespListOrder> response) {
                if (response.isSuccessful()){
                    itShouldLoadMore = true;
                    if (response.body().getData()!=null) {
                        List<ListOrder> list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {
                            String namdep, nambel, cfa, outlet, tanggal, status,reason, model;
                            Integer id, idunit, plafond, idcontact;
                            namdep = list.get(i).getContactFirstName();
                            nambel = list.get(i).getContactLastName();
                            cfa = list.get(i).getCmsUsersName();
                            outlet = list.get(i).getMstOutletOutletName();
                            tanggal = list.get(i).getCreatedAt();
                            status = list.get(i).getOrderMstStatusStatus();
                            reason = list.get(i).getOrderMstReasonReason();
                            model = list.get(i).getModel();
                            id = list.get(i).getId();
                            idunit = list.get(i).getIdMstUnit();
                            plafond = list.get(i).getPlafond();
                            idcontact = list.get(i).getIdContact();
                            listOrders.add(new ListOrder(id, idcontact, outlet, namdep, nambel, status,reason,
                                    cfa, idunit, model, tanggal, plafond));

                        }
                        int index = listOrders.size();
                        offset = index;
                        progress.setVisibility(View.GONE);
                        listOrderAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListOrder> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void firstLoad() {
        itShouldLoadMore = false;
        mApiService.listOrder2(sharedPrefManager.getSpBranchId(),4,10,0).enqueue(new Callback<RespListOrder>() {
            @Override
            public void onResponse(Call<RespListOrder> call, Response<RespListOrder> response) {
                if (response.isSuccessful()){
                    itShouldLoadMore = true;
                    if (response.body().getData()!=null){
                        List<ListOrder> list = response.body().getData();
                        for (int i=0 ;i<list.size();i++){
                            String namdep, nambel, cfa, outlet, tanggal, status,reason, model;
                            Integer id, idunit, plafond, idcontact;
                            namdep = list.get(i).getContactFirstName();
                            nambel = list.get(i).getContactLastName();
                            cfa = list.get(i).getCmsUsersName();
                            outlet = list.get(i).getMstOutletOutletName();
                            tanggal = list.get(i).getCreatedAt();
                            status = list.get(i).getOrderMstStatusStatus();
                            reason = list.get(i).getOrderMstReasonReason();
                            model = list.get(i).getModel();
                            id = list.get(i).getId();
                            idunit = list.get(i).getIdMstUnit();
                            plafond = list.get(i).getPlafond();
                            idcontact = list.get(i).getIdContact();
                            listOrders.add(new ListOrder(id, idcontact, outlet, namdep, nambel, status,reason,
                                    cfa, idunit, model, tanggal, plafond));

                        }

                        listOrderAdapter.notifyDataSetChanged();
                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListOrder> call, Throwable t) {
                itShouldLoadMore = true;
            }
        });
    }
}
