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

    private String uidUsuario;
    private String nickUsuario;
    private String categoriaSeleccionada;

    private ListView listaProductosPorCategoria;
    private Button botonVolver;

    DatabaseReference referencia;

    private ArrayList<String> listaProductos;
    private ArrayList<Producto> productosXCategoria;
    private ArrayAdapter<String> adaptador;

    private Producto prod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_por_categoria);

        listaProductosPorCategoria = (ListView) findViewById(R.id.listaProductos);
        botonVolver = (Button) findViewById(R.id.btnVolver);

        Intent intentMostrarPorCategoria= getIntent();
        uidUsuario = intentMostrarPorCategoria.getStringExtra("Uid");
        nickUsuario = intentMostrarPorCategoria.getStringExtra("Nick");
        categoriaSeleccionada = intentMostrarPorCategoria.getStringExtra("Categoria");

        referencia = FirebaseDatabase.getInstance().getReference("productos");

        Query q = referencia.orderByChild("categoria").equalTo(categoriaSeleccionada);

        q.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                productosXCategoria = new ArrayList<Producto>();

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    Producto prod = i.getValue(Producto.class);
                    productosXCategoria.add(prod);

                }

                listaProductos = new ArrayList<String>();

                for (int i = 0; i<productosXCategoria.size(); i++){

                    String mensaje = productosXCategoria.get(i).toString();

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

    /*
    //Método para comprobar si una categoría existe
    public boolean existeCategoria (String nombreCategoria){

        //Al nuevo nick le eliminamos los posibles espacios en blanco
        String nickTrim = categoriaSeleccionada.trim();

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
    */
}
