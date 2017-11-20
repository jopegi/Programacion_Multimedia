package josevi.android.com.intefaces_examen_act2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaramos un objeto de tipo ListView
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cargamos en la vista el listView
        lista = (ListView)findViewById(R.id.listViewItems);

        //Creamos un Array con los valores que queremos mostrar en el ListView, en este caso
        //diez números en letra
        String numeros [] = {"Uno","Dos","Tres","Cuatro","Cinco","Seis","Siete","Ocho","Nueve","Diez"};

        //Creamos un ArrayAdapter que recibe por parámetro: un contexto, un layout (en este caso, uno por defecto de android) y
        //una fuente de datos (nuestro array de Strings "numeros"
        ArrayAdapter<String> adp = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,numeros);

        //Cargamos en nuestro objeto vista nuestro ArrayAdapter
        lista.setAdapter(adp);

        //Registramos los controles para el menu contextual
        //Esta sentencia permitirá que se detecte un click largo y que se muestre
        //el menu contextual
        registerForContextMenu(lista);
    }

    //Método encargado de construir el menú contextual.
    //Se llama cada vez que se requiere mostrar el menú
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Hacemos una referencia a una View por su id
        int id = v.getId();
        //Cargamos en el contextMenu el listView
        lista = (ListView)findViewById(R.id.listViewItems);

        //Creamos un objeto de tipo AdapterContextMenuInfo para poder acceder a la info de la vista
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        //Personalizamos el encabezado del menu contextual mediante el método setHeaderTitle()
        //Se muestra el item sobre el que haya pulsado
        menu.setHeaderTitle(lista.getAdapter().getItem(info.position).toString());

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_contextual_listview, menu);

    }

    //Método que define las acciones a realizar para cada una de las opciones definidas en el menú
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //Creamos un objeto que nos permita obtener info del elemento sobre el que se ha hecho click largo
        AdapterView.AdapterContextMenuInfo infoItemSelected = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //Obtenemos el id del item seleccionado
        switch(item.getItemId()){
            //Si el id corresponde al item identificado como actionAnyadir
            case R.id.actionAnyadir:

                if (infoItemSelected.position == 0) {
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha añadido un elemento detrás del número 1", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 1){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha añadido un elemento detrás del número 2", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 2){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha añadido un elemento detrás del número 3", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 3){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha añadido un elemento detrás del número 4", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 4){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha añadido un elemento detrás del número 5", Toast.LENGTH_SHORT).show();
                } else if(infoItemSelected.position == 5){
                    //Creamos y mostramos un Toast de duración corta
                     Toast.makeText(MainActivity.this,"Se ha añadido un elemento detrás del número 6", Toast.LENGTH_SHORT).show();
                } else if(infoItemSelected.position == 6){
                    //Creamos y mostramos un Toast de duración corta
                     Toast.makeText(MainActivity.this,"Se ha añadido un elemento detrás del número 7", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 7){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha añadido un elemento detrás del número 8", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 8){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha añadido un elemento detrás del número 9", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 9){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha añadido un elemento detrás del número 10", Toast.LENGTH_SHORT).show();
                }
                return true;

            //Si el id corresponde al item identificado como actionEliminar
            case R.id.actionEliminar:
                if (infoItemSelected.position == 0) {
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha borrado el número 1", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 1){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha borrado el número 2", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 2){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha borrado el número 3", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 3){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha borrado el número 4", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 4){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha borrado el número 5", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 5){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha borrado el número 6", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 6){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha borrado el número 7", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 7){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha borrado el número 8", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 8){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha borrado el número 9", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 9){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Se ha borrado el número 10", Toast.LENGTH_SHORT).show();
                }
                return true;
        }

        return super.onContextItemSelected(item);
    }
}

