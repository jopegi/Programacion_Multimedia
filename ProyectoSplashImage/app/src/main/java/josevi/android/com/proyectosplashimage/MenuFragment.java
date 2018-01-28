package josevi.android.com.proyectosplashimage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/*
* Fragment de tipo estático que se cargará en la actividad MenuActivity y servirá para
* mostrar un menú que actuará sobre el fragment dinámico WellcomeFragment
* */

public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ComunicadorFragmentEstatic mListener;

    //Referencia al contededor de datos que se visualizarán en nuestro RecyclerView
    ArrayList<DatosRecyclerView> listaMenu;
    //Referencia a nuestro RecyclerView
    RecyclerView recycler;


    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //***************************************************************************************
    //***************************************************************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    //***************************************************************************************
    //***************************************************************************************

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_menu, container, false);

        listaMenu = new ArrayList<DatosRecyclerView>();

        recycler = (RecyclerView) vista.findViewById(R.id.recycler1);

        //Definimos el tipo de RecyclerView que necesitamos:
        //>>de tipo LinearLayoutManager
        //>>que se muestre en vertical
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        //recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));


        llenarLista();

        /*listaMenu.add("PERFIL");
        listaMenu.add("JUEGO");
        listaMenu.add("INSTRUCCIONES");
        listaMenu.add("INFORMACIÓN");*/

        AdapterDatos adapter = new AdapterDatos(listaMenu);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaMenu.get(recycler.getChildAdapterPosition(view)).getNombre().compareTo("Perfil")==0){
                    mListener.carregarPerfil();
                }else if(listaMenu.get(recycler.getChildAdapterPosition(view)).getNombre().compareTo("Juego")==0){
                    mListener.iniciarJoc();
                }else if(listaMenu.get(recycler.getChildAdapterPosition(view)).getNombre().compareTo("Instrucciones")==0){
                    mListener.voreInstruccions();
                }else if(listaMenu.get(recycler.getChildAdapterPosition(view)).getNombre().compareTo("Información")==0){
                    mListener.voreInformacio();
                }
                //Toast.makeText(getContext(),"Selección: "+listaMenu.get(recycler.getChildAdapterPosition(view)).getNombre(),Toast.LENGTH_SHORT).show();
            }
        });

        recycler.setAdapter(adapter);

        return vista;
    }

    private void llenarLista() {

        listaMenu.add(new DatosRecyclerView("Perfil",R.drawable.perfil));
        listaMenu.add(new DatosRecyclerView("Juego",R.drawable.juego));
        listaMenu.add(new DatosRecyclerView("Instrucciones",R.drawable.instrucciones));
        listaMenu.add(new DatosRecyclerView("Información",R.drawable.informacion));

    }

    /*
    * Método que permite adjuntar el fragment a una actividad o a un fragmento
    */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComunicadorFragmentEstatic) {
            mListener = (ComunicadorFragmentEstatic) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
    Interfaz donde debemos definir los métodos que permitirán interactuar a este fragmento
    *con una atividad u otro fragmento. Esta interfaz deberá de implementarse y sobreescribir
    *sus métodos abstractos en la actividad o fragmento con el que se pretenda interactuar
    */
    public interface ComunicadorFragmentEstatic {

        void carregarPerfil();
        void iniciarJoc();
        void voreInstruccions();
        void voreInformacio();
    }

}
