package josevi.android.com.sharedpreferences_3a;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //Valor que se pasará como parámetro al método startActivityForResult encargado de
    //lanzar el intent definido en el Listener del botón showData
    final static int subActivity = 2;
    //Referenciamos los objetos del layout
    Button save;
    Button showData;
    EditText txtNom;
    EditText fNa;
    EditText dni;
    RadioButton rMas;
    RadioButton rFem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicializaciones.
        save = (Button) findViewById(R.id.btnGuardar);
        showData = (Button) findViewById(R.id.btnMostrar);
        txtNom = (EditText) findViewById(R.id.txtNom);
        fNa = (EditText) findViewById(R.id.txtFna);
        dni = (EditText) findViewById(R.id.txtDni);
        rMas = (RadioButton) findViewById(R.id.radioButton1);
        rFem = (RadioButton) findViewById(R.id.radioButton2);

        //Listener del botón Guardar.
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validamos que todos los campos estén rellenos
                if(txtNom.getText().toString().isEmpty() || fNa.getText().toString().isEmpty() || dni.getText().toString().isEmpty() ||(!rMas.isChecked() && !rFem.isChecked())){
                    //Si algún campo de texto queda sin rellenar salta un Toast con un mensaje de aviso
                    Toast.makeText(getApplicationContext(),"Rellena Todos los Campos", Toast.LENGTH_SHORT).show();
                }else {
                    //Creamos un objeto de tipo SharedPreferences engargado de almacenar todos los datos recogidos
                    // por los campos de los EditText. Nuestro archivo xml SharedPrefernces se llamará "informacionPersonal"
                    SharedPreferences sp = getSharedPreferences("informacionPersonal", Activity.MODE_PRIVATE);

                    //Hacemos editable el objeto SharedPreferences
                    SharedPreferences.Editor editor = sp.edit();

                    //Mediante el editor del SharedPreferences guardamos los valores que nos interesa
                    //en forma de clave-valor.
                    //CLAVE - VALOR
                    //nombre - El que recogido por la caja de texto txtNom
                    //fecha_nacimiento - El que recogido por la caja de texto fNa
                    //dni - El que recogido por la caja de texto dni
                    editor.putString("nombre", txtNom.getText().toString());
                    editor.putString("fecha_nacimiento", fNa.getText().toString());
                    editor.putString("dni", dni.getText().toString());

                    //Toast.makeText(getApplicationContext(),"Nombre: "+txtNom.getText().toString(), Toast.LENGTH_SHORT).show();

                    if (rMas.isChecked()) {
                        editor.putString("sexo", "Masculino");
                    } else {
                        editor.putString("sexo", "Femenino");
                    }
                    //Hacemos commit para completar el almacenaje de la información correctamente.
                    editor.commit();

                    //Mostramos un Toast informativo de que las preferencias se han guardado correctamente.
                    Toast.makeText(getApplicationContext(),"Informacion preferente guardada!.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Listener del botón Mostrar engardado de mostrar las preferencias guardadas en el objeto SharedPreferences.
        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Con el Intent se pasa a la actividad VerPreferenciasActivity
                Intent i = new Intent(MainActivity.this, VerPreferenciasActivity.class);
                //Con el método startActivityForResult lanzamos una nueva actividad a la espera de que nos devuelva un resultado
                //startActivityForResult(i,subActivity);
                startActivity(i);
            }
        });

    }
}
