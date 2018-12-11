package Fragmentet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rks.youngdevelopers.autotreguks.HyrjaActivity;
import rks.youngdevelopers.autotreguks.LoginActivity;
import rks.youngdevelopers.autotreguks.R;
import rks.youngdevelopers.autotreguks.UserActivity;

public class LoginFragment extends Fragment {

    Button btnShiko, btnLogin;

    EditText etLoginEmail, etLoginPassword;

    CheckBox checkRuaj;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        btnShiko = (Button) view.findViewById(R.id.btnShiko);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);

        etLoginEmail = (EditText) view.findViewById(R.id.etLoginEmail);
        etLoginPassword = (EditText) view.findViewById(R.id.etLoginPassword);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String userEmail = sharedPref.getString("email", "");
        String userPass = sharedPref.getString("pass", "");
        etLoginEmail.setText(userEmail);
        etLoginPassword.setText(userPass);
        checkRuaj = (CheckBox) view.findViewById(R.id.checkRuaj);

        progressDialog = new ProgressDialog(getContext());

        btnShiko.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        btnShiko.setBackgroundResource(R.mipmap.shiko);
                        break;
                    case MotionEvent.ACTION_UP:
                        etLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        btnShiko.setBackgroundResource(R.mipmap.mosshiko);
                        etLoginPassword.setSelection(etLoginPassword.length());
                        break;

                }
                return true;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginEmail.getText().toString().trim();
                String pass = etLoginPassword.getText().toString().trim();
                userLogin(email, pass);
            }
        });

    }

    private void userLogin(final String email, final String pass) {
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog.setMessage("Duke u kyçur...!");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //ketu hapet user activity
                    if (checkRuaj.isChecked()) {
                        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        editor = sharedPref.edit();
                        editor.putString("email", email);
                        editor.putString("pass", pass);
                        editor.commit();
                    } else {
                        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        editor = sharedPref.edit();
                        editor.putString("email", " ");
                        editor.putString("pass", " ");
                        etLoginEmail.setText("");
                        etLoginPassword.setText("");
                    }
                    startActivity(new Intent(getContext(), UserActivity.class));
                    progressDialog.hide();
                    Toast.makeText(getContext(), "Mirësevini!", Toast.LENGTH_LONG).show();
                    editor.putString("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());

                } else {
                    progressDialog.hide();
                    Snackbar.make(getView(), "Kredencialet e gabuara!!!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
