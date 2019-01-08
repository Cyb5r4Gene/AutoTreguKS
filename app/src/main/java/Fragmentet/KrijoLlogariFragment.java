package Fragmentet;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import rks.youngdevelopers.autotreguks.Database;
import rks.youngdevelopers.autotreguks.MapsActivity;
import rks.youngdevelopers.autotreguks.R;
import rks.youngdevelopers.autotreguks.UserActivity;

public class KrijoLlogariFragment extends Fragment {

    EditText etKrijoEmri, etKrijoMbiemri, etKrijoTelefoni, etKrijoSalloni, etAutosalloniTel, etAutosalloniPershkrimi,
            etKrijoRruga, etKrijoNr, etKrijoKodi, etKrijoEmail, etKrijoPass, etKrijoKonfirmoPass;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    RadioButton radioPrivate, radioAutoSallon;
    TextView tvDatelindja, tvAuto;
    Spinner spinnerKrijoQyteti;
    public static LatLng adresaLatLng = null;
    ImageView gabimEmri, gabimMbiemri, gabimDatelindja, gabimTel, gabimAutosalloni, gabimRruga, gabimNr, gabimKodi,
            gabimEmail, gabimPass, gabimPass2, gabimQyteti;


    LinearLayout layoutAutoSallon, layoutTipi;

    Button btnRuaj, btnAnulo, btnAdresa;


    FirebaseAuth firebaseAuth;

    String emri, mbiemri, datelindja, tel, emriAutosallonit, telAutosallonit, pershkrimiAutosallonit,
            adresaRruga, adresaNr, adresaKodi, email, pass, pass2;


    int qyteti, tipi;


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dRef = db.getReference();

