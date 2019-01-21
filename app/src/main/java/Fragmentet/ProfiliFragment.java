package Fragmentet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import models.Adresa;
import models.Autosallon;
import rks.youngdevelopers.autotreguks.ProfiliActivity;
import rks.youngdevelopers.autotreguks.R;
import models.User;

public class ProfiliFragment extends Fragment {

    private FirebaseDatabase db;
    private DatabaseReference dRef;
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    String userID;
    User perdoruesi = null;
    Autosallon autosalloni = null;
    Adresa adresa = null;
    TextView tvAutoservis, tvProfilEmri, tvProfilAutosalloni, tvAutoTel, tvAutoPershkrimi, tvProfilEmail, tvProfilTel,
            tvProfilAdresa, tvProfilDatelindja, tvProfilRegistered;
    LinearLayout layoutAutosallon;
    ImageView imgProfiliUserit;

    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profili, null);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            Fragment objF = new NdryshoProfilinFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.screen_profili, objF);
            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProfiliActivity.toolbar.setBackground(new ColorDrawable(Color.argb(0, 0, 0, 0)));

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        userID = fUser.getUid();
        db = FirebaseDatabase.getInstance();
        dRef = db.getReference();

        tvProfilEmri = (TextView) view.findViewById(R.id.tvProfiliEmri);
        tvProfilEmail = (TextView) view.findViewById(R.id.tvProfilEmail);
        tvProfilAutosalloni = (TextView) view.findViewById(R.id.tvProfiliAutosalloni);
        tvAutoTel = (TextView) view.findViewById(R.id.tvProfilTelAutosallonit);
        tvAutoPershkrimi = (TextView) view.findViewById(R.id.tvProfilPershkrimiAutosallonit);
        tvProfilAdresa = (TextView) view.findViewById(R.id.tvProfilAdresa);
        tvProfilTel = (TextView) view.findViewById(R.id.tvProfilTel);
        tvProfilDatelindja = (TextView) view.findViewById(R.id.tvProfilDatelindja);
        tvProfilRegistered = (TextView) view.findViewById(R.id.tvProfilRegistered);
        layoutAutosallon = (LinearLayout) view.findViewById(R.id.layoutProfilAutosallon);
        tvAutoservis = (TextView) view.findViewById(R.id.tvAutoservisi);
        imgProfiliUserit = (ImageView) view.findViewById(R.id.imgProfiliUserit);

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void userData(DataSnapshot dataSnapshot) {
        try {
            perdoruesi = dataSnapshot.child("users").child(userID).getValue(User.class);
            autosalloni = dataSnapshot.child("autosallonet").child(userID).getValue(Autosallon.class);
            adresa = dataSnapshot.child("adresat").child(userID).getValue(Adresa.class);

            tvProfilEmri.setText(perdoruesi.getEmri()+" "+perdoruesi.getMbiemri());
            tvProfilEmail.setText(perdoruesi.getEmail());
            tvProfilRegistered.setText(perdoruesi.getDataKrijimit());
            tvProfilDatelindja.setText(perdoruesi.getDatelindja());
            tvProfilTel.setText(perdoruesi.getTel());

            String[] qyteti = getResources().getStringArray(R.array.qytetet);

            tvProfilAdresa.setText("Rr."+adresa.getRruga()+", Nr."+adresa.getNr()+", "+adresa.getKodi()+" "
                    +qyteti[Integer.valueOf(adresa.getQyteti())]);
            if(Integer.valueOf(perdoruesi.getTipiLlogarise())==2) {
                tvProfilAutosalloni.setText(autosalloni.getEmri());
                tvAutoTel.setText(autosalloni.getTelefoni());
                tvAutoPershkrimi.setText(autosalloni.getPershkrimi());
            }

            if(perdoruesi.getTipiLlogarise().equals("1"))
            {
                layoutAutosallon.setVisibility(View.GONE);
                tvAutoservis.setVisibility(View.GONE);
            }
            else
            {
                layoutAutosallon.setVisibility(View.VISIBLE);
            }


        } catch (Exception e) {

        }
    }

}
