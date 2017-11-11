package josevi.android.com.proyectosplashimage;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;


//Actividad que se ocupará de la gestión del Splash Screen
public class SplashActivity extends Activity {


    ProgressDialog  progress;
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

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Lanzamos la siguiente actividad
                Intent mainIntent = new Intent().setClass(
                        SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);

                // Destruimos la actividad para que el usuario no pueda volver
                // a esta Activity pulsando el botón "atrás"
                finish();
            }
        };

        // Creamos un temporizador encargado de controlar la duración de la actividad
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

}