package Fragmentet;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import Database.RemoteDatabase;
import rks.youngdevelopers.autotreguks.R;
import rks.youngdevelopers.autotreguks.UserActivity;

public class LoginFragment extends Fragment {

    // P A M J E T
    Button btnShiko, btnLogin;
    EditText etLoginEmail, etLoginPassword;
    CheckBox checkRuaj;
    TextView tvForgot;

    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    // INSTANCAT E MEMORJES SE PERBASHKET
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
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
        tvForgot = (TextView) view.findViewById(R.id.tvForgot);

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

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               resetPass();
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

    private void resetPass()
    {
        final Dialog fjalekalimiReset = new Dialog(getContext());
        fjalekalimiReset.setContentView(R.layout.fjalekalimi);
        fjalekalimiReset.setTitle("Dërgo kërkesën për rivendosje të fjalëkalimit");
        final EditText emailEdit = (EditText) fjalekalimiReset.findViewById(R.id.forgotEmail);
        emailEdit.setText(etLoginEmail.getText());
        Button btnDergo = (Button) fjalekalimiReset.findViewById(R.id.btnReseto);
        Button btnAnuloResetimin = (Button) fjalekalimiReset.findViewById(R.id.btnAnuloResetimin);
        btnDergo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailEdit.getText().toString().trim().isEmpty()) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(emailEdit.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Snackbar.make(getView(), "Kërkesa per resetim të fjalekalimit u dërgua në E-mail!", Snackbar.LENGTH_LONG).show();
                                fjalekalimiReset.dismiss();
                            } else {
                                Snackbar.make(getView(), "Rivendosja e fjalëklimit nuk u mundesua! Ju lutem, provoni me vone!", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Snackbar.make(v, "Ju lutem shkruani e-mailin tuaj!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        btnAnuloResetimin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fjalekalimiReset.dismiss();
            }
        });
        fjalekalimiReset.show();
    }

    private void userLogin(final String email, final String pass) {
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog.setMessage("Duke u kyçur...!");
        progressDialog.show();
        try {

        RemoteDatabase.loginUser(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser.isEmailVerified()) {

                        //ketu hapet user activity
                        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        editor = sharedPref.edit();
                        if (checkRuaj.isChecked()) {
                            editor.putString("email", email);
                            editor.putString("pass", pass);
                            editor.putString("userID", firebaseUser.getUid());
                            editor.commit();
                        } else {
                            editor.remove("email");
                            editor.remove("pass");
                            editor.remove("userID");
                            etLoginEmail.setText("");
                            etLoginPassword.setText("");
                        }
                        progressDialog.hide();
                        startActivity(new Intent(getContext(), UserActivity.class));
                        Toast.makeText(getContext(), "Mirësevini!", Toast.LENGTH_LONG).show();
                    } else {
                        progressDialog.hide();
                        final Dialog verifyDialog = new Dialog(getActivity());
                        verifyDialog.setContentView(R.layout.dialogu_per_verifikim);
                        verifyDialog.setTitle("Emaili i pa-verifikuar!");
                        Button btnVerifiko = (Button) verifyDialog.findViewById(R.id.btnVerifiko);
                        btnVerifiko.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Emaili për verifikim u dërgua, ju lutem shiko", Toast.LENGTH_LONG).show();
                                            verifyDialog.dismiss();

                                        } else {
                                            Toast.makeText(getContext(), "Dërgimi i emailit për verifikim dështoi!", Toast.LENGTH_LONG).show();
                                            verifyDialog.dismiss();
                                        }
                                    }
                                });
                            }
                        });
                        verifyDialog.show();
                        firebaseAuth.signOut();
                    }
                } else {
                    progressDialog.hide();
                    String errori = ((FirebaseAuthException) task.getException()).getErrorCode();
                    switch (errori) {
                        case "ERROR_INVALID_CREDENTIAL":
                            Snackbar.make(getView(), "Kredenciale të gabuara", Snackbar.LENGTH_LONG).show();
                            break;
                        case "ERROR_INVALID_EMAIL":
                            Snackbar.make(getView(), "Email adresa gabim", Snackbar.LENGTH_LONG).show();
                            break;
                        case "ERROR_WRONG_PASSWORD":
                            Snackbar.make(getView(), "Fjalëkalimi i gabuar", Snackbar.LENGTH_LONG).show();
                            break;
                        case "ERROR_USER_DISABLED":
                            Snackbar.make(getView(), "Llogaria juaj është deaktivizuar nga administratori", Snackbar.LENGTH_LONG).show();
                            break;
                        case "ERROR_USER_NOT_FOUND":
                            Snackbar.make(getView(), "Nuk ka përdorues me këtë llogari", Snackbar.LENGTH_LONG).show();
                            break;
                    }
                }
            }
        });

        } catch (Exception e) {
            Log.e("signInFailed",e.getMessage());
        }
    }
}