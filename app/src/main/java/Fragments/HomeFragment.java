package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

import Models.CarTypes;
import Models.SearchConditions;
import rks.youngdevelopers.autotreguks.NonUserActivity;
import rks.youngdevelopers.autotreguks.R;
import rks.youngdevelopers.autotreguks.SearchResultActivity;
import rks.youngdevelopers.autotreguks.UserActivity;

public class HomeFragment extends Fragment {

    RelativeLayout layoutFamiljar, layoutSport, layoutSuv, layoutKamionete, layoutTransporterN, layoutTransporterM;

    Button btnKerko;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kryefaqja, null);
    }


    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        //ketu zhvillohet interaksioni me UI te HomeFragment
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            ((NonUserActivity) getActivity()).getSupportActionBar().setTitle(R.string.kryefaqja);
        else
            ((UserActivity) getActivity()).getSupportActionBar().setTitle(R.string.kryefaqja);

        btnKerko = (Button) v.findViewById(R.id.btnKerko);

        layoutFamiljar = (RelativeLayout) v.findViewById(R.id.layoutFamiljar);
        layoutSport = (RelativeLayout) v.findViewById(R.id.layoutSport);
        layoutSuv = (RelativeLayout) v.findViewById(R.id.layoutSuv);
        layoutKamionete = (RelativeLayout) v.findViewById(R.id.layoutKamionete);
        layoutTransporterN = (RelativeLayout) v.findViewById(R.id.layoutTransporterN);
        layoutTransporterM = (RelativeLayout) v.findViewById(R.id.layoutTransporterM);

        btnKerko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ketu vendoset eventi i klikimit te butonit kerko
                Fragment fragment = new SearchFragment();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    ft.replace(R.id.screen_kryefaqja, fragment);
                else
                    ft.replace(R.id.screen_user, fragment);

                ft.commit();


            }
        });

        layoutFamiljar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*SEARCH CONDITIONS FOR FAMILY CAR*/
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                SearchConditions searchConditions = new SearchConditions();
                searchConditions.setSeatsNr(5);
                searchConditions.setDoorsNr(2);
                searchConditions.setDamaged(1);
                searchConditions.getVehicle().setCaravan(true);
                SearchResultActivity.searchConditions = searchConditions;
                startActivity(intent);
            }
        });

        layoutSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*SEARCH CONDITIONS FOR SPORT CARs*/
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                SearchConditions searchConditions = new SearchConditions();
                searchConditions.setDoorsNr(1);
                searchConditions.setDamaged(1);
                searchConditions.getVehicle().setSedan(true);
                searchConditions.getInteriorSearch().setSportSeats(true);
                searchConditions.setSportAmortization(true);
                searchConditions.setSportPacket(true);
                SearchResultActivity.searchConditions = searchConditions;
                startActivity(intent);
            }
        });

        layoutSuv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*SEARCH CONTDITIONS FOR SUVs*/
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                SearchConditions searchConditions = new SearchConditions();
                searchConditions.getCarTypes().add(new CarTypes(4, 17));
                searchConditions.getCarTypes().add(new CarTypes(4, 18));
                searchConditions.getCarTypes().add(new CarTypes(4, 19));
                searchConditions.getCarTypes().add(new CarTypes(4, 37));
                searchConditions.getCarTypes().add(new CarTypes(4, 38));
                searchConditions.getCarTypes().add(new CarTypes(6, 69));
                searchConditions.getCarTypes().add(new CarTypes(6, 70));
                searchConditions.getCarTypes().add(new CarTypes(6, 71));
                searchConditions.getCarTypes().add(new CarTypes(6, 72));
                searchConditions.getCarTypes().add(new CarTypes(6, 73));
                searchConditions.getCarTypes().add(new CarTypes(6, 74));
                searchConditions.getCarTypes().add(new CarTypes(6, 75));
                searchConditions.getCarTypes().add(new CarTypes(22, 1));
                searchConditions.getCarTypes().add(new CarTypes(22, 2));
                searchConditions.getCarTypes().add(new CarTypes(22, 3));
                searchConditions.getCarTypes().add(new CarTypes(22, 4));
                searchConditions.getCarTypes().add(new CarTypes(22, 5));
                searchConditions.getCarTypes().add(new CarTypes(22, 6));
                searchConditions.getCarTypes().add(new CarTypes(22, 7));
                searchConditions.getCarTypes().add(new CarTypes(22, 8));
                searchConditions.getCarTypes().add(new CarTypes(22, 9));
                searchConditions.getCarTypes().add(new CarTypes(22, 10));
                searchConditions.getCarTypes().add(new CarTypes(22, 11));
                searchConditions.getCarTypes().add(new CarTypes(22, 12));
                searchConditions.getCarTypes().add(new CarTypes(30, 1));
                searchConditions.getCarTypes().add(new CarTypes(30, 2));
                searchConditions.getCarTypes().add(new CarTypes(30, 3));
                searchConditions.getCarTypes().add(new CarTypes(30, 4));
                searchConditions.getCarTypes().add(new CarTypes(30, 5));
                searchConditions.getCarTypes().add(new CarTypes(30, 6));
                searchConditions.getCarTypes().add(new CarTypes(30, 7));
                searchConditions.getCarTypes().add(new CarTypes(30, 8));
                searchConditions.getCarTypes().add(new CarTypes(30, 9));
                searchConditions.getCarTypes().add(new CarTypes(30, 10));
                searchConditions.getCarTypes().add(new CarTypes(30, 11));
                searchConditions.getCarTypes().add(new CarTypes(30, 12));
                searchConditions.getCarTypes().add(new CarTypes(42, 29));
                searchConditions.getCarTypes().add(new CarTypes(42, 30));
                searchConditions.getCarTypes().add(new CarTypes(42, 31));
                searchConditions.getCarTypes().add(new CarTypes(42, 32));
                searchConditions.getCarTypes().add(new CarTypes(42, 33));
                searchConditions.getCarTypes().add(new CarTypes(42, 34));
                searchConditions.getCarTypes().add(new CarTypes(42, 35));
                searchConditions.getCarTypes().add(new CarTypes(42, 37));
                searchConditions.getCarTypes().add(new CarTypes(60, 41));
                searchConditions.getCarTypes().add(new CarTypes(60, 22));
                SearchResultActivity.searchConditions = searchConditions;
                startActivity(intent);
            }
        });

        layoutKamionete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* SEARCH CONDITIONS FOR CAMIONETE*/
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                SearchConditions searchConditions = new SearchConditions();
                searchConditions.getCarTypes().add(new CarTypes(60, 2));
                searchConditions.getCarTypes().add(new CarTypes(42, 51));
                searchConditions.getCarTypes().add(new CarTypes(21, 17));
                searchConditions.getCarTypes().add(new CarTypes(21, 16));
                searchConditions.getCarTypes().add(new CarTypes(18, 15));
                SearchResultActivity.searchConditions = searchConditions;
                startActivity(intent);
            }
        });

        layoutTransporterN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ketu shfaqen automjetet familjare
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                SearchConditions searchConditions = new SearchConditions();
                searchConditions.getCarTypes().add(new CarTypes(60, 7));
                searchConditions.getCarTypes().add(new CarTypes(60, 24));
                searchConditions.getCarTypes().add(new CarTypes(60, 34));
                searchConditions.getCarTypes().add(new CarTypes(60, 35));
                searchConditions.getCarTypes().add(new CarTypes(60, 36));
                searchConditions.getCarTypes().add(new CarTypes(60, 37));
                searchConditions.getCarTypes().add(new CarTypes(42, 45));
                searchConditions.getCarTypes().add(new CarTypes(42, 48));
                searchConditions.getCarTypes().add(new CarTypes(42, 49));
                searchConditions.getCarTypes().add(new CarTypes(42, 50));
                searchConditions.setSeatsNr(6);
                SearchResultActivity.searchConditions = searchConditions;
                startActivity(intent);
            }
        });

        layoutTransporterM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Shfaqet searchConditions i automjeteve sportive
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                SearchConditions searchConditions = new SearchConditions();
                searchConditions.getCarTypes().add(new CarTypes(60, 7));
                searchConditions.getCarTypes().add(new CarTypes(60, 24));
                searchConditions.getCarTypes().add(new CarTypes(60, 34));
                searchConditions.getCarTypes().add(new CarTypes(60, 35));
                searchConditions.getCarTypes().add(new CarTypes(60, 36));
                searchConditions.getCarTypes().add(new CarTypes(60, 37));
                searchConditions.getCarTypes().add(new CarTypes(42, 45));
                searchConditions.getCarTypes().add(new CarTypes(42, 48));
                searchConditions.getCarTypes().add(new CarTypes(42, 49));
                searchConditions.getCarTypes().add(new CarTypes(42, 50));
                searchConditions.setSeatsNr(2);
                SearchResultActivity.searchConditions = searchConditions;
                startActivity(intent);
            }
        });


    }
}
