package josevi.android.com.sqlite_prueba1;

/**
 * Created by josevi on 16/01/2018.
 */

public class DBEstructura {

    public DBEstructura() {
    }

    //Tablas
    public static final String TABLA_PROFESORES = "profesores";
    public static final String TABLA_ALUMNOS = "alumnos";
    public static final String TABLA_ASIGNATURAS = "asignaturas";

    //Campos tabla profesores
    public static final String ID_PROFESOR = "_id";
    public static final String NOMBRE_PROFESOR = "nombre";
    public static final String EDAD_PROFESOR = "edad";
    public static final String CICLO_PROFESOR = "ciclo";
    public static final String CURSO_PROFESOR = "curso";
    public static final String DESPACHO_PROFESOR = "despacho";

    //Campos tabla alumnos
    public static final String ID_ALUMNO = "_id";
    public static final String NOMBRE_ALUMNO = "nombre";
    public static final String EDAD_ALUMNO = "edad";
    public static final String CICLO_ALUMNO = "ciclo";
    public static final String CURSO_ALUMNO = "curso";
    public static final String NOTA_ALUMNO = "nota_media";

    //Campos tabla asignaturas
    public static final String ID_ASIG = "_id";
    public static final String NOMBRE_ASIG = "nombre";
    public static final String HORAS_ASIG = "horas";



    //Sentencias SQL de creación de tablas
    public static final String PROFESORES_CREATETABLE = "CREATE TABLE "+TABLA_PROFESORES+
            " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre text, edad text, ciclo text, curso text, despacho text);";

    public static final String ALUMNOS_CREATETABLE = "CREATE TABLE "+TABLA_ALUMNOS+
            " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre text, edad text, ciclo text, curso text, nota_media text);";

    public static final String ASIGNATURAS_CREATETABLE = "CREATE TABLE "+TABLA_ASIGNATURAS+
            " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre text, horas text);";

    //Tipos de datos: text, integer, real


    //Sentencias SQL de eliminación de tablas
    public static final String PROFESORES_DELETETABLE = "DROP TABLE IF EXISTS "+TABLA_PROFESORES+";";

    public static final String ALUMNOS_DELETETABLE = "DROP TABLE IF EXISTS "+TABLA_ALUMNOS+";";

    public static final String ASIGNATURAS_DELETETABLE = "DROP TABLE IF EXISTS "+TABLA_ASIGNATURAS+";";
}
