package josevi.android.com.sqlitedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnProfesores, btnAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProfesores = (Button) findViewById(R.id.btnProfesores);
        btnAlumnos = (Button) findViewById(R.id.btnAlumnos);

        //Botón que lleva a ProfesorActivity
        btnProfesores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentAProfesores = new Intent(getApplicationContext(), ProfesorActivity.class);
                startActivity(intentAProfesores);
            }
        });

        //Botón que lleva a AlumnoActivity
        btnAlumnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentAAlumos = new Intent(getApplicationContext(), AlumnoActivity.class);
                startActivity(intentAAlumos);
            }
        });
    }
}
