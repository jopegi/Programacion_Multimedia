package josevi.android.com.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InsertarActivity extends AppCompatActivity {

    private TextView cajaNick, cajaNombre, cajaApellidos, cajaEmail, cajaDireccion;
    private Button botonVolver, botonInsertar;

    //Objeto que hará referencia a la BBDD FireBase
    DatabaseReference referenciaBaseDatos;

    ArrayList<String> listadoNicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        //PASO1-FIREBASE. Obtenemos la referencia con la nuestra Base de Datos FireBase. Indicamos el nodo que
        //nos interesa referenciar, en este caso, usuarios.
        referenciaBaseDatos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        //Instanciamos los views de la vista
        cajaNick = (TextView) findViewById(R.id.cajaNick);
        cajaNombre = (TextView) findViewById(R.id.cajaNombre);
        cajaApellidos = (TextView) findViewById(R.id.cajaApellidos);
        cajaEmail = (TextView) findViewById(R.id.cajaEmail);
        cajaDireccion = (TextView) findViewById(R.id.cajaDireccion);
        botonVolver = (Button) findViewById(R.id.btnVolver);
        botonInsertar = (Button) findViewById(R.id.btnInsertar);

        //****************ESCUCHARÁ CUALQUIER CAMBIO EN EL NODO usuarios DE LA BBDD***********
        referenciaBaseDatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ArrayAdapter<String> adaptor;
                listadoNicks = new ArrayList<String>();

                /*Query q = referenciaBaseDatos.orderByChild("nick").equalTo("Loco3");
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int cont = 0;
                        for (DataSnapshot i: dataSnapshot.getChildren()){

                            cont++;
                            Usuario usu = dataSnapshot.getValue(Usuario.class);
                            Toast.makeText(InsertarActivity.this, "Nick size: "+cont, Toast.LENGTH_SHORT).show();
                            Toast.makeText(InsertarActivity.this, "Nick values: "+usu, Toast.LENGTH_SHORT).show();
                           // String nickUsuario = usu.getNick();
                            //Toast.makeText(InsertarActivity.this, "Nick size: "+usu, Toast.LENGTH_SHORT).show();
                            //listadoNicks.add(nickUsuario);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

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

        //Evento del botón Volver
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentVolver = new Intent(InsertarActivity.this, MainActivity.class);
                startActivity(intentVolver);

            }
        });

        //Evento del botón Insertar
        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nick = cajaNick.getText().toString();
                String nombre = cajaNombre.getText().toString();
                String apellidos = cajaApellidos.getText().toString();
                String email = cajaEmail.getText().toString();
                String direccion = cajaDireccion.getText().toString();

                //Si la caja para el nick está vacía...
                if (TextUtils.isEmpty(nick) == true) {
                    Toast.makeText(InsertarActivity.this, "Por favor, rellene el campo nick", Toast.LENGTH_SHORT).show();
                    //Si la caja para el nombre está vacía...
                } else if (TextUtils.isEmpty(nombre) == true) {
                    Toast.makeText(InsertarActivity.this, "Por favor, rellene el campo nombre", Toast.LENGTH_SHORT).show();
                    //Si la caja para los apellidos está vacía...
                } else if (TextUtils.isEmpty(apellidos) == true) {
                    Toast.makeText(InsertarActivity.this, "Por favor, rellene el campo apellidos", Toast.LENGTH_SHORT).show();
                    //Si la caja para el email está vacía...
                } else if (TextUtils.isEmpty(email) == true) {
                    Toast.makeText(InsertarActivity.this, "Por favor, rellene el campo email", Toast.LENGTH_SHORT).show();
                    //Si la caja para la dirección está vacía...
                } else if (TextUtils.isEmpty(direccion) == true) {
                    Toast.makeText(InsertarActivity.this, "Por favor, rellene el campo direccion", Toast.LENGTH_SHORT).show();
                    //Si las cajas para los atributos se han rellenado correctamente...
                    // Comprobamos si el nick a insertar existe previamente en Firebase
                } else {

                    //Si no existe...
                    if (!existeNick(nick)) {

                        //Creamos un nuevo usuario
                        Usuario usu = new Usuario(nick, nombre, apellidos, email, direccion);

                        //PASO2-FIREBASE. Creamos un nuevo nodo usuario (mediante una clave)
                        String claveNodoUsuarios = referenciaBaseDatos.push().getKey();

                        //PASO3-FIREBASE. Añadimos el nuevo usuario dentro del correspondiente nodo
                        referenciaBaseDatos.child(claveNodoUsuarios).setValue(usu);

                        //Reseteamos las cajas de texto
                        cajaNick.setText("");
                        cajaNombre.setText("");
                        cajaApellidos.setText("");
                        cajaEmail.setText("");
                        cajaDireccion.setText("");


                        //Si existe...
                    } else {

                        Toast.makeText(InsertarActivity.this, "El nick que ha elegido ya existe!! Por favor, elija otro nick", Toast.LENGTH_SHORT).show();
                    }
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