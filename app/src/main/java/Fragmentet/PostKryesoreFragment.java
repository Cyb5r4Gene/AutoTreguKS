package Fragmentet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import Database.LocalDatabase;
import rks.youngdevelopers.autotreguks.DialogPostAdapter;
import rks.youngdevelopers.autotreguks.PostActivity;
import rks.youngdevelopers.autotreguks.R;

public class PostKryesoreFragment extends Fragment implements DialogPostAdapter.OnDialogListener {

    private RecyclerView.Adapter dialogAdapter;
    Dialog dialogPicker;

    private static int elementi;

    private DatePickerDialog.OnDateSetListener mRegjistrimiDate;

    int width, height;

    List<String> dialogItems;


    LinearLayout postMarka, postModeli, postKarburanti, postVarianti, postKarroceria, postDyer,
            postViti, postDefekt, postAksident, postTransmisioni;

    TextView tvMarka, tvModeli, tvKarburanti, tvVarianti, tvKarroceria, tvDyer, tvViti, tvDefekt, tvAksident, tvTransmisioni;
    TextView tvMarkaID, tvModeliID, tvKarburantiID, tvVariantiID, tvKarroceriaID, tvDyerID,
            tvVitiID, tvDefektID, tvAksidentID, tvTransmisioniID, tvKmID, tvFuqiaID, tvCmimiID;

    EditText etKm, etFuqia, etCmimi;

