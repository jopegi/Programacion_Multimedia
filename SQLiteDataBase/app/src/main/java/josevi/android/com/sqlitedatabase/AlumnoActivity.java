package josevi.android.com.sqlitedatabase;

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

import static josevi.android.com.sqlitedatabase.BaseDatosHelper.DATABASE_NAME;

public class AlumnoActivity extends AppCompatActivity {

    private Button botonInsertar, botonBorrarRegistro, botonBorrarBBDD, botonVolver, botonBuscar;
    private EditText cajaNombre, cajaEdad, cajaCiclo, cajaCurso, cajaNotaMedia, cajaId;

    BaseDatosHelper miBBDDHelper = null;
    private int identificador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        botonInsertar = (Button) findViewById(R.id.btnInsertar);
        botonBorrarRegistro = (Button) findViewById(R.id.btnBorrarId);
        botonBorrarBBDD = (Button) findViewById(R.id.btnBorrarBBDD);
        botonVolver = (Button) findViewById(R.id.btnVolver);
        botonBuscar = (Button) findViewById(R.id.btnBuscar);


        cajaNombre = (EditText) findViewById(R.id.editTextNombre);
        cajaEdad = (EditText) findViewById(R.id.editTextEdad);
        cajaCiclo = (EditText)findViewById(R.id.editTextCiclo);
        cajaCurso = (EditText) findViewById(R.id.editTextCurso);
        cajaNotaMedia = (EditText) findViewById(R.id.editTextDespacho);
        cajaId = (EditText) findViewById(R.id.editTextId);

        //Instaciamos un objeto de tipo SQLiteOpenHelper
        miBBDDHelper = new BaseDatosHelper(this, BaseDatosHelper.DATABASE_NAME, null, BaseDatosHelper.DATABASE_VERSION);

        //Listener del botón búsquedas
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBuscar = new Intent(AlumnoActivity.this, ConsultasActivity.class);

                startActivity(intentBuscar);
            }
        });

        //Listener del botón volver
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentoVolver = new Intent(AlumnoActivity.this, MainActivity.class);

                startActivity(intentoVolver);
            }
        });

        //Listener del botón insertar
        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtenemos una referencia a la BBDD de lectura y escritura
                SQLiteDatabase db = miBBDDHelper.getWritableDatabase();

                //Creamos un contenedor de valores a modo de par clave-valor
                ContentValues values = new ContentValues();

                //Insertamos los datos en el contenedor
                values.put("nombre", cajaNombre.getText().toString());
                values.put("edad", Integer.valueOf(cajaEdad.getText().toString()));
                values.put("curso", cajaCiclo.getText().toString());
                values.put("ciclo", cajaCurso.getText().toString());
                values.put("nota_media", Float.valueOf(cajaNotaMedia.getText().toString()));

                //Comprobación de valores: los imprimimos en el log
                printContentValues(values);

                //Insertamos el nuevo registro en la tabla mediante el método insert()
                //Este método devuelve el valor del campo primary key de la nueva fila
                long newRowId = db.insert("alumnos", null, values);

                //Cerramos la BBDD
                db.close();

                //Toast.makeText(getApplicationContext(), "Se guardó el registro con id: "+newRowId, Toast.LENGTH_LONG).show();

                Toast.makeText(getApplicationContext(), "Nombre: "+cajaNombre.getText().toString()+"\n"+"Edad:" +Integer.valueOf(cajaEdad.getText().toString())+"\n"+"Ciclo:"+cajaCiclo.getText().toString()+"\n"+"Curso:"+cajaCurso.getText().toString()+"\n"+"Nota_Media:"+cajaNotaMedia.getText().toString(), Toast.LENGTH_LONG).show();

            }
        });

        //Listener del botón borrar registro
        botonBorrarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Comprobamos que se inserte un número como identificador
                if (isNumber(cajaId.getText().toString()) == false){

                    Toast.makeText(getApplicationContext(), "Por favor, inserte un id numérico!", Toast.LENGTH_SHORT).show();

                }else {

                    //Comprobamos que el indentificador indicado exista
                    if(CheckIsDataAlreadyInDBorNot(EstructuraBBDD.DATABASE_TABLE_ALUMNOS, "_id", cajaId.getText().toString())==true) {

                        // Obtenemos una referencia a la BBDD de lectura y escritura
                        SQLiteDatabase db = miBBDDHelper.getWritableDatabase();

                        identificador = Integer.valueOf(cajaId.getText().toString());
                        //Borramos el registro
                        borrarAlumno(identificador);

                        Toast.makeText(getApplicationContext(), "El alumno con id = " + identificador + " ha sido borrado", Toast.LENGTH_SHORT).show();

                        //Cerramos la BBDD
                        db.close();
                    }
                }
            }
        });

        //Listener del botón borrar BBDD
        botonBorrarBBDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    //SQLite no usa la sentencia DROP DATABASE como lo hacen muchos otros sistemas de administración de bases de datos.
                    //Si necesitamos eliminar completamente una base de datos, deberemos eliminar el archivo de la base de datos del sistema de archivos.
                    String pathDatabase = getDatabasePath(BaseDatosHelper.DATABASE_NAME).getAbsolutePath();
                    SQLiteDatabase.deleteDatabase(new File(pathDatabase));

                    Toast.makeText(getApplicationContext(), "BBDD " + DATABASE_NAME + " eliminada!", Toast.LENGTH_SHORT).show();

                }catch (SQLiteException e){

                    Toast.makeText(getApplicationContext(), "La BBDD no puede ser eliminada!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Método para imprimir los valores contenidos por el objeto de tipo ContentValues
    public void printContentValues(ContentValues vals){

        Set<Map.Entry<String, Object>> s=vals.valueSet();
        Iterator itr = s.iterator();

        Log.d("DatabaseSync", "ContentValue Length :: " +vals.size());

        while(itr.hasNext()){

            Map.Entry me = (Map.Entry)itr.next();
            String key = me.getKey().toString();
            Object value =  me.getValue();

            Log.d("DatabaseSync", "Key:"+key+", values:"+(String)(value == null?null:value.toString()));
        }
    }

    //Método para borrar un alumno por su id
    public void borrarAlumno(int id) {
        // Obtenemos una referencia a la BBDD de lectura y escritura
        SQLiteDatabase db = miBBDDHelper.getWritableDatabase();
        db.delete("alumnos", "_id="+id, null);
        db.close();
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
        SQLiteDatabase db = miBBDDHelper.getWritableDatabase();

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
}
