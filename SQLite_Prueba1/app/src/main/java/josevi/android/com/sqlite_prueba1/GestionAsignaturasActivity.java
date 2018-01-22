package josevi.android.com.sqlite_prueba1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GestionAsignaturasActivity extends AppCompatActivity {

    private EditText cajaNombreAsig, cajaHorasAsig;
    private Button botonInsertarAsig, botonMostrarAsig;
    private ListView listaAsig;

    private DBHelper midbhelper;

    private SQLiteDatabase db;

    private ContentValues values;

    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_asignaturas);

        cajaNombreAsig = (EditText) findViewById(R.id.editTextNombreAsig);
        cajaHorasAsig = (EditText) findViewById(R.id.editTextHorasAsig);

        botonInsertarAsig = (Button) findViewById(R.id.btnInsertar);
        botonMostrarAsig = (Button) findViewById(R.id.btnMostrar);

        listaAsig = (ListView) findViewById(R.id.ListView1);

        //(Contexto de la aplicación, nombre de la BBDD, Objeto CursorFactory, Version de la BBDD)
        midbhelper = new DBHelper(this, DBHelper.NOMBRE_DATABASE, null, DBHelper.VERSION_DATABASE);


        botonInsertarAsig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Abrimos la BBDD en modo lectura y escritura
                db = midbhelper.getWritableDatabase();

                //Creamos un contenedor de valores a modo de par clave-valor
                values = new ContentValues();

                values.put(DBEstructura.NOMBRE_ASIG,cajaNombreAsig.getText().toString());
                values.put(DBEstructura.HORAS_ASIG,cajaHorasAsig.getText().toString());


                //Insertamos el nuevo registro en la tabla mediante el método insert()
                //Este método devuelve el valor del campo primary key de la nueva fila, o
                //-1 si la consulta no se ha ejecutado correctamente.
                long newRowId = db.insert("asignaturas", null, values);

                if ( newRowId!= -1) {

                    Toast.makeText(getApplicationContext(),  "Inserción realizada con éxito!!", Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(getApplicationContext(), "Inserción fallida!", Toast.LENGTH_LONG).show();
                }

                //Cerramos la BBDD
                db.close();

                //Vacíamos las cajas de texto
                vaciarCajas();

            }
        });


        botonMostrarAsig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Recuperamos en un ArrayList el resultado del método recuperarProfesores()
                ArrayList<String> asig = recuperarAsignaturas();

                //Toast.makeText(GestionAsignaturasActivity.this, "Tamaño ArrayList asig= "+asig.size(), Toast.LENGTH_SHORT).show();

                //Insertamos en un adaptador el Array
                adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, asig);

                //Mostramos los resultados en un listView
                listaAsig.setAdapter(adaptador);

            }
        });
    }

    public void vaciarCajas(){

        cajaNombreAsig.setText("");
        cajaHorasAsig.setText("");

    }

    public ArrayList<String> recuperarAsignaturas() {

        ArrayList<String> asignaturas = new ArrayList<String>();

        SQLiteDatabase db = midbhelper.getReadableDatabase();

        Cursor cursor = db.query("asignaturas",null,null,null,null,null,null);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                asignaturas.add(cursor.getString(1)+" "+ cursor.getString(2));

            } while (cursor.moveToNext());
        }

        db.close();

        //Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

        return asignaturas;
    }
}
