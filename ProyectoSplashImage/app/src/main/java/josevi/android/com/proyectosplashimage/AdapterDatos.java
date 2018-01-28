package josevi.android.com.proyectosplashimage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by josevi on 06/12/2017.
 */


//Esta clase va a recibir una lista para poderla mostra ren nuestro Recycler
public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.UnElementDelRecycler> implements  View.OnClickListener{

    ArrayList<DatosRecyclerView> listaMenu;

    //Creamos una referencia a un listener
    private View.OnClickListener listener;

    //Constructor de la clase
    //Recibirá por parámetro una lista de datos
    public AdapterDatos(ArrayList<DatosRecyclerView> listaMenu) {
        this.listaMenu = listaMenu;
    }

    @Override
    //Método que enlazará el adaptador con el archivo item_list.xml
    public UnElementDelRecycler onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);


        //Ponemos a la eschucha nuestra aplicación
        view.setOnClickListener(this);

        return new UnElementDelRecycler(view);
    }

    @Override
    public void onBindViewHolder(UnElementDelRecycler holder, int position) {

        //holder.asignarDatos(listaMenu.get(position));
        holder.txtNombre.setText(listaMenu.get(position).getNombre());
        holder.foto.setImageResource(listaMenu.get(position).getImagenId());
    }

    @Override
    public int getItemCount() {

        return listaMenu.size();
    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {

        if(listener!=null){
            listener.onClick(v);
        }
    }


    public static class UnElementDelRecycler extends RecyclerView.ViewHolder {

        TextView txtNombre;
        ImageView foto;

            //TextView dato;

        public UnElementDelRecycler(View itemView) {
                super(itemView);

            //Indicamos la vista que se utilizará para mostrar los datos del RecyclerView
            //dato = (TextView) itemView.findViewById(R.id.idDatos);

            txtNombre = (TextView) itemView.findViewById(R.id.idNombre);
            foto = (ImageView) itemView.findViewById(R.id.idImagen);
             /*
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
        }

        //Mi método asignarDatos()
        //public void asignarDatos(String datos) {

            //dato.setText(datos);
        //}
    }
}
