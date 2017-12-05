package josevi.android.com.sqlitedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by josevi on 26/11/2017.
 */

//SQLiteOpenHelper es una clase abstracta para la creación,
//conexión y actualización de la BBDD
public class BaseDatosHelper extends SQLiteOpenHelper{

    private EstructuraBBDD estructura;

    //Versión de la BBDD
    public static final int DATABASE_VERSION = 1;
    //Nombre de la BBDD
    public static final String DATABASE_NAME = "centro_educativo.db";

    public BaseDatosHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        //Creación de las BBDD
        db.execSQL(EstructuraBBDD.PROFESORES_CREATETABLE);
        db.execSQL(EstructuraBBDD.ALUMNOS_CREATETABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(EstructuraBBDD.PROFESORES_DELETETABLE);
        db.execSQL(EstructuraBBDD.ALUMNOS_DELETETABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
