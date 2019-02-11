package rks.youngdevelopers.autotreguks;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Fragments.PostHomeFragment;
import Models.Airbag;
import Models.Features;
import Models.Post;

public class PostActivity extends AppCompatActivity {

    public static Toolbar toolbar;

    // VARIABLAT E NEVOJSHME PER INSERTIM NE DATABAZE (REMOTE)
    public static Post post = new Post();
    public static Features features = new Features();
    public static Airbag airbag = new Airbag();
    public static List<String> postFotos = new ArrayList<>();


    Fragment fragment;
    public static int i=0;
    public static TextView tvTitle;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        toolbar = (Toolbar) findViewById(R.id.toolbar_post);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvTitle = (TextView)findViewById(R.id.tvPostTitle);
        imgBack = (ImageView)findViewById(R.id.postBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
                features = new Features();
                PostHomeFragment.clicked = false;
                finish();
            }
        }

    }

    public static void setTitle(String titulli){
        tvTitle.setText(titulli);
    }


}
