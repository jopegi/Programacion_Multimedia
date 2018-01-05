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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GestorProductosActivity extends AppCompatActivity {

    //Cajas de texto
    private EditText cajaNombre;
    private EditText cajaDescripcion;
    private EditText cajaPrecio;

    //Labels
    private TextView labelNick;

    //Spinner
    private Spinner spinnerCategoria;

    //Botones
    private Button botonAltaProducto;
    private Button botonModificarProducto;
    private Button botonBorrarProducto;
    private Button botonMostrarEnVenta;
    private Button botonMostrarCategoria;

    private ArrayList<String> listadoNombreProductos;   //ArrayList para los nombres de producto

    private ArrayList<Producto> listadoProductos;   //ArrayList para los objetos producto

    private ArrayList<String> listadoNicks;     //ArrayList para los nicks de usuario

    DatabaseReference referenciaBaseDatos;      //Referencia que se utilizará para el nodo "usuarios"
    DatabaseReference referenciaBaseDatos2;     //Referencia que se utilizará para el nodo "productos"
    DatabaseReference referenciaBaseDatos3;

    private ArrayAdapter<String> adaptador;

    private Usuario usu;

    private String userUid;

    private String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor_productos);

        //Cajas de texto de la interfaz
        cajaNombre = (EditText) findViewById(R.id.editTextNombre);
        cajaDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
        cajaPrecio = (EditText) findViewById(R.id.editTextPrecio);

        //Label de texto de la interfaz
        labelNick = (TextView) findViewById(R.id.textViewNick);

        //Spinner de la interfaz
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoria);

        //Botones de la interfaz
        botonAltaProducto = (Button) findViewById(R.id.btnAltaProducto);
        botonModificarProducto = (Button) findViewById(R.id.btnModificarProducto);
        botonBorrarProducto = (Button) findViewById(R.id.btnBorrarProducto);
        botonMostrarEnVenta = (Button) findViewById(R.id.btnMostrarProductos);
        botonMostrarCategoria = (Button) findViewById(R.id.btnMostrarCategorias);

        //Instanciamos un array para las categorías
        String [] categorias = {"tecnología", "coches", "hogar"};

        //Instanciamos un ArrayAdapter pasándole el array con los categorías de productos
        ArrayAdapter<String> adaptadorCategorias = new ArrayAdapter<String>(GestorProductosActivity.this,
                android.R.layout.simple_list_item_1, categorias);
        //Rellenamos el spinner con el adaptador
        spinnerCategoria.setAdapter(adaptadorCategorias);

        //Obtenemos una referencia del intent que proviene del RegistarActivity
        Intent intentProductos = getIntent();

        //Obtenemos el valor del Uid enviado mediante el mencionado intent
        userUid = intentProductos.getStringExtra("Uid");

        //Obtenemos una referencia al nodo "usuarios/Uid"
        referenciaBaseDatos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios)).child(userUid);

        //Obtenemos una referencia al nodo "productos"
        referenciaBaseDatos2 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));

        //****************ESCUCHARÁ CUALQUIER CAMBIO EN EL NODO usuarios DE LA BBDD***********
        referenciaBaseDatos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Como la referencia a la BBDD apunta a un nodo en concreto (aquel que coincide
                //con el Uid del usuario logeado) no hace falta hacer uso de un bucle for para
                //recorrerlo
                usu = dataSnapshot.getValue(Usuario.class);

                labelNick.setText(usu.getNick());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //**********BOTÓN PARA INSERTAR UN NUEVO PRODUCTO**********************
        botonAltaProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recogemos los valores de las cajas de texto de la interfaz gráfica
                String nombre = cajaNombre.getText().toString();
                String descripcion = cajaDescripcion.getText().toString();
                String precio =  cajaPrecio.getText().toString();
                String categoria = spinnerCategoria.getSelectedItem().toString();
                String nickUsuario = labelNick.getText().toString();

                //Validamos que las caja de texto esten llenas
                if(!TextUtils.isEmpty(nombre)){

                    if(!TextUtils.isEmpty(descripcion)){

                        if(!TextUtils.isEmpty(categoria)){

                            if(!TextUtils.isEmpty(precio) && isNumber(precio)){

                               if(!TextUtils.isEmpty(nickUsuario)){

                                   //Parseamos el valor de la caja de texto que contiene el precio del producto a un double
                                    double precioDouble = Double.valueOf(precio);

                                    //Creamos un nuevo producto con los valores insertados por el usuario
                                    Producto p = new Producto(nickUsuario, nombre, descripcion, categoria, precioDouble, userUid);

                                    Toast.makeText(GestorProductosActivity.this, "producto creado!!", Toast.LENGTH_SHORT).show();

                                    //Obtenemos la clave de Firebase que apunta al nodo "productos"
                                    String clave = referenciaBaseDatos2.push().getKey();

                                    //Generamos un nuevo producto con la referencia de dicha clave
                                   referenciaBaseDatos2.child(clave).setValue(p);

                                }else{

                                    Toast.makeText(GestorProductosActivity.this, "Por favor, indica el usuario", Toast.LENGTH_SHORT).show();
                                }

                            }else{

                                Toast.makeText(GestorProductosActivity.this, "Por favor, indica el precio del producto", Toast.LENGTH_SHORT).show();
                            }


                        }else{

                            Toast.makeText(GestorProductosActivity.this, "Por favor, indica la categoría del producto", Toast.LENGTH_SHORT).show();
                        }


                    }else{

                        Toast.makeText(GestorProductosActivity.this, "Por favor, indica la descripción del producto", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(GestorProductosActivity.this, "Por favor, indica el nombre del producto", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //**********BOTÓN PARA INSERTAR UN NUEVO PRODUCTO**********************
        //El usuario podrá modificar sus productos por nombre
        botonModificarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listarProductos();

            }
        });

        //**********BOTÓN PARA BORRAR UN PRODUCTO***************************
        //El usuario podrá borrar sus productos por nombre
       botonBorrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentoBorrarProducto = new Intent(GestorProductosActivity.this, BorrarProductoActivity.class);

                intentoBorrarProducto.putExtra("Uid", userUid);

                intentoBorrarProducto.putExtra("Nick", nickName);

                startActivity(intentoBorrarProducto);
            }
        });

        botonMostrarEnVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentMostrarEnVenta = new Intent(GestorProductosActivity.this, MostrarEnVentaActivity.class);

                intentMostrarEnVenta.putExtra("Uid", userUid);

                intentMostrarEnVenta.putExtra("Nick", nickName);

                startActivity(intentMostrarEnVenta);

            }
        });

        botonMostrarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentMostrarPorCategoria = new Intent(GestorProductosActivity.this, MostrarPorCategoriaActivity.class);

                intentMostrarPorCategoria.putExtra("Categoria", spinnerCategoria.getSelectedItem().toString());

                intentMostrarPorCategoria.putExtra("Uid", userUid);

                intentMostrarPorCategoria.putExtra("Nick", nickName);

                startActivity(intentMostrarPorCategoria);

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

    //Método para comparar el nombre del producto que se
    //pretenda modifidicar con los ya existentes en FireBase
    public boolean existeProducto (String nombreProducto){

        //Al nuevo nick le eliminamos los posibles espacios en blanco
        String nickTrim = nombreProducto.trim();

        boolean rtn = false;

        //Vamos recorriendo el listado de nombres de producto existentes y comparándolos con
        //el que se pretende modificar o borrar
        for (int j = 0; j<listadoNombreProductos.size(); j++){

            //Se comparan las cadenas de texto ignorando las mayúsculas y minúsculas
            if(listadoNombreProductos.get(j).equalsIgnoreCase(nickTrim)){
                rtn = true;

            }
        }
        return rtn;
    }


    public ArrayList<Producto> listarProductos(){

        listadoProductos = new ArrayList<Producto>();

        Query q2 = referenciaBaseDatos2.orderByChild("uid").equalTo(userUid);

        //****************ESCUCHARÁ CUALQUIER CAMBIO EN EL NODO usuarios DE LA BBDD***********
        q2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int cont=0;

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    cont++;

                    Producto prod = i.getValue(Producto.class);

                    listadoProductos.add(prod);

                }

                if (listadoProductos.size()>0) {

                    referenciaBaseDatos3 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios)).child(userUid);

                    //****************ESCUCHARÁ CUALQUIER CAMBIO EN EL NODO usuarios DE LA BBDD***********
                    referenciaBaseDatos3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            usu = dataSnapshot.getValue(Usuario.class);
                            nickName = usu.getNick();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Intent intentoModificarProducto = new Intent(GestorProductosActivity.this, ModificarProductoActivity.class);

                    intentoModificarProducto.putExtra("Uid", userUid);

                    intentoModificarProducto.putExtra("Nick", nickName);

                    startActivity(intentoModificarProducto);

                }else{

                    Toast.makeText(GestorProductosActivity.this, "Todavía no tiene ningún producto registrado!!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return listadoProductos;
    }


}
