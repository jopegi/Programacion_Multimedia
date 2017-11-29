package josevi.android.com.sharedpreferences_3a;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class VerPreferenciasActivity extends AppCompatActivity {

    //Objectos de XMl.
    Button volver;
    TextView nom;
    TextView fNa;
    TextView dni;
    TextView sexo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias_ver);

        //Iniciamos Objetos del XML.
        volver = (Button) findViewById(R.id.back);
        nom = (TextView) findViewById(R.id.mostrarNom);
        fNa = (TextView) findViewById(R.id.mostrarFna);
        dni = (TextView) findViewById(R.id.mostrarDni);
        sexo = (TextView) findViewById(R.id.mostrarSexo);

        //Establecemos el Listener para volver.
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Lanzamos metodo recuperar datos de las preferencias.
        recuperaDatos();
    }

    public void recuperaDatos(){

        //Creamos un Objeto con las preferencias establecidas.
        SharedPreferences sp = getSharedPreferences("informacionPersonal", Activity.MODE_PRIVATE);

        //Añadimos a los TextView el valor de las preferencias recogidas.
        nom.setText(sp.getString("nombre","error"));
        fNa.setText(sp.getString("fecha_nacimiento","error"));
        dni.setText(sp.getString("dni","error"));
        sexo.setText(sp.getString("sexo","error"));
    }

}