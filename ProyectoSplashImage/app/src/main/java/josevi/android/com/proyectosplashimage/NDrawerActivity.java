package josevi.android.com.proyectosplashimage;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, WellcomeFragment.ComunicadorFragmentDinamic,
        MenuFragment.ComunicadorFragmentEstatic,JuegoFragment.ComunicadorFragmentJuego,PerfilFragment.ComunicadorFragmentPerfil{

    Jugador jugador1;
    private String userId;
    private String nickUser,apellidosUser,emailUser;
    String nombreUser;

    //Objeto que hará referencia a la BBDD FireBase
    DatabaseReference referenciaBaseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndrawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        //Hacemos accesible el intento que envía la actividad RegistroActivity;
        Intent intentDrawer = getIntent();
        //Recogemos el uid enviado desde la actividad anterior
        userId = intentDrawer.getStringExtra("Uid");


        referenciaBaseDatos = FirebaseDatabase.getInstance().getReference("usuarios");

        Query q = referenciaBaseDatos.orderByChild("uid_key").equalTo(userId);

        q.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot i: dataSnapshot.getChildren()){
                    Usuario usu = i.getValue(Usuario.class);
                    nombreUser = usu.getNombre();
                }
                Toast.makeText(getApplicationContext(), "Usuario logeado: " + nombreUser, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Instanciamos el fragment dinámico WellcomeFragment en la actividad y lo añadimos a la
        //misma, en el FrameLayout cuyo id se denomina contenidoDinamico. Podíamos haber instaciado
        //el Fragment directamente mediante su constructor, pero lo hemos hecho mediante el método
        //newInstance(param1) para poder pasarle un texto que se muestre al inicio de la Actividad.
        WellcomeFragment fragmentDinamico = WellcomeFragment.newInstance ("Bienvenido a la app!!"+nombreUser);
        //Toast.makeText(this, ""+fragmentDinamico.getArguments().getString("titol"), Toast.LENGTH_SHORT).show();
        //Creamos un manajador de Fragments
        FragmentManager fm = getSupportFragmentManager();
        //Iniciamos una transacción de Fragments
        FragmentTransaction ft = fm.beginTransaction();
        //Añadimos al view FrameLayout (android:id="@+id/contenidoDinamico") definido en la interfaz
        //de MenuActivity el Fragment instanciado anteriormente
        ft.add(R.id.contenidoDinamico, fragmentDinamico);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ndrawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) { //acceso a la cámara

            AlertDialog.Builder ventanaCamara = new AlertDialog.Builder(this);
            ventanaCamara.setMessage("Aquí se implementará el funcionamiento de la cámara.");
            ventanaCamara.setTitle("Cámara");
            ventanaCamara.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(NDrawerActivity.this, "Gracias por acceder a la cámara!", Toast.LENGTH_SHORT).show();
                }
            });
            ventanaCamara.show();
        } else if (id == R.id.nav_gallery) { //Información sobre la app

            /*
            Toast.makeText(this, "Aplicación MathDice para el entretenimiento " +
                    "desarrollada por Josevi Pérez al final del curso de DAM " +
                    "en el año 2018 para el módulo de Programación Multimedia " +
                    "y Dispositivos Móviles impartido por Manel Viel", Toast.LENGTH_LONG).show();
            */

            AlertDialog.Builder ventanaInfo = new AlertDialog.Builder(this);
            ventanaInfo.setMessage("Aplicación MathDice para el entretenimiento " +
                    "desarrollada por Josevi Pérez al final del curso de DAM " +
                    "en el año 2018 para el módulo de Programación Multimedia.");
            ventanaInfo.setTitle("Información");
            ventanaInfo.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(NDrawerActivity.this, "Gracias por leer la info!", Toast.LENGTH_SHORT).show();
                }
            });

            ventanaInfo.show();

        } else if (id == R.id.nav_slideshow) { //Modificar datos de usuario

            AlertDialog.Builder ventanaModificarUsuario = new AlertDialog.Builder(this);
            ventanaModificarUsuario.setMessage("Aquí se implementará la lógica para" +
                    " acceder a Firebase y modificar los datos de usuarios existentes.");
            ventanaModificarUsuario.setTitle("Modificar datos de usuario");
            ventanaModificarUsuario.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(NDrawerActivity.this, "Gracias por acceder!", Toast.LENGTH_SHORT).show();
                }
            });

            ventanaModificarUsuario.show();


        } else if (id == R.id.nav_manage) { //Acceso al juego

            AlertDialog.Builder ventanaJuego = new AlertDialog.Builder(this);
            ventanaJuego.setMessage("Aquí se implementará la lógica para" +
                    " acceder al juego.");
            ventanaJuego.setTitle("Juego");
            ventanaJuego.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(NDrawerActivity.this, "Gracias por jugar!", Toast.LENGTH_SHORT).show();
                }
            });

            ventanaJuego.show();

        }/*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Método abstracto de la interfaz ComunicadorFragmentPerfil perteneciente al fragment dinámico
    //PerfilFragment
    @Override
    public void recollirDadesPerfil(Jugador j) {

        //Recogemos el objeto jugador que viene de PerfilFragment y hacemos que apunte a la referencia
        //del objeto Jugador que hemos definido al inicio de esta actividad. Así, conseguimos que dicho
        //objeto jugador sea accesible para toda la clase.
        jugador1=j;
        WellcomeFragment fragmentDinamico = WellcomeFragment.newInstance("Bienvenido al juego " + jugador1.getNombre());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contenidoDinamico, fragmentDinamico);
        ft.commit();

    }

    @Override
    public void mostrarWellcomeFragment() {
        WellcomeFragment fragmentDinamico = WellcomeFragment.newInstance ("Bienvenido a la app!!");
        //Toast.makeText(this, ""+fragmentDinamico.getArguments().getString("titol"), Toast.LENGTH_SHORT).show();
        //Creamos un manajador de Fragments
        FragmentManager fm = getSupportFragmentManager();
        //Iniciamos una transacción de Fragments
        FragmentTransaction ft = fm.beginTransaction();
        //Añadimos al view FrameLayout (android:id="@+id/contenidoDinamico") definido en la interfaz
        //de MenuActivity el Fragment instanciado anteriormente
        ft.add(R.id.contenidoDinamico, fragmentDinamico);
        ft.commit();
    }

    //Métodos abstractos de la interfaz ComunicadorFragmentEstatic perteneciente al fragment estático
    //MenuFragment
    @Override
    public void carregarPerfil() {
        PerfilFragment fragmentPerfil = PerfilFragment.newInstance(userId);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contenidoDinamico, fragmentPerfil);
        ft.commit();
    }

    @Override
    public void iniciarJoc() {
        JuegoFragment fragmentJuego = JuegoFragment.newInstance(userId);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contenidoDinamico, fragmentJuego);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
