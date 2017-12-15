package josevi.android.com.sqlitecentroeducativo;

/**
 * Created by josevi on 14/12/2017.
 */

public class EstructuraBBDD {

    //Constructor de la clase
    public EstructuraBBDD() {

    }

    //Nombre de la tabla para los profesores
    public static final String TABLE_PROFESORES = "profesores";
    //Nombre de la tabla para los alumnos
    public static final String TABLE_ALUMNOS = "alumnos";

    //Campos tabla profesor
    public static final String ID_PROFESOR = "_id";
    public static final String NOMBRE_PROFESOR = "nombre";
    public static final String  EDAD_PROFESOR = "edad";
    public static final String  CICLO_PROFESOR = "ciclo";
    public static final String  CURSO_PROFESOR = "curso";
    public static final String  DESPACHO_PROFESOR = "despacho";

    //Campos tabla alumno
    public static final String ID_ALUMNO = "_id";
    public static final String NOMBRE_ALUMNO = "nombre";
    public static final String  EDAD_ALUMNO = "edad";
    public static final String  CICLO_ALUMNO = "ciclo";
    public static final String  CURSO_ALUMNO = "curso";
    public static final String  NOTA_MEDIA_ALUMNO = "nota_media";

    //Sentencias SQL de creación de tablas
    public static final String PROFESORES_CREATETABLE = "CREATE TABLE "+TABLE_PROFESORES+
            " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre text, edad integer, ciclo text, curso integer, despacho text);";

    public static final String ALUMNOS_CREATETABLE = "CREATE TABLE "+TABLE_ALUMNOS+
            " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre text, edad integer, ciclo text, curso integer, nota_media real);";


    //Sentencias SQL de eliminación de tablas
    public static final String PROFESORES_DELETETABLE = "DROP TABLE IF EXISTS "+TABLE_PROFESORES+";";

    public static final String ALUMNOS_DELETETABLE = "DROP TABLE IF EXISTS "+TABLE_ALUMNOS+";";
}
