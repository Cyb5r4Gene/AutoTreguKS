package rks.youngdevelopers.autotreguks;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Fragmentet.PostHomeFragment;
import models.Airbag;
import models.Karakteristikat;
import models.Post;

public class PostActivity extends AppCompatActivity {

    public static Toolbar toolbar;

    // VARIABLAT E NEVOJSHME PER INSERTIM NE DATABAZE (REMOTE)
    public static Post post = new Post();
    public static Karakteristikat karakteristikat = new Karakteristikat();
    public static Airbag airbag = new Airbag();
    public static List<String> postFotos = new ArrayList<>();


    Fragment fragment;
    public static int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        toolbar = (Toolbar) findViewById(R.id.toolbar_post);
        setSupportActionBar(toolbar);

        fragment = new PostHomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.screen_post, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else {
            if(i==0) {
                i++;
                Toast.makeText(getApplicationContext(), "Shtypni edhe një herë (back) për të anuluar postimin!", Toast.LENGTH_LONG).show();
            } else {
                i=0;
                post = new Post();
                karakteristikat = new Karakteristikat();
                PostHomeFragment.clicked = false;
                finish();
            }
        }

    }


}
