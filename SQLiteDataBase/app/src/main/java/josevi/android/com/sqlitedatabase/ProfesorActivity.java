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


public class ProfesorActivity extends AppCompatActivity {

    private Button botonInsertar, botonBorrarRegistro, botonBorrarBBDD, botonVolver, botonBuscar;
    private EditText cajaNombre, cajaEdad, cajaCiclo, cajaCurso, cajaDespacho, cajaId;

    BaseDatosHelper miBBDDHelper = null;
    private int identificador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor2);

        botonInsertar = (Button) findViewById(R.id.btnInsertar);
        botonBorrarRegistro = (Button) findViewById(R.id.btnBorrarId);
        botonBorrarBBDD = (Button) findViewById(R.id.btnBorrarBBDD);
        botonVolver = (Button) findViewById(R.id.btnVolver);
        botonBuscar = (Button) findViewById(R.id.btnBuscar);

        cajaNombre = (EditText) findViewById(R.id.editTextNombre);
        cajaEdad = (EditText) findViewById(R.id.editTextEdad);
        cajaCurso = (EditText) findViewById(R.id.editTextCurso);
        cajaCiclo = (EditText) findViewById(R.id.editTextCiclo);
        cajaDespacho = (EditText) findViewById(R.id.editTextDespacho);

        cajaId = (EditText) findViewById(R.id.editTextId);

        //Instaciamos un objeto de tipo SQLiteOpenHelper
        miBBDDHelper = new BaseDatosHelper(this, DATABASE_NAME, null, BaseDatosHelper.DATABASE_VERSION);

        //Listener del botón búsquedas
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBuscar = new Intent(ProfesorActivity.this, ConsultasActivity.class);

                startActivity(intentBuscar);
            }
        });


        //Listener del botón volver a la interfaz principal
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentVolver = new Intent(ProfesorActivity.this, MainActivity.class);

                startActivity(intentVolver);
            }
        });


        //Listener del botón insertar
        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Obtenemos una referencia a la BBDD de lectura y escritura
                SQLiteDatabase db = miBBDDHelper.getWritableDatabase();

                //Creamos un contenedor de valores a modo de par clave-valor
                ContentValues values = new ContentValues();

                //Insertamos los datos en el contenedor
                values.put(EstructuraBBDD.NOMBRE_PROFESOR, cajaNombre.getText().toString());
                values.put(EstructuraBBDD.EDAD_PROFESOR, Integer.valueOf(cajaEdad.getText().toString()));
                values.put(EstructuraBBDD.CURSO_PROFESOR, cajaCiclo.getText().toString());
                values.put(EstructuraBBDD.CICLO_PROFESOR, cajaCurso.getText().toString());
                values.put(EstructuraBBDD.DESPACHO_PROFESOR, cajaDespacho.getText().toString());

                //Comprobación de valores: los imprimimos en el log
                printContentValues(values);

                //Toast.makeText(getApplicationContext(), values.get("nombre").toString(), Toast.LENGTH_LONG).show();

                //Insertamos el nuevo registro en la tabla mediante el método insert()
                //Este método devuelve el valor del campo primary key de la nueva fila
                long newRowId = db.insert("profesores", null, values);

                //Cerramos la BBDD
                db.close();

               //Toast.makeText(getApplicationContext(), "Se guardó el registro con id: "+newRowId, Toast.LENGTH_LONG).show();


                Toast.makeText(getApplicationContext(), "Nombre: "+cajaNombre.getText().toString()+"\n"+"Edad:" +Integer.valueOf(cajaEdad.getText().toString())+"\n"
                        +"Curso:"+cajaCurso.getText().toString()+"\n"+"Ciclo:"+cajaCiclo.getText().toString()+"\n"+"Despacho:"+cajaDespacho.getText().toString(), Toast.LENGTH_LONG).show();

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
                    if(CheckIsDataAlreadyInDBorNot(EstructuraBBDD.DATABASE_TABLE_PROFESORES, "_id", cajaId.getText().toString())==true) {

                        // Obtenemos una referencia a la BBDD de lectura y escritura
                        SQLiteDatabase db = miBBDDHelper.getWritableDatabase();

                        identificador = Integer.valueOf(cajaId.getText().toString());
                        //Borramos el registro
                        borrarProfesor(identificador);

                        Toast.makeText(getApplicationContext(), "El profesor con id = " + identificador + " ha sido borrado", Toast.LENGTH_SHORT).show();

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

    //Método para borrar un profesor por su id
    public void borrarProfesor(int id) {

        // Obtenemos una referencia a la BBDD de lectura y escritura
        SQLiteDatabase db = miBBDDHelper.getWritableDatabase();
        //Borramos, en la tabla profesores, el registro cuyo _id coincida con el indicado
        db.delete("profesores", "_id=" + id, null);
        //Cerramos la BBDD
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
