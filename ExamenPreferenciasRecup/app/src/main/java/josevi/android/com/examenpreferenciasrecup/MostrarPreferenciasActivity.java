package josevi.android.com.examenpreferenciasrecup;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MostrarPreferenciasActivity extends AppCompatActivity {

    TextView cajaDia, cajaMes, cajaDinero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_preferencias);

        cajaDia = (TextView) findViewById(R.id.tViewDia);
        cajaMes = (TextView) findViewById(R.id.tViewMes);
        cajaDinero = (TextView) findViewById(R.id.tViewDinero);


        SharedPreferences sp = getSharedPreferences("Mis_Preferencias", MODE_PRIVATE);

        //Añadimos a los TextView el valor de las preferencias recogidas.
        cajaDia.setText(sp.getString("dia",""));
        cajaMes.setText(sp.getString("mes",""));
        cajaDinero.setText(sp.getString("dinero",""));

    }
}
