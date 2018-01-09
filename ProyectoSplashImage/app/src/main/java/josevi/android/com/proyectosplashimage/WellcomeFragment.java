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

    private static final String ARG_PARAM1 = "titol";
    private String mParam1;
    private TextView caixaTitol;
    private ComunicadorFragmentDinamic mListener;

    public WellcomeFragment() {
        //Se requiere que el constructor esté vacío
    }

    //Método newInstance() que se utiliza para que el fragment
    //pueda recibir parámetros
    public static WellcomeFragment newInstance(String param1) {
        WellcomeFragment fragment = new WellcomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

                View v =inflater.inflate(R.layout.fragment_wellcome, container, false);
                caixaTitol = (TextView) v.findViewById(R.id.LabelMensaje);
                caixaTitol.setText(mParam1);

                return v;
    }


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
