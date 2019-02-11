package Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Database.LocalDatabase;
import Models.SearchConditions;
import Models.RangeSearch;
import Models.Post;
import Adapters.DialogPostAdapter;
import rks.youngdevelopers.autotreguks.NonUserActivity;
import rks.youngdevelopers.autotreguks.R;
import Adapters.SearchCheckAdapter;
import rks.youngdevelopers.autotreguks.SearchResultActivity;
import rks.youngdevelopers.autotreguks.UserActivity;

public class SearchFragment extends Fragment implements DialogPostAdapter.OnDialogListener, SearchCheckAdapter.OnCheckListener {

    // P A M J E T
    Spinner kerkoQyteti;
    LinearLayout linearGjendja, linearMarkaModeli, linearCmimi, linearTargat, linearRegjistrimiPare, linearKm,
            linearFuqia, linearKarburanti, linearHollesishem;
    TextView tvGjendja, tvMarkaModeli, tvCmimi, tvTargat, tvRegjistrimiPare, tvKm, tvFuqia, tvKarburanti, tvHollesishem;
    Button btnKerko;
    Dialog kerkoDialog, modelDialog, markaDialog, radioDialog;

    // V A R I A B L A T
    int width, height;
    String[] pickerData;
    int btnID;
    List<String> dialogItems, items;
    public static SearchConditions searchConditions = new SearchConditions();
    //    public static VehicleType vehicle = new VehicleType();
//    public static TransmissionSearch transmission = new TransmissionSearch();
//    public static ColorSearch colorSearch = new ColorSearch();
//    public static InteriorSearch interiorSearch = new InteriorSearch();
//    public static SecurityAndEnvironmentSearch security = new SecurityAndEnvironmentSearch();
//    public static Airbag airbag = new Airbag();
//    public static ParkingSearch parking = new ParkingSearch();
    public static List<Post> result;
    boolean[] checked;
    public static DataSnapshot mDataSnapshot;
    public static List<List> finalResult;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kerko, container, false);
        setHasOptionsMenu(true);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        kerkoQyteti = (Spinner) view.findViewById(R.id.spinnerKerkoQyteti);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity().getApplicationContext(), R.array.qytetet,
                        R.layout.spinner_kerko);
        // Percaktimi i pamjes se qelise se spinnerit
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Vendosja e adapterit te spineri
        kerkoQyteti.setAdapter(staticAdapter);

        kerkoQyteti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchConditions.setQyteti(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        linearGjendja = (LinearLayout) view.findViewById(R.id.kerkoGjendja);
        linearMarkaModeli = (LinearLayout) view.findViewById(R.id.kerkoMarkaModeli);
        linearCmimi = (LinearLayout) view.findViewById(R.id.kerkoCmimi);
        linearTargat = (LinearLayout) view.findViewById(R.id.kerkoTarga);
        linearRegjistrimiPare = (LinearLayout) view.findViewById(R.id.kerkoRegjistrimiPare);
        linearKm = (LinearLayout) view.findViewById(R.id.kerkoKm);
        linearFuqia = (LinearLayout) view.findViewById(R.id.kerkoFuqia);
        linearKarburanti = (LinearLayout) view.findViewById(R.id.kerkoKarburanti);
        linearHollesishem = (LinearLayout) view.findViewById(R.id.kerkoHollesishem);

        tvGjendja = (TextView) view.findViewById(R.id.tvKerkoGjendja);
        tvMarkaModeli = (TextView) view.findViewById(R.id.tvMarkaModeli);
        tvCmimi = (TextView) view.findViewById(R.id.tvKerkoCmimi);
        tvTargat = (TextView) view.findViewById(R.id.tvKerkoTarga);
        tvRegjistrimiPare = (TextView) view.findViewById(R.id.tvKerkoRegjistrimiPare);
        tvKm = (TextView) view.findViewById(R.id.tvKerkoKm);
        tvFuqia = (TextView) view.findViewById(R.id.tvKerkoFuqia);
        tvKarburanti = (TextView) view.findViewById(R.id.tvKerkoKarburanti);
        tvHollesishem = (TextView) view.findViewById(R.id.tvKerkoHollesishem);


        btnKerko = (Button) view.findViewById(R.id.btnShfaqKerkimet);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        finalResult = new ArrayList<>();
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDataSnapshot = dataSnapshot.child("posts");
                finalResult = Database.RemoteDatabase.postSearch(dataSnapshot.child("posts"), searchConditions);
                btnKerko.setText(finalResult.get(0).size() + " Rezultate");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnKerko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dRef = db.getReference();
                dRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (finalResult.get(0).size() != 0) {
                            //finalResult = Database.RemoteDatabase.postSearch(dataSnapshot, searchConditions);
                            Intent intent = new Intent(getContext(), SearchResultActivity.class);
                            SearchResultActivity.searchConditions = searchConditions;
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Ska rezultate", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        initValues();
        linearGjendja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 1;
                dialogGjendja();
            }
        });

        linearMarkaModeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 2;
                markaModeli();
            }
        });

        linearTargat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vendore, shqiptare, te huaja
                btnID = 3;
                checkDialog(btnID);

            }
        });

        linearCmimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 4;
                kerkoDialog(btnID);
            }
        });

        linearRegjistrimiPare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 5;
                kerkoDialog(btnID);
            }
        });

        linearKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 6;
                kerkoDialog(btnID);
            }
        });

        linearFuqia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 7;
                kerkoDialog(btnID);
            }
        });
        linearKarburanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check karburantet
                btnID = 8;
                checkDialog(8);
            }
        });

        linearHollesishem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HAPET FRAGMENTI I RI
                Fragment fragment = new AdvancedSearchFragment();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    ft.replace(R.id.screen_kryefaqja, fragment);
                else
                    ft.replace(R.id.screen_user, fragment);

                ft.commit();

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            ((NonUserActivity) getActivity()).getSupportActionBar().setTitle(R.string.kerko);
        } else {
            ((UserActivity) getActivity()).getSupportActionBar().setTitle(R.string.kerko);
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_clear, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_clear){
            searchConditions = new SearchConditions();
            initValues();
        }


        return super.onOptionsItemSelected(item);
    }

    private void kerkoDialog(int i) {
        final int btn = i;
        kerkoDialog = new Dialog(getContext());
        kerkoDialog.setContentView(R.layout.range_dialog);
        kerkoDialog.setTitle(" ");
        kerkoDialog.getWindow().setLayout(width - 100, width + 100);
        kerkoDialog.show();
        final EditText etPrej = (EditText) kerkoDialog.findViewById(R.id.etDialogPrej);
        final EditText etDeri = (EditText) kerkoDialog.findViewById(R.id.etDialogDeri);
        TextView tvTitulli = (TextView) kerkoDialog.findViewById(R.id.tvKerkoDialog);
        final NumberPicker pickerPrej = (NumberPicker) kerkoDialog.findViewById(R.id.pickerDialogPrej);
        final NumberPicker pickerDeri = (NumberPicker) kerkoDialog.findViewById(R.id.pickerDialogDeri);
        Button btnOk = (Button) kerkoDialog.findViewById(R.id.btnDialogOk);
        Button btnAnulo = (Button) kerkoDialog.findViewById(R.id.btnDialogCancel);
        ImageView btnClear = (ImageView) kerkoDialog.findViewById(R.id.dialogClear);

        btnAnulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kerkoDialog.dismiss();
            }
        });

        if (i == 4 || i == 6 || i == 7) {

            if (i == 4) {
                pickerData = getResources().getStringArray(R.array.kerkoCmimet);
                tvTitulli.setText("Çmimi");
            } else if (i == 6) {
                pickerData = getResources().getStringArray(R.array.kerkoKm);
                tvTitulli.setText("Të kaluara (km)");
            } else if (i == 7) {
                pickerData = getResources().getStringArray(R.array.kerkoFuqia);
                tvTitulli.setText("Fuqia (KW)");

            }
            pickerPrej.setMinValue(0);
            pickerPrej.setMaxValue(pickerData.length - 1);
            pickerPrej.setDisplayedValues(pickerData);

            pickerDeri.setMinValue(0);
            pickerDeri.setMaxValue(pickerData.length - 1);
            pickerDeri.setDisplayedValues(pickerData);

            pickerPrej.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    etPrej.setText(pickerData[pickerPrej.getValue()]);
                }
            });

            pickerDeri.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    etDeri.setText(pickerData[pickerDeri.getValue()]);
                }
            });

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn == 4) {
                        if (pickerPrej.getValue() < pickerDeri.getValue()) {
                            searchConditions.setCmimi(new RangeSearch(Long.parseLong(pickerData[pickerPrej.getValue()]), Long.parseLong(pickerData[pickerDeri.getValue()])));
                            tvCmimi.setText(pickerData[pickerPrej.getValue()] + " - " + pickerData[pickerDeri.getValue()] + " Euro");
                            kerkoDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Te dhena jovalide", Toast.LENGTH_LONG).show();
                        }
                    } else if (btn == 6) {
                        if (pickerPrej.getValue() < pickerDeri.getValue()) {
                            searchConditions.setKm(new RangeSearch(Long.parseLong(pickerData[pickerPrej.getValue()]), Long.parseLong(pickerData[pickerDeri.getValue()])));
                            tvKm.setText(pickerData[pickerPrej.getValue()] + " - " + pickerData[pickerDeri.getValue()] + " KM");
                            kerkoDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Te dhena jovalide", Toast.LENGTH_LONG).show();
                        }

                    } else if (btn == 7) {
                        if (pickerPrej.getValue() < pickerDeri.getValue()) {
                            String[] array1 = pickerData[pickerPrej.getValue()].split(" ");
                            String[] array2 = pickerData[pickerDeri.getValue()].split(" ");

                            searchConditions.setFuqia(new RangeSearch(Long.parseLong(array1[0]), Long.parseLong(array2[0])));
                            tvFuqia.setText(pickerData[pickerPrej.getValue()] + " - " + pickerData[pickerDeri.getValue()]);
                            kerkoDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Te dhena jovalide", Toast.LENGTH_LONG).show();
                        }
                    }
                    searchResultNr();

                }
            });

            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn == 1) {
                        searchConditions.setCmimi(new RangeSearch());
                        tvCmimi.setText("");
                    } else if (btn == 6) {
                        searchConditions.setKm(new RangeSearch());
                        tvKm.setText("");
                    } else if (btn == 4) {
                        searchConditions.setFuqia(new RangeSearch());
                        tvFuqia.setText("");
                    }
                }
            });

        } else if (i == 5) {
            pickerPrej.setMinValue(1950);
            pickerPrej.setMaxValue(2019);

            pickerDeri.setMinValue(1950);
            pickerDeri.setMaxValue(2019);

            tvTitulli.setText("Regjistrimi i parë");
            pickerPrej.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    etPrej.setText(String.valueOf(pickerPrej.getValue()));
                }
            });

            pickerDeri.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    etDeri.setText(String.valueOf(pickerDeri.getValue()));
                }
            });

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pickerPrej.getValue() < pickerDeri.getValue()) {
                        searchConditions.setRegjistrimiPare(new RangeSearch(pickerPrej.getValue(), pickerDeri.getValue()));
                        tvRegjistrimiPare.setText(String.valueOf(pickerPrej.getValue()) + " - " + String.valueOf(pickerDeri.getValue()));
                        kerkoDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Te dhena jovalide", Toast.LENGTH_LONG).show();

                    }
                }
            });

            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchConditions.setRegjistrimiPare(new RangeSearch());
                    tvRegjistrimiPare.setText("");
                }
            });
        }


    }

    private void dialogGjendja() {
        radioDialog = new Dialog(getContext());
        radioDialog.setContentView(R.layout.dialog_post);
        radioDialog.getWindow().setLayout(width - 100, width - 100);
        TextView tvTitulli = (TextView) radioDialog.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) radioDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioDialog.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) radioDialog.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        dialogItems = new ArrayList<>();
        dialogItems = Arrays.asList(getResources().getStringArray(R.array.gjendja));
        tvTitulli.setText("Gjendja e veturës");
        DialogPostAdapter dialogAdapteri = new DialogPostAdapter(dialogItems, getContext(), this);
        recyclerDialog.setAdapter(dialogAdapteri);
        radioDialog.show();
    }

    private void checkDialog(int id) {
        radioDialog = new Dialog(getContext());
        radioDialog.setContentView(R.layout.dialog_post);
        radioDialog.getWindow().setLayout(width - 100, width - 100);
        TextView tvTitulli = (TextView) radioDialog.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) radioDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioDialog.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) radioDialog.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        dialogItems = new ArrayList<>();

        if (id == 8) {
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.karburanti));
            checked = new boolean[dialogItems.size()];
            checked[0] = searchConditions.getFuel().isDiesel();
            checked[1] = searchConditions.getFuel().isPetrol();
            checked[2] = searchConditions.getFuel().isGas();
            checked[3] = searchConditions.getFuel().isElectric();
            checked[4] = searchConditions.getFuel().isHybrid();
            tvTitulli.setText("Karburanti");
        } else if (id == 3) {
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.targat));
            checked = new boolean[dialogItems.size()];
            checked[0] = searchConditions.getLicencePlates().isUnspecified();
            checked[1] = searchConditions.getLicencePlates().isKosovo();
            checked[2] = searchConditions.getLicencePlates().isAlbania();
            checked[3] = searchConditions.getLicencePlates().isForeign();
            tvTitulli.setText("Targat");
        }

        SearchCheckAdapter searchCheckAdapter = new SearchCheckAdapter(dialogItems, getContext(), this, checked);
        recyclerDialog.setAdapter(searchCheckAdapter);
        radioDialog.show();

    }

    private void markaModeli() {
        markaDialog = new Dialog(getContext());
        markaDialog.setContentView(R.layout.dialog_post);
        markaDialog.getWindow().setLayout(width - 100, width - 100);

        TextView tvTitulli = (TextView) markaDialog.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) markaDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markaDialog.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) markaDialog.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        SQLiteDatabase objDb = (new LocalDatabase(getContext())).getReadableDatabase();
        Cursor c = null;
        dialogItems = new ArrayList<>();
        c = objDb.rawQuery("Select * from tblMarka", null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                for (int j = 0; j < c.getCount(); j++) {
                    dialogItems.add(c.getString(1));
                    c.moveToNext();
                }
                c.close();
            }
        }
        tvTitulli.setText("Marka");
        DialogPostAdapter dialogAdapteri = new DialogPostAdapter(dialogItems, getContext(), this);
        recyclerDialog.setAdapter(dialogAdapteri);
        markaDialog.show();

    }

    private void modeliDialog(int id) {
        modelDialog = new Dialog(getContext());
        btnID = 20;
        modelDialog.setContentView(R.layout.dialog_post);
        modelDialog.getWindow().setLayout(width - 100, width - 100);
        TextView tvTitulli = (TextView) modelDialog.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) modelDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelDialog.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) modelDialog.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        SQLiteDatabase objDb = (new LocalDatabase(getContext())).getReadableDatabase();
        Cursor c = null;
        items = new ArrayList<>();
        c = objDb.rawQuery("Select * from tblModeli where mID=" + (id + 1), null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                for (int j = 0; j < c.getCount(); j++) {
                    items.add(c.getString(2));
                    c.moveToNext();
                }
                c.close();
            }
        }
        tvTitulli.setText("Modeli");
        DialogPostAdapter dialogAdapteri = new DialogPostAdapter(items, getContext(), this);
        recyclerDialog.setAdapter(dialogAdapteri);
        modelDialog.show();
    }

    @Override
    public void onDialogItemClick(int position) {
        if (btnID == 1) {
            tvGjendja.setText(dialogItems.get(position));
            searchConditions.setGjendja(position);
            radioDialog.dismiss();
        } else if (btnID == 2) {
            searchConditions.setMarkaID(position + 1);
            searchConditions.setModeliID(0);
            tvMarkaModeli.setText(dialogItems.get(position));
            markaDialog.dismiss();
            modeliDialog(position);

        } else if (btnID == 20) {
            searchConditions.setModeliID(position + 1);
            tvMarkaModeli.setText(tvMarkaModeli.getText() + " " + items.get(position));
            modelDialog.dismiss();
        }
        searchResultNr();
    }

    private void initValues() {

        List<String> values;
        values = Arrays.asList(getResources().getStringArray(R.array.karburanti));
        String text = "";
        if (SearchFragment.searchConditions.getFuel().isDiesel())
            text += values.get(0);
        if (searchConditions.getFuel().isPetrol())
            text += " - " + values.get(1);
        if (searchConditions.getFuel().isGas())
            text += " - " + values.get(2);
        if (searchConditions.getFuel().isElectric())
            text += " - " + values.get(3);
        if (searchConditions.getFuel().isHybrid())
            text += " - " + values.get(4);
        tvKarburanti.setText(text);

        values = Arrays.asList(getResources().getStringArray(R.array.targat));
        text = "";
        if (searchConditions.getLicencePlates().isUnspecified())
            text += values.get(0);
        if (searchConditions.getLicencePlates().isKosovo())
            text += " - " + values.get(1);
        if (searchConditions.getLicencePlates().isAlbania())
            text += " - " + values.get(2);
        if (searchConditions.getLicencePlates().isForeign())
            text += " - " + values.get(3);
        tvTargat.setText(text);

        values = Arrays.asList(getResources().getStringArray(R.array.gjendja));
        tvGjendja.setText(values.get(searchConditions.getGjendja()));

        text = "";
        values = new ArrayList<>();
        if (searchConditions.getMarkaID() != 0) {
            SQLiteDatabase objDb = (new LocalDatabase(getContext())).getReadableDatabase();
            Cursor c = null;
            values = new ArrayList<>();
            c = objDb.rawQuery("Select * from tblMarka", null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int j = 0; j < c.getCount(); j++) {
                        values.add(c.getString(1));
                        c.moveToNext();
                    }
                    c.close();
                    text += values.get(searchConditions.getMarkaID()-1);
                }
            }
            c = null;
            values = new ArrayList<>();
            c = objDb.rawQuery("Select * from tblModeli", null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int j = 0; j < c.getCount(); j++) {
                        values.add(c.getString(2));
                        c.moveToNext();
                    }
                    c.close();
                    text += values.get(searchConditions.getMarkaID()-1);
                }
            }


        }
        tvMarkaModeli.setText(text);


        try {
            if (searchConditions.getCmimi().getFrom() != 0 || searchConditions.getCmimi().getTo() != 0) {
                values = new ArrayList<>();
                text = "";
                text = String.valueOf(searchConditions.getCmimi().getFrom()) + " - " + String.valueOf(searchConditions.getCmimi().getTo());
                tvCmimi.setText(text);
            }
        } catch (Exception e) {

        }


        try {
            if (searchConditions.getRegjistrimiPare().getFrom() != 0 || searchConditions.getRegjistrimiPare().getTo() != 0) {

                values = new ArrayList<>();
                text = "";
                text = String.valueOf(searchConditions.getRegjistrimiPare().getFrom()) + " - "
                        + String.valueOf(searchConditions.getRegjistrimiPare().getTo());
                tvRegjistrimiPare.setText(text);
            }
        } catch (Exception e) {

        }


        try {
            if (searchConditions.getKm().getFrom() != 0 || searchConditions.getKm().getTo() != 0) {

                values = new ArrayList<>();
                text = "";
                text = String.valueOf(searchConditions.getKm().getFrom()) + " - "
                        + String.valueOf(searchConditions.getKm().getTo());
                tvKm.setText(text);
            }
        } catch (Exception e) {

        }


        try {
            if (searchConditions.getFuqia().getFrom() != 0 || searchConditions.getFuqia().getTo() != 0) {

                values = new ArrayList<>();
                text = "";
                text = String.valueOf(searchConditions.getFuqia().getFrom()) + " KW - "
                        + String.valueOf(searchConditions.getFuqia().getTo()) + " KW";
                tvKm.setText(text);
            }
        } catch (Exception e) {

        }


    }


    @Override
    public void onSearchCheck(int position) {

        if (btnID == 8) {
            if (position == 0)
                SearchFragment.searchConditions.getFuel().setDiesel(!SearchFragment.searchConditions.getFuel().isDiesel());
            else if (position == 1)
                SearchFragment.searchConditions.getFuel().setPetrol(!SearchFragment.searchConditions.getFuel().isPetrol());
            else if (position == 2)
                SearchFragment.searchConditions.getFuel().setGas(!SearchFragment.searchConditions.getFuel().isGas());
            else if (position == 3)
                SearchFragment.searchConditions.getFuel().setElectric(!SearchFragment.searchConditions.getFuel().isElectric());
            else
                SearchFragment.searchConditions.getFuel().setHybrid(!SearchFragment.searchConditions.getFuel().isHybrid());

            String text = "";
            if (SearchFragment.searchConditions.getFuel().isDiesel())
                text += dialogItems.get(0);
            if (searchConditions.getFuel().isPetrol())
                text += " - " + dialogItems.get(1);
            if (searchConditions.getFuel().isGas())
                text += " - " + dialogItems.get(2);
            if (searchConditions.getFuel().isElectric())
                text += " - " + dialogItems.get(3);
            if (searchConditions.getFuel().isHybrid())
                text += " - " + dialogItems.get(4);
            tvKarburanti.setText(text);
        } else if (btnID == 3) {
            if (position == 0)
                SearchFragment.searchConditions.getLicencePlates().setUnspecified(!SearchFragment.searchConditions.getLicencePlates().isUnspecified());
            else if (position == 1)
                SearchFragment.searchConditions.getLicencePlates().setKosovo(!SearchFragment.searchConditions.getLicencePlates().isKosovo());
            else if (position == 2)
                SearchFragment.searchConditions.getLicencePlates().setAlbania(!SearchFragment.searchConditions.getLicencePlates().isAlbania());
            else if (position == 3)
                SearchFragment.searchConditions.getLicencePlates().setForeign(!SearchFragment.searchConditions.getLicencePlates().isForeign());

            String text = "";
            if (searchConditions.getLicencePlates().isUnspecified())
                text += dialogItems.get(0);
            if (searchConditions.getLicencePlates().isKosovo())
                text += " - " + dialogItems.get(1);
            if (searchConditions.getLicencePlates().isAlbania())
                text += " - " + dialogItems.get(2);
            if (searchConditions.getLicencePlates().isForeign())
                text += " - " + dialogItems.get(3);
            tvTargat.setText(text);
        }
        searchResultNr();
    }

    public void searchResultNr() {
        if(mDataSnapshot!=null) {
            finalResult = Database.RemoteDatabase.postSearch(mDataSnapshot, searchConditions);
            btnKerko.setText(finalResult.get(0).size() + " Rezultate");
        }
    }

}
