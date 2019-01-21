package Fragmentet;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rks.youngdevelopers.autotreguks.DialogCheckAdapter;
import rks.youngdevelopers.autotreguks.DialogPostAdapter;
import rks.youngdevelopers.autotreguks.PostActivity;
import rks.youngdevelopers.autotreguks.R;

public class PostKarakteristikatFragment extends Fragment implements DialogPostAdapter.OnDialogListener, DialogCheckAdapter.OnCheckListener {

    CheckBox checkBluetooth, checkComputer, checkCD, checkXhama, checkUlseElektrike, checkUlseNxemje, checkUlseSportive,
            checkMP3, checkAux, checkPulla, checkNav, checkShiber, checkPanorame, checkBagazh, checkMbylljeQendrore,
            checkPasqyre, checkAmortizim, checkSportPakete, checkAbs, check4x4,  checkEsp, checkDritaAdaptuese,
            checkSensorDritash, checkMjegull, checkXenon, checkBiXenon, checkSensorShiu, checkStartStop;

    TextView tvKondicioneri, tvAirbag;

    LinearLayout kondicioneri, airbag;
    int width, height;
    int btnID;
    Dialog pickerDialog;

    List<String> dialogItems;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_karakteristikat, container, false);
        ((PostActivity) getActivity()).getSupportActionBar().setTitle("Karakteristikat e veturës");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        kondicioneri = (LinearLayout) view.findViewById(R.id.postKlima);
        airbag = (LinearLayout) view.findViewById(R.id.postAirbag);

        kondicioneri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 1;
                klima();
            }
        });

        airbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                airbag();
            }
        });

        tvKondicioneri = (TextView)view.findViewById(R.id.tvPostKondicioneri);
        tvAirbag = (TextView)view.findViewById(R.id.tvPostAirbag);

        checkBluetooth = (CheckBox)view.findViewById(R.id.checkBluetooth);
        checkComputer = (CheckBox)view.findViewById(R.id.checkComputer);
        checkCD = (CheckBox)view.findViewById(R.id.checkCD);
        checkXhama = (CheckBox)view.findViewById(R.id.checkXhama);
        checkUlseElektrike = (CheckBox)view.findViewById(R.id.checkUlse);
        checkUlseNxemje = (CheckBox)view.findViewById(R.id.checkUlseNxemje);
        checkUlseSportive = (CheckBox)view.findViewById(R.id.checkUlseSportive);
        checkMP3 = (CheckBox)view.findViewById(R.id.checkMP3);
        checkAux = (CheckBox)view.findViewById(R.id.checkAux);
        checkPulla = (CheckBox)view.findViewById(R.id.checkTimon);
        checkNav = (CheckBox)view.findViewById(R.id.checkNav);
        checkShiber = (CheckBox)view.findViewById(R.id.checkShiber);
        checkPanorame = (CheckBox)view.findViewById(R.id.checkPanorame);
        checkBagazh = (CheckBox)view.findViewById(R.id.checkBagazh);
        checkMbylljeQendrore = (CheckBox)view.findViewById(R.id.checkCentral);
        checkPasqyre = (CheckBox)view.findViewById(R.id.checkPasqyre);
        checkAmortizim = (CheckBox)view.findViewById(R.id.checkAmortizim);
        checkSportPakete = (CheckBox)view.findViewById(R.id.checkSportPakete);
        checkAbs = (CheckBox)view.findViewById(R.id.checkABS);
        check4x4 = (CheckBox)view.findViewById(R.id.check4x4);
        checkEsp = (CheckBox)view.findViewById(R.id.checkESP);
        checkDritaAdaptuese = (CheckBox)view.findViewById(R.id.checkDrita);
        checkSensorDritash = (CheckBox)view.findViewById(R.id.checkDritaSensor);
        checkMjegull = (CheckBox)view.findViewById(R.id.checkMjegull);
        checkXenon = (CheckBox)view.findViewById(R.id.checkXenon);
        checkBiXenon = (CheckBox)view.findViewById(R.id.checkBiXenon);
        checkSensorShiu = (CheckBox)view.findViewById(R.id.checkShi);
        checkStartStop = (CheckBox)view.findViewById(R.id.checkStartStop);

        checkBluetooth.setChecked(PostActivity.karakteristikat.isBluetooth());
        checkComputer.setChecked(PostActivity.karakteristikat.isOnBoardKompjuter());
        checkCD.setChecked(PostActivity.karakteristikat.isCDPlayer());
        checkXhama.setChecked(PostActivity.karakteristikat.isXhamaElektrik());
        checkUlseElektrike.setChecked(PostActivity.karakteristikat.isUleseMeLevizjeElektrike());
        checkUlseNxemje.setChecked(PostActivity.karakteristikat.isUleseMeNxemje());
        checkUlseSportive.setChecked(PostActivity.karakteristikat.isUleseSportive());
        checkMP3.setChecked(PostActivity.karakteristikat.isMp3());
        checkAux.setChecked(PostActivity.karakteristikat.isAux());
        checkPulla.setChecked(PostActivity.karakteristikat.isPullaNeTimon());
        checkNav.setChecked(PostActivity.karakteristikat.isNavigacion());
        checkShiber.setChecked(PostActivity.karakteristikat.isShiber());
        checkPanorame.setChecked(PostActivity.karakteristikat.isPanorame());
        checkBagazh.setChecked(PostActivity.karakteristikat.isBagazhNeCati());
        checkMbylljeQendrore.setChecked(PostActivity.karakteristikat.isMbylljeQendrore());
        checkPasqyre.setChecked(PostActivity.karakteristikat.isPasqyreElektrike());
        checkAmortizim.setChecked(PostActivity.karakteristikat.isAmortizimSportiv());
        checkSportPakete.setChecked(PostActivity.karakteristikat.isSportPakete());
        checkAbs.setChecked(PostActivity.karakteristikat.isAbs());
        check4x4.setChecked(PostActivity.karakteristikat.isKaterX4());
        checkEsp.setChecked(PostActivity.karakteristikat.isEsp());
        checkDritaAdaptuese.setChecked(PostActivity.karakteristikat.isDritaAdaptuese());
        checkSensorDritash.setChecked(PostActivity.karakteristikat.isSensorDritash());
        checkMjegull.setChecked(PostActivity.karakteristikat.isDritaTeMjegulles());
        checkXenon.setChecked(PostActivity.karakteristikat.isDritaXenon());
        checkBiXenon.setChecked(PostActivity.karakteristikat.isDritaBiXenon());
        checkSensorShiu.setChecked(PostActivity.karakteristikat.isSensorShiu());
        checkStartStop.setChecked(PostActivity.karakteristikat.isStartStop());

        if(PostActivity.karakteristikat.getKondicioneri()!=null)
            tvKondicioneri.setText(PostActivity.karakteristikat.getKondicioneri());
        String ajri = "";
        if(PostActivity.airbag.isAirbagShoferi())
            ajri+="Airbag Shoferi";
        if(PostActivity.airbag.isAirbagAnesor())
            ajri+=", airbag Anësor";
        if(PostActivity.airbag.isAirbagIPrapme())
            ajri+=", airbag i prapmë";
        if(PostActivity.airbag.isAirbagTjere())
            ajri+=" dhe airbag tjerë";

        if(!ajri.isEmpty())
            tvAirbag.setText(ajri);

        checkBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setBluetooth(isChecked);
            }
        });

        checkComputer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setOnBoardKompjuter(isChecked);
            }
        });

        checkCD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setCDPlayer(isChecked);
            }
        });

        checkXhama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setXhamaElektrik(isChecked);
            }
        });

        checkUlseElektrike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setUleseMeLevizjeElektrike(isChecked);
            }
        });

        checkUlseNxemje.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setUleseMeNxemje(isChecked);
            }
        });

        checkUlseSportive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setUleseSportive(isChecked);
            }
        });

        checkMP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setMp3(isChecked);
            }
        });

        checkAux.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setAux(isChecked);
            }
        });

        checkPulla.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setPullaNeTimon(isChecked);
            }
        });

        checkNav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setNavigacion(isChecked);
            }
        });

        checkShiber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setShiber(isChecked);
            }
        });

        checkPanorame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setPanorame(isChecked);
            }
        });

        checkBagazh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setBagazhNeCati(isChecked);
            }
        });

        checkMbylljeQendrore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setMbylljeQendrore(isChecked);
            }
        });

        checkPasqyre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setPasqyreElektrike(isChecked);
            }
        });

        checkAmortizim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setAmortizimSportiv(isChecked);
            }
        });

        checkSportPakete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setSportPakete(isChecked);
            }
        });

        checkAbs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setAbs(isChecked);
            }
        });

        check4x4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setKaterX4(isChecked);
            }
        });

        checkEsp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setEsp(isChecked);
            }
        });

        checkDritaAdaptuese.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setDritaAdaptuese(isChecked);
            }
        });

        checkSensorDritash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setSensorDritash(isChecked);
            }
        });

        checkMjegull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setDritaTeMjegulles(isChecked);
            }
        });

        checkXenon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setDritaXenon(isChecked);
            }
        });

        checkBiXenon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setDritaBiXenon(isChecked);
            }
        });

        checkSensorShiu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setSensorShiu(isChecked);
            }
        });

        checkStartStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.karakteristikat.setStartStop(isChecked);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void klima() {
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
        dialogItems = new ArrayList<>();
        tvTitulli.setText("Kondicioneri");
        dialogItems = Arrays.asList(getResources().getStringArray(R.array.kondicioneri));
        DialogPostAdapter dialogAdapteri = new DialogPostAdapter(dialogItems, getContext(), this);
        recyclerDialog.setAdapter(dialogAdapteri);
        pickerDialog.show();
    }

    private void airbag() {
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
        dialogItems = new ArrayList<>();
        tvTitulli.setText("Airbag");
        dialogItems = Arrays.asList(getResources().getStringArray(R.array.airbag));
        DialogCheckAdapter dialogCheckAdapter = new DialogCheckAdapter(dialogItems, getContext(), this);
        recyclerDialog.setAdapter(dialogCheckAdapter);

        String ajri = "";
        if(PostActivity.airbag.isAirbagShoferi())
            ajri+="Airbag Shoferi";
        if(PostActivity.airbag.isAirbagAnesor())
            ajri+=", airbag Anësor";
        if(PostActivity.airbag.isAirbagIPrapme())
            ajri+=", airbag i prapmë";
        if(PostActivity.airbag.isAirbagTjere())
            ajri+=" dhe airbag tjerë";

        if(!ajri.isEmpty())
            tvAirbag.setText(ajri);

        pickerDialog.show();


    }

    @Override
    public void onDialogItemClick(int position) {
            PostActivity.karakteristikat.setKondicioneri(dialogItems.get(position));
            tvKondicioneri.setText(dialogItems.get(position));
            pickerDialog.dismiss();

    }

    @Override
    public void onDialogCheckClick(int position) {


    }
}
