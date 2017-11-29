package josevi.android.com.trabajarpreferencias2;

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

    EditText cajaNombre;
    EditText cajaEdad;
    EditText cajaDni;

    Button botonGuardar, botonMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cajaNombre = (EditText) findViewById(R.id.editTextNombre);
        cajaEdad = (EditText) findViewById(R.id.editTextEdad);
        cajaDni = (EditText) findViewById(R.id.editTextDni);

        botonGuardar = (Button) findViewById(R.id.btnGuardar);
        botonMostrar = (Button) findViewById(R.id.btnMostrar);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences mySharedPreferences = getSharedPreferences("Mis_Preferencias", Activity.MODE_PRIVATE);

                SharedPreferences.Editor editor = mySharedPreferences.edit();

                editor.putString("nombre",cajaNombre.getText().toString());
                editor.putInt("edad",Integer.valueOf(cajaEdad.getText().toString()));
                editor.putString("dni",cajaDni.getText().toString());

                editor.commit();

                Toast.makeText(getApplicationContext(), "Preferencias guardadas", Toast.LENGTH_SHORT).show();
            }

        });

        botonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),PreferenciasActivity.class);

                startActivity(i);
            }
        });

    }
}
