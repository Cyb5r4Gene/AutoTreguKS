package rks.youngdevelopers.autotreguks;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapters.PostPagerAdapter;
import Database.LocalDatabase;
import Database.RemoteDatabase;
import Models.Adresa;
import Models.Autosallon;
import Models.ErrorReport;
import Models.Post;
import Models.User;

public class PostViewActivity extends AppCompatActivity {

    List<String> images;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ViewPager viewPager;
    PostPagerAdapter adapter;
    public static String postID;
    int i;
    static MapView mapView;
    GoogleMap googleMap;
    Autosallon autosallon;
    User user;
    Post post;

    ImageView imgBack, imgP;

    TextView tvTitle, tvPrice, tvKm, tvVehicle, tvFirstRegistration, tvFuel, tvPower, tvCondition, tvTransmission, tvSeatsNr,
            tvDoorsNr, tvLicencePlates, tvLicencePlatesDesc, tvColor, tvInterior, tvBluetooth, tvOnBoard, tvCd, tvElectricWindow,
            tvElectricSeats, tvHeatedSeats, tvSportSeats, tvClimatisation, tvMp3, tvAux, tvSteeringWheel, tvNa, tvShiber, tvPanorame,
            tvRoofRack, tvCentral, tvMirror, tvSportAmortization, tvSportPacket, tvAbs, tvAirbag, tv4x4, tvEsp, tvAdaptingLights,
            tvLightSensor, tvFog, tvXenon, tvBiXenon, tvRain, tvStartStop, tvDescription, tvSellerName, tvSellerAddress, tvSellerTel, tvSellerEmail;
    Button btnReport;

    int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;


