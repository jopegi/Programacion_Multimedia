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

public class MostrarPorCategoriaActivity extends AppCompatActivity {

    private ListView listaProductosPorCategoria;
    private Button botonVolver;

    DatabaseReference referencia;

    private ArrayList<String> listadoCategorias;             //ArrayList para los nicks de usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_por_categoria);

        listaProductosPorCategoria = (ListView) findViewById(R.id.listaProductos);
        botonVolver = (Button) findViewById(R.id.btnVolver);

        //PASO1-FIREBASE. Obtenemos la referencia con la nuestra Base de Datos FireBase. Indicamos el nodo que
        //nos interesa referenciar, en este caso, productos.
        referencia = FirebaseDatabase.getInstance().getReference("productos");

        //Definimos un listener que se encargará de suscribirse a nuestra referencia a FireBase
        //y de estar pendiente de ante cualquier cambio que se produzca en el nodo actual...
        referencia.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Declaramos un ArrayAdapter
                ArrayAdapter<String> adaptador;

                //Instanciamos un ArrayList para los usuarios
                listadoCategorias = new ArrayList<String>();

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    //Vamos recorriendo el Snapshot y guardando las categorias en un ArrayList
                    Producto prod = i.getValue(Producto.class);
                    String categoria = prod.getCategoria();
                    listadoCategorias.add(categoria);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Obtenemos una referencia del intent que proviene del GestorProductosActivity
        Intent intentMostrarPorCategoria = getIntent();

        //Obtenemos el valor de la categoría enviado mediante el intent
        String categoriaSeleccionada = intentMostrarPorCategoria.getStringExtra("categoria");


        Query q = referencia.orderByChild("categoria").equalTo(categoriaSeleccionada);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;

                ArrayList<Producto> listadoProductosPorCategoria = new ArrayList<Producto>();

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    Producto prod = i.getValue(Producto.class);

                    listadoProductosPorCategoria.add(prod);

                }

                ArrayList<String> listaProductos = new ArrayList<String>();

                for (int i = 0; i<listadoProductosPorCategoria.size(); i++){

                    String mensaje = listadoProductosPorCategoria.get(i).toString();

                    listaProductos.add(mensaje);

                }

                adaptador = new ArrayAdapter<String>(MostrarPorCategoriaActivity.this, android.R.layout.simple_list_item_1, listaProductos);

                listaProductosPorCategoria.setAdapter(adaptador);
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

    //Método para comprobar si una categoría existe
    public boolean existeCategoria (String nombreCategoria){

        //Al nuevo nick le eliminamos los posibles espacios en blanco
        String nickTrim = nombreCategoria.trim();

        boolean rtn = false;

        //Vamos recorriendo el listado de categorias existentes y comparándolos con
        //la categoría elegida por el usuario
        for (int j = 0; j<listadoCategorias.size(); j++){

            //Se comparan las cadenas de texto ignorando las mayúsculas y minúsculas
            if(listadoCategorias.get(j).equalsIgnoreCase(nickTrim)){
                rtn = true;

            }
        }
        return rtn;
    }
}
