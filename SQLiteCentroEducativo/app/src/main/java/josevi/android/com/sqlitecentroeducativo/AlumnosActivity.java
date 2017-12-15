package josevi.android.com.sqlitecentroeducativo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static josevi.android.com.sqlitecentroeducativo.DataBaseHelper.DATABASE_NAME;

public class AlumnosActivity extends AppCompatActivity {

    //Declaramos un objeto de tipo DataBaseHelper para poder manejar la Base de Datos
    private DataBaseHelper miDataBaseHelper;

    //Declaramos los views de la vista
    private EditText cajaNombre, cajaEdad, cajaCiclo, cajaCurso, cajaNota, cajaId;
    private Button botonInsertar, botonBorrarXId, botonBorrarBBDD, botonVolver, botonConsultas;

    private int identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        //Instaciamos los views de la vista
        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaEdad = (EditText) findViewById(R.id.cajaEdad);
        cajaCiclo = (EditText) findViewById(R.id.cajaCiclo);
        cajaCurso = (EditText) findViewById(R.id.cajaCurso);
        cajaNota = (EditText) findViewById(R.id.cajaNota);
        cajaId = (EditText) findViewById(R.id.cajaId);

        botonInsertar = (Button) findViewById(R.id.btnInsertar);
        botonBorrarXId = (Button) findViewById(R.id.btnBorrarXId);
        botonBorrarBBDD = (Button) findViewById(R.id.btnBorrarBBDD);
        botonVolver = (Button) findViewById(R.id.btnVolver);
        botonConsultas = (Button) findViewById(R.id.btnConsultas);

        //Instaciamos un objeto de tipo SQLiteOpenHelper
        //En este momento es cuando se crea la Base de Datos
        // y sus tablas, en caso de no existir ya previamente.
        miDataBaseHelper = new DataBaseHelper(this, DATABASE_NAME, null, miDataBaseHelper.DATABASE_VERSION);


        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Abrimos la BBDD en modo lectura y escritura
                SQLiteDatabase db = miDataBaseHelper.getWritableDatabase();

                //Creamos un contenedor de valores a modo de par clave-valor
                ContentValues values = new ContentValues();

                //Insertamos los datos en el contenedor
                values.put(EstructuraBBDD.NOMBRE_ALUMNO, cajaNombre.getText().toString());
                values.put(EstructuraBBDD.EDAD_ALUMNO, Integer.valueOf(cajaEdad.getText().toString()));
                values.put(EstructuraBBDD.CICLO_ALUMNO, cajaCiclo.getText().toString());
                values.put(EstructuraBBDD.CURSO_ALUMNO, Integer.valueOf(cajaCurso.getText().toString()));
                values.put(EstructuraBBDD.NOTA_MEDIA_ALUMNO, Float.valueOf(cajaNota.getText().toString()));

                //Comprobación de valores: los imprimimos en el log
                printContentValues(values);

                //Insertamos el nuevo registro en la tabla mediante el método insert()
                //Este método devuelve el valor del campo primary key de la nueva fila, o
                //-1 si la consulta no se ha ejecutado correctamente.
                long newRowId = db.insert("alumnos", null, values);

                //Cerramos la BBDD
                db.close();

                //Toast.makeText(getApplicationContext(), "Se guardó el registro con id: "+newRowId, Toast.LENGTH_LONG).show();

                if ( newRowId!= -1) {
                    //Si la inserción ha ido bien mostramos en un Toast los datos insertados
                    Toast.makeText(getApplicationContext(), "Nombre: " + cajaNombre.getText().toString() + "\n" + "Edad:" + cajaEdad.getText().toString() + "\n"
                            + "Ciclo:" + cajaCiclo.getText().toString() + "\n" + "Curso:" + cajaCurso.getText().toString() + "\n" + "Nota Media:" + cajaNota.getText().toString(), Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(getApplicationContext(), "Inserción fallida!", Toast.LENGTH_LONG).show();
                }

                vaciarCajas();

            }
        });

        botonBorrarXId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Comprobamos que se inserte un número como identificador
                if (isNumber(cajaId.getText().toString()) == false){

                    Toast.makeText(getApplicationContext(), "Por favor, inserte un id numérico!", Toast.LENGTH_SHORT).show();

                }else {

                    //Comprobamos que el indentificador indicado exista
                    if(CheckIsDataAlreadyInDBorNot(EstructuraBBDD.TABLE_ALUMNOS, "_id", cajaId.getText().toString())==true) {

                        //Abrimos la BBDD en modo lectura y escritura
                        SQLiteDatabase db = miDataBaseHelper.getWritableDatabase();

                        identificador = Integer.valueOf(cajaId.getText().toString());

                        //Borramos el registro
                        borrarAlumno(identificador);

                        Toast.makeText(getApplicationContext(), "El alumno con id = " + identificador + " ha sido borrado", Toast.LENGTH_SHORT).show();

                        //Cerramos la BBDD
                        db.close();
                    }
                }

                cajaId.setText("");
            }
        });

        botonBorrarBBDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    //SQLite no usa la sentencia DROP DATABASE como lo hacen muchos otros sistemas de administración
                    //de Bases de Batos. Si necesitamos eliminar completamente una base de datos, deberemos
                    //eliminar el archivo de la base de datos del sistema de archivos.
                    String pathDatabase = getDatabasePath(DATABASE_NAME).getAbsolutePath();
                    SQLiteDatabase.deleteDatabase(new File(pathDatabase));

                    Toast.makeText(getApplicationContext(), "BBDD " + DATABASE_NAME + " eliminada!", Toast.LENGTH_SHORT).show();

                }catch (SQLiteException e){

                    Toast.makeText(getApplicationContext(), "La BBDD no puede ser eliminada!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intentVolver = new Intent(ProfesoresActivity.this, MainActivity.class);
                //startActivity(intentVolver);
                finish();

            }
        });

        botonConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentConsultas = new Intent(AlumnosActivity.this, ConsultasActivity.class);
                startActivity(intentConsultas);

            }
        });


    }

    //Método para imprimir los valores contenidos por el objeto de tipo ContentValues
    public void printContentValues(ContentValues vals)
    {
        Set<Map.Entry<String, Object>> s=vals.valueSet();
        Iterator itr = s.iterator();

        Log.d("DatabaseSync", "ContentValue Length :: " +vals.size());

        while(itr.hasNext())
        {
            Map.Entry me = (Map.Entry)itr.next();
            String key = me.getKey().toString();
            Object value =  me.getValue();

            Log.d("DatabaseSync", "Key:"+key+", values:"+(String)(value == null?null:value.toString()));
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

        //Abrimos la BBDD en modo lectura y escritura
        SQLiteDatabase db = miDataBaseHelper.getWritableDatabase();

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

    //Método para borrar un alumno por su id
    public void borrarAlumno(int id) {

        //Abrimos la BBDD en modo lectura y escritura
        SQLiteDatabase db = miDataBaseHelper.getWritableDatabase();
        //Borramos, en la tabla alumno, el registro cuyo _id coincida con el indicado
        db.delete("alumno", "_id=" + id, null);
        //Cerramos la BBDD
        db.close();

    }

    public void vaciarCajas(){

        cajaNombre.setText("");
        cajaEdad.setText("");
        cajaCiclo.setText("");
        cajaCurso.setText("");
        cajaNota.setText("");
    }
}