        tvTitle = (TextView) findViewById(R.id.tvPostViewTitle);
        tvPrice = (TextView) findViewById(R.id.tvPostViewPrice);
        tvVehicle = (TextView) findViewById(R.id.tvVehicle);
        tvFirstRegistration = (TextView) findViewById(R.id.firstRegistration);
        tvKm = (TextView) findViewById(R.id.tvKm);
        tvFuel = (TextView) findViewById(R.id.tvFuel);
        tvPower = (TextView) findViewById(R.id.tvPower);
        tvTransmission = (TextView) findViewById(R.id.tvPostViewTransmission);
        tvCondition = (TextView) findViewById(R.id.tvCondition);
        tvSeatsNr = (TextView) findViewById(R.id.tvPostViewSeats);
        tvDoorsNr = (TextView) findViewById(R.id.tvPostViewDoors);
        tvLicencePlates = (TextView) findViewById(R.id.tvPostViewLicencePlates);
        tvLicencePlatesDesc = (TextView) findViewById(R.id.tvPlatesDesc);
        tvColor = (TextView) findViewById(R.id.tvPostViewColor);
        tvInterior = (TextView) findViewById(R.id.tvPostViewInterior);
        tvBluetooth = (TextView) findViewById(R.id.tvPostViewBluetooth);
        tvOnBoard = (TextView) findViewById(R.id.tvPostViewOnBoard);
        tvCd = (TextView) findViewById(R.id.tvPostViewCd);
        tvElectricWindow = (TextView) findViewById(R.id.tvPostViewElectricWindows);
        tvElectricSeats = (TextView) findViewById(R.id.tvPostViewElectricSeads);
        tvHeatedSeats = (TextView) findViewById(R.id.tvPostViewHeatedSeats);
        tvSportSeats = (TextView) findViewById(R.id.tvPostViewSportSeats);
        tvClimatisation = (TextView) findViewById(R.id.tvPostViewClimatisation);
        tvMp3 = (TextView) findViewById(R.id.tvPostViewMp3);
        tvAux = (TextView) findViewById(R.id.tvPostViewAux);
        tvSteeringWheel = (TextView) findViewById(R.id.tvPostViewSteeringWheel);
        tvNa = (TextView) findViewById(R.id.tvPostViewNav);
        tvShiber = (TextView) findViewById(R.id.tvPostViewShiber);
        tvPanorame = (TextView) findViewById(R.id.tvPostViewPanorame);
        tvRoofRack = (TextView) findViewById(R.id.tvPostViewRoofRack);
        tvCentral = (TextView) findViewById(R.id.tvPostViewCentral);
        tvSportAmortization = (TextView) findViewById(R.id.tvPostViewSportAmortisation);
        tvSportPacket = (TextView) findViewById(R.id.tvPostViewSportPacket);
        tvAbs = (TextView) findViewById(R.id.tvPostViewAbs);
        tvAirbag = (TextView) findViewById(R.id.tvPostViewAirbag);
        tv4x4 = (TextView) findViewById(R.id.tvPostViewFour);
        tvEsp = (TextView) findViewById(R.id.tvPostViewEsp);
        tvAdaptingLights = (TextView) findViewById(R.id.tvPostViewAdaptingLights);
        tvLightSensor = (TextView) findViewById(R.id.tvPostViewLightSensor);
        tvXenon = (TextView) findViewById(R.id.tvPostViewXenon);
        tvBiXenon = (TextView) findViewById(R.id.tvPostViewBiXenon);
        tvRain = (TextView) findViewById(R.id.tvPostViewRainSensor);
        tvStartStop = (TextView) findViewById(R.id.tvPostViewStartStop);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvSellerName = (TextView) findViewById(R.id.tvSellerName);
        tvSellerAddress = (TextView) findViewById(R.id.tvSellerAddress);
        tvFog = (TextView) findViewById(R.id.tvPostViewFog);
        tvMirror = (TextView) findViewById(R.id.tvPostViewElectricMirrors);
        btnReport = (Button)findViewById(R.id.btnPostViewReport);
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report();
            }
        });
        tvSellerTel = (TextView) findViewById(R.id.tvSellerTel);
        tvSellerEmail = (TextView) findViewById(R.id.tvSellerEmail);

        imgBack = (ImageView) findViewById(R.id.postViewBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgP = (ImageView) findViewById(R.id.imgSavePost);
        imgP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser()==null || !FirebaseAuth.getInstance().getCurrentUser().getUid().equals(post.getPronariID())) {
                    savePost();
                } else{
                    /**
                     *
                     *
                     *
                     KETU BEHET EDITIMI I POSTIMIT
                    *
                    *
                    *
                    * */
                }
            }
        });

        mapView = (MapView) findViewById(R.id.postMapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dRef = firebaseDatabase.getReference();

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebaseStorage = FirebaseStorage.getInstance();
        images = new ArrayList<>();
//        postID = "-LX-FnYY6YE-xRCLnazH";
        storageReference = firebaseStorage.getReference();

        i = 1;
        if (postID != null) {
            /*KERKOHEN FOTOGRAFITE E POSTIMIT NE DATABAZE*/
            viewPager = (ViewPager) findViewById(R.id.postViewPager);

            //KERKOHET FOTO 1
            storageReference.child("postImages/" + postID + "/Foto" + i).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    images.add(uri.toString());
                    i++;
                    /*
                     * Kerkohet FOTO 2
                     * */
                    storageReference.child("postImages/" + postID + "/Foto" + i).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            images.add(uri.toString());
                            i++;
                            /*
                             * Kerkohet FOTO 3
                             * */
                            storageReference.child("postImages/" + postID + "/Foto" + i).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    images.add(uri.toString());
                                    i++;
                                    /*
                                     * Kerkohet FOTO 4
                                     * */
                                    storageReference.child("postImages/" + postID + "/Foto" + i).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            images.add(uri.toString());
                                            i++;
                                            /*
                                             * Kerkohet FOTO 5
                                             * */
                                            storageReference.child("postImages/" + postID + "/Foto" + i).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    images.add(uri.toString());
                                                    i++;
                                                    /*
                                                     * Kerkohet FOTO 6
                                                     * */
                                                    storageReference.child("postImages/" + postID + "/Foto" + i).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            images.add(uri.toString());
                                                            i++;
                                                            /*
                                                             * Kerkohet FOTO 7
                                                             * */
                                                            storageReference.child("postImages/" + postID + "/Foto" + i).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    images.add(uri.toString());
                                                                    i++;
                                                                    /*
                                                                     * Kerkohet FOTO 8
                                                                     * */
                                                                    storageReference.child("postImages/" + postID + "/Foto" + i).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri) {
                                                                            images.add(uri.toString());
                                                                            i++;
                                                                            /*
                                                                             * Kerkohet FOTO 9
                                                                             * */
                                                                            storageReference.child("postImages/" + postID + "/Foto" + i).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                @Override
                                                                                public void onSuccess(Uri uri) {
                                                                                    images.add(uri.toString());
                                                                                    i++;
                                                                                    /*
                                                                                     * Kerkohet FOTO 10
                                                                                     * */
                                                                                    storageReference.child("postImages/" + postID + "/Foto" + i).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                        @Override
                                                                                        public void onSuccess(Uri uri) {
                                                                                            images.add(uri.toString());
                                                                                            i++;
                                                                                            /*
                                                                                             * Inicializohet adapteri i VIEWPAGER
                                                                                             * */
                                                                                            adapter = new PostPagerAdapter(PostViewActivity.this, images);
                                                                                            viewPager.setAdapter(adapter);
                                                                                        }
                                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            adapter = new PostPagerAdapter(PostViewActivity.this, images);
                                                                                            viewPager.setAdapter(adapter);
                                                                                        }
                                                                                    });


                                                                                }
                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    adapter = new PostPagerAdapter(PostViewActivity.this, images);
                                                                                    viewPager.setAdapter(adapter);
                                                                                }
                                                                            });


                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            adapter = new PostPagerAdapter(PostViewActivity.this, images);
                                                                            viewPager.setAdapter(adapter);
                                                                        }
                                                                    });


                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    adapter = new PostPagerAdapter(PostViewActivity.this, images);
                                                                    viewPager.setAdapter(adapter);
                                                                }
                                                            });


                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            adapter = new PostPagerAdapter(PostViewActivity.this, images);
                                                            viewPager.setAdapter(adapter);
                                                        }
                                                    });


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    adapter = new PostPagerAdapter(PostViewActivity.this, images);
                                                    viewPager.setAdapter(adapter);
                                                }
                                            });


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            adapter = new PostPagerAdapter(PostViewActivity.this, images);
                                            viewPager.setAdapter(adapter);
                                        }
                                    });


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    adapter = new PostPagerAdapter(PostViewActivity.this, images);
                                    viewPager.setAdapter(adapter);
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            adapter = new PostPagerAdapter(PostViewActivity.this, images);
                            viewPager.setAdapter(adapter);
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Nuk ka fotografi për këtë postim!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void postData(DataSnapshot dataSnapshot) {
        if (postID != null) {

            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                post = dataSnapshot.child("posts").child(postID).getValue(Post.class);
                tvTitle.setText(post.getTitulli());
                tvPrice.setText(post.getCmimi() + "€");

                List<String> items = Arrays.asList(getResources().getStringArray(R.array.karroceria));
                tvVehicle.setText(items.get(post.getKarroceriaID()));
                tvFirstRegistration.setText(post.getRegjistrimiPare());
                tvKm.setText(post.getKm() + "KM");

                items = Arrays.asList(getResources().getStringArray(R.array.karburanti));
                tvFuel.setText(items.get(post.getKarburantiID()-1));
                tvPower.setText(post.getFuqia() + " KW");
                if (post.getAksident() == 1) {
                    tvCondition.setText("Aksidentuar");
                } else {
                    tvCondition.setText("Pa Aksidentuar");
                }
                if (post.getNrUleseve() != null)
                    tvSeatsNr.setText(post.getNrUleseve());
                else tvSeatsNr.setVisibility(View.GONE);

                if (post.getNrDyer() != 0)
                    tvDoorsNr.setText(String.valueOf(post.getNrDyer()));
                else
                    tvDoorsNr.setVisibility(View.GONE);

                items = Arrays.asList(getResources().getStringArray(R.array.targat));
                if (post.getTarga() != null)
                    tvLicencePlates.setText(post.getTarga());
                else tvLicencePlates.setVisibility(View.GONE);

                if (post.getTarga().equals("Vendore")) {
                    tvLicencePlatesDesc.setText("Regjistrim deri: " + post.getRegjistrim());
                } else if (post.getTarga().equals("Të huaja")) {
                    tvLicencePlatesDesc.setText("Doganuar: " + post.getDoganuar());
                } else {
                    tvLicencePlatesDesc.setVisibility(View.GONE);
                }
                items = Arrays.asList(getResources().getStringArray(R.array.ngjyra));
                tvColor.setText(items.get(post.getNgjyra()));

                items = Arrays.asList(getResources().getStringArray(R.array.enterieri));
                if (post.getMaterialiEnterier() != 0) {
                    tvInterior.setText(items.get(post.getMaterialiEnterier()));
                } else {
                    tvInterior.setVisibility(View.GONE);
                }

                items = Arrays.asList(getResources().getStringArray(R.array.ngjyraEnterier));
                if (post.getNgjyraEnterier() != 0) {
                    tvInterior.setText(tvInterior.getText() + ", " + items.get(post.getNgjyraEnterier()));
                }

                if (!post.getFeatures().isBluetooth()) {
                    tvBluetooth.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isOnBoardKompjuter()) {
                    tvOnBoard.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isCDPlayer()) {
                    tvCd.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isXhamaElektrik()) {
                    tvElectricWindow.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isUleseMeLevizjeElektrike()) {
                    tvElectricSeats.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isUleseMeNxemje()) {
                    tvHeatedSeats.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isUleseSportive()) {
                    tvSportSeats.setVisibility(View.GONE);
                }

                if (post.getFeatures().getKondicioneri() != 0) {
                    items = Arrays.asList(getResources().getStringArray(R.array.kondicioneri));
                    tvClimatisation.setText(items.get(post.getFeatures().getKondicioneri()));
                }

                if (!post.getFeatures().isMp3()) {
                    tvMp3.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isAux()) {
                    tvAux.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isPullaNeTimon()) {
                    tvSteeringWheel.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isNavigacion()) {
                    tvNa.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isShiber()) {
                    tvShiber.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isPanorame()) {
                    tvPanorame.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isBagazhNeCati()) {
                    tvRoofRack.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isMbylljeQendrore()) {
                    tvCentral.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isPasqyreElektrike()) {
                    tvMirror.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isAmortizimSportiv()) {
                    tvSportAmortization.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isSportPakete()) {
                    tvSportPacket.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isAbs()) {
                    tvAbs.setVisibility(View.GONE);
                }

                String text = "";
                if (post.getFeatures().getAirbag().isAirbagShoferi()) {
                    text += "Airbag Shoferi,";
                }

                if (post.getFeatures().getAirbag().isAirbagAnesor()) {
                    text += " anesor,";
                }

                if (post.getFeatures().getAirbag().isAirbagIPrapme()) {
                    text += " prapme";
                }

                if (post.getFeatures().getAirbag().isAirbagTjere()) {
                    text += " dhe airbag tjere";
                }
                tvAirbag.setText(text);


                if (!(post.getFeatures().getAirbag().isAirbagTjere() || post.getFeatures().getAirbag().isAirbagIPrapme()
                        || post.getFeatures().getAirbag().isAirbagShoferi() || post.getFeatures().getAirbag().isAirbagAnesor())) {
                    tvAirbag.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isKaterX4()) {
                    tv4x4.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isEsp()) {
                    tvEsp.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isDritaAdaptuese()) {
                    tvAdaptingLights.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isSensorDritash()) {
                    tvLightSensor.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isDritaTeMjegulles()) {
                    tvFog.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isDritaXenon()) {
                    tvXenon.setVisibility(View.GONE);
                }


                if (!post.getFeatures().isDritaBiXenon()) {
                    tvBiXenon.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isSensorShiu()) {
                    tvRain.setVisibility(View.GONE);
                }

                if (!post.getFeatures().isStartStop()) {
                    tvStartStop.setVisibility(View.GONE);
                }

                if (post.getPershkrimi() != null) {
                    tvDescription.setText(post.getPershkrimi());
                }

                user = dataSnapshot.child("users").child(post.getPronariID()).getValue(User.class);
                if (user.getTipiLlogarise().equals("2")) {
                    autosallon = dataSnapshot.child("carDealers").child(post.getPronariID()).getValue(Autosallon.class);
                    tvSellerName.setText(autosallon.getEmri());
                    tvSellerTel.setText(autosallon.getTelefoni());
                    tvSellerEmail.setText(user.getEmail());
                } else{
                    tvSellerName.setText(user.getEmri()+" "+user.getMbiemri());
                    tvSellerTel.setText(user.getTel());
                    tvSellerEmail.setText(user.getEmail());
                }
                final Adresa address = dataSnapshot.child("addresses").child(user.getAdresaID()).getValue(Adresa.class);
                items = Arrays.asList(getResources().getStringArray(R.array.qytetet));
                tvSellerAddress.setText("Rr." + address.getRruga() + " Nr." + address.getNr() + ", " + address.getKodi() + ", " + items.get(Integer.parseInt(address.getQyteti())));

                if (!address.getLat().equals("-") && !address.getLng().equals("-")) {
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap mMap) {
                            googleMap = mMap;
                            LatLng marker = new LatLng(Double.parseDouble(address.getLat()), Double.parseDouble(address.getLng()));
                            if (autosallon != null)
                                googleMap.addMarker(new MarkerOptions().position(marker).title(post.getTitulli()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));


                            googleMap.getUiSettings().setAllGesturesEnabled(false);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 17));
                        }
                    });
                } else
                    mapView.setVisibility(View.GONE);


                // RUAJTJA E POSTIMIT OSE EDITIMI I TIJ NESE PRONAR ESHTE USERI I LOGUAR
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    if (post.getPronariID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        imgP.setImageResource(R.drawable.ic_clear_white);
                    } else {
                        /*LISTIMI I POSTIMEVE TE RUAJTURA */
                        SQLiteDatabase objDb = (new LocalDatabase(this)).getReadableDatabase();
                        Cursor c = null;
                        List<String> savedPosts = new ArrayList<>();
                        c = objDb.rawQuery("Select * from tblSaved", null);
                        if (c != null) {
                            c.moveToFirst();
                            if (c.getCount() > 0) {
                                for (int j = 0; j < c.getCount(); j++) {
                                    savedPosts.add(c.getString(1));
                                    c.moveToNext();
                                }
                                c.close();
                            }
                        }

                        /*NESE EKZISTON POSTIMI I RUAJTUR VENDOSE FOTON PARKED, PERNDRYSHE NOT PARKED*/
                        int exist = 0;
                        if (savedPosts != null) {
                            for (int i = 0; i < savedPosts.size(); i++) {
                                if (savedPosts.get(i).equals(postID)) {
                                    exist++;
                                }
                            }
                        }
                        if (exist == 0) {
                            imgP.setImageResource(R.drawable.ic_not_parked);
                        } else {
                            imgP.setImageResource(R.drawable.ic_parked);
                        }
                    }
                } else {
                    /* NESE USERI NUK ESHTE I LOGUAR, POR E PERDOR APLIKACIONIN PA USER */

                    /* LISTIMI I POSTIMEVE TE RUAJTURA */
                    SQLiteDatabase objDb = (new LocalDatabase(this)).getReadableDatabase();
                    Cursor c = null;
                    List<String> savedPosts = new ArrayList<>();
                    c = objDb.rawQuery("Select * from tblSaved", null);
                    if (c != null) {
                        c.moveToFirst();
                        if (c.getCount() > 0) {
                            for (int j = 0; j < c.getCount(); j++) {
                                savedPosts.add(c.getString(1));
                                c.moveToNext();
                            }
                            c.close();
                        }
                    }

                    /*NESE EKZISTON POSTIMI I RUAJTUR VENDOSE FOTON PARKED, PERNDRYSHE NOT PARKED*/
                    int exist = 0;
                    if (savedPosts != null) {
                        for (int i = 0; i < savedPosts.size(); i++) {
                            if (savedPosts.get(i).equals(postID)) {
                                exist++;
                            }
                        }
                    }

                    if (exist == 0) {
                        imgP.setImageResource(R.drawable.ic_not_parked);
                    } else {
                        imgP.setImageResource(R.drawable.ic_parked);
                    }
                }

            }
        }
    }

    private void savePost() {
        SQLiteDatabase objDb = (new LocalDatabase(this)).getReadableDatabase();
        Cursor c = null;
        List<String> savedPosts = new ArrayList<>();
        c = objDb.rawQuery("Select * from tblSaved", null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                for (int j = 0; j < c.getCount(); j++) {
                    savedPosts.add(c.getString(1));
                    c.moveToNext();
                }
                c.close();
            }
        }

        /*NESE EKZISTON POSTIMI I RUAJTUR FSHIJE, PERNDRYSHE RUAJE*/
        int exist = 0;
        if (savedPosts != null) {
            for (int i = 0; i < savedPosts.size(); i++) {
                if (savedPosts.get(i).equals(postID)) {
                    exist++;
                }
            }
        }


        //NESE POSTIMI NUK EKZITON NE DB ATEHERE RUAJE
        if (exist == 0) {
            objDb.execSQL("Insert into tblSaved (postID) values ('" + postID + "');");
            imgP.setImageResource(R.drawable.ic_parked);
        } else {
            objDb.execSQL("Delete from tblSaved where postID='" + postID+"'");
            imgP.setImageResource(R.drawable.ic_not_parked);
        }

    }
    private void report() {
        final Dialog reportDialog = new Dialog(this);
        reportDialog.setContentView(R.layout.dialog_report);
        reportDialog.getWindow().setLayout((width - 100), (width - 100));
        reportDialog.setTitle("");
        final Spinner reportSpinner = (Spinner) reportDialog.findViewById(R.id.spinnerReport);
        reportSpinner.setVisibility(View.GONE);
        TextView tvTitle = (TextView)reportDialog.findViewById(R.id.reportTitle);
        tvTitle.setText("Raporto shpalljen");
        final EditText etDoing = (EditText)reportDialog.findViewById(R.id.etDoing);
        final EditText etError = (EditText)reportDialog.findViewById(R.id.etError);
        etDoing.setText(postID);
        etDoing.setVisibility(View.GONE);
        etDoing.setClickable(false);
        etDoing.setFocusable(false);
        etError.setHint("Arsyeja e raportimit");
        etError.setVisibility(View.VISIBLE);
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
                    if(!etError.getText().toString().isEmpty()){
                        String reporterID;
                        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                            reporterID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        } else {
                            reporterID = "AnonimUser";
                        }
                        ErrorReport error = new ErrorReport(reporterID,3,postID, etError.getText().toString());
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
        });
        reportDialog.show();
    }



}
