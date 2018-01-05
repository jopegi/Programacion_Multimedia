package josevi.android.com.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BorrarProductoActivity extends AppCompatActivity {

    String nickUsuario;
    String uidUsuario;

    private Spinner spinnerProductos;

    private Button botonEliminar;
    private Button botonVolver;

    DatabaseReference referenciaBaseDatos;

    ArrayList<String> listadoProductos;

    Producto prod;

    ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar_producto);

        spinnerProductos = (Spinner) findViewById(R.id.spinnerProductos);

        botonEliminar = (Button) findViewById(R.id.btnBorrar);
        botonVolver = (Button) findViewById(R.id.btnVolver);

        Intent intentoBorrarProducto = getIntent();

        nickUsuario = intentoBorrarProducto.getStringExtra("Nick");
        uidUsuario = intentoBorrarProducto.getStringExtra("Uid");

        //Obtenemos una referencia al nodo "productos/Uid"
        referenciaBaseDatos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));


        Query q = referenciaBaseDatos.orderByChild("uid").equalTo(uidUsuario);

        //****************ESCUCHARÁ CUALQUIER CAMBIO EN EL NODO productos DE LA BBDD***********
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listadoProductos = new ArrayList<String>();

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    prod = i.getValue(Producto.class);
                    String nombreProducto = prod.getNombre();
                    listadoProductos.add(nombreProducto);
                }

                adaptador = new ArrayAdapter<String>(BorrarProductoActivity.this, android.R.layout.simple_list_item_1, listadoProductos);

                spinnerProductos.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = spinnerProductos.getSelectedItem().toString();

                //Validamos que la caja con el nombre del producto esté llena
                if(!TextUtils.isEmpty(nombre)){

                        //Nos quedamos con los nodos cuyo atributo nombre coincida con el especificado en la interfaz gráfica
                        Query q = referenciaBaseDatos.orderByChild(getString(R.string.campo_nombre)).equalTo(nombre);

                        //Definimos un listener
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot i : dataSnapshot.getChildren()) {

                                    //Obtenemos la clave que identifica al nodo anterior
                                    String clave = i.getKey();

                                    referenciaBaseDatos.child(clave).removeValue();

                                }

                                Toast.makeText(BorrarProductoActivity.this, "Producto eliminado con éxito!!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }else{

                        Toast.makeText(BorrarProductoActivity.this, "El producto que intenta modificar no existe!!",
                                Toast.LENGTH_SHORT).show();
                    }

            }
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }
}
