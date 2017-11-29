package josevi.android.com.trabajarpreferencias;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText cajaNombre,cajaDni,cajaEdad;
    Button btnGuardar,btnMostrar;
    RadioButton rHombre,rMujer;
    TextView cajaNom,cajaDn,cajaEd,cajaSex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cajaNombre = (EditText) findViewById(R.id.editTextNombre);
        cajaDni = (EditText) findViewById(R.id.editTextDni);
        cajaEdad = (EditText) findViewById(R.id.editTextEdad);
        rHombre = (RadioButton) findViewById(R.id.radioButtonHombre);
        rMujer = (RadioButton) findViewById(R.id.radioButtonMujer);
        cajaNom = (TextView) findViewById(R.id.textViewNom);
        cajaDn = (TextView) findViewById(R.id.textViewDn);
        cajaEd = (TextView) findViewById(R.id.textViewEd);
        cajaSex = (TextView) findViewById(R.id.textViewSex);
        btnGuardar = (Button) findViewById(R.id.buttonGuardar);
        btnMostrar = (Button) findViewById(R.id.buttonMostrar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cajaNombre.getText().toString().isEmpty() || cajaDni.getText().toString().isEmpty() || cajaEdad.getText().toString().isEmpty() ||(!rHombre.isChecked() && !rMujer.isChecked())) {

                    Toast.makeText(getApplicationContext(), "Rellena Todos los Campos", Toast.LENGTH_SHORT).show();

                }else {
                    SharedPreferences sp = getSharedPreferences("informacionPersonal", Activity.MODE_PRIVATE);

                    //Hacemos editable el objeto SharedPreferences
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("nombre", cajaNombre.getText().toString());
                    editor.putString("dni", cajaDni.getText().toString());
                    editor.putInt("edad", Integer.parseInt(cajaEdad.getText().toString()));


                    if (rHombre.isChecked()) {
                        editor.putString("sexo", "Masculino");
                    }
                    if (rMujer.isChecked()) {
                        editor.putString("sexo", "Femenino");
                    }

                    editor.commit();

                    Toast.makeText(getApplicationContext(), "Informacion preferente guardada!.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences("informacionPersonal", Activity.MODE_PRIVATE);

                //Añadimos a los TextView el valor de las preferencias recogidas.
                cajaNom.setText(sp.getString("nombre",""));
                cajaDn.setText(sp.getString("dni",""));
                cajaEd.setText(String.valueOf(sp.getInt("edad",1)));
                cajaSex.setText(sp.getString("sexo",""));
            }
        });

    }

}
