package rks.youngdevelopers.autotreguks;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Fragments.ProfileFragment;

public class CarDealerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_dealer);

        android.support.v4.app.Fragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.screen_cardealer, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
