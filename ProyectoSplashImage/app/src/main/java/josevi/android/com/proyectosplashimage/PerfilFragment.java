package josevi.android.com.proyectosplashimage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PerfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText cajaNick, cajaNombre;
    private Button botonGuardar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Obtenemos una referencia a la interfaz de comunicación del Fragment con la actividad que lo contiene
    private ComunicadorFragmentPerfil mListener;

    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_perfil, container, false);

        //Instanciamos los elementos definidos en el layout del fragment
        cajaNick = (EditText) v.findViewById(R.id.editTextNick);
        cajaNombre = (EditText) v.findViewById(R.id.editTextNombre);
        botonGuardar = (Button) v.findViewById(R.id.btnGuardar);


        //Evento del botón guardar de la interfaz
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Si las caja de texto no están vacias...
                if(!cajaNick.getText().toString().isEmpty() && !cajaNombre.getText().toString().isEmpty()){

                    //Instanciamos un objeto de la clase Jugador y lo rellenamos con los datos insertados
                    //por el usuario
                    Jugador j1 = new Jugador(cajaNick.getText().toString(), cajaNombre.getText().toString());
                    //Llamamos a la interfaz de comunicación ComunicadorFragmentPerfil definida como una interfaz
                    //interna dentro de este fragment. Con esto indicamos que cuando se pulse el botón de guardar
                    //se vaya a la actividad MenuActivity y se ejecute el método abstracto recollirDadesPerfil allí
                    //sobreescrito pasándole el objeto jugador instanciado dentro del evento de este botón.
                    mListener.recollirDadesPerfil(j1);

                    Toast.makeText(getActivity(), "Datos guardados...", Toast.LENGTH_SHORT).show();

                //Si las caja de texto están vacias...
                }else{

                    Toast.makeText(getActivity(), "Debe de rellenar los campos nombre y nick!", Toast.LENGTH_SHORT).show();
                }

            }

        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComunicadorFragmentPerfil) {
            mListener = (ComunicadorFragmentPerfil) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ComunicadorFragmentPerfil {

        void recollirDadesPerfil(Jugador jugador);
    }
}
