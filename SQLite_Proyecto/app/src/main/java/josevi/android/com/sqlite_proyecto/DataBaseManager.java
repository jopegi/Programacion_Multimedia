package josevi.android.com.sqlite_proyecto;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.io.File;

/**
 * Created by josevi on 08/12/2017.
 */


//Clase que contiene la estructura de la Base de Datos y define lógica de las
// operaciones que vamos a lanzar contra la Base de Datos (operaciones CRUD)
public class DataBaseManager extends Activity{

    //Estructura de la tabla profesores
    //Nombre de la tabla
    public static final String TABLE_NAME_PROFESORES = "profesores";
    //Campos de la tabla
    public static final String ID_PROFESOR = "_id";
    public static final String NOMBRE_PROFESOR = "nombre";
    public static final String  EDAD_PROFESOR = "edad";
    public static final String  CURSO_PROFESOR = "curso";
    public static final String  CICLO_PROFESOR = "ciclo";
    public static final String DESPACHO_PROFESOR = "despacho";

    //Estructura de la tabla alumnos
    //Nombre de la tabla
    public static final String TABLE_NAME_ALUMNOS = "alumnos";
    //Campos de la tabla
    public static final String ID_ALUMNO = "_id";
    public static final String NOMBRE_ALUMNO = "nombre";
    public static final String  EDAD_ALUMNO = "edad";
    public static final String  CICLO_ALUMNO = "ciclo";
    public static final String  CURSO_ALUMNO = "curso";
    public static final String NOTA_MEDIA_ALUMNO = "nota_media";


    //Sentencias para la creación de tablas
    //Creación tabla profesores
    public static final String PROFESORES_CREATETABLE = "CREATE TABLE "+TABLE_NAME_PROFESORES+
            " ("+ID_PROFESOR + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + NOMBRE_PROFESOR +
            " text, " + EDAD_PROFESOR + " integer, "+CURSO_PROFESOR+ " text, "+ CICLO_PROFESOR + " text, "
            + DESPACHO_PROFESOR + " text);";

    //Creación tabla alumnos
    public static final String ALUMNOS_CREATETABLE = "CREATE TABLE "+TABLE_NAME_ALUMNOS+
            " ("+ID_ALUMNO + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + NOMBRE_ALUMNO +
            " text, " + EDAD_ALUMNO + " integer, "+CURSO_ALUMNO+ " text, "+ CICLO_ALUMNO + " text, " +
            NOTA_MEDIA_ALUMNO + " real);";


    DBHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {

        helper = new DBHelper(context);
        //Cuando se ejecute la sentencia siguiente es cuando se creará la BBDD
        //Si la BBDD no existe, el método getWritableDatabase() la crea y la
        //devuelve en modo editable pero, si cuando se ejecuta este método ya
        //existe, solamente la devuelve.
        db = helper.getWritableDatabase();
    }

    public void insertarProfesor(String nombre, String edad, String curso, String ciclo, String despacho){

        //Creamos un contenedor de valores a modo de par clave-valor
        ContentValues valores = new ContentValues();

        //Insertamos los datos en el contenedor
        valores.put("nombre", nombre);
        valores.put("edad", edad);
        valores.put("curso", curso);
        valores.put("ciclo", ciclo);
        valores.put("despacho", despacho);

        //Insertamos los valores en la tabla a partir de los datos almacenados
        //en el contenedor
        //El método insert() devuelve el valor (long) del campo primary key de la nueva fila
        //En el caso de devolver -1 indicaría que se ha producido un error en la inserción
        db.insert(TABLE_NAME_PROFESORES, null, valores);

    }

    public void insertarAlumno(String nombre, int edad, String curso, String ciclo, double nota){

        //Creamos un contenedor de valores a modo de par clave-valor
        ContentValues valores = new ContentValues();

        //Insertamos los datos en el contenedor
        valores.put("nombre", nombre);
        valores.put("edad", edad);
        valores.put("curso", curso);
        valores.put("ciclo", ciclo);
        valores.put("nota_media", nota);

        //Insertamos los valores en la tabla a partir de los datos almacenados
        //en el contenedor
        //El método insert() devuelve el valor (long) del campo primary key de la nueva fila
        //En el caso de devolver -1 indicaría que se ha producido un error en la inserción
        db.insert(TABLE_NAME_ALUMNOS, null, valores);

    }

