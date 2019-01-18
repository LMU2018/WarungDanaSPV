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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.andrealfarysie.warungdanaspv200.API.OnLoadMoreListener;
import com.example.andrealfarysie.warungdanaspv200.BottomSheet.BsPreviewOrderFragment;
import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.Response.ListOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private Context context;
    private List<ListOrder> contacts;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public OrderAdapter(RecyclerView recyclerView, List<ListOrder> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return contacts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_list_order_home, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            final ListOrder contact = contacts.get(position);
            final UserViewHolder userViewHolder = (UserViewHolder) holder;

            if (contact.getContactLastName() != null) {
                userViewHolder.konsumen.setText(contact.getContactFirstName() + " " + contact.getContactLastName());
            } else {
                userViewHolder.konsumen.setText(contact.getContactFirstName());
            }

            userViewHolder.cfa.setText(contact.getCmsUsersName());
            userViewHolder.outlet.setText(contact.getMstOutletOutletName());
            userViewHolder.tanggal.setText(convertTimeDay(contact.getCreatedAt()));
            userViewHolder.status.setText(contact.getOrderMstStatusStatus());

            userViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BsPreviewOrderFragment bsPreviewOrderFragment = new BsPreviewOrderFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("konsumen", userViewHolder.konsumen.getText().toString());
                    bundle.putInt("idOrder", contact.getId());
                    bundle.putInt("idContact", contact.getIdContact());
                    bundle.putInt("idUnit", contact.getIdMstUnit());
                    bundle.putString("status", contact.getOrderMstStatusStatus());
                    bundle.putString("reason", contact.getOrderMstReasonReason());
                    bundle.putString("tanggal", userViewHolder.tanggal.getText().toString());
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    bsPreviewOrderFragment.setArguments(bundle);
                    bsPreviewOrderFragment.show(fragmentManager, bsPreviewOrderFragment.getTag());
                }
            });


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {
        TextView konsumen, cfa, outlet, tanggal, status;
        CardView cardView;

        public UserViewHolder(View view) {
            super(view);
            konsumen = view.findViewById(R.id.konsumen);
            cfa = view.findViewById(R.id.cfa);
            outlet = view.findViewById(R.id.outlet);
            tanggal = view.findViewById(R.id.tanggal);
            status = view.findViewById(R.id.status);
            cardView = view.findViewById(R.id.cardView);
        }
    }
}