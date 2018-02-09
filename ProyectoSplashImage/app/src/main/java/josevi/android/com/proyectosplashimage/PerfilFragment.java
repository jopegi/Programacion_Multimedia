package josevi.android.com.proyectosplashimage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PerfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USERID = "userId";
    private static final String ARG_PARAM2 = "param2";

    private EditText cajaNick, cajaNombre, cajaApellidos;
    private Button botonGuardar, botonOk;

    private ArrayList<String> listadoNicks;

    //Objeto que hará referencia a la BBDD FireBase
    DatabaseReference referenciaBaseDatos,referenciaBaseDatos2;

    private Usuario usu;
    private String nickUsuario;
    private String userId;

    private boolean esInsercio = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Obtenemos una referencia a la interfaz de comunicación del Fragment con la actividad que lo contiene
    private ComunicadorFragmentPerfil mListener;

    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance(String userId) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USERID);
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
        cajaApellidos = (EditText) v.findViewById(R.id.editTextApellidos);
        botonGuardar = (Button) v.findViewById(R.id.btnGuardar);
        botonOk = (Button) v.findViewById(R.id.btnOk);

        referenciaBaseDatos = FirebaseDatabase.getInstance().getReference("usuarios");

        //****************ESCUCHARÁ CUALQUIER CAMBIO EN EL NODO usuarios DE LA BBDD***********
        referenciaBaseDatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listadoNicks = new ArrayList<String>();

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    usu = i.getValue(Usuario.class);
                    nickUsuario = usu.getNick();
                    listadoNicks.add(nickUsuario);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println("Buscant usuari");
        Log.d("Josevi","Buscant usuari");
        referenciaBaseDatos2 = FirebaseDatabase.getInstance().getReference("usuarios");

        referenciaBaseDatos2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String nick = null;
                String nom = null,cognom = null;

                for (DataSnapshot i: dataSnapshot.getChildren()){
                    System.out.println("Usuari trobat");
                    Log.d("Josevi","Usuari trobat");
                    usu = i.getValue(Usuario.class);
                    if(usu.getUid_key().compareTo(userId) == 0){
                        nick = usu.getNick();
                        nom = usu.getNombre();
                        cognom = usu.getApellidos();
                    }

                }

                if(nick == null && nom == null && cognom == null){
                    esInsercio = true;
                    System.out.println("Inserció");
                    Log.d("Josevi","Inserció");
                }else{
                    Log.d("Josevi","Actualit.");
                    System.out.println("Actualit");
                    esInsercio = false;
                    cajaNombre.setText(nom);
                    cajaApellidos.setText(cognom);
                    cajaNick.setText(nick);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Evento del botón guardar de la interfaz
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(!cajaNick.getText().toString().isEmpty() && !cajaNombre.getText().toString().isEmpty() && !cajaApellidos.getText().toString().isEmpty()){
                        Usuario u1 = new Usuario(userId, cajaNick.getText().toString(), cajaNombre.getText().toString(), cajaApellidos.getText().toString());
                        if(!existeNick(u1.getNick())){
                            referenciaBaseDatos.child(userId).setValue(u1);
                            Toast.makeText(getContext(), "Usuario insertado!", Toast.LENGTH_SHORT).show();
                            mListener.mostrarWellcomeFragment();
                        }else{
                            Toast.makeText(getContext(), "El nick ya existe. Por favor, elija otro nick!", Toast.LENGTH_SHORT).show();
                        }

                    }else{

                        Toast.makeText(getActivity(), "Debe de rellenar los campos nombre y nick!", Toast.LENGTH_SHORT).show();
                    }
            }

        });

        //Evento del botón guardar de la interfaz
        botonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!cajaNick.getText().toString().isEmpty() && !cajaNombre.getText().toString().isEmpty() && !cajaApellidos.getText().toString().isEmpty()){
                    Usuario u1 = new Usuario(userId, cajaNick.getText().toString(), cajaNombre.getText().toString(), cajaApellidos.getText().toString());
                    referenciaBaseDatos.child(userId).setValue(u1);
                    mListener.mostrarWellcomeFragment();
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
        void mostrarWellcomeFragment();
    }


    //Método para comparar el nuevo nick con los ya existentes en FireBase
    public boolean existeNick (String nuevoNick){

        //Al nuevo nick le eliminamos los posibles espacios en blanco
        String nickTrim = nuevoNick.trim();

        boolean rtn = false;

        //Vamos recorriendo el listado de nicks existentes y comparándolos con
        //el nuevo nick que se pretende crear
        for (int j = 0; j<listadoNicks.size(); j++){

            //Se comparan las cadenas de texto ignorando las mayúsculas y minúsculas
            if(listadoNicks.get(j).equalsIgnoreCase(nickTrim)){
                rtn = true;

            }
        }
        return rtn;
    }
}
