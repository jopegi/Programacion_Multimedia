package josevi.android.com.apppastillas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.R.attr.button;
import static josevi.android.com.apppastillas.R.id.btnGuardar;
import static josevi.android.com.apppastillas.R.id.btnMostrar;

public class MainActivity extends AppCompatActivity {

    //public static final String PREFS = "My Preferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*final Button botonGuardar = (Button) findViewById(btnGuardar);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SharedPreferences mySharedPreferences = getSharedPreferences(PREFS, Activity.MODE_PRIVATE);

                SharedPreferences.Editor editor = mySharedPreferences.edit();

                editor.putString("TextEntrada", "mensaje");
                editor.putFloat("LastFloat", (float) 1.5);

                editor.commit();
            }
        });

        final Button botonMostrar = (Button) findViewById(btnMostrar);

        botonMostrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SharedPreferences mySharedPreferences = getSharedPreferences(PREFS, Activity.MODE_PRIVATE);

                String dia = mySharedPreferences.getString("TextEntrada", "mensaje");
                float hora = mySharedPreferences.getFloat("LastFloat", (float) 1.5);

                Toast.makeText(MainActivity.this, "He recuperado "+dia+" y "+hora, Toast.LENGTH_LONG);
            }
        });*/


    }

    public void guardarDatos(View view){
        Intent i = new Intent(this, SecondActivity.class);

        startActivity(i);
    }

    public void salirApp(View view){

        finish();
    }
}
