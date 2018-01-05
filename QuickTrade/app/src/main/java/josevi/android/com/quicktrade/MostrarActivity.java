package josevi.android.com.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MostrarActivity extends AppCompatActivity {

    private Button botonVolver;
    private ListView listaUsuarios;

    //Objeto que hará referencia a la BBDD FireBase
    DatabaseReference referenciaBaseDatos;

    private ArrayAdapter<String> adaptador;

    private ArrayList<String> listadoNicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        botonVolver = (Button) findViewById(R.id.btnVolver);
        listaUsuarios = (ListView) findViewById(R.id.listaUsuarios);

        //PASO1-FIREBASE. Obtenemos la referencia con la nuestra Base de Datos FireBase. Indicamos el nodo que
        //nos interesa referenciar, en este caso, usuarios.
        referenciaBaseDatos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        //En la Query nos quedamos con todos los usuarios que contengan un atributo llamado "nick"
        Query q = referenciaBaseDatos.orderByChild(getString(R.string.campo_nick));

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        listadoNicks = new ArrayList<String>();

                        for (DataSnapshot i: dataSnapshot.getChildren()){

                            Usuario usu = i.getValue(Usuario.class);
                            String nickUsuario = usu.getNick();
                            listadoNicks.add(nickUsuario);
                        }

                        adaptador = new ArrayAdapter<String>(MostrarActivity.this, android.R.layout.simple_list_item_1, listadoNicks);

                        listaUsuarios.setAdapter(adaptador);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentVolver = new Intent(MostrarActivity.this, MainActivity.class);
                startActivity(intentVolver);
            }
        });

    }
}
