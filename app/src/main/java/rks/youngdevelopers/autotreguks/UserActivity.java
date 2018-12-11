package rks.youngdevelopers.autotreguks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import Fragmentet.FragmentKryefaqja;
import Fragmentet.KerkoFragment;
import Fragmentet.LoginFragment;
import Fragmentet.PostimetFragment;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //krijohet navigatori
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        // thirret fragmenti kryefaqja
        onNavigationItemSelected(navigationView.getMenu().getItem(0));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1){
                getSupportFragmentManager().popBackStack();
            }
            // Default action on back pressed
            //else super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // manipulimi me navigim
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id)
        {
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
                //posto
                break;
        }

        if(fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.screen_user, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
