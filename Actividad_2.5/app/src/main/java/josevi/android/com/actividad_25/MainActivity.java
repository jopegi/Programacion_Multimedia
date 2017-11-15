package josevi.android.com.actividad_25;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    //Declaramos un objwto de tipo ListView
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cargamos en la vista el listView
        lv = (ListView)findViewById(R.id.listViewItems);

        //Creamos un Array con los valores que queremos mostrar
        String nombres [] = {"Víctor","Silvia","Manolo","Carlos","Ana"};

        //Creamos un ArrayAdapter que recibe por parámetro: un contexto, un layout (en este caso, uno por defecto de android) y
        //una fuente de datos
        ArrayAdapter<String> adp = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,nombres);

        lv.setAdapter(adp);

        //Registramos los controles para el menu contextual
        //Esta sentencia permitirá que se detecte un click largo y que se muestre
        //el menu contextual
        registerForContextMenu(lv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Hacemos una referencia a una View por su id
        int id = v.getId();
        //Cargamos en el contextMenu el listView
        lv = (ListView)findViewById(R.id.listViewItems);

        //Creamos un objeto de tipo AdapterContextMenuInfo para poder acceder a la info de la vista
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        //Personalizamos el encabezado del menu contextual mediante el método setHeaderTitle()
        menu.setHeaderTitle(lv.getAdapter().getItem(info.position).toString());

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_contextual_listview, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //Creamos un objeto que nos permita obtener info del elemento sobre el que se ha hecho click largo
        AdapterView.AdapterContextMenuInfo infoItemSelected = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //Obtenemos el id del item seleccionado
        switch(item.getItemId()){
            //Si el id corresponde al item identificado como actionMostrar
            case R.id.actionMostrar:

                if (infoItemSelected.position == 0) {
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Víctor: " + "Opción Mostrar", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 1){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Silvia: " + "Opción Mostrar", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 2){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Manolo: " + "Opción Mostrar", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 3){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Carlos: " + "Opción Mostrar", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 4){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Ana: " + "Opción Mostrar", Toast.LENGTH_SHORT).show();
                }

                //getListAdapter().getItem(info.position)
                return true;
            //Si el id corresponde al item identificado como actionEliminar
            case R.id.actionEliminar:
                if (infoItemSelected.position == 0) {
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Víctor: " + "Opción Eliminar", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 1){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Silvia: " + "Opción Eliminar", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 2){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Manolo: " + "Opción Eliminar", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 3){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Carlos: " + "Opción Eliminar", Toast.LENGTH_SHORT).show();
                }else if(infoItemSelected.position == 4){
                    //Creamos y mostramos un Toast de duración corta
                    Toast.makeText(MainActivity.this,"Ana: " + "Opción Eliminar", Toast.LENGTH_SHORT).show();
                }
                return true;
        }

        return super.onContextItemSelected(item);
    }
}
