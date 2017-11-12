package josevi.android.com.preferencias2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtDni;
    private EditText txtFecha;
    private EditText txtSexo;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos una referencia a los controles de la interfaz
        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtDni = (EditText)findViewById(R.id.txtDni);
        txtFecha = (EditText)findViewById(R.id.txtFecha);
        txtSexo = (EditText)findViewById(R.id.txtSexo);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);

        //Implementamos el evento click del botón
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MainActivity.this, PreferenciasActivity.class);

                //Creamos la información a pasar entre actividades
                Bundle b = new Bundle();
                b.putString("Nombre", txtNombre.getText().toString());
                b.putString("DNI", txtDni.getText().toString());
                b.putString("Fecha", txtFecha.getText().toString());
                b.putString("Sexo", txtSexo.getText().toString());

                //Añadimos la información al intent
                intent.putExtras(b);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
    }
}
