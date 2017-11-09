package josevi.android.com.preferencias;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Mostramos el fragmento de preferencias en la actividad
        getFragmentManager().beginTransaction().replace(android.R.id.content,new SettingsFragment()).commit();

    }
}


