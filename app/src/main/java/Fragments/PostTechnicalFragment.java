package Fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import Database.LocalDatabase;
import Adapters.DialogPostAdapter;
import rks.youngdevelopers.autotreguks.PostActivity;
import rks.youngdevelopers.autotreguks.R;

public class PostTechnicalFragment extends Fragment implements DialogPostAdapter.OnDialogListener {
    LinearLayout postTarga, postDogana, postRegjistrim, postPronar, postUlse, postNgjyra, postMateriali, postNgjyraEnterier;
    TextView tvPostTarga, tvPostDogana, tvPostRegjistrim, tvPosPronar, tvPostUlse, tvPostNgjyra, tvPostMateriali, tvPostNgjyraEnterier;
    RadioButton radioMetalike, radioMat;
    RadioGroup group;
    Dialog pickerDialog;
    int width, height;
    List<String> dialogItems;
    int btnID;
    private DatePickerDialog.OnDateSetListener mRegjistrimDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_teknike, container, false);
        ((PostActivity) getActivity()).getSupportActionBar().show();
        PostActivity.setTitle("Të dhënat teknike");


        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        postTarga = (LinearLayout) v.findViewById(R.id.postTargat);
        postDogana = (LinearLayout) v.findViewById(R.id.postDogana);
        postRegjistrim = (LinearLayout) v.findViewById(R.id.postRegjistrim);
        postPronar = (LinearLayout) v.findViewById(R.id.postPronar);
        postUlse = (LinearLayout) v.findViewById(R.id.postUlse);
        postNgjyra = (LinearLayout) v.findViewById(R.id.postNgjyra);
        postMateriali = (LinearLayout) v.findViewById(R.id.postMateriali);
        postNgjyraEnterier = (LinearLayout) v.findViewById(R.id.postNgjyraEnterier);

        tvPostTarga = (TextView) v.findViewById(R.id.tvPostTargat);
        tvPostDogana = (TextView) v.findViewById(R.id.tvPostDoganuar);
        tvPostRegjistrim = (TextView) v.findViewById(R.id.tvPostRegjistrim);
        tvPosPronar = (TextView) v.findViewById(R.id.tvPostPronar);
        tvPostUlse = (TextView) v.findViewById(R.id.tvPostUlse);
        tvPostNgjyra = (TextView) v.findViewById(R.id.tvPostNgjyra);
        tvPostMateriali = (TextView) v.findViewById(R.id.tvPostMateriali);
        tvPostNgjyraEnterier = (TextView) v.findViewById(R.id.tvPostNgjyraMaterialit);

        radioMetalike = (RadioButton) v.findViewById(R.id.btnMetalike);
        radioMat = (RadioButton) v.findViewById(R.id.btnMat);

        radioMetalike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvPostNgjyra.getText().toString().trim().equals("Pa përcaktuar")) {
                    PostActivity.post.setNgjyraMetalike(true);
                }
            }
        });

        radioMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvPostNgjyra.getText().toString().trim().equals("Pa përcaktuar")) {
                    PostActivity.post.setNgjyraMetalike(false);
                }
            }
        });

        group = (RadioGroup) v.findViewById(R.id.group);

        if (PostActivity.post.getTarga() != null) {
            tvPostTarga.setText(PostActivity.post.getTarga());

            if (PostActivity.post.getTarga().equals("Vendore")) {
                postRegjistrim.setVisibility(View.VISIBLE);
                postDogana.setVisibility(View.GONE);
            } else if (PostActivity.post.getTarga().equals("Të huaja")) {
                postRegjistrim.setVisibility(View.GONE);
                postDogana.setVisibility(View.VISIBLE);
            } else {
                postRegjistrim.setVisibility(View.GONE);
                postDogana.setVisibility(View.GONE);
            }
        }

        if (PostActivity.post.getDoganuar() != null)
            tvPostDogana.setText(PostActivity.post.getDoganuar());

        if (PostActivity.post.getRegjistrim() != null)
            tvPostRegjistrim.setText(PostActivity.post.getRegjistrim());

        if (PostActivity.post.getNrPronareve() != null)
            tvPosPronar.setText(PostActivity.post.getNrPronareve());

        if (PostActivity.post.getNrUleseve() != null)
            tvPostUlse.setText(PostActivity.post.getNrUleseve());

        if (PostActivity.post.getNgjyra() != 0) {
            List<String> ngjyra = Arrays.asList(getResources().getStringArray(R.array.ngjyra));
            tvPostNgjyra.setText(ngjyra.get(PostActivity.post.getNgjyra()));
            if(PostActivity.post.isNgjyraMetalike())
                radioMetalike.setChecked(true);
            else
                radioMat.setChecked(true);
        }

        if (PostActivity.post.getMaterialiEnterier() != 0){
            List<String> enterier = Arrays.asList(getResources().getStringArray(R.array.enterieri));
            tvPostMateriali.setText(enterier.get(PostActivity.post.getMaterialiEnterier()));
        }


        if (PostActivity.post.getNgjyraEnterier() != 0) {
            List<String> interiorColor = Arrays.asList(getResources().getStringArray(R.array.ngjyraEnterier));
            tvPostNgjyraEnterier.setText(interiorColor.get(PostActivity.post.getNgjyraEnterier()));
        }

        postTarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 1;
                clicker(btnID);
            }
        });

        postDogana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 2;
                clicker(btnID);
            }
        });

        postRegjistrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int viti = calendar.get(Calendar.YEAR);
                int muaji = calendar.get(Calendar.MONTH);
                int dita = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogu = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mRegjistrimDate,
                        viti, muaji, dita);
                String vit = "31556952000";
                ((ViewGroup) dialogu.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                dialogu.getDatePicker().setMaxDate(calendar.getTimeInMillis()+Long.parseLong(vit));
                dialogu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogu.show();
            }
        });

        mRegjistrimDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String viti = String.valueOf(month+1)+"/"+String.valueOf(year);
                tvPostRegjistrim.setText(viti);
                PostActivity.post.setRegjistrim(viti);
            }
        };

        postPronar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 4;
                clicker(btnID);
            }
        });

        postUlse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 5;
                clicker(btnID);
            }
        });

        postNgjyra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 6;
                clicker(btnID);
            }
        });

        postMateriali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 7;
                clicker(btnID);
            }
        });

        postNgjyraEnterier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 8;
                clicker(btnID);
            }
        });


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void clicker(int button) {
        pickerDialog = new Dialog(getContext());
        pickerDialog = new Dialog(getContext());
        pickerDialog.setContentView(R.layout.dialog_post);
        pickerDialog.setTitle(" ");
        pickerDialog.getWindow().setLayout((width - 100), (width - 100));

        TextView tvTitulli = (TextView) pickerDialog.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) pickerDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDialog.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) pickerDialog.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));


        SQLiteDatabase objDb = (new LocalDatabase(getContext())).getReadableDatabase();
        Cursor c = null;

        dialogItems = new ArrayList<>();

        if (button == 1) {
            tvTitulli.setText("Targat e veturës:");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.targat));
        } else if (button == 2) {
            tvTitulli.setText("Daganuar:");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.opsione));
        } else if (button == 4) {
            tvTitulli.setText("Numri i pronarëve:");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.pronar));
        } else if (button == 5) {
            tvTitulli.setText("Numri i ulëseve:");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.ulse));
        } else if (button == 6) {
            tvTitulli.setText("Ngjyra e veturës");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.ngjyra));
        } else if (button == 7) {
            tvTitulli.setText("Enterieri");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.enterieri));
        } else if (button == 8) {
            tvTitulli.setText("Ngjyra e enterierit");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.ngjyraEnterier));
        }
        DialogPostAdapter dialogAdapteri = new DialogPostAdapter(dialogItems, getContext(), this);
        recyclerDialog.setAdapter(dialogAdapteri);
        pickerDialog.show();
    }

    @Override
    public void onDialogItemClick(int position) {
        if (btnID == 1) {
            PostActivity.post.setTarga(dialogItems.get(position));
            tvPostTarga.setText(dialogItems.get(position));
            if (position == 1) {
                postDogana.setVisibility(View.GONE);
                postRegjistrim.setVisibility(View.VISIBLE);
                PostActivity.post.setDoganuar(null);
            } else if (position == 3) {
                postDogana.setVisibility(View.VISIBLE);
                postRegjistrim.setVisibility(View.GONE);
                PostActivity.post.setRegjistrim(null);
            } else {
                postDogana.setVisibility(View.GONE);
                postRegjistrim.setVisibility(View.GONE);
                PostActivity.post.setDoganuar(null);
                PostActivity.post.setRegjistrim(null);
            }
        } else if (btnID == 2) {
            PostActivity.post.setDoganuar(dialogItems.get(position));
            tvPostDogana.setText(dialogItems.get(position));
        } else if (btnID == 4) {
            PostActivity.post.setNrPronareve(dialogItems.get(position));
            tvPosPronar.setText(dialogItems.get(position));
        } else if (btnID == 5) {
            PostActivity.post.setNrUleseve(dialogItems.get(position));
            tvPostUlse.setText(dialogItems.get(position));
        } else if (btnID == 6) {
            PostActivity.post.setNgjyra(position);
            PostActivity.post.setNgjyraMetalike(radioMetalike.isChecked());
            tvPostNgjyra.setText(dialogItems.get(position));
        } else if (btnID == 7) {
            PostActivity.post.setMaterialiEnterier(position);
            tvPostMateriali.setText(dialogItems.get(position));
        } else if (btnID == 8) {
            PostActivity.post.setNgjyraEnterier(position);
            tvPostNgjyraEnterier.setText(dialogItems.get(position));
        }
        pickerDialog.dismiss();
    }
}
