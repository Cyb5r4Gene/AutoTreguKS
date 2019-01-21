package rks.youngdevelopers.autotreguks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Database.RemoteDatabase;
import Fragmentet.FragmentKryefaqja;
import Fragmentet.KerkoFragment;
import Fragmentet.PostimetFragment;
import models.Autosallon;
import models.User;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase db;
    private DatabaseReference dRef;
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    String userID;
    User perdoruesi = null;
    NavigationView navigationView;


    public TextView tvEmriMbiemri, tvAutosalloniDescription;
    private ImageView imgProfili;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        db = FirebaseDatabase.getInstance();
        dRef = db.getReference();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        userID = fUser.getUid();

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData(dataSnapshot);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //krijohet navigatori
        navigationView = (NavigationView) findViewById(R.id.nav_view_user);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        // thirret fragmenti kryefaqja
        onNavigationItemSelected(navigationView.getMenu().getItem(0));

        navigationView.getMenu().findItem(R.id.nav_logout).setActionView(R.layout.nav_button);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // manipulimi me navigim
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id) {
            case (R.id.nav_kryefaqja_user):
                fragment = new FragmentKryefaqja();
                break;
            case (R.id.nav_kerko_user):
                fragment = new KerkoFragment();
                break;
            case (R.id.nav_saved_user):
                fragment = new PostimetFragment();
                break;
            case (R.id.nav_logout):
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), HyrjaActivity.class));
                break;
            case (R.id.nav_post):
                Intent objPost = new Intent(this, PostActivity.class);
                startActivity(objPost);
                break;
        }

        if (fragment != null) {
            nderrimiFragmenteve(fragment);
        }
        else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void userData(DataSnapshot dataSnapshot) {
        tvEmriMbiemri = (TextView) findViewById(R.id.tvEmriMbiemri);
        tvAutosalloniDescription = (TextView) findViewById(R.id.tvAutosalloniDescription);
        imgProfili = (ImageView) findViewById(R.id.profili);

        imgProfili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profili = new Intent(UserActivity.this, ProfiliActivity.class);
                startActivity(profili);
            }
        });

        try {
            perdoruesi = RemoteDatabase.getPerdoruesi(dataSnapshot, userID);
            tvEmriMbiemri.setText(perdoruesi.getEmri() + " " + perdoruesi.getMbiemri());
            if(perdoruesi.getTipiLlogarise().equals("1"))
            {
                tvAutosalloniDescription.setVisibility(View.GONE);
            }
            else {
                Autosallon autosallon = RemoteDatabase.getAutosalloni(dataSnapshot, userID);
                tvAutosalloniDescription.setText(autosallon.getEmri());
                tvAutosalloniDescription.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }
    }

    private void nderrimiFragmenteve(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.screen_user, fragment);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        drawer.closeDrawer(GravityCompat.START);
    }


}
