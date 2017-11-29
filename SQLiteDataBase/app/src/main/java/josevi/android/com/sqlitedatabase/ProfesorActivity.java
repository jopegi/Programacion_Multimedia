package josevi.android.com.sqlitedatabase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ProfesorActivity extends AppCompatActivity {

    private Button botonInsertar, botonBorrarRegistro, botonBorrarBBDD, botonVolver;
    private EditText cajaNombre, cajaEdad, cajaCiclo, cajaDespacho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        botonInsertar = (Button) findViewById(R.id.btnInsertar);
        botonBorrarRegistro = (Button) findViewById(R.id.btnBorrarId);
        botonBorrarBBDD = (Button) findViewById(R.id.btnBorrarBBDD);
        botonVolver = (Button) findViewById(R.id.btnVolver);

        cajaNombre = (EditText) findViewById(R.id.editTextNombre);
        cajaEdad = (EditText) findViewById(R.id.editTextEdad);
        cajaCiclo = (EditText) findViewById(R.id.editTextCiclo);
        cajaDespacho = (EditText) findViewById(R.id.editTextCurso);

        //Instaciamos un objeto de tipo SQLiteOpenHelper
        final BaseDatosHelper miBBDDHelper = new BaseDatosHelper(this, BaseDatosHelper.DATABASE_NAME, null, BaseDatosHelper.DATABASE_VERSION);


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

                // Obtenemos una referencia a la BBDD de lectura y escritura
                SQLiteDatabase db = miBBDDHelper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();


               /* values.put(EstructuraBBDD.NOMBRE_PROFESOR, cajaNombre.getText().toString());
                values.put(EstructuraBBDD.EDAD_PROFESOR, Integer.valueOf(cajaEdad.getText().toString()));
                values.put(EstructuraBBDD.CICLO_PROFESOR, cajaCiclo.getText().toString());
                values.put(EstructuraBBDD.DESPACHO_PROFESOR, cajaDespacho.getText().toString());*/

                values.put("nombre", cajaNombre.getText().toString());
                values.put("edad", Integer.valueOf(cajaEdad.getText().toString()));
                values.put("ciclo", cajaCiclo.getText().toString());
                values.put("despacho", cajaDespacho.getText().toString());

                printContentValues(values);

                //Toast.makeText(getApplicationContext(), values.get("nombre").toString(), Toast.LENGTH_LONG).show();

                //Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert("profesores", null, values);

                db.close();

                //db.execSQL("insert into profesores (nombre, edad, ciclo, despacho) values ( "+ cajaNombre.getText().toString()+", "+Integer.valueOf(cajaEdad.getText().toString())+", "+cajaCiclo.getText().toString()+", "+cajaDespacho.getText().toString()+" )");

               //Toast.makeText(getApplicationContext(), "Se guardó el registro con id: "+newRowId, Toast.LENGTH_LONG).show();


                Toast.makeText(getApplicationContext(), "Nombre: "+cajaNombre.getText().toString()+"Edad:" +Integer.valueOf(cajaEdad.getText().toString())+"Ciclo:"+cajaCiclo.getText().toString()+"Despacho:"+cajaDespacho.getText().toString(), Toast.LENGTH_LONG).show();

            }
        });


        //Listener del botón borrar registro
        botonBorrarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Listener del botón borrar BBDD
        botonBorrarBBDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

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
}
