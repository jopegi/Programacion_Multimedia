package josevi.android.com.sqlite_proyecto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by josevi on 08/12/2017.
 */

//Clase encargada de la creación y actualización de la Base de Datos
public class DBHelper extends SQLiteOpenHelper {


    //Nombre de nuestra Base de Datos
    //Nota: la extensión de la BBDD puede ser .db, .sqlite, etc.
    private static final String DB_NAME = "centro_educativo.sqlite";

    //Version de la Base de Datos
    //Indicará cualquier cambio en el esquema de la Base de Datos
    //Se utiliza en el método onUpgrade() para indicar que se ha modificado la estructura de la BBDD
    private static final int DB_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    //Método que se encargará de crear la BBDD y las tablas
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DataBaseManager.PROFESORES_CREATETABLE);
        db.execSQL(DataBaseManager.ALUMNOS_CREATETABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
