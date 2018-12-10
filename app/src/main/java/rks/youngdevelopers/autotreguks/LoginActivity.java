package rks.youngdevelopers.autotreguks;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import Fragmentet.KrijoLlogariFragment;
import Fragmentet.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    TabLayout tabs;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(viewPager);

        tabs = (TabLayout)findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapteri = new SectionsPageAdapter(getSupportFragmentManager());
        adapteri.addFragment(new LoginFragment(), "Kyçu");
        adapteri.addFragment(new KrijoLlogariFragment(), "Krijo Llogari");
        viewPager.setAdapter(adapteri);
    }


}