    CheckBox checkNegociueshem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_kryesore, container, false);
        ((PostActivity) getActivity()).getSupportActionBar().setTitle("Të dhënat kryesore");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;


        postMarka = (LinearLayout)view.findViewById(R.id.postMarka);
        postModeli = (LinearLayout)view.findViewById(R.id.postModeli);
        postKarburanti = (LinearLayout)view.findViewById(R.id.postKarburanti);
        postVarianti = (LinearLayout)view.findViewById(R.id.postVarianti);
        postKarroceria = (LinearLayout)view.findViewById(R.id.postKarroceria);
        postDyer = (LinearLayout)view.findViewById(R.id.postDyer);
        postViti = (LinearLayout)view.findViewById(R.id.postViti);
        postDefekt = (LinearLayout)view.findViewById(R.id.postDefekt);
        postAksident = (LinearLayout)view.findViewById(R.id.postAksidentuar);
        postTransmisioni = (LinearLayout)view.findViewById(R.id.postTransmisioni);

        tvMarka = (TextView) view.findViewById(R.id.tvPostMarka);
        tvModeli = (TextView) view.findViewById(R.id.tvPostModeli);
        tvKarburanti = (TextView) view.findViewById(R.id.tvPostKarburanti);
        tvKarroceria = (TextView) view.findViewById(R.id.tvPostKarroceria);
        tvVarianti = (TextView) view.findViewById(R.id.tvPostVarianti);
        tvDyer = (TextView) view.findViewById(R.id.tvPostDyer);
        tvViti = (TextView) view.findViewById(R.id.tvPostViti);
        tvDefekt = (TextView) view.findViewById(R.id.tvPostDefekt);
        tvAksident = (TextView) view.findViewById(R.id.tvPostAksidentuar);
        tvTransmisioni = (TextView) view.findViewById(R.id.tvPostTransmisioni);

        tvMarkaID = (TextView) view.findViewById(R.id.tvMarkaID);
        tvModeliID = (TextView) view.findViewById(R.id.tvModeliID);
        tvKarburantiID = (TextView) view.findViewById(R.id.tvKarburantiID);
        tvKarroceriaID = (TextView) view.findViewById(R.id.tvKarroceriaID);
        tvVariantiID = (TextView) view.findViewById(R.id.tvVariantiID);
        tvDyerID = (TextView) view.findViewById(R.id.tvDyerID);
        tvVitiID = (TextView) view.findViewById(R.id.tvVitiID);
        tvDefektID = (TextView) view.findViewById(R.id.tvDefektID);
        tvAksidentID = (TextView) view.findViewById(R.id.tvAksidentID);
        tvTransmisioniID = (TextView) view.findViewById(R.id.tvTransmisioniID);
        tvKmID = (TextView) view.findViewById(R.id.tvKmID);
        tvFuqiaID = (TextView) view.findViewById(R.id.tvFuqiaID);
        tvCmimiID = (TextView) view.findViewById(R.id.tvCmimiID);

        etKm = (EditText)view.findViewById(R.id.etKm);
        etFuqia = (EditText)view.findViewById(R.id.etFuqia);
        etCmimi = (EditText) view.findViewById(R.id.etCmimi);

        checkNegociueshem = (CheckBox)view.findViewById(R.id.checkNegociueshem);

        SQLiteDatabase objDbStart = (new LocalDatabase(getContext())).getReadableDatabase();

        if(PostActivity.post.getMarkaID()!=0){
            Cursor c = objDbStart.rawQuery("Select * from tblMarka where mID=="+ PostActivity.post.getMarkaID(), null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int i = 0; i < c.getCount(); i++) {
                        tvMarka.setText(c.getString(1));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
        } else
        {
            if(PostHomeFragment.clicked) {
                tvMarka.setTextColor(getResources().getColor(R.color.colorAccent));
                tvMarkaID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }
        if(PostActivity.post.getModeliID()!=0){
            Cursor c = objDbStart.rawQuery("Select * from tblModeli where modID=="+ PostActivity.post.getModeliID(), null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int i = 0; i < c.getCount(); i++) {
                        tvModeli.setText(c.getString(2));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
        }else
        {
            if(PostHomeFragment.clicked) {
                tvModeli.setTextColor(getResources().getColor(R.color.colorAccent));
                tvModeliID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getKarburantiID()!=0){
            Cursor c = objDbStart.rawQuery("Select * from tblKarburanti where kID=="+ PostActivity.post.getKarburantiID(), null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int i = 0; i < c.getCount(); i++) {
                        tvKarburanti.setText(c.getString(1));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
        }else
        {
            if(PostHomeFragment.clicked) {
                tvKarburanti.setTextColor(getResources().getColor(R.color.colorAccent));
                tvKarburantiID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getKarroceriaID()!=0){
            Cursor c = objDbStart.rawQuery("Select * from tblKarroceria where krrID=="+ PostActivity.post.getKarroceriaID(), null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int i = 0; i < c.getCount(); i++) {
                        tvKarroceria.setText(c.getString(1));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
        }else
        {
            if(PostHomeFragment.clicked) {
                tvKarroceria.setTextColor(getResources().getColor(R.color.colorAccent));
                tvKarroceriaID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getVariantiID()!=0){
            Cursor c = objDbStart.rawQuery("Select * from tblVarianti where vID=="+ PostActivity.post.getVariantiID(), null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int i = 0; i < c.getCount(); i++) {
                        tvVarianti.setText(c.getString(3));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
        }

        if(PostActivity.post.getNrDyer()!=null) {
            tvDyer.setText(PostActivity.post.getNrDyer());
        } else {
            if(PostHomeFragment.clicked) {
                tvDyer.setTextColor(getResources().getColor(R.color.colorAccent));
                tvDyerID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getCmimi()!=0) {
            etCmimi.setText(String.valueOf(PostActivity.post.getCmimi()));
        } else {
            if(PostHomeFragment.clicked) {
                etCmimi.setTextColor(getResources().getColor(R.color.colorAccent));
                tvCmimiID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getRegjistrimiPare()!=null) {
            tvViti.setText(PostActivity.post.getRegjistrimiPare());
        } else{
                if(PostHomeFragment.clicked) {
                    tvViti.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvVitiID.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }

        if(PostActivity.post.getKm()!=null) {
            etKm.setText(PostActivity.post.getKm());
        }else
        {
            if(PostHomeFragment.clicked) {
                etKm.setTextColor(getResources().getColor(R.color.colorAccent));
                tvKmID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getDefekt()!=null)
            tvDefekt.setText(PostActivity.post.getDefekt());

        if(PostActivity.post.getAksident()!=null)
            tvAksident.setText(PostActivity.post.getAksident());

        if(PostActivity.post.getTransmisioni()!=null)
            tvTransmisioni.setText(PostActivity.post.getTransmisioni());
        else
        {
            if(PostHomeFragment.clicked) {
                tvTransmisioni.setTextColor(getResources().getColor(R.color.colorAccent));
                tvTransmisioniID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getFuqia()!=null)
            etFuqia.setText(PostActivity.post.getFuqia());

        if(PostActivity.post.isiNegociueshem())
            checkNegociueshem.setChecked(true);
        else checkNegociueshem.setChecked(false);

        etKm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!etKm.getText().toString().trim().isEmpty())
                    if(!hasFocus)
                        PostActivity.post.setKm(etKm.getText().toString().trim());
            }
        });

        etFuqia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!etFuqia.getText().toString().trim().isEmpty())
                    if(!hasFocus)
                        PostActivity.post.setFuqia(etFuqia.getText().toString().trim());
            }
        });

        etFuqia.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                PostActivity.post.setFuqia(etFuqia.getText().toString().trim());
                return false;
            }
        });

       etCmimi.setOnKeyListener(new View.OnKeyListener() {
           @Override
           public boolean onKey(View v, int keyCode, KeyEvent event) {
               if (!etCmimi.getText().toString().trim().isEmpty())
                   PostActivity.post.setCmimi(Long.parseLong(etCmimi.getText().toString()));
               else
                   PostActivity.post.setCmimi(0);
               return false;
           }
       });

        checkNegociueshem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNegociueshem.isChecked())
                    PostActivity.post.setiNegociueshem(true);
                else if(!checkNegociueshem.isChecked())
                    PostActivity.post.setiNegociueshem(false);
            }
        });

        postMarka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(0);
            }
        });

        postModeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PostActivity.post.getMarkaID()==0)
                    Toast.makeText(getContext(), "Ju duhet të zgjedhni markën fillimisht!",Toast.LENGTH_LONG).show();
                else
                    postDialog(1);
            }
        });

        postKarburanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(2);
            }
        });

        postVarianti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PostActivity.post.getKarburantiID()==0 || PostActivity.post.getModeliID()==0)
                    Toast.makeText(getContext(), "Ju duhet të zgjedhni modelin fillimisht!",Toast.LENGTH_LONG).show();
                else
                    postDialog(3);
            }
        });

        postKarroceria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(4);
            }
        });

        postDyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(5);
            }
        });

        postDefekt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(8);
            }
        });

        postAksident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(9);
            }
        });

        postTransmisioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(10);
            }
        });

        postViti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int viti = calendar.get(Calendar.YEAR);
                int muaji = calendar.get(Calendar.MONTH);
                int dita = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogu = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mRegjistrimiDate,
                        viti, muaji, dita);
                ((ViewGroup) dialogu.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                dialogu.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                dialogu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogu.show();
            }
        });

        mRegjistrimiDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String viti = String.valueOf(month+1)+"/"+String.valueOf(year);
                tvViti.setText(viti);
                PostActivity.post.setRegjistrimiPare(viti);
            }
        };

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void postDialog(int i) {
        elementi = i;
        dialogPicker = new Dialog(getContext());
        dialogPicker.setContentView(R.layout.dialog_post);
        dialogPicker.setTitle(" ");
        dialogPicker.getWindow().setLayout((width - 100), (width - 100));

        TextView tvTitulli = (TextView) dialogPicker.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) dialogPicker.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPicker.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) dialogPicker.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));


            SQLiteDatabase objDb = (new LocalDatabase(getContext())).getReadableDatabase();
            Cursor c = null;

            dialogItems = new ArrayList<>();
            int[] dbStringIndex = {1, 2, 1, 3, 1};

            if (i == 0) {
                tvTitulli.setText("Marka");
                c = objDb.rawQuery("Select * from tblMarka", null);
            } else if (i == 1) {
                tvTitulli.setText("Modeli");
                c = objDb.rawQuery("Select * from tblModeli where mID=="+ PostActivity.post.getMarkaID(), null);
            } else if (i == 2) {
                tvTitulli.setText("Karburanti");
                c = objDb.rawQuery("Select * from tblKarburanti", null);
            } else if (i == 3) {
                tvTitulli.setText("Varianti i modelit");
                c = objDb.rawQuery("Select * from tblVarianti where modID=="+ PostActivity.post.getModeliID()
                        +" and kID=="+ PostActivity.post.getKarburantiID(), null);
            } else if (i == 4) {
                tvTitulli.setText("Karroceria");
                c = objDb.rawQuery("Select * from tblKarroceria", null);
            } else if (i == 5) {
                tvTitulli.setText("Numri i dyerve");
                dialogItems = Arrays.asList(getResources().getStringArray(R.array.dyer));
            } else if (i == 8) {
                tvTitulli.setText("Veturë me defekt?");
                dialogItems = Arrays.asList(getResources().getStringArray(R.array.opsione));
            } else if (i == 9) {
                tvTitulli.setText("Veturë e aksidentuar");
                dialogItems = Arrays.asList(getResources().getStringArray(R.array.opsione));
            } else if (i == 10) {
                tvTitulli.setText("Transmisioni");
                dialogItems = Arrays.asList(getResources().getStringArray(R.array.transmisioni));
            }
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int j = 0; j < c.getCount(); j++) {
                        dialogItems.add(c.getString(dbStringIndex[i]));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
            dialogAdapter = new DialogPostAdapter(dialogItems, getContext(), this);
            recyclerDialog.setAdapter(dialogAdapter);
            dialogPicker.show();
    }

    @Override
    public void onDialogItemClick(int position) {

        if(elementi==0)
        {
            PostActivity.post.setMarkaID(position + 1);
            PostActivity.post.setModeliID(0);
            PostActivity.post.setVariantiID(0);
            tvMarka.setText(dialogItems.get(position));
            tvModeli.setText("Përcakto modelin");
            tvVarianti.setText("Përcakto variantin");
        }
        else if(elementi==1) {
            PostActivity.post.setModeliID(position + 1);
            tvModeli.setText(dialogItems.get(position));
            tvVarianti.setText("Përcakto variantin");
        }
        else if(elementi==2) {
            PostActivity.post.setKarburantiID(position + 1);
            tvKarburanti.setText(dialogItems.get(position));
            tvVarianti.setText("Përcakto variantin");
        }
        else if(elementi==3) {
            PostActivity.post.setVariantiID(position + 1);
            tvVarianti.setText(dialogItems.get(position));
        }
        else if(elementi==4) {
            PostActivity.post.setKarroceriaID(position + 1);
            tvKarroceria.setText(dialogItems.get(position));
        }
        else if(elementi==5) {
            PostActivity.post.setNrDyer(dialogItems.get(position));
            tvDyer.setText(dialogItems.get(position));
        }
        else if(elementi==8) {
            PostActivity.post.setDefekt(dialogItems.get(position));
            tvDefekt.setText(dialogItems.get(position));
        }
        else if(elementi==9) {
            PostActivity.post.setAksident(dialogItems.get(position));
            tvAksident.setText(dialogItems.get(position));
        }
        else if(elementi==10) {
            PostActivity.post.setTransmisioni(dialogItems.get(position));
            tvTransmisioni.setText(dialogItems.get(position));
        }

        dialogPicker.dismiss();
    }

}
