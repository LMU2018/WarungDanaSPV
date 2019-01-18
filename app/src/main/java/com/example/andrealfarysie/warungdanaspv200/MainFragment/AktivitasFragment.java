package com.example.andrealfarysie.warungdanaspv200.MainFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrealfarysie.warungdanaspv200.AktivitasFragment.JadwalFragment;
import com.example.andrealfarysie.warungdanaspv200.AktivitasFragment.TemplateFragment;
import com.example.andrealfarysie.warungdanaspv200.BottomSheet.BsTambahAktivitas;
import com.example.andrealfarysie.warungdanaspv200.Helper.ViewPagerAdapter;
import com.example.andrealfarysie.warungdanaspv200.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AktivitasFragment extends Fragment implements Toolbar.OnMenuItemClickListener {
    private android.support.v7.widget.Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public AktivitasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_aktivitas, container, false);
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Aktivitas");
        toolbar.inflateMenu(R.menu.menu_aktivitas);
        toolbar.setOnMenuItemClickListener(this);
        tabLayout = view.findViewById(R.id.tabsHome);
        viewPager = view.findViewById(R.id.frame_container);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new JadwalFragment(), "Jadwal Aktivitas");
        adapter.addFragment(new TemplateFragment(), "template jadwal");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                BsTambahAktivitas bsTambahAktivitas = new BsTambahAktivitas();
                bsTambahAktivitas.show(getFragmentManager(),bsTambahAktivitas.getTag());
                return true;
        }
        return false;
    }
}
