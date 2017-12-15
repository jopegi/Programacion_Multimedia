package josevi.android.com.sqlitecentroeducativo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by josevi on 14/12/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private EstructuraBBDD estructura;

    //Versión de la BBDD
    public static final int DATABASE_VERSION = 1;

    //Nombre de la BBDD
    public static final String DATABASE_NAME = "centro_educativo.sqlite";


    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    //Método que será ejecutado automáticamente por nuestra clase DataBaseHelper
    //cuando sea necesaria la creación de la base de datos.
    //>>>Si la base de datos ya existe y su versión actual coincide con la solicitada
    //   simplemente se realizará la conexión con ella.
    //>>>Si la base de datos existe pero su versión actual es anterior a la solicitada,
    //   se llamará automáticamente al método onUpgrade() para convertir la base de datos
    //   a la nueva versión y se conectará con la base de datos convertida.
    //>>>Si la base de datos no existe, se llamará automáticamente al método onCreate()
    //   para crearla y se conectará con la base de datos creada.
    public void onCreate(SQLiteDatabase db) {

        //Creación de las BBDD
        db.execSQL(EstructuraBBDD.PROFESORES_CREATETABLE);
        db.execSQL(EstructuraBBDD.ALUMNOS_CREATETABLE);

    }

    @Override
    //Método que se lanzará automáticamente cuando sea necesaria una actualización
    //de la estructura de la base de datos o una conversión de los datos.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(EstructuraBBDD.PROFESORES_DELETETABLE);
        db.execSQL(EstructuraBBDD.ALUMNOS_DELETETABLE);
        onCreate(db);

    }
}
