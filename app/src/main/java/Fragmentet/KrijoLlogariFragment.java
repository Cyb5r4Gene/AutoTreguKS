package Fragmentet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import Database.RemoteDatabase;
import rks.youngdevelopers.autotreguks.LoginActivity;
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
    ImageView gabimQyteti;
    TextView tvSukses, tv2;

    LinearLayout layoutAutoSallon, layoutTipi;

    Button btnRuaj, btnAnulo, btnAdresa;


    FirebaseAuth firebaseAuth;
    FirebaseUser fUser;

    String emri, mbiemri, datelindja, tel, emriAutosallonit, telAutosallonit, pershkrimiAutosallonit,
            adresaRruga, adresaNr, adresaKodi, email, pass, pass2;


    int qyteti;
    String tipi = "1";


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dRef = db.getReference();

    FirebaseUser useri;

    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_krijo_llogari, container, false);
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

        gabimQyteti = (ImageView) view.findViewById(R.id.gabimQyteti);


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
                dialogu.getDatePicker().setMaxDate(calendar.getTimeInMillis());
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
                    tipi = "2";
                }
            }
        });

        radioPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioPrivate.isChecked()) {
                    layoutAutoSallon.setVisibility(View.GONE);
                    tvAuto.setVisibility(View.GONE);
                    tipi = "1";
                }
            }
        });

        gabimQyteti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ju lutem zgjidhni qytetin", Toast.LENGTH_SHORT).show();
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
                    etKrijoEmri.setError("Emri duhet të jetë më i gjatë se 2 karaktere");
                    etKrijoEmri.requestFocus();
                }

                if (mbiemri.isEmpty() || mbiemri.length() < 3) {
                    gabime++;
                    etKrijoMbiemri.setError("Mbiemri duhet të jetë më i gjatë se 2 karaktere");
                    etKrijoMbiemri.requestFocus();
                }

                if (tvDatelindja.getText().toString().trim().equals("Datëlindja")) {
                    gabime++;
                    tvDatelindja.setError("Ju lutem caktoni datëlindjen");
                    tvDatelindja.requestFocus();
                }

                if (radioAutoSallon.isChecked()) {
                    if (emriAutosallonit.isEmpty() || emriAutosallonit.length() < 3) {
                        gabime++;
                        etKrijoSalloni.setError("Emri i Autosallonit duhet të jetë më i gjatë se 2 karaktere");
                        etKrijoEmri.requestFocus();
                    }
                }

                if (adresaRruga.isEmpty() || adresaRruga.length() < 3) {
                    gabime++;
                    etKrijoRruga.setError("Shënoni emrin e rrugës");
                    etKrijoRruga.requestFocus();
                }

                if (adresaNr.isEmpty()) {
                    gabime++;
                    etKrijoNr.setError("Ju lutem shënoni numrin e adresës");
                    etKrijoNr.requestFocus();
                }

                if (adresaKodi.isEmpty() || adresaKodi.length() < 4) {
                    gabime++;
                    etKrijoKodi.setError("Ju lutem shkruani kodin postar tuaj");
                    etKrijoKodi.requestFocus();
                }

                if (qyteti == 0) {
                    gabime++;
                    gabimQyteti.setVisibility(View.VISIBLE);
                } else {
                    gabimQyteti.setVisibility(View.GONE);
                }

                if (tel.isEmpty() || tel.length() != 9) {
                    gabime++;
                    etKrijoTelefoni.setError("Shkruani numër valid të telefonit");
                    etKrijoTelefoni.requestFocus();
                }

                if (email.isEmpty() || email.length() < 10) {
                    gabime++;
                    etKrijoEmail.setError("Ju lutemi shkruani email valide");
                    etKrijoEmail.requestFocus();
                }

                if (pass.length() < 6 )
                {
                    etKrijoPass.setError("Fjalekalimi duhet të jetë më i gjatë se 6 karaktere");
                    etKrijoPass.requestFocus();
                }
                if(!pass.equals(pass2)) {
                    gabime++;
                    etKrijoPass.setError("Fjalekalimet nuk përputhen");
                    etKrijoPass.requestFocus();
                    etKrijoKonfirmoPass.setError("Fjalekalimet nuk përputhen");
                }

                if (gabime == 0) {
                    progressDialog.show();
                    progressDialog.setMessage("Duke regjistruar perdoruesin...!");

                    RemoteDatabase.regjistrimiKredencialeve(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                RemoteDatabase.regjistroPerdoruesin(email, pass, emri, mbiemri, datelindja, tel, tipi);
                                String lat, lng;
                                if (adresaLatLng == null) {
                                    lat = "-";
                                    lng = "-";
                                } else {
                                    lat = String.valueOf(adresaLatLng.latitude);
                                    lng = String.valueOf(adresaLatLng.longitude);
                                }

                                RemoteDatabase.regjistroAdresen(adresaRruga, adresaNr, adresaKodi, String.valueOf(qyteti), lat, lng);

                                if (radioAutoSallon.isChecked()) {
                                    RemoteDatabase.regjistroAutosallonin(emriAutosallonit, pershkrimiAutosallonit, telAutosallonit);
                                }

                                fUser = FirebaseAuth.getInstance().getCurrentUser();
                                fUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            final Dialog verify = new Dialog(getActivity());
                                            verify.setContentView(R.layout.dialog_information);
                                            verify.setTitle("Verifiko emailin");
                                            verify.show();
                                            tvSukses = (TextView) verify.findViewById(R.id.tvSukses);
                                            tv2 = (TextView) verify.findViewById(R.id.tv2);
                                            Thread prit = new Thread() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        sleep(30000);
                                                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                if (task.isSuccessful()) {
                                                                    if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                                                                        Intent objUserLogin = new Intent(getActivity().getApplicationContext(), UserActivity.class);
                                                                        startActivity(objUserLogin);
                                                                    } else {
                                                                        firebaseAuth.signOut();
                                                                        Toast.makeText(getActivity(), "Llogaria nuk u verifikuar. " +
                                                                                "\nPas verifikimit në email, ju mund të keni çasje në llogarinë tuaj përmes modulit 'KYÇu'", Toast.LENGTH_LONG).show();
                                                                        LoginActivity.imgBack.callOnClick();
                                                                    }
                                                                }
                                                            }
                                                        });
                                                        verify.dismiss();
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            prit.start();
                                        }
                                    }
                                });
                            } else {
                                progressDialog.hide();

                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthException e) {
                                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                                    switch (errorCode) {


                                        case "ERROR_INVALID_EMAIL":
                                            Toast.makeText(getActivity(), "Formati i email-it është gabim", Toast.LENGTH_LONG).show();
                                            etKrijoEmail.setError("TFormati i këtij email-i është gabim!");
                                            etKrijoEmail.requestFocus();
                                            break;

                                        case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                            Toast.makeText(getActivity(), "Emaili ekziston në databazë", Toast.LENGTH_LONG).show();
                                            etKrijoEmail.setError("Emaili ekziston në databazë");
                                            etKrijoEmail.requestFocus();
                                            break;

                                        case "ERROR_EMAIL_ALREADY_IN_USE":
                                            Toast.makeText(getActivity(), "Emaili ekziston në databazë", Toast.LENGTH_LONG).show();
                                            etKrijoEmail.setError("Emaili ekziston në databazë");
                                            etKrijoEmail.requestFocus();
                                            break;

                                        case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                            Toast.makeText(getActivity(), "Ekziston llogari tjetër me këto kredenciale!", Toast.LENGTH_LONG).show();
                                            break;
                                    }


                                }catch(FirebaseNetworkException e)
                                {
                                    Toast.makeText(getActivity(), "Nuk ka çasje në internet!", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Log.e("createUser:failed", e.getMessage());
                                }
                                //Toast.makeText(getActivity(), "Regjistrimi deshtoi!\nEmaili ekziston në databazë!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Ju lutem plotësoni fushat me të dhëna të sakta!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        }
}
