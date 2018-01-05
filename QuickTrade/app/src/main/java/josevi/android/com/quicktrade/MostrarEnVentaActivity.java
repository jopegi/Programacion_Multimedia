package josevi.android.com.quicktrade;

import android.content.Intent;
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

    private String uidUsuario;
    private String nickUsuario;

    private Producto prod;

    private ListView listaProductosEnVenta;
    private Button botonVolver;

    DatabaseReference referencia;

    private ArrayList<Producto> listadoProductos;

    private ArrayList<String> listaProductos;

    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_en_venta);

        listaProductosEnVenta = (ListView) findViewById(R.id.listaProductos);
        botonVolver = (Button) findViewById(R.id.btnVolver);

        Intent intentMostrarEnVenta = getIntent();

        uidUsuario = intentMostrarEnVenta.getStringExtra("Uid");

        referencia = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));

        Query q = referencia.orderByChild("uid").equalTo(uidUsuario);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listadoProductos = new ArrayList<Producto>();

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    Producto prod = i.getValue(Producto.class);

                    listadoProductos.add(prod);

                }

                listaProductos = new ArrayList<String>();

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
