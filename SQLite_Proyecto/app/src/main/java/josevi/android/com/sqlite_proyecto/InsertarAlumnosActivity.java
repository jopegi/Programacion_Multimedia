package josevi.android.com.sqlite_proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InsertarAlumnosActivity extends AppCompatActivity {

    private Button botonVolver,botonInsertar,botonBorrarPorId,botonBorrarBBDD,BotonIrAFiltros;
    private TextView cajaNombre, cajaEdad, cajaCurso, cajaCiclo,cajaNota,cajaId;

    private DataBaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_alumnos);

        botonVolver = (Button) findViewById(R.id.btnVolver);
        botonInsertar = (Button) findViewById(R.id.btnInsertarAlumno);
        botonBorrarPorId = (Button) findViewById(R.id.btnBorrarAlumnoId);
        botonBorrarBBDD = (Button) findViewById(R.id.btnBorrarBBDD);
        BotonIrAFiltros = (Button) findViewById(R.id.btnFiltrar);

        cajaNombre = (TextView) findViewById(R.id.cajaNombreAlumno);
        cajaEdad = (TextView) findViewById(R.id.cajaEdadAlumno);
        cajaCurso = (TextView) findViewById(R.id.cajaCursoAlumno);
        cajaCiclo = (TextView) findViewById(R.id.cajaCicloAlumno);
        cajaNota = (TextView) findViewById(R.id.cajaNotaAlumno);
        cajaId = (TextView) findViewById(R.id.cajaIdAlumno);



        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentVolver = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intentVolver);
            }
        });

        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               manager.insertarAlumno(cajaNombre.getText().toString(),Integer.valueOf(cajaEdad.getText().toString()),
                       cajaCurso.getText().toString(),cajaCiclo.getText().toString(),Double.valueOf(cajaNota.getText().toString()));
            }
        });

        botonBorrarPorId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager.borrarAlumnoPorID(cajaId.getText().toString());

            }
        });


        BotonIrAFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentIrAFiltros = new Intent(getApplicationContext(),FiltrosActivity.class);
                startActivity(intentIrAFiltros);

            }
        });

        botonBorrarBBDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager.borrarBaseDatos();

            }
        });


    }
}
