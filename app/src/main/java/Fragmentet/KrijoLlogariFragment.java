package Fragmentet;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import rks.youngdevelopers.autotreguks.KryefaqjaActivity;
import rks.youngdevelopers.autotreguks.R;

public class KrijoLlogariFragment extends Fragment {

    Spinner spinnerKrijoQyteti;

    RadioButton radioPrivate, radioAutoSallon;
    LinearLayout layoutAutoSallon, layoutTipi;

    Button btnRuaj, btnAnulo;

    TextView tvDatelindja;

    EditText etKrijoEmri, etKrijoMbiemri, etKrijoEmail, etKrijoPass, etKrijoKonfirmoPass, etKrijoSalloni;

    FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_krijo_llogari, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerKrijoQyteti = (Spinner)view.findViewById(R.id.spinnerKrijoQyteti);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity().getApplicationContext(), R.array.qytetet,
                        R.layout.spinner_style);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerKrijoQyteti.setAdapter(staticAdapter);

        etKrijoEmri = (EditText)view.findViewById(R.id.etKrijoEmri);
        etKrijoMbiemri = (EditText)view.findViewById(R.id.etKrijoMbiemri);
        etKrijoEmail = (EditText)view.findViewById(R.id.etKrijoEmail);
        etKrijoPass = (EditText)view.findViewById(R.id.etKrijoPassword);
        etKrijoKonfirmoPass = (EditText)view.findViewById(R.id.etKrijoKonfirmPassword);
        etKrijoSalloni = (EditText)view.findViewById(R.id.etKrijoAutosalloni);

        radioPrivate = (RadioButton)view.findViewById(R.id.radioPrivate);
        radioAutoSallon = (RadioButton)view.findViewById(R.id.radioAutoSallon);
        layoutAutoSallon = (LinearLayout)view.findViewById(R.id.layoutAutoSallon);
        layoutTipi = (LinearLayout)view.findViewById(R.id.layoutTipi);
        btnAnulo = (Button)view.findViewById(R.id.btnAnulo);
        btnRuaj = (Button)view.findViewById(R.id.btnKrijo);
        tvDatelindja = (TextView)view.findViewById(R.id.tvKrijoDatelindja);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());

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
                        viti,muaji,dita);
                dialogu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogu.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String data = String.valueOf(dayOfMonth)+" / "+String.valueOf(month+1)+" / "+String.valueOf(year);
                tvDatelindja.setText("Datëlindja:  "+data);

            }
        };
        //final LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) layoutTipi.getLayoutParams();

        radioAutoSallon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioAutoSallon.isChecked())
                {
                    layoutAutoSallon.setVisibility(View.VISIBLE);
                }
            }
        });

        radioPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioPrivate.isChecked())
                {
                    layoutAutoSallon.setVisibility(View.GONE);
                }
            }
        });

        btnAnulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etKrijoEmri.setText("");
                etKrijoMbiemri.setText("");
                //rikthehet data
                spinnerKrijoQyteti.setSelection(0);
                etKrijoEmail.setText("");
                etKrijoPass.setText("");
                etKrijoKonfirmoPass.setText("");
                radioPrivate.callOnClick();
                etKrijoSalloni.setText("");
                 //resetohet lokacioni ne harte

            }
        });

        etKrijoKonfirmoPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    if(!etKrijoPass.getText().toString().equals(etKrijoKonfirmoPass.getText().toString()))
                    {
                        Toast.makeText(getContext(), "Fjalëkalimet nuk përputhen",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btnRuaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etKrijoEmail.getText().toString();
                String pass = etKrijoPass.getText().toString();
                regjistrimiKredencialeve(email,pass);
            }
        });

    }

    //funksioni per regjistrimin e kredencialeve te autentikimit te perdoruesit
    private void regjistrimiKredencialeve (String email, String pass)
    {
        progressDialog.show();
        progressDialog.setMessage("Duke regjistruar perdoruesin...!");
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.hide();
                    Toast.makeText(getActivity(), "Regjistrimi u krye me sukses!", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.hide();
                    Toast.makeText(getActivity(), "Regjistrimi deshtoi!\nEmaili ekziston në databazë!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