    FirebaseUser useri;

    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_krijo_llogari, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerKrijoQyteti = (Spinner) view.findViewById(R.id.spinnerKrijoQyteti);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity().getApplicationContext(), R.array.qytetet,
                        R.layout.spinner_style);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerKrijoQyteti.setAdapter(staticAdapter);

        etKrijoEmri = (EditText) view.findViewById(R.id.etKrijoEmri);
        etKrijoMbiemri = (EditText) view.findViewById(R.id.etKrijoMbiemri);
        etKrijoTelefoni = (EditText) view.findViewById(R.id.etKrijoNumri);
        etKrijoSalloni = (EditText) view.findViewById(R.id.etKrijoAutosalloni);
        etAutosalloniTel = (EditText) view.findViewById(R.id.etAutosalloniTel);
        etAutosalloniPershkrimi = (EditText) view.findViewById(R.id.etPershkrimiAutosallonit);
        etKrijoRruga = (EditText) view.findViewById(R.id.etKrijoRruga);
        etKrijoNr = (EditText) view.findViewById(R.id.etKrijoNr);
        etKrijoKodi = (EditText) view.findViewById(R.id.etKrijoKodiPostal);

        etKrijoEmail = (EditText) view.findViewById(R.id.etKrijoEmail);
        etKrijoPass = (EditText) view.findViewById(R.id.etKrijoPassword);
        etKrijoKonfirmoPass = (EditText) view.findViewById(R.id.etKrijoKonfirmPassword);


        tvAuto = (TextView) view.findViewById(R.id.tvAdresaAutosallonit);
        radioPrivate = (RadioButton) view.findViewById(R.id.radioPrivate);
        radioAutoSallon = (RadioButton) view.findViewById(R.id.radioAutoSallon);
        layoutAutoSallon = (LinearLayout) view.findViewById(R.id.layoutAutoSallon);
        layoutTipi = (LinearLayout) view.findViewById(R.id.layoutTipi);
        btnAnulo = (Button) view.findViewById(R.id.btnAnulo);
        btnRuaj = (Button) view.findViewById(R.id.btnKrijo);
        btnAdresa = (Button) view.findViewById(R.id.btnQyteti);
        tvDatelindja = (TextView) view.findViewById(R.id.tvKrijoDatelindja);

        gabimEmri = (ImageView) view.findViewById(R.id.gabimEmri);
        gabimMbiemri = (ImageView) view.findViewById(R.id.gabimMbiemri);
        gabimEmail = (ImageView) view.findViewById(R.id.gabimEmail);
        gabimPass = (ImageView) view.findViewById(R.id.gabimPass);
        gabimPass2 = (ImageView) view.findViewById(R.id.gabimPass2);
        gabimDatelindja = (ImageView) view.findViewById(R.id.gabimDatelindja);
        gabimQyteti = (ImageView) view.findViewById(R.id.gabimQyteti);
        gabimTel = (ImageView) view.findViewById(R.id.gabimTel);
        gabimAutosalloni = (ImageView) view.findViewById(R.id.gabimAutosalloni);
        gabimRruga = (ImageView) view.findViewById(R.id.gabimRruga);
        gabimNr = (ImageView) view.findViewById(R.id.gabimNr);
        gabimKodi = (ImageView) view.findViewById(R.id.gabimKodiPostal);


        datelindja = "";

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());

        btnAdresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adresaID();
                Intent objIntent = new Intent(getContext(), MapsActivity.class);
                startActivity(objIntent);
            }
        });

        tvDatelindja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // shfaqet dialogu i datelindjes
                Calendar calendar = Calendar.getInstance();
                int viti = calendar.get(Calendar.YEAR);
                int muaji = calendar.get(Calendar.MONTH);
                int dita = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogu = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        viti, muaji, dita);
                dialogu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogu.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                datelindja = String.valueOf(dayOfMonth) + " / " + String.valueOf(month + 1) + " / " + String.valueOf(year);
                tvDatelindja.setText("Datëlindja:  " + datelindja);

            }
        };

        radioAutoSallon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioAutoSallon.isChecked()) {
                    layoutAutoSallon.setVisibility(View.VISIBLE);
                    tvAuto.setVisibility(View.VISIBLE);
                    tipi = 2;
                }
            }
        });

        radioPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioPrivate.isChecked()) {
                    layoutAutoSallon.setVisibility(View.GONE);
                    tvAuto.setVisibility(View.GONE);
                    tipi = 1;
                }
            }
        });

        gabimEmri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Emri duhet të jetë më i gjatë se 2 karaktere", Toast.LENGTH_SHORT).show();
            }
        });

        gabimMbiemri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Mbiemri duhet të jetë më i gjatë se 2 karaktere", Toast.LENGTH_SHORT).show();
            }
        });

        gabimDatelindja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ju lutem vendosni datëlindjen tuaj", Toast.LENGTH_SHORT).show();
            }
        });

        gabimAutosalloni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ju lutem shkruani emrin e autosallonit", Toast.LENGTH_SHORT).show();
            }
        });

        gabimRruga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ju lutem shkruani emrin e rrugës", Toast.LENGTH_SHORT).show();
            }
        });

        gabimNr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ju lutem shkruani numrin e shtëpisë", Toast.LENGTH_SHORT).show();
            }
        });

        gabimKodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ju lutem shkruani kodin postar", Toast.LENGTH_SHORT).show();
            }
        });

        gabimQyteti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ju lutem zgjidhni qytetin", Toast.LENGTH_SHORT).show();
            }
        });

        gabimTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Numri i telefonit duhet të jetë në formatin:\n04X123456", Toast.LENGTH_SHORT).show();
            }
        });

        gabimEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ju lutem shënoni një email valide", Toast.LENGTH_SHORT).show();
            }
        });

        gabimPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Fjalëkalimi duhet të jetë i gjatë së paku 6 karaktere", Toast.LENGTH_SHORT).show();
            }
        });

        gabimPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.equals(pass2))
                    Toast.makeText(getContext(), "Fjalëkalimi duhet të jetë i gjatë së paku 6 karaktere", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Fjalëkalimet nuk përputhen", Toast.LENGTH_SHORT).show();
            }
        });

        btnAnulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etKrijoEmri.setText("");
                etKrijoMbiemri.setText("");
                etKrijoTelefoni.setText("");
                tvDatelindja.setText("Datëlindja");
                spinnerKrijoQyteti.setSelection(0);
                etKrijoEmail.setText("");
                etKrijoPass.setText("");
                etKrijoKonfirmoPass.setText("");
                radioPrivate.callOnClick();
                etKrijoSalloni.setText("");
                etAutosalloniTel.setText("");
                etAutosalloniPershkrimi.setText("");
                etKrijoRruga.setText("");
                etKrijoNr.setText("");
                etKrijoKodi.setText("");

            }
        });

        btnRuaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gabime = 0;

                emri = etKrijoEmri.getText().toString().trim();
                mbiemri = etKrijoMbiemri.getText().toString().trim();
                email = etKrijoEmail.getText().toString();
                pass = etKrijoPass.getText().toString();
                pass2 = etKrijoKonfirmoPass.getText().toString().trim();
                tel = etKrijoTelefoni.getText().toString().trim();
                emriAutosallonit = etKrijoSalloni.getText().toString().trim();
                telAutosallonit = etAutosalloniTel.getText().toString().trim();
                pershkrimiAutosallonit = etAutosalloniPershkrimi.getText().toString().trim();
                adresaRruga = etKrijoRruga.getText().toString().trim();
                adresaNr = etKrijoNr.getText().toString().trim();
                adresaKodi = etKrijoKodi.getText().toString().trim();
                qyteti = spinnerKrijoQyteti.getSelectedItemPosition();

                if (emri.isEmpty() || emri.length() < 3) {
                    gabime++;
                    gabimEmri.setVisibility(View.VISIBLE);
                } else {
                    gabimEmri.setVisibility(View.GONE);
                }

                if (mbiemri.isEmpty() || mbiemri.length() < 3) {
                    gabime++;
                    gabimMbiemri.setVisibility(View.VISIBLE);
                } else {
                    gabimMbiemri.setVisibility(View.GONE);
                }

                if (tvDatelindja.getText().toString().trim().equals("Datëlindja")) {
                    gabime++;
                    gabimDatelindja.setVisibility(View.VISIBLE);
                } else {
                    gabimDatelindja.setVisibility(View.GONE);
                }

                if (radioAutoSallon.isChecked()) {
                    if (emriAutosallonit.isEmpty() || emriAutosallonit.length() < 3) {
                        gabime++;
                        gabimAutosalloni.setVisibility(View.VISIBLE);
                    } else {
                        gabimAutosalloni.setVisibility(View.GONE);
                    }
                }

                if (adresaRruga.isEmpty() || adresaRruga.length() < 3) {
                    gabime++;
                    gabimRruga.setVisibility(View.VISIBLE);
                } else {
                    gabimRruga.setVisibility(View.GONE);
                }

                if (adresaNr.isEmpty()) {
                    gabime++;
                    gabimNr.setVisibility(View.VISIBLE);
                } else {
                    gabimNr.setVisibility(View.GONE);
                }

                if (adresaKodi.isEmpty() || adresaKodi.length() < 4) {
                    gabime++;
                    gabimKodi.setVisibility(View.VISIBLE);
                } else {
                    gabimKodi.setVisibility(View.GONE);
                }

                if (qyteti == 0) {
                    gabime++;
                    gabimQyteti.setVisibility(View.VISIBLE);
                } else {
                    gabimQyteti.setVisibility(View.GONE);
                }

                if (tel.isEmpty() || tel.length() != 9) {
                    gabime++;
                    gabimTel.setVisibility(View.VISIBLE);
                } else {
                    gabimTel.setVisibility(View.GONE);
                }

                if (email.isEmpty() || email.length() < 10) {
                    gabime++;
                    gabimEmail.setVisibility(View.VISIBLE);
                } else {
                    gabimEmail.setVisibility(View.GONE);
                }

                if (pass.length() < 6 || !pass.equals(pass2)) {
                    gabime++;
                    gabimPass.setVisibility(View.VISIBLE);
                    gabimPass2.setVisibility(View.VISIBLE);
                } else {
                    gabimPass.setVisibility(View.GONE);
                    gabimPass2.setVisibility(View.GONE);
                }

                if (gabime == 0) {
                    progressDialog.show();
                    progressDialog.setMessage("Duke regjistruar perdoruesin...!");
                    //Boolean b =Database.regjistrimiKredencialeve(email, pass);

                    Database.regjistrimiKredencialeve(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Database.regjistroPerdoruesin(email, pass, emri, mbiemri, datelindja, tel, String.valueOf(tipi));
                                String lat, lng;
                                if (adresaLatLng == null) {
                                    lat = "-";
                                    lng = "-";
                                } else {
                                    lat = String.valueOf(adresaLatLng.latitude);
                                    lng = String.valueOf(adresaLatLng.longitude);
                                }

                                Database.regjistroAdresen(adresaRruga, adresaNr, adresaKodi, String.valueOf(qyteti), lat, lng);

                                if (radioAutoSallon.isChecked()) {
                                    Database.regjistroAutosallonin(emriAutosallonit, pershkrimiAutosallonit, telAutosallonit);
                                }

                                Intent objUserLogin = new Intent(getActivity().getApplicationContext(), UserActivity.class);
                                startActivity(objUserLogin);

                                Toast.makeText(getActivity(), "Regjistrimi u krye me sukses!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), "Regjistrimi deshtoi!\nEmaili ekziston në databazë!", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                    progressDialog.hide();

                } else {
                    Toast.makeText(getContext(), "Ju lutem plotësoni fushat me të dhëna të sakta!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //funksioni per regjistrimin e kredencialeve te autentikimit te perdoruesit
//    private void regjistrimiKredencialeve (String email, String pass)
//    {
//
//        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    regjistroPerdoruesin();
//                    Toast.makeText(getActivity(), "Regjistrimi u krye me sukses!", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getActivity(), "Regjistrimi deshtoi!\nEmaili ekziston në databazë!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }

    //funksioni per ruajtjen e te dhenave te userit ne databaze
//    private void regjistroPerdoruesin()
//    {
//        firebaseAuth.signInWithEmailAndPassword(etKrijoEmail.getText().toString(), etKrijoPass.getText().toString());
//        String data = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
//        User user = new User(emri, mbiemri, email, datelindja, String.valueOf(adresaID), tel, String.valueOf(tipi), data, data);
//        useri = FirebaseAuth.getInstance().getCurrentUser();
//        dRef.child("users").child(useri.getUid()).setValue(user);
//    }


//    int PLACE_PICKER_REQUEST = 1;
//
//    public void adresaID()
//    {
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//
//        try {
//            getActivity().startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
//        } catch (GooglePlayServicesRepairableException e) {
//            e.printStackTrace();
//        } catch (GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//        }
//    }


}
