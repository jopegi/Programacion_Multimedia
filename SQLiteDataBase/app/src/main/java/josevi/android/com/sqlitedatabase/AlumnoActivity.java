package josevi.android.com.sqlitedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AlumnoActivity extends AppCompatActivity {

    private Button botonInsertar, botonBorrarRegistro, botonBorrarBBDD, botonVolver;
    private EditText cajaNombre, cajaEdad, cajaCiclo, cajaCurso, cajaNotaMedia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        botonInsertar = (Button) findViewById(R.id.btnInsertar);
        botonBorrarRegistro = (Button) findViewById(R.id.btnBorrarId);
        botonBorrarBBDD = (Button) findViewById(R.id.btnBorrarBBDD);
        botonVolver = (Button) findViewById(R.id.btnVolver);

        //Listener del botón volver
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentoVolver = new Intent(AlumnoActivity.this, MainActivity.class);

                startActivity(intentoVolver);
            }
        });

        //Listener del botón insertar
        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Listener del botón borrar registro
        botonBorrarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Listener del botón borrar BBDD
        botonBorrarBBDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
