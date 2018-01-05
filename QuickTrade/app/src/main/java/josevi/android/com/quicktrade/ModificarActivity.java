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

    //Objeto que hará referencia a la BBDD FireBase
    DatabaseReference referenciaBaseDatos;

    //Objeto que hará referencia a la BBDD FireBase
    DatabaseReference referenciaBaseDatos2;

    private Usuario usu;

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

        Intent intentModificar = getIntent();
        String uid_clave_nodo = intentModificar.getStringExtra("Uid");

        Toast.makeText(ModificarActivity.this, "UID: "+uid_clave_nodo, Toast.LENGTH_SHORT).show();

        //PASO1-FIREBASE. Obtenemos la referencia con la nuestra Base de Datos FireBase. Indicamos el nodo que
        //nos interesa referenciar, en este caso, usuarios.
        referenciaBaseDatos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios)).child(uid_clave_nodo);

        //Query q = referenciaBaseDatos.orderByChild("uid_key").equalTo(uid_clave_nodo);
        //Query q = ref2.child("uid_key").equalTo(uid_clave_nodo);


        //****************ESCUCHARÁ CUALQUIER CAMBIO EN EL NODO usuarios DE LA BBDD***********
        referenciaBaseDatos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Toast.makeText(ModificarActivity.this, "!! "+dataSnapshot.child("nombre").getValue(), Toast.LENGTH_SHORT).show();

                //No hace falta recorrer con un for el DataSnapshot porque referenciaBaseDatos apunta
                //a un nodo en concreto
                usu = dataSnapshot.getValue(Usuario.class);

                cajaNick.setText(usu.getNick());
                cajaNick.setEnabled(false);
                cajaNombre.setText(usu.getNombre());
                cajaApellidos.setText(usu.getApellidos());
                cajaEmail.setText(usu.getEmail());
                cajaDireccion.setText(usu.getDireccion());
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
                if (!TextUtils.isEmpty(nick)){

                            String nombre = cajaNombre.getText().toString();
                            String apellidos = cajaApellidos.getText().toString();
                            String email = cajaEmail.getText().toString();
                            String direccion = cajaDireccion.getText().toString();

                            Intent intentModificar = getIntent();
                            String uid_clave_nodo = intentModificar.getStringExtra("Uid");

                            //Toast.makeText(ModificarActivity.this, "UID: "+uid_clave_nodo, Toast.LENGTH_SHORT).show();
                            referenciaBaseDatos2 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

                                if (!TextUtils.isEmpty(nombre)){
                                    referenciaBaseDatos2.child(uid_clave_nodo).child(getString(R.string.campo_nombre)).setValue(cajaNombre.getText().toString());
                                }

                                if (!TextUtils.isEmpty(apellidos)){
                                    referenciaBaseDatos2.child(uid_clave_nodo).child(getString(R.string.campo_apellidos)).setValue(cajaApellidos.getText().toString());
                                }

                                if (!TextUtils.isEmpty(email)){
                                    referenciaBaseDatos2.child(uid_clave_nodo).child(getString(R.string.campo_email)).setValue(cajaEmail.getText().toString());
                                }

                                if (!TextUtils.isEmpty(direccion)){
                                    referenciaBaseDatos2.child(uid_clave_nodo).child(getString(R.string.campo_direccion)).setValue(cajaDireccion.getText().toString());
                                }


                    Toast.makeText(ModificarActivity.this, "Información de usuario modificada con éxito!!", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(ModificarActivity.this, "Debes indicar un nick existente!!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

/*
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
*/
}
