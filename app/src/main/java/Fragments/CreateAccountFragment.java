package Fragments;

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

public class CreateAccountFragment extends Fragment {

    // V A R I A B L E S
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String name, surname, birthdate, tel, dealershipName, dealershipTel, dealershipDesc,
            road, nr, postalCode, email, pass, pass2;
    int city;
    String type = "1";

    // S T A T I C  V A R I A B L E S
    public static LatLng addressLatLng = null;

    // V I E W S
    EditText etCreateName, etCreateSurname, etCreateTel, etCreateCarDealership, etDealershipTel, etDealershipDesc,
            etCreateRoad, etCreateNr, etCreateCode, etCreateEmail, etCreatePass, etCreateConfirmPass;
    RadioButton radioPrivate, radioCarDealership;
    TextView tvBirthDate, tvAuto;
    Spinner spinnerCity;
    ImageView errorCity, errorMap;
    TextView tvSuccess, tv2;
    LinearLayout layoutCarDealer, layoutType;
    Button btnSave, btnCancel, btnAddress;
    private ProgressDialog progressDialog;

    // D A T A B A S E
    FirebaseAuth firebaseAuth;
    FirebaseUser fUser;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dRef = db.getReference();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_krijo_llogari, container, false);


//        --------------------------------------VIEW INITIALIZATION--------------------------------------

        spinnerCity = (Spinner) view.findViewById(R.id.spinnerKrijoQyteti);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity().getApplicationContext(), R.array.qytetet,
                        R.layout.spinner_style);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerCity.setAdapter(staticAdapter);

        etCreateName = (EditText) view.findViewById(R.id.etKrijoEmri);
        etCreateSurname = (EditText) view.findViewById(R.id.etKrijoMbiemri);
        etCreateTel = (EditText) view.findViewById(R.id.etKrijoNumri);
        etCreateCarDealership = (EditText) view.findViewById(R.id.etKrijoAutosalloni);
        etDealershipTel = (EditText) view.findViewById(R.id.etAutosalloniTel);
        etDealershipDesc = (EditText) view.findViewById(R.id.etPershkrimiAutosallonit);
        etCreateRoad = (EditText) view.findViewById(R.id.etKrijoRruga);
        etCreateNr = (EditText) view.findViewById(R.id.etKrijoNr);
        etCreateCode = (EditText) view.findViewById(R.id.etKrijoKodiPostal);

        etCreateEmail = (EditText) view.findViewById(R.id.etKrijoEmail);
        etCreatePass = (EditText) view.findViewById(R.id.etKrijoPassword);
        etCreateConfirmPass = (EditText) view.findViewById(R.id.etKrijoKonfirmPassword);

        tvAuto = (TextView) view.findViewById(R.id.tvAdresaAutosallonit);
        radioPrivate = (RadioButton) view.findViewById(R.id.radioPrivate);
        radioCarDealership = (RadioButton) view.findViewById(R.id.radioAutoSallon);
        layoutCarDealer = (LinearLayout) view.findViewById(R.id.layoutAutoSallon);
        layoutType = (LinearLayout) view.findViewById(R.id.layoutTipi);
        btnCancel = (Button) view.findViewById(R.id.btnAnulo);
        btnSave = (Button) view.findViewById(R.id.btnKrijo);
        btnAddress = (Button) view.findViewById(R.id.btnQyteti);
        tvBirthDate = (TextView) view.findViewById(R.id.tvKrijoDatelindja);
        errorCity = (ImageView) view.findViewById(R.id.gabimQyteti);
        errorMap = (ImageView) view.findViewById(R.id.gabimHarta);

        /*---------------------------------------------------------------------------------------  */

        birthdate = "";

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objIntent = new Intent(getContext(), MapsActivity.class);
                startActivity(objIntent);
            }
        });

        /* THE FUNCTION TO SHOW THE BIRTHDATE DIALOG PICKER */
        tvBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                birthdate = String.valueOf(dayOfMonth) + " / " + String.valueOf(month + 1) + " / " + String.valueOf(year);
                tvBirthDate.setText("Datëlindja:  " + birthdate);
            }
        };

        radioCarDealership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioCarDealership.isChecked()) {
                    layoutCarDealer.setVisibility(View.VISIBLE);
                    tvAuto.setVisibility(View.VISIBLE);
                    type = "2";
                }
            }
        });

        radioPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioPrivate.isChecked()) {
                    layoutCarDealer.setVisibility(View.GONE);
                    tvAuto.setVisibility(View.GONE);
                    type = "1";
                }
            }
        });

        /*  */
        errorCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ju lutem zgjidhni qytetin", Toast.LENGTH_SHORT).show();
            }
        });

        errorMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ju lutem vendosni lokacionin ne harte", Toast.LENGTH_SHORT).show();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCreateName.setText("");
                etCreateSurname.setText("");
                etCreateTel.setText("");
                tvBirthDate.setText("Datëlindja");
                spinnerCity.setSelection(0);
                etCreateEmail.setText("");
                etCreatePass.setText("");
                etCreateConfirmPass.setText("");
                radioPrivate.callOnClick();
                etCreateCarDealership.setText("");
                etDealershipTel.setText("");
                etDealershipDesc.setText("");
                etCreateRoad.setText("");
                etCreateNr.setText("");
                etCreateCode.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gabime = 0;

                name = etCreateName.getText().toString().trim();
                surname = etCreateSurname.getText().toString().trim();
                email = etCreateEmail.getText().toString();
                pass = etCreatePass.getText().toString();
                pass2 = etCreateConfirmPass.getText().toString().trim();
                tel = etCreateTel.getText().toString().trim();
                dealershipName = etCreateCarDealership.getText().toString().trim();
                dealershipTel = etDealershipTel.getText().toString().trim();
                dealershipDesc = etDealershipDesc.getText().toString().trim();
                road = etCreateRoad.getText().toString().trim();
                nr = etCreateNr.getText().toString().trim();
                postalCode = etCreateCode.getText().toString().trim();
                city = spinnerCity.getSelectedItemPosition();

                if (name.isEmpty() || name.length() < 3) {
                    gabime++;
                    etCreateName.setError("Emri duhet të jetë më i gjatë se 2 karaktere");
                    etCreateName.requestFocus();
                }

                if (surname.isEmpty() || surname.length() < 3) {
                    gabime++;
                    etCreateSurname.setError("Mbiemri duhet të jetë më i gjatë se 2 karaktere");
                    etCreateSurname.requestFocus();
                }

                if (tvBirthDate.getText().toString().trim().equals("Datëlindja")) {
                    gabime++;
                    tvBirthDate.setError("Ju lutem caktoni datëlindjen");
                    tvBirthDate.requestFocus();
                }

                if (radioCarDealership.isChecked()) {
                    if (dealershipName.isEmpty() || dealershipName.length() < 3) {
                        gabime++;
                        etCreateCarDealership.setError("Emri i Autosallonit duhet të jetë më i gjatë se 2 karaktere");
                        etCreateName.requestFocus();
                    }
                }

                if (road.isEmpty() || road.length() < 3) {
                    gabime++;
                    etCreateRoad.setError("Shënoni emrin e rrugës");
                    etCreateRoad.requestFocus();
                }

                if (nr.isEmpty()) {
                    gabime++;
                    etCreateNr.setError("Ju lutem shënoni numrin e adresës");
                    etCreateNr.requestFocus();
                }

                if (postalCode.isEmpty() || postalCode.length() < 4) {
                    gabime++;
                    etCreateCode.setError("Ju lutem shkruani kodin postar tuaj");
                    etCreateCode.requestFocus();
                }

                if (city == 0) {
                    gabime++;
                    errorCity.setVisibility(View.VISIBLE);
                } else {
                    errorCity.setVisibility(View.GONE);
                }

                if (tel.isEmpty() || tel.length() != 9) {
                    gabime++;
                    etCreateTel.setError("Shkruani numër valid të telefonit");
                    etCreateTel.requestFocus();
                }

                if (email.isEmpty() || email.length() < 10) {
                    gabime++;
                    etCreateEmail.setError("Ju lutemi shkruani email valide");
                    etCreateEmail.requestFocus();
                }

                if (pass.length() < 6 )
                {
                    etCreatePass.setError("Fjalekalimi duhet të jetë më i gjatë se 6 karaktere");
                    etCreatePass.requestFocus();
                }
                if(!pass.equals(pass2)) {
                    gabime++;
                    etCreatePass.setError("Fjalekalimet nuk përputhen");
                    etCreatePass.requestFocus();
                    etCreateConfirmPass.setError("Fjalekalimet nuk përputhen");
                }

                if (addressLatLng == null) {
                    gabime++;
                    errorMap.setVisibility(View.VISIBLE);
                } else {
                    errorMap.setVisibility(View.GONE);
                }

                if (gabime == 0) {
                    progressDialog.show();
                    progressDialog.setMessage("Duke regjistruar perdoruesin...!");

                    RemoteDatabase.userCredentials(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String lat, lng;

                                lat = String.valueOf(addressLatLng.latitude);
                                lng = String.valueOf(addressLatLng.longitude);


                                String adresaID = RemoteDatabase.addressRegistration(road, nr, postalCode, String.valueOf(city), lat, lng);

                                if(adresaID!=null)
                                    RemoteDatabase.userDbRegistration(email, pass, name, surname, birthdate, tel, type,adresaID);


                                if (radioCarDealership.isChecked()) {
                                    RemoteDatabase.carDealerRegistration(dealershipName, dealershipDesc, dealershipTel);
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
                                            tvSuccess = (TextView) verify.findViewById(R.id.tvSukses);
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
                                                                        Intent objUserLogin = new Intent(getContext(), UserActivity.class);
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
                                            etCreateEmail.setError("Formati i këtij email-i është gabim!");
                                            etCreateEmail.requestFocus();
                                            break;

                                        case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                            Toast.makeText(getActivity(), "Emaili ekziston në databazë", Toast.LENGTH_LONG).show();
                                            etCreateEmail.setError("Emaili ekziston në databazë");
                                            etCreateEmail.requestFocus();
                                            break;

                                        case "ERROR_EMAIL_ALREADY_IN_USE":
                                            Toast.makeText(getActivity(), "Emaili ekziston në databazë", Toast.LENGTH_LONG).show();
                                            etCreateEmail.setError("Emaili ekziston në databazë");
                                            etCreateEmail.requestFocus();
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
