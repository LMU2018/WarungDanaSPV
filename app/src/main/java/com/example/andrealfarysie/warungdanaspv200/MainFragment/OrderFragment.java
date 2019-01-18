package com.example.andrealfarysie.warungdanaspv200.MainFragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.andrealfarysie.warungdanaspv200.Helper.ViewPagerAdapter;
import com.example.andrealfarysie.warungdanaspv200.OrderFragment.OrderApproveFragment;
import com.example.andrealfarysie.warungdanaspv200.OrderFragment.OrderBatalFragment;
import com.example.andrealfarysie.warungdanaspv200.OrderFragment.OrderCairFragment;
import com.example.andrealfarysie.warungdanaspv200.OrderFragment.OrderProsesFragment;
import com.example.andrealfarysie.warungdanaspv200.OrderFragment.OrderRejectFragment;
import com.example.andrealfarysie.warungdanaspv200.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    private android.support.v7.widget.Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public OrderFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Order");
//        toolbar.inflateMenu(R.menu.menu_aktivitas);
        tabLayout = view.findViewById(R.id.tabsHome);
        viewPager = view.findViewById(R.id.frame_container);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OrderProsesFragment(), "Proses");
        adapter.addFragment(new OrderApproveFragment(), "approve");
        adapter.addFragment(new OrderCairFragment(), "cair");
        adapter.addFragment(new OrderRejectFragment(), "reject");
        adapter.addFragment(new OrderBatalFragment(), "batal");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }



}
