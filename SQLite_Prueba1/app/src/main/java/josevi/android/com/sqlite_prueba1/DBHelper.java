package josevi.android.com.sqlite_prueba1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by josevi on 16/01/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private DBEstructura estructura;

    //Versión de la BBDD
    public static final int VERSION_DATABASE = 1;

    //Nombre de la BBDD
    public static final String NOMBRE_DATABASE = "centro_estudios.sqlite";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(estructura.PROFESORES_CREATETABLE);
        db.execSQL(estructura.ALUMNOS_CREATETABLE);
        db.execSQL(estructura.ASIGNATURAS_CREATETABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //Se eliminan versiones antiguas de las tablas
        db.execSQL(estructura.PROFESORES_DELETETABLE);
        db.execSQL(estructura.ALUMNOS_DELETETABLE);
        db.execSQL(estructura.ASIGNATURAS_DELETETABLE);
        //Se crean las versiones nuevas de las tablas
        onCreate(db);

    }
}
