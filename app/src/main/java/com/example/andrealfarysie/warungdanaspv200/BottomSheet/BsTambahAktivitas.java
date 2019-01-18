package com.example.andrealfarysie.warungdanaspv200.BottomSheet;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.andrealfarysie.warungdanaspv200.R;
import com.example.andrealfarysie.warungdanaspv200.TambahJadwalActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BsTambahAktivitas extends BottomSheetDialogFragment {

    LinearLayout addJadwal,addTemplate;


    public BsTambahAktivitas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bs_add_aktivitas,container,false);
        addJadwal = view.findViewById(R.id.addJadwal);
        addTemplate = view.findViewById(R.id.addTemplate);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),TambahJadwalActivity.class));
                dismiss();
            }
        });
        addTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                BsTambahTemplate tambahTemplate = new BsTambahTemplate();
                tambahTemplate.show(getFragmentManager(),tambahTemplate.getTag());
            }
        });
    }
}
