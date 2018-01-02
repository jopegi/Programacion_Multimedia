package josevi.android.com.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MostrarEnVentaActivity extends AppCompatActivity {

    private ListView listaProductosEnVenta;
    private Button botonVolver;

    DatabaseReference referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_en_venta);

        listaProductosEnVenta = (ListView) findViewById(R.id.listaProductos);
        botonVolver = (Button) findViewById(R.id.btnVolver);

        //PASO1-FIREBASE. Obtenemos la referencia con la nuestra Base de Datos FireBase. Indicamos el nodo que
        //nos interesa referenciar, en este caso, usuarios.
        referencia = FirebaseDatabase.getInstance().getReference("productos");

        Query q = referencia;

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;

                ArrayList<Producto> listadoProductos = new ArrayList<Producto>();

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    Producto prod = i.getValue(Producto.class);

                    listadoProductos.add(prod);

                }

                ArrayList<String> listaProductos = new ArrayList<String>();

                for (int i = 0; i<listadoProductos.size(); i++){

                    String mensaje = listadoProductos.get(i).toString();

                    listaProductos.add(mensaje);

                }

                adaptador = new ArrayAdapter<String>(MostrarEnVentaActivity.this, android.R.layout.simple_list_item_1, listaProductos);

                listaProductosEnVenta.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
