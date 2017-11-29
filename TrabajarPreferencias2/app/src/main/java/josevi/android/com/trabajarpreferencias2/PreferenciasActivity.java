package josevi.android.com.trabajarpreferencias2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PreferenciasActivity extends AppCompatActivity {

    TextView viewNombre, viewEdad, viewDni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        viewNombre = (TextView) findViewById(R.id.textViewNom);
        viewEdad = (TextView) findViewById(R.id.textViewEdad);
        viewDni = (TextView) findViewById(R.id.textViewDni);

        recuperaDatos();

    }

    public void recuperaDatos(){

        //Creamos un Objeto con las preferencias establecidas.
        SharedPreferences sp = getSharedPreferences("Mis_Preferencias", MODE_PRIVATE);

        //Añadimos a los TextView el valor de las preferencias recogidas.
        viewNombre.setText(sp.getString("nombre",""));
        viewEdad.setText(String.valueOf(sp.getInt("edad",0)));
        viewDni.setText(sp.getString("dni",""));
    }
}
