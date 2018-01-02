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

public class GestorProductosActivity extends AppCompatActivity {

    //Cajas de texto
    private EditText cajaNombre;
    private EditText cajaDescripcion;
    private EditText cajaPrecio;

    //Spinner
    private Spinner spinnerUsuario;
    private Spinner spinnerCategoria;

    //Botones
    private Button botonAltaProducto;
    private Button botonModificarProducto;
    private Button botonBorrarProducto;
    private Button botonMostrarEnVenta;
    private Button botonMostrarCategoria;

    private ArrayList<String> listadoNicks;             //ArrayList para los nicks de usuario
    private ArrayList<String> listadoNombreProductos;   //ArrayList para los nombres de producto

    DatabaseReference dbr;      //Referencia que se utilizará para el nodo "usuarios"
    DatabaseReference dbr2;     //Referencia que se utilizará para el nodo "productos"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor_productos);

        //Cajas de texto de la interfaz
        cajaNombre = (EditText) findViewById(R.id.editTextNombre);
        cajaDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
        cajaPrecio = (EditText) findViewById(R.id.editTextPrecio);

        //Spinner de la interfaz
        spinnerUsuario = (Spinner) findViewById(R.id.spinnerNick);
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

        //Obtenemos una referencia al nodo "usuarios"
        dbr = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        //Definimos un listener que se encargará de suscribirse a nuestra referencia a FireBase
        //y de estar pendiente de ante cualquier cambio que se produzca en el nodo actual...
        dbr.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Declaramos un ArrayAdapter
                ArrayAdapter<String> adaptadorUsuarios;

