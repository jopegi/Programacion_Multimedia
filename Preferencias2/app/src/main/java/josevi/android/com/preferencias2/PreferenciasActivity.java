package josevi.android.com.preferencias2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PreferenciasActivity extends AppCompatActivity {

    private TextView txtPreferenciaNombre;
    private TextView txtPreferenciaDni;
    private TextView txtPreferenciaFecha;
    private TextView txtPreferenciaSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        //Localizar los controles
        this.txtPreferenciaNombre = (TextView)findViewById(R.id.txtPreferenciaNombre);
        this.txtPreferenciaDni = (TextView)findViewById(R.id.txtPreferenciaDni);
        this.txtPreferenciaFecha = (TextView)findViewById(R.id.txtPreferenciaFecha);
        this.txtPreferenciaSexo = (TextView)findViewById(R.id.txtPreferenciaSexo);

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        //Construimos el mensaje a mostrar
        txtPreferenciaNombre.setText(bundle.getString("Nombre"));
        txtPreferenciaDni.setText(bundle.getString("DNI"));
        txtPreferenciaFecha.setText(bundle.getString("Fecha"));
        txtPreferenciaSexo.setText(bundle.getString("Sexo"));

    }
}
