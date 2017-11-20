package josevi.android.com.preferenciascombustible;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Valor que se pasará como parámetro al método startActivityForResult encargado de
    //lanzar el intent definido en el Listener del botón showData
    final static int subActivity = 2;
    //Referenciamos los objetos del layout
    Button mostrar;
    Button guardar;
    EditText cajaDia;
    EditText cajaMes;
    EditText cajaDinero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializaciones.
        mostrar = (Button) findViewById(R.id.buttonMostrar);
        guardar = (Button) findViewById(R.id.buttonGuardar);
        cajaDia = (EditText) findViewById(R.id.editTextDia);
        cajaMes = (EditText) findViewById(R.id.editTextMes);
        cajaDinero = (EditText) findViewById(R.id.editTextDinero);

        //Listener del botón Guardar.
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validamos que todos los campos estén rellenos
                if(cajaDia.getText().toString().isEmpty() || cajaMes.getText().toString().isEmpty() || cajaDinero.getText().toString().isEmpty()){
                    //Si algún campo de texto queda sin rellenar salta un Toast con un mensaje de aviso
                    Toast.makeText(getApplicationContext(),"Rellena Todos los Campos", Toast.LENGTH_SHORT).show();
                }else {
                    //Creamos un objeto de tipo SharedPreferences engargado de almacenar todos los datos recogidos
                    // por los campos de los EditText. Nuestro archivo xml SharedPrefernces se llamará "informacion"
                    SharedPreferences sp = getSharedPreferences("preferencias", Activity.MODE_PRIVATE);

                    //Hacemos editable el objeto SharedPreferences
                    SharedPreferences.Editor editor = sp.edit();

                    //Mediante el editor del SharedPreferences guardamos los valores que nos interesa
                    //en forma de clave-valor.
                    //CLAVE - VALOR
                    //nombre - El que recogido por la caja de texto txtNom
                    //fecha_nacimiento - El que recogido por la caja de texto fNa
                    //dni - El que recogido por la caja de texto dni
                    editor.putString("dia", cajaDia.getText().toString());
                    editor.putString("mes", cajaMes.getText().toString());
                    editor.putString("dinero", cajaDinero.getText().toString());

                    //Hacemos commit para completar el almacenaje de la información correctamente.
                    editor.commit();

                    //Mostramos un Toast informativo de que las preferencias se han guardado correctamente.
                    Toast.makeText(getApplicationContext(),"Informacion guardada!.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Listener del botón Mostrar engardado de mostrar las preferencias guardadas en el objeto SharedPreferences.
        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Con el Intent se pasa a la actividad VerPreferenciasActivity
                Intent i = new Intent(MainActivity.this, PreferenciasActivity.class);
                //Con el método startActivityForResult lanzamos una nueva actividad a la espera de que nos devuelva un resultado
                startActivityForResult(i,subActivity);
            }
        });

    }
}
