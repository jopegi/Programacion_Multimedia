package josevi.android.com.sqlite_prueba1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botonProfesores, botonAlumnos, botonConsultas, botonAsignaturas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonProfesores = (Button) findViewById(R.id.btnProfesores);
        botonAlumnos = (Button) findViewById(R.id.btnAlumnos);
        botonConsultas = (Button) findViewById(R.id.btnConsultas);
        botonAsignaturas = (Button) findViewById(R.id.btnAsignaturas);

        botonProfesores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentoProfesores = new Intent(MainActivity.this,GestionProfesoresActivity.class);
                startActivity(intentoProfesores);

            }
        });

        botonAlumnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentoAlumnos = new Intent(MainActivity.this,GestionAlumnosActivity.class);
                startActivity(intentoAlumnos);

            }
        });

        botonConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentoConsultas = new Intent(MainActivity.this,ConsultasActivity.class);
                startActivity(intentoConsultas);

            }
        });

        botonAsignaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentoAsignaturas = new Intent(MainActivity.this,GestionAsignaturasActivity.class);
                startActivity(intentoAsignaturas);

            }
        });

    }


}
