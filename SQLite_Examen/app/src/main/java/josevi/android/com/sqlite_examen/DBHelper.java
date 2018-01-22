package josevi.android.com.sqlite_examen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by josevi on 22/01/2018.
 */

public class DBHelper extends SQLiteOpenHelper{

    private DBEstructura estructura;

    //Atributos de la clase:
    //Versión de la BBDD
    public static final int VERSION_DATABASE = 1;

    //Nombre de la BBDD
    public static final String NOMBRE_DATABASE = "mydatabase.sqlite";



    //Constructor de la clase:
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Métodos heredados de la superclase SQLiteOpenHelper:
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(estructura.PROFESORES_CREATETABLE);
        db.execSQL(estructura.ALUMNOS_CREATETABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //Se eliminan versiones antiguas de las tablas
        db.execSQL(estructura.PROFESORES_DELETETABLE);
        db.execSQL(estructura.ALUMNOS_DELETETABLE);
        //Se crean las versiones nuevas de las tablas
        onCreate(db);

    }
}
