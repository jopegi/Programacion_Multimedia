package josevi.android.com.proyectosplashimage;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

//Se han tenido que implementar las interfaces ComunicadorFragmentDinamic y ComunicadorFragmentEstatic
//y sus correspondientes métodos abstractos para poder interactuar con los fragmentos en las que se
//definen (dichas interfaces)
public class MenuActivity extends AppCompatActivity implements WellcomeFragment.ComunicadorFragmentDinamic,MenuFragment.ComunicadorFragmentEstatic{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Instanciamos el fragment dinámico WellcomeFragment en la actividad
        //y lo añadimos a la misma en el frameLayout cuyo id se denomina contenidoDinamico
        WellcomeFragment fragmentDinamico = new WellcomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.contenidoDinamico, fragmentDinamico);
        ft.commit();
    }

    //Método abstracto de la interfaz ComunicadorFragmentDinamic perteneciente al fragment dinámico
    //WellcomeFragment
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //Métodos abstractos de la interfaz ComunicadorFragmentEstatic perteneciente al fragment estático
    //MenuFragment
    @Override
    public void carregarPerfil() {
        //Desde esta actividad le pasamos un parémetro de tipo String al fragmento dinámico
        //WellcomeFragment, mediante su método newInstance(). Esto es así, porque el
        //constructor de una fragmento no admite parámetros.
        WellcomeFragment fragmentDinamico = WellcomeFragment.newInstance("Bienvenido a Perfil");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contenidoDinamico, fragmentDinamico);
        ft.commit();
    }

    @Override
    public void iniciarJoc() {
        //Desde esta actividad le pasamos un parémetro de tipo String al fragmento dinámico
        //WellcomeFragment, mediante su método newInstance(). Esto es así, porque el
        //constructor de una fragmento no admite parámetros.
        WellcomeFragment fragmentDinamico = WellcomeFragment.newInstance("Bienvenido a Juego");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contenidoDinamico, fragmentDinamico);
        ft.commit();
    }
    @Override
    public void voreInstruccions(){
        //Desde esta actividad le pasamos un parémetro de tipo String al fragmento dinámico
        //WellcomeFragment, mediante su método newInstance(). Esto es así, porque el
        //constructor de una fragmento no admite parámetros.
        WellcomeFragment fragmentDinamico = WellcomeFragment.newInstance("Bienvenido a Instrucciones");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contenidoDinamico, fragmentDinamico);
        ft.commit();
    }
    @Override
    public void voreInformacio(){
        //Desde esta actividad le pasamos un parémetro de tipo String al fragmento dinámico
        //WellcomeFragment, mediante su método newInstance(). Esto es así, porque el
        //constructor de una fragmento no admite parámetros.
        WellcomeFragment fragmentDinamico = WellcomeFragment.newInstance("Bienvenido a Información");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contenidoDinamico, fragmentDinamico);
        ft.commit();
    }
}
