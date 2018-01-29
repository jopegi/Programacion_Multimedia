package josevi.android.com.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModificarProductoActivity extends AppCompatActivity {

    private EditText cajaDescripcion;
    private EditText cajaPrecio;

    private Spinner spinnerNombreProducto;
    private Spinner spinnerCategoria;

    private Button botonModificar;
    private Button botonVolver;

    //Objeto que hará referencia a la BBDD FireBase
    DatabaseReference referenciaBaseDatos;

    Producto producto;

    private ArrayList<String> listadoNombreProductos;   //ArrayList para los nombres de producto

    private ArrayList<Producto> listadoProductos;   //ArrayList para los objetos producto

    ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);

        cajaDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
        cajaPrecio = (EditText) findViewById(R.id.editTextPrecio);

        spinnerNombreProducto = (Spinner) findViewById(R.id.spinnerProducto);
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoria);

        botonModificar = (Button) findViewById(R.id.btnModificar);
        botonVolver = (Button) findViewById(R.id.btnVolver);


        //Instanciamos un array para las categorías
        String [] categorias = {"tecnología", "coches", "hogar"};

        Intent intentoModificarProducto = getIntent();

        String Uid_usuario = intentoModificarProducto.getStringExtra("Uid");

        //Obtenemos una referencia al nodo "productos"
        referenciaBaseDatos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));

        Query q = referenciaBaseDatos.orderByChild("uid").equalTo(Uid_usuario);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listadoProductos = new ArrayList<Producto>();
                listadoNombreProductos = new ArrayList<String>();

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    //Vamos recorriendo el Snapshot y guardando los productos
                    //en un ArrayList
                    Producto product = i.getValue(Producto.class);

                    String nombreProducto = product.getNombre();

                    listadoProductos.add(product);

                    listadoNombreProductos.add(nombreProducto);

                }

                adaptador = new ArrayAdapter<String>(ModificarProductoActivity.this, android.R.layout.simple_list_item_1,listadoNombreProductos);

                spinnerNombreProducto.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Instanciamos un ArrayAdapter pasándole el array con los categorías de productos
        ArrayAdapter<String> adaptadorCategorias = new ArrayAdapter<String>(ModificarProductoActivity.this,
                android.R.layout.simple_list_item_1, categorias);
        //Rellenamos el spinner con el adaptador
        spinnerCategoria.setAdapter(adaptadorCategorias);


        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Obtenemos una referencia del intent que proviene del RegistarActivity
                Intent intentoModificarProducto = getIntent();

                //Obtenemos el valor del Uid enviado mediante el mencionado intent
                String userUid = intentoModificarProducto.getStringExtra("Uid");

                //Obtenemos el valor del Nick enviado mediante el mencionado intent
                String userNick = intentoModificarProducto.getStringExtra("Nick");

                String nombre = spinnerNombreProducto.getSelectedItem().toString();
                String descripcion = cajaDescripcion.getText().toString();
                String precio = cajaPrecio.getText().toString();
                String categoria = spinnerCategoria.getSelectedItem().toString();

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

                                    //Damos nuevos valores a los atributos de nuestro nodo, validando
                                    //únicamente aquellas cajas de texto que no estén vacías

                                    if (!TextUtils.isEmpty(cajaDescripcion.getText().toString())) {

                                        referenciaBaseDatos.child(clave).child("descripcion").setValue(cajaDescripcion.getText().toString());
                                    }

                                    if (!TextUtils.isEmpty(spinnerCategoria.getSelectedItem().toString())) {

                                        referenciaBaseDatos.child(clave).child("categoria").setValue(spinnerCategoria.getSelectedItem().toString());
                                    }

                                    if (isNumber(cajaPrecio.getText().toString()) && !TextUtils.isEmpty(cajaPrecio.getText().toString())) {

                                        referenciaBaseDatos.child(clave).child("precio").setValue(Double.parseDouble(cajaPrecio.getText().toString()));
                                    }

                                }

                                Toast.makeText(ModificarProductoActivity.this, "Producto modificado con éxito!!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }else{

                        Toast.makeText(ModificarProductoActivity.this, "El producto que intenta modificar no existe!!",
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

    //Método para comprobar si el contenido de una caja de texto
    //es parseable a un double
    public boolean isNumber (String cadena){

        boolean rtn;

        try{

            double number = Double.parseDouble(cadena);
            rtn = true;

        }catch(NumberFormatException e){

            e.printStackTrace();
            rtn = false;
        }

        return rtn;
    }
}