                //Instanciamos un ArrayList para los usuarios
                listadoNicks = new ArrayList<String>();

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    //Vamos recorriendo el Snapshot y guardando los nicks de usuario
                    //en un ArrayList que, posteriormente se cargará en un adaptador
                    //para poder mostrarse en un Spinner
                    Usuario usu = i.getValue(Usuario.class);
                    String nickUsuario = usu.getNick();
                    listadoNicks.add(nickUsuario);

                }

                //Instanciamos el ArrayAdapter pasándole la lista con los nicks de los usuarios
                adaptadorUsuarios = new ArrayAdapter<String>(GestorProductosActivity.this, android.R.layout.simple_list_item_1, listadoNicks);
                //Rellenamos el spinner con el adaptador
                spinnerUsuario.setAdapter(adaptadorUsuarios);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Obtenemos una referencia al nodo "productos"
        dbr2 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));

        //Definimos un listener que se encargará de suscribirse a nuestra referencia a FireBase
        //y de estar pendiente de ante cualquier cambio que se produzca en el nodo actual...
        dbr2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Instanciamos un ArrayList para los productos
                listadoNombreProductos = new ArrayList<String>();

                for (DataSnapshot j: dataSnapshot.getChildren()){

                    //Vamos recorriendo el Snapshot y guardando los nombres de los
                    //productos en un ArrayList. Y, este Arraylist servirá para
                    //comprobar, posteriormente, si el producto existe en la Base
                    //de Datos cuando se necesite su modificación
                    Producto prod = j.getValue(Producto.class);
                    String nombreProducto = prod.getNombre();
                    listadoNombreProductos.add(nombreProducto);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //**********BOTÓN PARA INSERTAR UN NUEVO PRODUCTO**********************
        botonAltaProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //Obtenemos una referencia del intent que proviene del RegistarActivity
                Intent intentProductos = getIntent();

                //Obtenemos el valor del Uid enviado mediante el mencionado intent
                String userUid = intentProductos.getStringExtra("Uid");
                //Toast.makeText(GestorProductosActivity.this, "Este es el Usid pasado en el intent: "+userUid, Toast.LENGTH_SHORT).show();

                //Recogemos los valores de las cajas de texto de la interfaz gráfica
                String nombre = cajaNombre.getText().toString();
                String descripcion = cajaDescripcion.getText().toString();
                String precio =  cajaPrecio.getText().toString();
                String categoria = spinnerCategoria.getSelectedItem().toString();
                String nickUsuario = spinnerUsuario.getSelectedItem().toString();

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
                                    String clave = dbr2.push().getKey();

                                    //Generamos un nuevo producto con la referencia de dicha clave
                                    dbr2.child(clave).setValue(p);

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

                String nombre = cajaNombre.getText().toString();
               // Toast.makeText(GestorProductosActivity.this, ""+nombre, Toast.LENGTH_SHORT).show();
              //  Toast.makeText(GestorProductosActivity.this, ""+listadoNombreProductos.get(0), Toast.LENGTH_SHORT).show();

                /*

                //Obtenemos una referencia del intent que proviene del RegistarActivity
                Intent intentProductos = getIntent();

                //Obtenemos el valor del Uid enviado mediante el mencionado intent
                String userUid = intentProductos.getStringExtra("Uid");

                */

                //Validamos que la caja con el nombre del producto esté llena
                if(!TextUtils.isEmpty(nombre)){

                    if(existeProducto(nombre)) {

                        //Nos quedamos con los nodos cuyo atributo nombre coincida con el especificado en la interfaz gráfica
                        Query q = dbr2.orderByChild(getString(R.string.campo_nombre)).equalTo(nombre);

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

                                        dbr2.child(clave).child("descripción").setValue(cajaDescripcion.getText().toString());
                                    }

                                    if (!TextUtils.isEmpty(spinnerCategoria.getSelectedItem().toString())) {

                                        dbr2.child(clave).child("categoria").setValue(spinnerCategoria.getSelectedItem().toString());
                                    }

                                    if (isNumber(cajaPrecio.getText().toString()) && !TextUtils.isEmpty(cajaPrecio.getText().toString())) {

                                        dbr2.child(clave).child("precio").setValue(Double.parseDouble(cajaPrecio.getText().toString()));
                                    }

                                }

                                Toast.makeText(GestorProductosActivity.this, "Producto modificado con éxito!!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }else{

                        Toast.makeText(GestorProductosActivity.this, "El producto que intenta modificar no existe!!",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(GestorProductosActivity.this, "Debe de indicar el nombre del producto que desea modificar!!",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

        //**********BOTÓN PARA BORRAR UN PRODUCTO***************************
        //El usuario podrá borrar sus productos por nombre
       botonBorrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(GestorProductosActivity.this, ""+listadoNombreProductos.get(1), Toast.LENGTH_SHORT).show();

                String nombre = cajaNombre.getText().toString();

                /*

                //Obtenemos una referencia del intent que proviene del RegistarActivity
                Intent intentProductos = getIntent();

                //Obtenemos el valor del Uid enviado mediante el mencionado intent
                String userUid = intentProductos.getStringExtra("Uid");

                */

                //Validamos que la caja con el nombre del producto esté llena
                if(!TextUtils.isEmpty(nombre)){

                    if(existeProducto(nombre) == true) {

                        //Nos quedamos con los nodos cuyo atributo nombre coincida con el especificado en la interfaz gráfica
                        Query q = dbr2.orderByChild(getString(R.string.campo_nombre)).equalTo(nombre);

                        //Definimos un listener
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot i : dataSnapshot.getChildren()) {

                                    //Obtenemos la clave que identifica al nodo anterior
                                    String clave = i.getKey();

                                    dbr2.child(clave).removeValue();

                                }

                                Toast.makeText(GestorProductosActivity.this, "Producto eliminado con éxito!!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }else{

                        Toast.makeText(GestorProductosActivity.this, "El producto que intenta modificar no existe!!",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(GestorProductosActivity.this, "Debe de indicar el nombre del producto que desea modificar!!",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

        botonMostrarEnVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentMostrarEnVenta = new Intent(GestorProductosActivity.this, MostrarEnVentaActivity.class);
                startActivity(intentMostrarEnVenta);

            }
        });

        botonMostrarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentMostrarPorCategoria = new Intent(GestorProductosActivity.this, MostrarPorCategoriaActivity.class);
                intentMostrarPorCategoria.putExtra("categoria", spinnerCategoria.getSelectedItem().toString());
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




}
