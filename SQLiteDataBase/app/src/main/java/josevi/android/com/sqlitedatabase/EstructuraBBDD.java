package josevi.android.com.sqlitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EstructuraBBDD extends AppCompatActivity {

    private BaseDatosHelper helper;

    public EstructuraBBDD() {

    }

    //Nombre de la tabla para los profesores
    public static final String DATABASE_TABLE_PROFESORES = "profesores";
    //Nombre de la tabla para los alumnos
    public static final String DATABASE_TABLE_ALUMNOS = "alumnos";

    //Campos tabla profesor
    public static final String ID_PROFESOR = "_id";
    public static final String NOMBRE_PROFESOR = "nombre";
    public static final String  EDAD_PROFESOR = "edad";
    public static final String  CURSO_PROFESOR = "curso";
    public static final String  CICLO_PROFESOR = "ciclo";
    public static final String  DESPACHO_PROFESOR = "despacho";

    //Campos tabla alumno
    public static final String ID_ALUMNO = "_id";
    public static final String NOMBRE_ALUMNO = "nombre";
    public static final String  EDAD_ALUMNO = "edad";
    public static final String  CICLO_ALUMNO = "ciclo";
    public static final String  CURSO_ALUMNO = "curso";
    public static final String  NOTA_MEDIA_ALUMNO = "nota_media";

    public static final String PROFESORES_CREATETABLE = "CREATE TABLE "+DATABASE_TABLE_PROFESORES+
            " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre text, edad integer, curso text, ciclo text, despacho text);";

    public static final String ALUMNOS_CREATETABLE = "CREATE TABLE "+DATABASE_TABLE_ALUMNOS+
            " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre text, edad integer, curso text, ciclo text, nota_media real);";

    public static final String PROFESORES_DELETETABLE = "DROP TABLE IF EXISTS "+EstructuraBBDD.DATABASE_TABLE_PROFESORES+";";


    public static final String ALUMNOS_DELETETABLE = "DROP TABLE IF EXISTS "+EstructuraBBDD.DATABASE_TABLE_ALUMNOS+";";

    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dbadapter);
    }

    //***************************************************
    //***************************************************
    public Cursor cargarCursorProfesores(){

        String[] columnas = new String[]{ID_PROFESOR,NOMBRE_PROFESOR,EDAD_PROFESOR,
                CURSO_PROFESOR,CICLO_PROFESOR,DESPACHO_PROFESOR};

        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(DATABASE_TABLE_PROFESORES,columnas,null,null,null,null,null);

    }
    //********************************************************
    //********************************************************

}
