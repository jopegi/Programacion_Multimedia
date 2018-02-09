package josevi.android.com.proyectosplashimage;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;


//Actividad que se ocupará de la gestión del Splash Screen
public class SplashActivity extends Activity {


    //ProgressDialog  progress;

    // Constante que almacenará el tiempo de duración de la Splash Screen.
    //En este caso, 3 segundos
    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuramos la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Escondemos el actionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Cargamos el activity_splash.xml
        setContentView(R.layout.activity_splash);

        //Creamos un objeto de tipo TimerTask o, lo que es lo mismo, una tarea a realizar
        TimerTask task = new TimerTask() {
            //Sobreescribimos el método run() del TimerTask y definimos en el
            //el código que deseamos que se ejecute
            @Override
            public void run() {

                //Lanzamos la siguiente actividad mediante un Intent
                //Con el método setClass() de la clase Intent definimos
                //el Contexto desde el que partimos y la clase hasta la
                //que nos "desplazaremos"
                Intent mainIntent = new Intent().setClass(
                SplashActivity.this, RegistroActivity.class);
                //Lanzamos el Intent
                startActivity(mainIntent);

                // Destruimos la actividad para que el usuario no pueda volver
                // a esta Activity pulsando el botón "atrás"
                finish();
            }
        };

        // Creamos un temporizador encargado de controlar la duración de la actividad
        Timer timer = new Timer();
        //Con la función schedule() de la clase Timer, programamos un temporizador
        //indicando la tarea a realizar (el task en este caso) y el tiempo que debe
        //de esperar para comenzar su ejecución (3 segundos, en nuestro caso)
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

}