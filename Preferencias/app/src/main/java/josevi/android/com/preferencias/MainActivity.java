package josevi.android.com.preferencias;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button btnObtenerPreferencias;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Mostramos el fragmento de preferencias en la actividad mediante el
        //FragmentManager
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content,new SettingsFragment())
                .commit();

        setContentView(R.layout.activity_main);

        btnObtenerPreferencias = (Button)findViewById(R.id.btnObtenerPreferencias);


       btnObtenerPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref =
                        PreferenceManager.getDefaultSharedPreferences(
                                MainActivity.this);

                Log.i("", "Sexo: " + pref.getString("sexo", ""));
                Log.i("", "Nombre: " + pref.getString("nombre", ""));
                Log.i("", "DNI: " + pref.getString("dni", ""));
                Log.i("", "Fecha Nacimiento: " + pref.getString("fecha_nac", ""));
            }
        });

    }
}


