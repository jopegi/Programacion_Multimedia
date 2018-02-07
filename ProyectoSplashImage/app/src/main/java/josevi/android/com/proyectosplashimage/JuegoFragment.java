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

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JuegoFragment.ComunicadorFragmentJuego} interface
 * to handle interaction events.
 * Use the {@link JuegoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JuegoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NICK_USU = "nick";
    private static final String ARG_NOMBRE_USU = "nombre";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    public JuegoFragment() {
        // Required empty public constructor
    }


    public static JuegoFragment newInstance(String nick, String nombre) {
        JuegoFragment fragment = new JuegoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NICK_USU, nick);
        args.putString(ARG_NOMBRE_USU, nombre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_NICK_USU);
            mParam2 = getArguments().getString(ARG_NOMBRE_USU);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_juego, container, false);
        labelNick = (TextView) v.findViewById(R.id.textViewNick);
        labelNick.setText(mParam1);
        labelNombre = (TextView) v.findViewById(R.id.textViewPuntos);
        labelNombre.setText(mParam2);
        imagenDado = (ImageView) v.findViewById(R.id.imageViewDado);
        botonLanzar = (Button) v.findViewById(R.id.btnLanzar);
        botonStop = (Button) v.findViewById(R.id.btnStop);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        sp = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build();

        soundTrain = sp.load(getContext(), R.raw.train_sound, 1);

        flujodemusica= sp.load(getContext(),R.raw.train_sound,1);

        botonLanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mp != null || soundPoolPlay != 0){
                    mp.stop();
                    //sp.stop(soundTrain);

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
