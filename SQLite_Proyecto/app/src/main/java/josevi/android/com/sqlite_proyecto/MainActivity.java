package josevi.android.com.sqlite_proyecto;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botonProfesores;
    private Button botonAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonProfesores = (Button) findViewById(R.id.btnProfesores);
        botonAlumnos = (Button) findViewById(R.id.btnAlumnos);


        //En este momento creamos nuestra Base de Datos y las tablas
        //Ver constructor de DataBaseManager
        DataBaseManager manager = new DataBaseManager(this);

        botonProfesores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentProfesores = new Intent(getApplicationContext(), InsertarProfesoresActivity.class);
                startActivity(intentProfesores);

            }
        });


        botonAlumnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentAlumnos = new Intent(getApplicationContext(), InsertarAlumnosActivity.class);
                startActivity(intentAlumnos);

            }
        });

    }
}
