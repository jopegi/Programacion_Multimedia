package josevi.android.com.examenpreferenciasrecup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnIrAPreferencias, btnMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIrAPreferencias = (Button) findViewById(R.id.buttonPreferencias);
        btnMostrar = (Button) findViewById(R.id.buttonMostrar);

        btnIrAPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(MainActivity.this, PreferenciasActivity.class);
                startActivity(intent1);
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(), MostrarPreferenciasActivity.class);
                startActivity(intent2);

            }
        });


    }
}
