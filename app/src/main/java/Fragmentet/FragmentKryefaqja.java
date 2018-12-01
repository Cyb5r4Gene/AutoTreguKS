package Fragmentet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import rks.youngdevelopers.autotreguks.KryefaqjaActivity;
import rks.youngdevelopers.autotreguks.R;

public class FragmentKryefaqja extends Fragment {

    RelativeLayout layoutFamiljar, layoutSport, layoutSuv, layoutKamionete, layoutKombi;

    Button btnKerko;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kryefaqja, null);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        //ketu zhvillohet interaksioni me UI te FragmentKryefaqja

        btnKerko = (Button) v.findViewById(R.id.btnKerko);

        layoutFamiljar = (RelativeLayout)v.findViewById(R.id.layoutFamiljar);
        layoutSport = (RelativeLayout)v.findViewById(R.id.layoutSport);

        btnKerko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ketu vendoset eventi i klikimit te butonit kerko


            }
        });

        layoutFamiljar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ketu shfaqen automjetet familjare
            }
        });

        layoutSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Shfaqet kerkimi i automjeteve sportive
            }
        });


    }
}
