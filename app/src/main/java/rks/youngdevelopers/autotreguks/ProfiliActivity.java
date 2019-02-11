package rks.youngdevelopers.autotreguks;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import Fragments.ProfileFragment;

public class ProfiliActivity extends AppCompatActivity {

    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profili);

        toolbar = (Toolbar) findViewById(R.id.toolbar_profili);
        setSupportActionBar(toolbar);

        Fragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.screen_profili, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
        else {
            finish();
        }

    }


}
