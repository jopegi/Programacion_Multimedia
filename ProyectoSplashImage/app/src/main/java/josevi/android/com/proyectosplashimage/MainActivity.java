package josevi.android.com.proyectosplashimage;

import android.app.Activity;
import android.os.Bundle;

//Clase principal de la aplicación
public class MainActivity extends Activity {

    //OnCreate(): Primer método que se llama cuando se crea una actividad
    //Encargado de definir el fichero de recursos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}
