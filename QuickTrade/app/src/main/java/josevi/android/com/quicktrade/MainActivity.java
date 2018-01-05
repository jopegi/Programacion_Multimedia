package josevi.android.com.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button botonMostrar, botonModificar, botonProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos los botones de la vista
        botonModificar = (Button) findViewById(R.id.btnModificar);
        botonMostrar = (Button) findViewById(R.id.btnMostrar);
        botonProductos = (Button) findViewById(R.id.btnProductos);

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

                //Obtenemos una referencia del intent que proviene del RegistarActivity
                Intent intentMain = getIntent();

                //Obtenemos el valor del Uid enviado mediante el mencionado intent
                String UserUid = intentMain.getStringExtra("Uid");

                Intent intentModificar = new Intent(MainActivity.this, ModificarActivity.class);

                //Volvemos a pasar el valor del Uid a la nueva actividad del intent
                intentModificar.putExtra("Uid", UserUid);

                startActivity(intentModificar);

            }
        });

        //Evento del botón Productos
        botonProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Obtenemos una referencia del intent que proviene del RegistarActivity
                Intent intentoLoginOk = getIntent();

                //Obtenemos el valor del Uid enviado mediante el mencionado intent
                String UserUid = intentoLoginOk.getStringExtra("Uid");

               //Creamos un nuevo intent para pasar a la actividad GestorProductosActivity
                Intent intentProductos = new Intent(MainActivity.this, GestorProductosActivity.class);

                //Volvemos a pasar el valor del Uid a la nueva actividad del intent
                intentProductos.putExtra("Uid", UserUid);

                startActivity(intentProductos);

            }
        });


    }
}
