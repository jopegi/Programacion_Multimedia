package josevi.android.com.proyectosplashimage;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class JuegoFragment extends Fragment {

    private static final String ARG_ID_USU = "userId";

    private String mParam1;

    private TextView labelNick, labelNombre;

    private ImageView imagenDado;

    private Button botonLanzar, botonStop;

    private int tiradaDado;

    private int min = 1;
    private int max = 3;

    public MediaPlayer mp;
    public SoundPool sp;
    public int flujodemusica;
    private int soundTrain;
    private int soundPoolPlay;

    private ComunicadorFragmentJuego mListener;

    //Objeto que hará referencia a la BBDD FireBase
    DatabaseReference referenciaBaseDatos;

    private Usuario usu;
    private String nombre;
    private String nick;

    public JuegoFragment() {
        // Required empty public constructor
    }


    public static JuegoFragment newInstance(String userId) {
        JuegoFragment fragment = new JuegoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_USU, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_ID_USU);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_juego, container, false);
        labelNick = (TextView) v.findViewById(R.id.textViewNick);

        labelNombre = (TextView) v.findViewById(R.id.textViewPuntos);

        imagenDado = (ImageView) v.findViewById(R.id.imageViewDado);
        botonLanzar = (Button) v.findViewById(R.id.btnLanzar);
        botonStop = (Button) v.findViewById(R.id.btnStop);

        referenciaBaseDatos = FirebaseDatabase.getInstance().getReference("usuarios");
        Query q = referenciaBaseDatos.orderByChild("uid_key").equalTo(mParam1);

        //****************ESCUCHARÁ CUALQUIER CAMBIO EN EL NODO usuarios DE LA BBDD***********
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot i: dataSnapshot.getChildren()){

                    usu = i.getValue(Usuario.class);
                    nombre = usu.getNombre();
                    nick = usu.getNick();
                }
                labelNick.setText(nick);
                labelNombre.setText(nombre);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        sp = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build();

        soundTrain = sp.load(getContext(), R.raw.train_sound, 1);

        //flujodemusica= sp.load(getContext(),R.raw.train_sound,1);

        botonLanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mp != null || soundPoolPlay != 0){
                    mp.stop();
                    sp.stop(soundTrain);

                    tiradaDado = new Random().nextInt(max - min + 1) + min;

                    switch (tiradaDado) {
                        case 1:
                            imagenDado.setImageResource(R.drawable.dado1_150x150);
                            mp = MediaPlayer.create(getContext(), R.raw.latin_dance);
                            mp.start();
                            Toast.makeText(getContext(), "Està escuchando música latina!!", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            imagenDado.setImageResource(R.drawable.dado2_150x150);
                            mp = MediaPlayer.create(getContext(), R.raw.tropical_future_bass);
                            mp.start();
                            Toast.makeText(getContext(), "Està escuchando música dance!!", Toast.LENGTH_SHORT).show();
                            break;

                        case 3:

                            imagenDado.setImageResource(R.drawable.dado3_150x150);
                            mp = MediaPlayer.create(getContext(), R.raw.train_sound);
                            mp.start();
                            Toast.makeText(getContext(), "Està escuchando una locomotora!!", Toast.LENGTH_SHORT).show();
                            break;

                            /*
                            imagenDado.setImageResource(R.drawable.dado3_150x150);
                            soundPoolPlay = sp.play (soundTrain, 0.9f, 0.9f, 1, 0, 1);
                            break;
                            */
                    }

                }else {

                    tiradaDado = new Random().nextInt(max - min + 1) + min;

                    switch (tiradaDado) {
                        case 1:
                            imagenDado.setImageResource(R.drawable.dado1_150x150);
                            mp = MediaPlayer.create(getContext(), R.raw.latin_dance);
                            mp.start();
                            Toast.makeText(getContext(), "Està escuchando música latina!!", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            imagenDado.setImageResource(R.drawable.dado2_150x150);
                            mp = MediaPlayer.create(getContext(), R.raw.tropical_future_bass);
                            mp.start();
                            Toast.makeText(getContext(), "Està escuchando música dance!!", Toast.LENGTH_SHORT).show();
                            break;

                        case 3:
                            imagenDado.setImageResource(R.drawable.dado3_150x150);
                            //soundPoolPlay = sp.play (soundTrain, 0.9f, 0.9f, 1, 0, 1);
                            mp = MediaPlayer.create(getContext(), R.raw.train_sound);
                            mp.start();
                            Toast.makeText(getContext(), "Està escuchando una locomotora!!", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });

        botonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                //sp.stop(soundTrain);
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComunicadorFragmentJuego) {
            mListener = (ComunicadorFragmentJuego) context;
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
    public interface ComunicadorFragmentJuego {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
