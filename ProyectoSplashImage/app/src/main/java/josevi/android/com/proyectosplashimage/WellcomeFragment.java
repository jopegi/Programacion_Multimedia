package josevi.android.com.proyectosplashimage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WellcomeFragment extends Fragment {

    private static final String ARG_TITOL = "titol";
    private static final String ARG_AUTOR = "autor";
    private String mParam1;
    private TextView caixaTitol;
    private ComunicadorFragmentDinamic mListener;

    public WellcomeFragment() {
        //Se requiere que el constructor esté vacío
    }

    //Método newInstance() que se utiliza para que el fragment pueda recibir parámetros
    public static WellcomeFragment newInstance(String param1) {
        //1 Se instancia un objeto fragment
        WellcomeFragment fragment = new WellcomeFragment();
        //2 Se inatancia un objeto de tipo Bundle (colección pares clave-valor)
        Bundle args = new Bundle();
        //3 Introducimos en el Bundle la clave y el valor del parámetro
        args.putString(ARG_TITOL, param1);
        //4  Con el setArguments(args) se guardan los atributos de nuestro Fragment en un objeto
        //Bundle propio de la clase Fragment. Dicho objeto se denomina mSavedFragmentState
        fragment.setArguments(args);
        //5 El método newInstance() devuelve un Fragment con los atributos seteados por el método anterior
        return fragment;
    }

    //Se crea el Fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //Bundle b = getArguments();
            //mParam1 = b.getString(ARG_PARAM1);

            mParam1 = getArguments().getString(ARG_TITOL);

        }
    }

    //Se asocia el layout al Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_wellcome, container, false);
        caixaTitol = (TextView) v.findViewById(R.id.LabelMensaje);
        caixaTitol.setText(mParam1);

        return v;
    }


    //Se adjunta el Fragment a la Actividad
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComunicadorFragmentDinamic) {
            mListener = (ComunicadorFragmentDinamic) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    //Se libera el Fragment a la Actividad
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface ComunicadorFragmentDinamic {
        void onFragmentInteraction(Uri uri);
    }

}
