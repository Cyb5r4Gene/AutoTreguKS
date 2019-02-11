package Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import Adapters.SearchRecyclerAdapter;
import Models.Adresa;
import Models.Autosallon;
import Models.SearchConditions;
import rks.youngdevelopers.autotreguks.ProfiliActivity;
import rks.youngdevelopers.autotreguks.R;
import Models.User;
import Other.UniversalImageLoader;

public class ProfileFragment extends Fragment implements SearchRecyclerAdapter.OnPostListener {

    private FirebaseDatabase db;
    private DatabaseReference dRef;
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    public static String userID;
    User perdoruesi = null;
    Autosallon autosalloni = null;
    Adresa adresa = null;
    TextView tvAutoservis, tvProfilEmri, tvProfilAutosalloni, tvAutoTel, tvAutoPershkrimi, tvProfilEmail, tvProfilTel,
            tvProfilAdresa, tvProfilDatelindja, tvProfilRegistered;
    LinearLayout layoutAutosallon;
    ImageView imgProfiliUserit;
    RecyclerView recyclerProfile;
    private MapView mapView;
    private GoogleMap googleMap;

    String url;
    ProgressDialog progressDialog;
    public static List<List> finalResult;
    SearchRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profili, null);
        setHasOptionsMenu(true);
        try {
            ProfiliActivity.toolbar.setBackground(new ColorDrawable(Color.argb(0, 0, 0, 0)));

        } catch (Exception e) {

        }

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
        mapView = (MapView) view.findViewById(R.id.profileMapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();


        recyclerProfile = (RecyclerView) view.findViewById(R.id.recyclerProfile);
        recyclerProfile.setHasFixedSize(true);
        recyclerProfile.setLayoutManager(new LinearLayoutManager(getContext()));

        loadImage();

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(fUser!=null && userID!=null && fUser.getUid().equals(userID)) {
            inflater.inflate(R.menu.profile_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(fUser!=null && userID!=null && fUser.getUid().equals(userID)) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            Fragment objF = new UpdateProfileFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.screen_profili, objF);
            ft.commit();
        }}
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void userData(DataSnapshot dataSnapshot) {
        try {
            perdoruesi = dataSnapshot.child("users").child(userID).getValue(User.class);
            autosalloni = dataSnapshot.child("carDealers").child(userID).getValue(Autosallon.class);
            adresa = dataSnapshot.child("addresses").child(perdoruesi.getAdresaID()).getValue(Adresa.class);

            tvProfilEmri.setText(perdoruesi.getEmri() + " " + perdoruesi.getMbiemri());
            tvProfilEmail.setText(perdoruesi.getEmail());
            tvProfilRegistered.setText(perdoruesi.getDataKrijimit());
            tvProfilDatelindja.setText(perdoruesi.getDatelindja());
            tvProfilTel.setText(perdoruesi.getTel());

            String[] qyteti = getResources().getStringArray(R.array.qytetet);

            tvProfilAdresa.setText("Rr." + adresa.getRruga() + ", Nr." + adresa.getNr() + ", " + adresa.getKodi() + " "
                    + qyteti[Integer.valueOf(adresa.getQyteti())]);
            if (Integer.valueOf(perdoruesi.getTipiLlogarise()) == 2) {
                tvProfilAutosalloni.setText(autosalloni.getEmri());
                tvAutoTel.setText(autosalloni.getTelefoni());
                tvAutoPershkrimi.setText(autosalloni.getPershkrimi());
            }

            if (!adresa.getLat().equals("-") && !adresa.getLng().equals("-")) {
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        googleMap = mMap;
                        LatLng autosalloniMap = new LatLng(Double.parseDouble(adresa.getLat()), Double.parseDouble(adresa.getLng()));
                        if (autosalloni != null)
                            googleMap.addMarker(new MarkerOptions().position(autosalloniMap).title(autosalloni.getEmri()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));
                        else
                            googleMap.addMarker(new MarkerOptions().position(autosalloniMap).title(perdoruesi.getEmri()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));

                        googleMap.getUiSettings().setAllGesturesEnabled(false);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(autosalloniMap, 17));
                    }
                });
            } else
                mapView.setVisibility(View.GONE);


            if (perdoruesi.getTipiLlogarise().equals("1")) {
                layoutAutosallon.setVisibility(View.GONE);
                tvAutoservis.setVisibility(View.GONE);
            } else {
                layoutAutosallon.setVisibility(View.VISIBLE);
            }

            finalResult = new ArrayList<>();
            SearchConditions searchConditions = new SearchConditions();
            searchConditions.setOwnerID(userID);
            finalResult = Database.RemoteDatabase.postSearch(dataSnapshot.child("posts"), searchConditions);
            adapter = new SearchRecyclerAdapter(finalResult.get(0), finalResult.get(1), getContext(), this);
            recyclerProfile.setAdapter(adapter);


        } catch (Exception e) {

        }


    }

    private void loadImage() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child("userImages/" + fUser.getUid() + "/profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = uri.toString();
                UniversalImageLoader.setImage(url, imgProfiliUserit, null, "");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onPostClick(int position, int id) {

    }
}
