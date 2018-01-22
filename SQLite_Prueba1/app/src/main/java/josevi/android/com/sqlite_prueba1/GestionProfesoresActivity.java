package josevi.android.com.sqlite_prueba1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class GestionProfesoresActivity extends AppCompatActivity {

    private DBHelper midbhelper;

    private SQLiteDatabase db;

    private EditText cajaNombre, cajaEdad, cajaCurso, cajaCiclo, cajaDespacho;
    private Button botonInsertar, botonBorrar, botonBorrarDB;

    private Spinner spiner;

    private ArrayList<String> ids;

    private ContentValues values;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_profesores);

        cajaNombre = (EditText) findViewById(R.id.editTextNombre);
        cajaEdad = (EditText) findViewById(R.id.editTextEdad);
        cajaCurso = (EditText) findViewById(R.id.editTextCurso);
        cajaCiclo = (EditText) findViewById(R.id.editTextCiclo);
        cajaDespacho = (EditText) findViewById(R.id.editTextDespacho);

        botonInsertar = (Button) findViewById(R.id.btnInsertar);
        botonBorrar = (Button) findViewById(R.id.btnBorrar);
        botonBorrarDB = (Button) findViewById(R.id.btnBorrarBaseDatos);

        spiner = (Spinner) findViewById(R.id.spinner);

        //(Contexto de la aplicación, nombre de la BBDD, Objeto CursorFactory, Version de la BBDD)
        midbhelper = new DBHelper(this, DBHelper.NOMBRE_DATABASE, null, DBHelper.VERSION_DATABASE);

        //1.Al iniciar la app, recuperamos los ids de los profesores ya insertados en la BBDD
        //Nota: el método recuperarIds() ya abre y cierra una instancia a la BBDD
        ids = recuperarIds();

        try {
            //Si en la BBDD ya existía algún id insertado lo mostramos en un spinner
            if (ids.size() > 0) {

                //Instanciamos un ArrayAdapter pasándole el array con los ids
                ArrayAdapter adaptador = new ArrayAdapter<String>(GestionProfesoresActivity.this,
                        android.R.layout.simple_spinner_item, ids);

                //Rellenamos el spinner con el adaptador
                spiner.setAdapter(adaptador);

                //Sino inhabilitamos el spinner
            } else {

                spiner.setEnabled(false);
            }

        } catch (Exception e) {
            Toast.makeText(GestionProfesoresActivity.this, "Error al cargar el spinner!", Toast.LENGTH_SHORT).show();
        }


        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Abrimos la BBDD en modo lectura y escritura
                db = midbhelper.getWritableDatabase();

                //Creamos un contenedor de valores a modo de par clave-valor
                values = new ContentValues();

                values.put(DBEstructura.NOMBRE_PROFESOR,cajaNombre.getText().toString());
                values.put(DBEstructura.EDAD_PROFESOR,cajaEdad.getText().toString());
                values.put(DBEstructura.CICLO_PROFESOR,cajaCiclo.getText().toString());
                values.put(DBEstructura.CURSO_PROFESOR,cajaCurso.getText().toString());
                values.put(DBEstructura.DESPACHO_PROFESOR,cajaDespacho.getText().toString());

                //Insertamos el nuevo registro en la tabla mediante el método insert()
                //Este método devuelve el valor del campo primary key de la nueva fila, o
                //-1 si la consulta no se ha ejecutado correctamente.
                long newRowId = db.insert("profesores", null, values);

                if ( newRowId!= -1) {

                    Toast.makeText(getApplicationContext(),  "Inserción realizada con éxito!!", Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(getApplicationContext(), "Inserción fallida!", Toast.LENGTH_LONG).show();
                }

                //Cerramos la BBDD
                db.close();

                //Vacíamos las cajas de texto
                vaciarCajas();

                //2.Tras insertar un nuevo usuario, volvemos a recuperar los ids de los usuarios
                //Nota: el método recuperarIds() ya abre y cierra una instancia a la BBDD
                ids = recuperarIds();

                //Instanciamos un ArrayAdapter pasándole el array con los ids
                ArrayAdapter adaptador = new ArrayAdapter<String>(GestionProfesoresActivity.this,
                        android.R.layout.simple_spinner_item, ids);

                //Rellenamos el spinner con el adaptador
                spiner.setAdapter(adaptador);

                spiner.setEnabled(true);

            }
        });

        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    id = Integer.parseInt(spiner.getSelectedItem().toString());

                }catch(NumberFormatException e){

                    Toast.makeText(GestionProfesoresActivity.this, "Por favor, inserte un id numérico!", Toast.LENGTH_SHORT).show();
                }

                //Borramos el registro
                borrarProfesor(id);

                Toast.makeText(getApplicationContext(), "El profesor con id = " + id + " ha sido borrado con éxito!", Toast.LENGTH_SHORT).show();

                //3.Tras insertar un nuevo usuario, volvemos a recuperar los ids de los usuarios
                //Nota: el método recuperarIds() ya abre y cierra una instancia a la BBDD
                ids = recuperarIds();

                //Instanciamos un ArrayAdapter pasándole el array con los ids
                ArrayAdapter adaptador = new ArrayAdapter<String>(GestionProfesoresActivity.this,
                        android.R.layout.simple_spinner_item, ids);

                //Rellenamos el spinner con el adaptador
                spiner.setAdapter(adaptador);

            }
        });

        botonBorrarDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String pathDatabase = getDatabasePath(DBHelper.NOMBRE_DATABASE).getAbsolutePath();
                    SQLiteDatabase.deleteDatabase(new File(pathDatabase));
                    Toast.makeText(getApplicationContext(), "BBDD " + DBHelper.NOMBRE_DATABASE + " eliminada con éxito!", Toast.LENGTH_SHORT).show();
                    //Borramos la info del spinner
                    spiner.setAdapter(null);
                }catch(SQLiteException e){
                    Toast.makeText(getApplicationContext(), "La BBDD no puede ser eliminada!", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }


    public ArrayList<String> recuperarIds(){

        //Abrimos la BBDD en modo lectura
        db = midbhelper.getReadableDatabase();

        ArrayList<String> ProfesoresId= new ArrayList<String>();

        Cursor c = db.query(DBEstructura.TABLA_PROFESORES,null,null,null,null,null,null);

        if(c != null && c.moveToFirst()){

            do{
                ProfesoresId.add(c.getString(0));
            }while(c.moveToNext());
        }

        //Cerramos la instancia de la BBDD;
        db.close();

        return ProfesoresId;
    }


    public void vaciarCajas(){

        cajaNombre.setText("");
        cajaEdad.setText("");
        cajaCiclo.setText("");
        cajaCurso.setText("");
        cajaDespacho.setText("");
        spiner.setAdapter(null);
    }

    //Método para borrar un profesor por su id
    public void borrarProfesor(int ident) {
        //Abrimos la BBDD en modo lectura y escritura
        db = midbhelper.getWritableDatabase();
        //Borramos, en la tabla profesores, el registro cuyo _id coincida con el indicado
        db.delete("profesores", "_id=" + ident, null);
        //db.execSQL("DELETE FROM profesores WHERE _id=id");
        //Cerramos la BBDD
        db.close();

    }
}
