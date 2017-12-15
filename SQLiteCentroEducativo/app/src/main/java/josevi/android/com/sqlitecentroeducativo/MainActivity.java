package josevi.android.com.sqlitecentroeducativo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaramos los botones de la interfaz
    private Button botonProfesores,botonAlumnos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instaciamos los botones
        botonProfesores = (Button) findViewById(R.id.btnProfesores);
        botonAlumnos = (Button)findViewById(R.id.btnAlumnos);

        //Eventos de botón
        botonProfesores.setOnClickListener(this);
        botonAlumnos.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnProfesores:
                Intent intentProfesores = new Intent(MainActivity.this,ProfesoresActivity.class);
                startActivity(intentProfesores);
                break;
            case R.id.btnAlumnos:
                Intent intentAlumnos = new Intent(MainActivity.this,AlumnosActivity.class);
                startActivity(intentAlumnos);
                break;
        }

    }
}
