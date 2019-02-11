package rks.youngdevelopers.autotreguks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.LocalDatabase;
import Database.RemoteDatabase;
import Fragments.HomeFragment;
import Fragments.SearchFragment;
import Fragments.SavedPostsFragment;
import Models.ErrorReport;
import Models.SearchConditions;

public class NonUserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    SearchConditions searchConditions=null;
    int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kryefaqja);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //krijohet navigatori
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        // thirret fragmenti kryefaqja
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.getMenu().findItem(R.id.nav_login).setActionView(R.layout.login_button);


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
            case (R.id.nav_kryefaqja):
                fragment = new HomeFragment();
                break;
            case (R.id.nav_kerko):
                fragment = new SearchFragment();
                break;
            case (R.id.nav_saved):
                intent = new Intent(this, SearchResultActivity.class);
                searchConditions = new SearchConditions();

                SQLiteDatabase objDb = (new LocalDatabase(this)).getReadableDatabase();
                Cursor c = null;
                List<String> savedPosts=null;
                c = objDb.rawQuery("Select * from tblSaved", null);
                if (c != null) {
                    savedPosts = new ArrayList<>();
                    c.moveToFirst();
                    if (c.getCount() > 0) {
                        for (int j = 0; j < c.getCount(); j++) {
                            savedPosts.add(c.getString(1));
                            c.moveToNext();
                        }
                        c.close();
                    }
                }
                searchConditions.setPostIdList(savedPosts);
                SearchResultActivity.searchConditions = searchConditions;
                startActivity(intent);
                break;
            case (R.id.nav_login):
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
//            case (R.id.nav_settings):
//                // ketu hapet PreferencatActivity
//                break;
            case (R.id.nav_report):
                report();

                break;
        }

        if(fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.screen_kryefaqja, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void report() {
        final Dialog reportDialog = new Dialog(this);
        reportDialog.setContentView(R.layout.dialog_report);
        reportDialog.getWindow().setLayout((width - 100), (width - 100));
        reportDialog.setTitle("");
        final Spinner reportSpinner = (Spinner) reportDialog.findViewById(R.id.spinnerReport);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.report_types,
                        R.layout.spinner_kerko);
        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        reportSpinner.setAdapter(staticAdapter);
        final EditText etDoing = (EditText)reportDialog.findViewById(R.id.etDoing);
        final EditText etError = (EditText)reportDialog.findViewById(R.id.etError);

        reportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    etError.setVisibility(View.GONE);
                    etDoing.setVisibility(View.GONE);
                } else if(position==1){
                    etDoing.setHint("Emaili i perdoruesit");
                    etError.setHint("Arsyeja e raportimit");
                    etError.setVisibility(View.VISIBLE);
                    etDoing.setVisibility(View.VISIBLE);
                } else if(position==2){

                    etDoing.setHint("Çfarë ishit duke bërë kur u paraqit errori?!");
                    etError.setHint("Përshkruani errorin që ka ndodhur!");
                    etError.setVisibility(View.VISIBLE);
                    etDoing.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btnSend = (Button)reportDialog.findViewById(R.id.btnReportSend);
        Button btnCancel = (Button)reportDialog.findViewById(R.id.btnReportCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportDialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = reportSpinner.getSelectedItemPosition();
                if(position==0){
                    Toast.makeText(getApplicationContext(), "Ju lutem zgjedhni një opsion!", Toast.LENGTH_LONG).show();
                }else if(position==1){
                    if(!etError.getText().toString().isEmpty() && !etDoing.getText().toString().isEmpty()){
                        String reporterID;
                        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                            reporterID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        } else {
                            reporterID = "AnonimUser";
                        }
                        ErrorReport error = new ErrorReport(reporterID,position,etDoing.getText().toString(), etError.getText().toString());
                        RemoteDatabase.errorReport(error).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Raportimi u dërgua!" +
                                        "\nAdministratorët do të veprojnë posa të kenë qasje!\nFaleminderit për mbështetjen tuaj!", Toast.LENGTH_LONG).show();
                                reportDialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Raportimi nuk mund të bëhet!\nProvoni më vonë", Toast.LENGTH_LONG).show();
                                reportDialog.dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Ju lutem mbushni fushat me të dhëna!", Toast.LENGTH_LONG).show();
                    }
                }  else if(position==2){
                    if(etError.getText()!=null && etDoing.getText()!=null){
                        String reporterID;
                        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                            reporterID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        } else {
                            reporterID = "AnonimUser";
                        }
                        ErrorReport error = new ErrorReport(reporterID,position,etDoing.getText().toString(), etError.getText().toString());
                        RemoteDatabase.errorReport(error).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Raportimi u dërgua!" +
                                        "\nAdministratorët do të veprojnë posa të kenë qasje!\nFaleminderit për mbështetjen tuaj!", Toast.LENGTH_LONG).show();
                                reportDialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Raportimi nuk mund të bëhet!\nProvoni më vonë", Toast.LENGTH_LONG).show();
                                reportDialog.dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Ju lutem mbushni fushat me të dhëna!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        reportDialog.show();
    }


}
