package josevi.android.com.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertarActivity extends AppCompatActivity {

    private TextView cajaNick, cajaNombre, cajaApellidos, cajaEmail, cajaDireccion;
    private Button botonVolver, botonInsertar;

    //Obejto que hará referencia a la BBDD FireBase
    DatabaseReference referenciaBaseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        //PASO1-FIREBASE. Obtenemos la referencia con la nuestra Base de Datos FireBase. Indicamos el nodo raíz
        //referenciaBaseDatos = FirebaseDatabase.getInstance().getReference(usuarios));
        referenciaBaseDatos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        //Instanciamos los views de la vista
        cajaNick = (TextView) findViewById(R.id.cajaNick);
        cajaNombre = (TextView) findViewById(R.id.cajaNombre);
        cajaApellidos = (TextView) findViewById(R.id.cajaApellidos);
        cajaEmail = (TextView) findViewById(R.id.cajaEmail);
        cajaDireccion = (TextView) findViewById(R.id.cajaDireccion);
        botonVolver = (Button) findViewById(R.id.btnVolver);
        botonInsertar = (Button) findViewById(R.id.btnInsertar);

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

                if(!TextUtils.isEmpty(cajaNick.toString())){
                    if(!TextUtils.isEmpty(cajaNombre.toString())){
                        if(!TextUtils.isEmpty(cajaApellidos.toString())){
                            if(!TextUtils.isEmpty(cajaEmail.toString())){
                                if(!TextUtils.isEmpty(cajaDireccion.toString())){

                                    //Creamos un nuevo usuario
                                    Usuario usu = new Usuario(nick, nombre, apellidos, email, direccion);

                                    //PASO2-FIREBASE. Creamos un nuevo nodo usuario
                                    String claveNodoUsuarios = referenciaBaseDatos.push().getKey();

                                    //PASO3-FIREBASE. Añadimos el nuevo usuario dentro del correspondiente nodo
                                    referenciaBaseDatos.child(claveNodoUsuarios).setValue(usu);

                                }
                            }

                        }
                    }
                }else{
                    Toast.makeText(InsertarActivity.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
