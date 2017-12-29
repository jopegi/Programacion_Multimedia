package josevi.android.com.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModificarActivity extends AppCompatActivity {

    private Button botonVolver;
    private Button botonModificar;

    private EditText cajaNick;
    private EditText cajaNombre;
    private EditText cajaApellidos;
    private EditText cajaEmail;
    private EditText cajaDireccion;

    private ArrayList<String> listadoNicks;

    //Objeto que hará referencia a la BBDD FireBase
    DatabaseReference referenciaBaseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        botonVolver = (Button) findViewById(R.id.btnVolver);
        botonModificar = (Button) findViewById(R.id.btnModificar);

        cajaNick = (EditText) findViewById(R.id.editTextNick);

        //Centramos el foco sobre la caja de inserción del nick
        cajaNick.requestFocus();

        cajaNombre = (EditText) findViewById(R.id.editTextNombre);
        cajaApellidos = (EditText) findViewById(R.id.editTextApellidos);
        cajaEmail = (EditText) findViewById(R.id.editTextEmail);
        cajaDireccion = (EditText) findViewById(R.id.editTextDireccion);

        //PASO1-FIREBASE. Obtenemos la referencia con la nuestra Base de Datos FireBase. Indicamos el nodo que
        //nos interesa referenciar, en este caso, usuarios.
        referenciaBaseDatos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        //****************ESCUCHARÁ CUALQUIER CAMBIO EN EL NODO usuarios DE LA BBDD***********
        referenciaBaseDatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listadoNicks = new ArrayList<String>();

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    Usuario usu = i.getValue(Usuario.class);
                    String nickUsuario = usu.getNick();
                    listadoNicks.add(nickUsuario);
                    //Toast.makeText(InsertarActivity.this, "Nick size: "+usu.getNombre(), Toast.LENGTH_SHORT).show();

                }
                //Toast.makeText(InsertarActivity.this, "Nick size: "+cont, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //**********************************************************************

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentVolver = new Intent(ModificarActivity.this, MainActivity.class);
                startActivity(intentVolver);
            }
        });

        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nick = cajaNick.getText().toString();

                //Si la caja que contiene el nick no está vacía...
                if (!TextUtils.isEmpty(nick) && existeNick(nick) == true){

                    Query q = referenciaBaseDatos.orderByChild(getString(R.string.campo_nick)).equalTo(nick);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String nombre = cajaNombre.getText().toString();
                            String apellidos = cajaApellidos.getText().toString();
                            String email = cajaEmail.getText().toString();
                            String direccion = cajaDireccion.getText().toString();

                            for(DataSnapshot i : dataSnapshot.getChildren()){

                                //el método getKey() devuelve la clave del nodo que contiene el nick que el
                                //usuario indica en la interfaz gráfica
                                String claveNodo = i.getKey();

                                if (!TextUtils.isEmpty(nombre)){
                                    referenciaBaseDatos.child(claveNodo).child(getString(R.string.campo_nombre)).setValue(cajaNombre.getText().toString());
                                }

                                if (!TextUtils.isEmpty(apellidos)){
                                    referenciaBaseDatos.child(claveNodo).child(getString(R.string.campo_apellidos)).setValue(cajaApellidos.getText().toString());
                                }

                                if (!TextUtils.isEmpty(email)){
                                    referenciaBaseDatos.child(claveNodo).child(getString(R.string.campo_email)).setValue(cajaEmail.getText().toString());
                                }

                                if (!TextUtils.isEmpty(direccion)){
                                    referenciaBaseDatos.child(claveNodo).child(getString(R.string.campo_direccion)).setValue(cajaDireccion.getText().toString());
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(ModificarActivity.this, "Información de usuario modificada con éxito!!", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(ModificarActivity.this, "Debes indicar un nick existente!!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    //Método para comparar el nuevo nick con los ya existentes en FireBase
    public boolean existeNick (String nuevoNick){

        //Al nuevo nick le eliminamos los posibles espacios en blanco
        String nickTrim = nuevoNick.trim();

        boolean rtn = false;

        //Vamos recorriendo el listado de nicks existentes y comparándolos con
        //el nuevo nick que se pretende crear
        for (int j = 0; j<listadoNicks.size(); j++){

            //Se comparan las cadenas de texto ignorando las mayúsculas y minúsculas
            if(listadoNicks.get(j).equalsIgnoreCase(nickTrim)){
                rtn = true;

            }
        }
        return rtn;
    }
}
