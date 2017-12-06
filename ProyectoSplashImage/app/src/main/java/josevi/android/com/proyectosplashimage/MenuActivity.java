package josevi.android.com.proyectosplashimage;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//Nótese que se ha tenido que implementar la interfaz OnFragmentInteractionListener y su correspondiente
//método abstracto onFragmentInteraction()
public class MenuActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
