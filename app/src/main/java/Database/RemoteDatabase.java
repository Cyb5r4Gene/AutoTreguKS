package Database;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import models.Adresa;
import models.Autosallon;
import models.Post;
import models.User;

public class RemoteDatabase {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dRef = db.getReference();
    FirebaseUser useri = firebaseAuth.getCurrentUser();


    public static Boolean ret = false;


    //funksioni per ruajtjen e te dhenave te userit ne databaze
    public static Task<AuthResult> regjistrimiKredencialeve(String email, String pass) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task<AuthResult> register = firebaseAuth.createUserWithEmailAndPassword(email, pass);

        return register;
    }

    //funksioni per ruajtjen e te dhenave te userit ne databaze
    public static boolean regjistroPerdoruesin(String email, String pass, String emri, String mbiemri, String datelindja, String tel, String tipi) {
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dRef = db.getReference();
            FirebaseUser useri = firebaseAuth.getCurrentUser();

            String data = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            User user = new User(emri, mbiemri, email, datelindja, tel, tipi, data, data);
            dRef.child("users").child(useri.getUid()).setValue(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //funksioni per ruajtjen e te dhenave te autosallonit ne databaze
    public static boolean regjistroAutosallonin(String emri, String pershkrimi, String telefoni) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();
        FirebaseUser useri = firebaseAuth.getCurrentUser();

        Autosallon autosalloniRi = new Autosallon(emri,pershkrimi, telefoni);
        if(dRef.child("autosallonet").child(useri.getUid()).setValue(autosalloniRi).isSuccessful()) {
            return true;
        } else
            return false;
    }

    //funksioni per ruajtjen e adresave ne databaze
    public static boolean regjistroAdresen(String rruga, String nr, String kodi, String qyteti, String lat, String lng) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();
        FirebaseUser useri = firebaseAuth.getCurrentUser();

        Adresa adresaRe = new Adresa(rruga, nr, kodi, qyteti, lat, lng);
        if(dRef.child("adresat").child(useri.getUid()).setValue(adresaRe).isSuccessful()) {
            return true;
        } else
            return false;
    }


    //funksioni per marrjen e te dhenave te perdoruesit
    public static User getPerdoruesi(DataSnapshot dataSnapshot, String userID)
    {
        User perdoruesi = dataSnapshot.child("users").child(userID).getValue(User.class);
        return perdoruesi;
    }

    //funksioni per marrjen e te dhenave te autosallonit
    public static Autosallon getAutosalloni(DataSnapshot dataSnapshot, String autosallonID)
    {
        Autosallon autosalloni = dataSnapshot.child("autosallonet").child(autosallonID).getValue(Autosallon.class);
        return autosalloni;
    }

    //funksioni per marrjen e te dhenave te adreses
    public static Adresa getAdresa(DataSnapshot dataSnapshot, String adresaID)
    {
        Adresa adresa = dataSnapshot.child("adresat").child(adresaID).getValue(Adresa.class);
        return adresa;
    }

    //funksioni per update te userit
    public static Task<Void> profileUpdate(FirebaseUser user, String emriMbiemri, Uri fotoPath)
    {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(emriMbiemri)
                .setPhotoUri(fotoPath)
                .build();

        return user.updateProfile(profileUpdates);
    }

    //funksioni per update te te dhenave te userit ne databaze
    public static Task<Void> userDbUpdate(FirebaseUser firebaseUser, User perdoruesi)
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("users").child(firebaseUser.getUid()).setValue(perdoruesi);
    }

    //funksioni per update te adreses te userit ne databaze
    public static Task<Void> adresaUpdate(FirebaseUser firebaseUser, Adresa adresa)
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("adresat").child(firebaseUser.getUid()).setValue(adresa);
    }

    //funksioni per update te autosallonit ne databaze
    public static Task<Void> autosalloniUpdate(FirebaseUser firebaseUser, Autosallon autosallon)
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("autosallonet").child(firebaseUser.getUid()).setValue(autosallon);
    }

    //funksioni per nderrimin e emailit
    public static Task<Void> emailUpdate(FirebaseUser fUser, String email)
    {
        return fUser.updateEmail(email);
    }

    //funksioni per nderrimin e fjalekalimit
    public static Task<Void> updatePass(FirebaseUser fUser, String pass)
    {
        return fUser.updatePassword(pass);
    }

    //funksioni per login
    public static Task<AuthResult> loginUser(String email, String pass)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task<AuthResult> login = firebaseAuth.signInWithEmailAndPassword(email, pass);
        return login;
    }

    public  static Task<Void> deleteAutosallon(FirebaseUser firebaseUser)
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("autosallonet").child(firebaseUser.getUid()).removeValue();
    }

    public static boolean addPost(Post post, Uri[] uri) {
        try {
            //db
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dRef = db.getReference();
            FirebaseUser useri = firebaseAuth.getCurrentUser();
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();

            //id e postimit
            String id = dRef.child("posts").push().getKey();

            //ruajtja ne db
            dRef.child("posts").child(id).setValue(post);

            //ruajtja e fotove


            return true;
        } catch (Exception e) {
            return false;
        }
    }





}
