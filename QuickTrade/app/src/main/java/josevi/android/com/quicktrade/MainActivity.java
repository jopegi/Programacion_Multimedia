package josevi.android.com.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botonInsertar, botonMostrar, botonModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos los botones de la vista
        botonInsertar = (Button) findViewById(R.id.btnInsertar);
        botonModificar = (Button) findViewById(R.id.btnModificar);
        botonMostrar = (Button) findViewById(R.id.btnMostrar);

        //Evento del botón Insertar
        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentInsertar = new Intent(MainActivity.this, InsertarActivity.class);
                startActivity(intentInsertar);

            }
        });

        //Evento del botón Mostrar
        botonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentMostrar = new Intent(MainActivity.this, MostrarActivity.class);
                startActivity(intentMostrar);

            }
        });

        //Evento del botón Modificar
        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentModificar = new Intent(MainActivity.this, ModificarActivity.class);
                startActivity(intentModificar);

            }
        });
    }
}