    //Método para borrar un alumno por su id
    public void borrarProfesor(int id) {
        // Obtenemos una referencia a la BBDD de lectura y escritura
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME_PROFESORES, "_id="+id, null);
        db.close();
    }

    //Método para borrar un alumno por su id
    public void borrarAlumno(int id) {
        // Obtenemos una referencia a la BBDD de lectura y escritura
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME_ALUMNOS, "_id="+id, null);
        db.close();
    }


    public void borrarProfesorPorID (String id){

        int identificador;

        if (isNumber(id) == false){

            Toast.makeText(getApplicationContext(), "Por favor, inserte un id numérico!", Toast.LENGTH_SHORT).show();

        }else {

            //Comprobamos que el indentificador indicado exista
            if(CheckIsDataAlreadyInDBorNot(TABLE_NAME_PROFESORES, "_id", id)==true) {

                // Obtenemos una referencia a la BBDD de lectura y escritura
                SQLiteDatabase db = helper.getWritableDatabase();

                identificador = Integer.valueOf(id);
                //Borramos el registro
                borrarProfesor(identificador);

                Toast.makeText(getApplicationContext(), "El profesor con id = " + identificador + " ha sido borrado", Toast.LENGTH_SHORT).show();

                //Cerramos la BBDD
                db.close();
            }
        }
    }

    public void borrarAlumnoPorID (String id){

        int identificador;

        if (isNumber(id) == false){

            Toast.makeText(getApplicationContext(), "Por favor, inserte un id numérico!", Toast.LENGTH_SHORT).show();

        }else {

            //Comprobamos que el indentificador indicado exista
            if(CheckIsDataAlreadyInDBorNot(TABLE_NAME_ALUMNOS, "_id", id)==true) {

                // Obtenemos una referencia a la BBDD de lectura y escritura
                SQLiteDatabase db = helper.getWritableDatabase();

                identificador = Integer.valueOf(id);
                //Borramos el registro
                borrarAlumno(identificador);

                Toast.makeText(getApplicationContext(), "El alumno con id = " + identificador + " ha sido borrado", Toast.LENGTH_SHORT).show();

                //Cerramos la BBDD
                db.close();
            }
        }
    }

    public void borrarBaseDatos(){

        try {

            //SQLite no usa la sentencia DROP DATABASE como lo hacen muchos otros sistemas de administración de bases de datos.
            //Si necesitamos eliminar completamente una base de datos, deberemos eliminar el archivo de la base de datos del sistema de archivos.
            String pathDatabase = getDatabasePath("centro_educativo.sqlite").getAbsolutePath();
            SQLiteDatabase.deleteDatabase(new File(pathDatabase));

            Toast.makeText(getApplicationContext(), "BBDD centro_educativo.sqlite eliminada!", Toast.LENGTH_SHORT).show();

        }catch (SQLiteException e){

            Toast.makeText(getApplicationContext(), "La BBDD no puede ser eliminada!", Toast.LENGTH_SHORT).show();
        }
    }


    //Método para comprobar si un campo de texto puede ser parseado a númerico o no.
    public static boolean isNumber (String string){
        try{
            Integer.parseInt(string);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Método para comprobar si un registro existe en una tabla
    public boolean CheckIsDataAlreadyInDBorNot(String TableName, String dbfield, String fieldValue) {

        // Obtenemos una referencia a la BBDD de lectura y escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;

        Cursor cursor = db.rawQuery(Query, null);

        if(cursor.getCount() <= 0){ //getCount()>> Devuelve un entero, indicando el número de líneas que contiene en cursor tras ejecutarse la consulta
            Toast.makeText(getApplicationContext(), "No se encontró en la tabla el registro indicado!", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Cerramos el Cursor
        cursor.close();
        //Cerramos la BBDD
        db.close();

        return true;
    }


    //***************************************************
    //***************************************************
    public Cursor cargarCursorProfesores(){

        String[] columnas = new String[]{ID_PROFESOR,NOMBRE_PROFESOR,EDAD_PROFESOR,
                CURSO_PROFESOR,CICLO_PROFESOR,DESPACHO_PROFESOR};

        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(TABLE_NAME_PROFESORES,columnas,null,null,null,null,null);

    }
    //********************************************************
    //********************************************************

    //***************************************************
    //***************************************************
    public Cursor cargarCursorAlumnos(){

        String[] columnas = new String[]{ID_ALUMNO,NOMBRE_ALUMNO,EDAD_ALUMNO,
                CURSO_ALUMNO,CICLO_ALUMNO,NOTA_MEDIA_ALUMNO};

        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(TABLE_NAME_ALUMNOS,columnas,null,null,null,null,null);

    }
    //********************************************************
    //********************************************************

    //***************************************************
    //***************************************************
    public Cursor buscarProfesoresPorCurso(String curso){

        String[] columnas = new String[]{ID_PROFESOR,NOMBRE_PROFESOR,EDAD_PROFESOR,
                CURSO_PROFESOR,CICLO_PROFESOR,DESPACHO_PROFESOR};

        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(TABLE_NAME_PROFESORES,columnas,CURSO_PROFESOR + "=?",new String []{curso},null,null,null);

    }
    //********************************************************
    //********************************************************

    public Cursor buscarProfesoresPorCiclo(String ciclo){

        String[] columnas = new String[]{ID_PROFESOR,NOMBRE_PROFESOR,EDAD_PROFESOR,
                CURSO_PROFESOR,CICLO_PROFESOR,DESPACHO_PROFESOR};

        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(TABLE_NAME_PROFESORES,columnas,CICLO_PROFESOR + "=?",new String []{ciclo},null,null,null);

    }
    //********************************************************
    //********************************************************

    //***************************************************
    //***************************************************
    public Cursor buscarAlumnosPorCurso(String curso){

        String[] columnas = new String[]{ID_ALUMNO,NOMBRE_ALUMNO,EDAD_ALUMNO,
                CURSO_ALUMNO,CICLO_ALUMNO,NOTA_MEDIA_ALUMNO};

        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(TABLE_NAME_ALUMNOS,columnas,CURSO_ALUMNO + "=?",new String []{curso},null,null,null);

    }
    //********************************************************
    //********************************************************

    public Cursor buscarAlumnosPorCiclo(String ciclo){

        String[] columnas = new String[]{ID_ALUMNO,NOMBRE_ALUMNO,EDAD_ALUMNO,
                CURSO_ALUMNO,CICLO_ALUMNO,NOTA_MEDIA_ALUMNO};

        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(TABLE_NAME_ALUMNOS,columnas,CURSO_ALUMNO + "=?",new String []{ciclo},null,null,null);

    }
    //********************************************************
    //********************************************************

    //***************************************************
    //***************************************************
    public Cursor buscarProfesoresPorCursoYCiclo(String curso, String ciclo){

        String[] columnas = new String[]{ID_PROFESOR,NOMBRE_PROFESOR,EDAD_PROFESOR,
                CURSO_PROFESOR,CICLO_PROFESOR,DESPACHO_PROFESOR};

        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(TABLE_NAME_PROFESORES,columnas,CURSO_PROFESOR + " = '?' AND " + CICLO_PROFESOR+" = '?'",new String []{curso,ciclo},null,null,null);

    }
    //********************************************************
    //********************************************************

    //***************************************************
    //***************************************************
    public Cursor buscarAlumnosPorCursoYCiclo(String curso, String ciclo){

        String[] columnas = new String[]{ID_ALUMNO,NOMBRE_ALUMNO,EDAD_ALUMNO,
                CURSO_ALUMNO,CICLO_ALUMNO,NOTA_MEDIA_ALUMNO};

        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(TABLE_NAME_ALUMNOS,columnas,CURSO_ALUMNO + " = '?' AND " + CICLO_ALUMNO + " = '?'",new String []{curso,ciclo},null,null,null);

    }
    //********************************************************
//********************************************************
}
