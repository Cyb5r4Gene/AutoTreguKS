package rks.youngdevelopers.autotreguks;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import Database.LocalDatabase;
import Database.RemoteDatabase;
import Fragments.HomeFragment;
import Fragments.SearchFragment;
import Models.Autosallon;
import Models.ErrorReport;
import Models.Post;
import Models.SearchConditions;
import Models.User;
import Other.UniversalImageLoader;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase db;
    private DatabaseReference dRef;
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    String userID;
    User perdoruesi = null;
    NavigationView navigationView;
    String url;
    Intent intent;
    SearchConditions searchConditions;
    int width, height;

    public TextView tvEmriMbiemri, tvAutosalloniDescription, tvPostsNr;
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

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

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
                fragment = new HomeFragment();
                break;
            case (R.id.nav_kerko_user):
                fragment = new SearchFragment();
                break;
            case (R.id.nav_saved_user):
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
            case (R.id.nav_car_dealers):
                startActivity(new Intent(getApplicationContext(), CarDealersMap.class));
                break;
            case (R.id.nav_logout):
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
                break;
            case (R.id.nav_add_post):
                Intent objPost = new Intent(this, PostActivity.class);
                startActivity(objPost);
                break;
            case (R.id.nav_my_posts):
                intent = new Intent(this, SearchResultActivity.class);
                searchConditions = new SearchConditions();
                searchConditions.setOwnerID(userID);
                SearchResultActivity.searchConditions = searchConditions;
                startActivity(intent);
                break;
            case (R.id.nav_profile):
                Intent profili = new Intent(UserActivity.this, ProfiliActivity.class);
                startActivity(profili);
                break;
            case (R.id.nav_report_user):
                report();
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
        View headerView = navigationView.getHeaderView(0);
        tvEmriMbiemri = (TextView) headerView.findViewById(R.id.tvEmriMbiemri);
        tvAutosalloniDescription = (TextView) headerView.findViewById(R.id.tvAutosalloniDescription);
        imgProfili = (ImageView) headerView.findViewById(R.id.profili);
        tvPostsNr = (TextView)headerView.findViewById(R.id.tvPostsNr);
        int i=0;
        for(DataSnapshot ds:dataSnapshot.child("posts").getChildren()){
            Post post = ds.getValue(Post.class);
            if(post.getPronariID().equals(userID)){
                i++;
            }
        }
        tvPostsNr.setText(String.valueOf(i));

        loadImage();

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

    private void loadImage() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference =firebaseStorage.getReference();

        storageReference.child("userImages/"+fUser.getUid()+"/profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = uri.toString();
                UniversalImageLoader.setImage(url, imgProfili, null, "");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


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
                }else if(position==2 || position==1){
                    if(etError.getText()!=null && etDoing.getText()!=null && etError.getText().toString().trim().length()>0 && etDoing.getText().toString().trim().length()>0){
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
