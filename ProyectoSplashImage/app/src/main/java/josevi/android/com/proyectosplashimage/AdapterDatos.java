package josevi.android.com.proyectosplashimage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by josevi on 06/12/2017.
 */


//Esta clase va a recibir una lista para poderla mostra ren nuestro Recycler
public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    ArrayList<String> listaMenu;

    //Cosntructor de la clase
    //Recibirá por parámetro una lista de datos
    public AdapterDatos(ArrayList<String> listaMenu) {
        this.listaMenu = listaMenu;
    }

    @Override
    //Método que enlazará el adaptador con el archivo item_list.xml
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {

        holder.asignarDatos(listaMenu.get(position));
    }

    @Override
    public int getItemCount() {

        return listaMenu.size();
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView dato;

        public ViewHolderDatos(View itemView) {
            super(itemView);

            //Indicamos la vista que se utilizará para mostrar los datos del RecyclerView
            dato = (TextView) itemView.findViewById(R.id.idDatos);
        }

        //Mi método asignarDatos()
        public void asignarDatos(String datos) {

            dato.setText(datos);
        }
    }
}
