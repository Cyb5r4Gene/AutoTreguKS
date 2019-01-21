package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabase extends SQLiteOpenHelper {

    public LocalDatabase(Context context) {
        super(context, "dbPost ", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table tblMarka (mID Integer Primary Key AUTOINCREMENT, mEmri nVarchar(100))");
        db.execSQL("Create table tblModeli (modID Integer Primary Key AUTOINCREMENT, mID Integer, modEmri nVarchar(100), Foreign Key (mID) References tblMarka)");
        db.execSQL("Create table tblKarburanti (kID Integer Primary Key AUTOINCREMENT, kEmri nVarchar(100))");
        db.execSQL("Create table tblVarianti (vID Integer Primary Key AUTOINCREMENT, modID Integer, kID Integer, vEmri nVarchar(100), Foreign Key (modID) References tblModeli, Foreign Key (kID) References tblKarburanti)");
        db.execSQL("Create table tblKarroceria (krrID Integer Primary Key AUTOINCREMENT, krrEmri nVarchar(100))");

        db.execSQL("Insert into tblMarka(mEmri) values ('Alfa Romeo'), ('Aston Martin'), ('Audi'), " +
                "('BMW'), ('Mercedes'),('Volkswagen')");
        db.execSQL("Insert into tblModeli(mID, modEmri) values ('6','Golf I'), ('6','Golf II'), ('6','Golf III'), " +
                "('6','Golf IV'), ('6','Golf V'),('6','Golf VI')");
        db.execSQL("Insert into tblKarburanti(kEmri) values ('Dizel'), ('Benzin'), ('Benzin/Plin'), " +
                "('Elektrik'), ('Hibrid')");
        db.execSQL("Insert into tblVarianti(modID, kID, vEmri) values ('4','1','1.9 SDI'), ('4','1','1.9 TDI'), ('4','1','1.9 TDI 4MOTION'), " +
                "('4','1','1.9 TDI')");
        db.execSQL("Insert into tblKarroceria(krrEmri) values ('Sedan'), ('Karavan'), ('Kabriolet')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
