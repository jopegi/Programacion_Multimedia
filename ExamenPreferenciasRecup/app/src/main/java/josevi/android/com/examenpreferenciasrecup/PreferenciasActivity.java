package josevi.android.com.examenpreferenciasrecup;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PreferenciasActivity extends AppCompatActivity {

    EditText cajaDia, cajaMes, cajaDinero;

    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        cajaDia = (EditText) findViewById(R.id.editTextDia);
        cajaMes = (EditText) findViewById(R.id.editTextMes);
        cajaDinero = (EditText) findViewById(R.id.editTextDinero);

        btnGuardar = (Button) findViewById(R.id.buttonGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences mySharedPreferences = getSharedPreferences("Mis_Preferencias", Activity.MODE_PRIVATE);

                SharedPreferences.Editor editor = mySharedPreferences.edit();

                editor.putString("dia",cajaDia.getText().toString());
                editor.putString("mes",cajaMes.getText().toString());
                editor.putString("dinero",cajaDinero.getText().toString());

                editor.commit();

                Toast.makeText(getApplicationContext(), "Preferencias guardadas!", Toast.LENGTH_SHORT).show();

                finish();

            }
        });

    }
}
