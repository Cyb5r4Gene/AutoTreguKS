package Database;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Models.Adresa;
import Models.Autosallon;
import Models.ErrorReport;
import Models.SearchConditions;
import Models.Post;
import Models.User;
import Other.ImageConverter;

public class RemoteDatabase {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dRef = db.getReference();
    FirebaseUser useri = firebaseAuth.getCurrentUser();


    public static Boolean ret = false;


    //funksioni per ruajtjen e te dhenave te userit ne databaze
    public static Task<AuthResult> userCredentials(String email, String pass) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task<AuthResult> register = firebaseAuth.createUserWithEmailAndPassword(email, pass);

        return register;
    }

    //funksioni per ruajtjen e te dhenave te userit ne databaze
    public static boolean userDbRegistration(String email, String pass, String emri, String mbiemri, String datelindja, String tel, String tipi, String adresaID) {
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dRef = db.getReference();
            FirebaseUser useri = firebaseAuth.getCurrentUser();

            String data = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            User user = new User(emri, mbiemri, email, datelindja, tel, tipi, data, data, adresaID);
            dRef.child("users").child(useri.getUid()).setValue(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //funksioni per ruajtjen e te dhenave te autosallonit ne databaze
    public static boolean carDealerRegistration(String emri, String telefoni, String pershkrimi) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Autosallon autosalloniRi = new Autosallon(emri, pershkrimi, telefoni);
        if (dRef.child("carDealers").child(user.getUid()).setValue(autosalloniRi).isSuccessful()) {
            return true;
        } else
            return false;
    }

    //funksioni per ruajtjen e adresave ne databaze
    public static String addressRegistration(String rruga, String nr, String kodi, String qyteti, String lat, String lng) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();
        FirebaseUser useri = firebaseAuth.getCurrentUser();

        Adresa adresaRe = new Adresa(rruga, nr, kodi, qyteti, lat, lng);

        String aKey = dRef.child("addresses").push().getKey();

        dRef.child("addresses").child(aKey).setValue(adresaRe);
        return aKey;

    }


    //funksioni per marrjen e te dhenave te perdoruesit
    public static User getPerdoruesi(DataSnapshot dataSnapshot, String userID) {
        User perdoruesi = dataSnapshot.child("users").child(userID).getValue(User.class);
        return perdoruesi;
    }

    //funksioni per marrjen e te dhenave te autosallonit
    public static Autosallon getAutosalloni(DataSnapshot dataSnapshot, String autosallonID) {
        Autosallon autosalloni = dataSnapshot.child("carDealers").child(autosallonID).getValue(Autosallon.class);
        return autosalloni;
    }

    //funksioni per marrjen e te dhenave te adreses
    public static Adresa getAddress(DataSnapshot dataSnapshot, String adresaID) {
        Adresa adresa = dataSnapshot.child("addresses").child(adresaID).getValue(Adresa.class);
        return adresa;
    }

    //funksioni per update te userit
    public static Task<Void> profileUpdate(FirebaseUser user, String emriMbiemri, Uri fotoPath) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(emriMbiemri)
                .setPhotoUri(fotoPath)
                .build();

        return user.updateProfile(profileUpdates);
    }

    //funksioni per update te te dhenave te userit ne databaze
    public static Task<Void> userDbUpdate(FirebaseUser firebaseUser, User perdoruesi) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("users").child(firebaseUser.getUid()).setValue(perdoruesi);
    }

    //funksioni per update te adreses te userit ne databaze
    public static Task<Void> updateAddress(String addressID, Adresa adresa) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("addresses").child(addressID).setValue(adresa);
    }

    //funksioni per update te autosallonit ne databaze
    public static Task<Void> updateCarDealer(FirebaseUser firebaseUser, Autosallon autosallon) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("carDealers").child(firebaseUser.getUid()).setValue(autosallon);
    }

    //funksioni per nderrimin e emailit
    public static Task<Void> emailUpdate(FirebaseUser fUser, String email) {
        return fUser.updateEmail(email);
    }

    //funksioni per nderrimin e fjalekalimit
    public static Task<Void> updatePass(FirebaseUser fUser, String pass) {
        return fUser.updatePassword(pass);
    }

    //funksioni per login
    public static Task<AuthResult> loginUser(String email, String pass) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task<AuthResult> login = firebaseAuth.signInWithEmailAndPassword(email, pass);
        return login;
    }

    public static Task<Void> deleteCarDealer(FirebaseUser firebaseUser) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("carDealers").child(firebaseUser.getUid()).removeValue();
    }

    public static String addPost(Post post) {
        try {
            //db
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dRef = db.getReference();
            FirebaseUser useri = firebaseAuth.getCurrentUser();

            //id e postimit
            String id = dRef.child("posts").push().getKey();

            //ruajtja ne db
            dRef.child("posts").child(id).setValue(post);
            return id;
        } catch (Exception e) {
            return null;
        }
    }

    public static UploadTask postImages(String id, String imgUrl, int i) {
        //ruajtja e fotove
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("postImages/" + id + "/");
        Bitmap bm = ImageConverter.imgToBitmap(imgUrl);
        byte[] bt = ImageConverter.bitmapToBytes(bm, 40);
        UploadTask uploadTask = storageReference.child("Foto" + (i + 1)).putBytes(bt);
        return uploadTask;
    }

    public static List<List> postSearch(@NotNull DataSnapshot dataSnapshot, SearchConditions search) {

        List<Post> result = new ArrayList<>();
        List<String> resultID = new ArrayList<>();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Post post = ds.getValue(Post.class);
            String id = ds.getKey();
            int searchNr = 0;
            int matchedNr = 0;

            if(search.getOwnerID()!=null){
                searchNr++;
                if(search.getOwnerID().equals(post.getPronariID())){
                    matchedNr++;
                }
            }

            if(search.getPostIdList()!=null){
                searchNr++;
                for (int i=0;i<search.getPostIdList().size();i++){
                    if(id.equals(search.getPostIdList().get(i))){
                        matchedNr++;
                        i=search.getPostIdList().size();
                    }
                }
            }

            if (search.getGjendja() == 1) {
                searchNr++;
                if (Long.parseLong(post.getKm()) < 10000) {
                    matchedNr++;
                }
            }

            if (search.getGjendja() == 2) {
                searchNr++;
                if (Long.parseLong(post.getKm()) > 10000) {
                    matchedNr++;
                }
            }

            if (search.getMarkaID() != 0) {
                searchNr++;
                if (post.getMarkaID() == search.getMarkaID())
                    matchedNr++;

            }

            if (search.getModeliID() != 0) {
                searchNr++;
                if (search.getModeliID() == post.getModeliID())
                    matchedNr++;
            }

            if (search.getCmimi() != null) {
                searchNr++;
                if (post.getCmimi() >= search.getCmimi().getFrom() && post.getCmimi() <= search.getCmimi().getTo())
                    matchedNr++;
            }

            if (search.getLicencePlates().isUnspecified() || search.getLicencePlates().isKosovo()
                    || search.getLicencePlates().isAlbania() || search.getLicencePlates().isForeign()) {
                searchNr++;
                if ((search.getLicencePlates().isUnspecified() && post.getTarga() != null && post.getTarga().equals("Pa përcaktuar"))
                        || (search.getLicencePlates().isKosovo() && post.getTarga() != null && post.getTarga().equals("Vendore"))
                        || (search.getLicencePlates().isAlbania() && post.getTarga() != null && post.getTarga().equals("Të Shqipërisë"))
                        || (search.getLicencePlates().isForeign() && post.getTarga() != null && post.getTarga().equals("Të huaja"))) {
                    matchedNr++;
                }
            }


            if (search.getRegjistrimiPare() != null) {
                searchNr++;
                if (Long.parseLong(post.getRegjistrimiPare().split("/")[1]) >= search.getRegjistrimiPare().getFrom() && Long.parseLong(post.getRegjistrimiPare().split("/")[1]) <= search.getRegjistrimiPare().getTo())
                    matchedNr++;
            }

            if (search.getKm() != null) {
                searchNr++;
                if (search.getKm().getFrom() <= Long.parseLong(post.getKm()) && search.getKm().getTo() >= Long.parseLong(post.getKm())) {
                    matchedNr++;
                }
            }

            if(search.getFuqia() !=null){
                searchNr++;
                if (search.getFuqia().getFrom() <= Long.parseLong(post.getFuqia()) && search.getFuqia().getTo() >= Long.parseLong(post.getFuqia())) {
                    matchedNr++;
                }
            }

            if (search.getFuel().isDiesel() || search.getFuel().isPetrol() || search.getFuel().isElectric()
                    || search.getFuel().isGas() || search.getFuel().isHybrid()) {
                searchNr++;
                if ((search.getFuel().isDiesel() && post.getKarburantiID() == 1)
                        || (search.getFuel().isPetrol() && post.getKarburantiID() == 2)
                        || (search.getFuel().isGas() && post.getKarburantiID() == 3)
                        || (search.getFuel().isElectric() && post.getKarburantiID() == 4)
                        || (search.getFuel().isHybrid()) && post.getKarburantiID() == 5) {
                    matchedNr++;
                }
            }

            if (search.getQyteti() != 0) {
                searchNr++;
                User user = dataSnapshot.child("users").child(post.getPronariID()).getValue(User.class);
                Adresa address = dataSnapshot.child("addresses").child(user.getAdresaID()).getValue(Adresa.class);
                if (String.valueOf(search.getQyteti()).equals(address.getQyteti()))
                    matchedNr++;
            }

            if(search.getVehicle().isSedan() || search.getVehicle().isCaravan() || search.getVehicle().isCabriolet()
                    || search.getVehicle().isOffroad() || search.getVehicle().isSmall()){
                searchNr++;
                if((search.getVehicle().isSedan() && post.getKarroceriaID()==1)
                        || (search.getVehicle().isCaravan() && post.getKarroceriaID()==2)
                        || (search.getVehicle().isCabriolet() && post.getKarroceriaID()==3)
                        || (search.getVehicle().isOffroad() && post.getKarroceriaID()==4)
                        || (search.getVehicle().isSmall() && post.getKarroceriaID()==5)){
                    matchedNr++;
                }
            }

            if(search.getSeatsNr()!=0 && post.getNrUleseve()!=null && !post.getNrUleseve().equals("0")){
                searchNr++;
                int seatsNr=Integer.parseInt(post.getNrUleseve().substring(0,1))+1;
                if(search.getSeatsNr()<7 && search.getSeatsNr()>seatsNr){
                    matchedNr++;
                } else if(search.getSeatsNr()==7 && seatsNr>6){
                    matchedNr++;
                }
            }

            if(search.getDoorsNr()!=0){
                searchNr++;
                if(post.getNrDyer()==search.getDoorsNr()){
                    matchedNr++;
                }
            }

            if(search.getTransmission().isUnspecified() || search.getTransmission().isManual() ||search.getTransmission().isHalfAutomatic() || search.getTransmission().isAutomatic()){
                searchNr++;
                if(search.getTransmission().isUnspecified()
                        || (search.getTransmission().isManual() && post.getTransmisioni()==1)
                        || (search.getTransmission().isHalfAutomatic() && post.getTransmisioni()==2)
                        || (search.getTransmission().isAutomatic() && post.getTransmisioni()==3)){
                    matchedNr++;
                }
            }

            if(search.getClimatissation()!=0){
                searchNr++;
                if(search.getClimatissation()==post.getFeatures().getKondicioneri()){
                    matchedNr++;
                }
            }

            if(search.getInteriorSearch().isBluetooth() || search.getInteriorSearch().isOnBoardComputer()
                    || search.getInteriorSearch().isCdPlayer() || search.getInteriorSearch().isElectricWindow()
                    || search.getInteriorSearch().isHeatedSeats() || search.getInteriorSearch().isSportSeats()
                    || search.getInteriorSearch().isMp3() || search.getInteriorSearch().isAux()
                    || search.getInteriorSearch().isSteeringWheelButtons() || search.getInteriorSearch().isNav()
                    || search.getInteriorSearch().isShiber() || search.getInteriorSearch().isPanoramic()
                    || search.getInteriorSearch().isRoofRack() || search.getInteriorSearch().isCentralCloser()){
                searchNr++;
                if((search.getInteriorSearch().isBluetooth() == post.getFeatures().isBluetooth())
                        && (search.getInteriorSearch().isOnBoardComputer() == post.getFeatures().isOnBoardKompjuter())
                        && (search.getInteriorSearch().isCdPlayer() == post.getFeatures().isCDPlayer())
                        && (search.getInteriorSearch().isElectricWindow() == post.getFeatures().isXhamaElektrik())
                        && (search.getInteriorSearch().isHeatedSeats() == post.getFeatures().isUleseMeNxemje())
                        && (search.getInteriorSearch().isSportSeats() == post.getFeatures().isUleseSportive())
                        && (search.getInteriorSearch().isMp3() == post.getFeatures().isMp3())
                        && (search.getInteriorSearch().isAux() == post.getFeatures().isAux())
                        && (search.getInteriorSearch().isSteeringWheelButtons() == post.getFeatures().isPullaNeTimon())
                        && (search.getInteriorSearch().isNav() == post.getFeatures().isNavigacion())
                        && (search.getInteriorSearch().isShiber() == post.getFeatures().isShiber())
                        && (search.getInteriorSearch().isPanoramic() == post.getFeatures().isPanorame())
                        && (search.getInteriorSearch().isRoofRack() == post.getFeatures().isBagazhNeCati())
                        && (search.getInteriorSearch().isCentralCloser() == post.getFeatures().isMbylljeQendrore())){
                    matchedNr++;
                }
            }

            if(search.isSportPacket()){
                searchNr++;
                if(post.getFeatures().isSportPakete()){
                    matchedNr++;
                }
            }

            if(search.isSportAmortization()){
                searchNr++;
                if(post.getFeatures().isAmortizimSportiv()){
                    matchedNr++;
                }
            }

            if(search.getSecurity().isAbs() || search.getSecurity().isFourX4() || search.getSecurity().isEsp()
                    || search.getSecurity().isAdaptingLights() || search.getSecurity().isLightsSensor()
                    || search.getSecurity().isXenonHeadlights() || search.getSecurity().isBiXenonHeadlights()
                    || search.getSecurity().isRainSensor() || search.getSecurity().isStartStopSensor()){
                searchNr++;
                if((search.getSecurity().isAbs()==post.getFeatures().isAbs())
                        && (search.getSecurity().isFourX4()==post.getFeatures().isKaterX4())
                        && (search.getSecurity().isEsp()==post.getFeatures().isEsp())
                        && (search.getSecurity().isAdaptingLights() == post.getFeatures().isDritaAdaptuese())
                        && (search.getSecurity().isLightsSensor() ==  post.getFeatures().isSensorDritash())
                        && (search.getSecurity().isXenonHeadlights() == post.getFeatures().isDritaXenon())
                        && (search.getSecurity().isBiXenonHeadlights() == post.getFeatures().isDritaBiXenon())
                        && (search.getSecurity().isRainSensor() == post.getFeatures().isSensorShiu())
                        && (search.getSecurity().isStartStopSensor() == post.getFeatures().isStartStop())){
                    matchedNr++;
                }
            }

            if(search.getCarTypes()!=null && search.getCarTypes().size()!=0){
                searchNr++;
                for(int i=0;i<search.getCarTypes().size();i++){
                    if(search.getCarTypes().get(i).getMarke() == post.getMarkaID() && search.getCarTypes().get(i).getModel()==post.getModeliID()){
                        matchedNr++;
                    }
                }

            }

            if(search.getAirbag().isAirbagShoferi() || search.getAirbag().isAirbagAnesor()
                    || search.getAirbag().isAirbagIPrapme() ||search.getAirbag().isAirbagTjere()){
                searchNr++;
                if((search.getAirbag().isAirbagShoferi() && post.getFeatures().getAirbag().isAirbagShoferi())
                        && (search.getAirbag().isAirbagAnesor() && post.getFeatures().getAirbag().isAirbagAnesor())
                        && (search.getAirbag().isAirbagIPrapme() && post.getFeatures().getAirbag().isAirbagIPrapme())
                        && (search.getAirbag().isAirbagTjere() && post.getFeatures().getAirbag().isAirbagTjere())){
                    matchedNr++;
                }
            }

            if(search.getSellerType()!=0){
                searchNr++;
                User user = dataSnapshot.child("users").child(post.getPronariID()).getValue(User.class);
                if(String.valueOf(search.getSellerType()).equals(user.getTipiLlogarise())){
                    matchedNr++;
                }
            }

            if(search.getOwners()!=0 && post.getNrPronareve()!=null){
                searchNr++;
                if(Integer.valueOf(post.getNrPronareve())<=search.getOwners()){
                    matchedNr++;
                }
            }

            if(search.getDamaged()==1){
                searchNr++;
                if(post.getDefekt()!=1){
                    matchedNr++;
                }
            }


            if(post.getTtl()>System.currentTimeMillis()){
                if (searchNr == matchedNr) {
                    result.add(post);
                    resultID.add(id);
                }
            }


        }

        List<List> finalResult = new ArrayList<>();
        finalResult.add(result);
        finalResult.add(resultID);


        return finalResult;
    }

    public static UploadTask profileImageUpdate(String userID, String type, String imgUrl){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("userImages/" +userID+ "/");
        Bitmap bm = ImageConverter.imgToBitmap(imgUrl);
        byte[] bt = ImageConverter.bitmapToBytes(bm, 100);
        UploadTask uploadTask = storageReference.child(type).putBytes(bt);
        return uploadTask;
    }

    public static Task<Void> errorReport(ErrorReport error){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        return databaseReference.child("reports").push().setValue(error);
    }
}
