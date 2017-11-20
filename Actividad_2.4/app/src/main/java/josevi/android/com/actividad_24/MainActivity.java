package josevi.android.com.actividad_24;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnLongClickListener{

    private ToggleButton btCambioColorLayer;
    private ToggleButton btCambioColorTexto;
    private LinearLayout llCambioColor;
    private CheckBox cB;
    private TextView tView;
    private TextView tViewToast;
    private TextView tViewRating;
    private RatingBar rB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Se obtiene un referencia del TextView en el que se implementará el click largo
        tViewToast = (TextView) findViewById(R.id.textViewClickLargo);

        //Se emplea el método setOnLongClickListener que recibe como parámetro de entrada
        //la vista actual
        tViewToast.setOnLongClickListener(this);

        // Se obtiene una referencia del RatingBar del Layout
        rB = (RatingBar) findViewById(R.id.ratingBar2);

        //Se obtiene un referencia del TextView en el que se mostrará la cantidad de estrellas del
        //RatingBar
        tViewRating = (TextView) findViewById(R.id.textViewRatting);


        //Con el método setOnRatingBarChangeListener propio de la clase RatingBar
        //añado un Listener de eventos haciendo uso de una clase anónima, dentro de
        // la cual se implementa el método onRatingChanged encargado de definir la
        //lógica que se ejecutará tras elegir un detreminado número de estrellas del
        //RatingBar
        rB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                tViewRating.setText( "[" + Math.round(rating) + "/ 5]");
            }
        });

    }


    //Creamos el método onToggle1Clicked que será llamado desde
    //activity_main.xml, haciendo uso de la propiedad
    //android:onClick="onToggle1Clicked"
    public void onToggle1Clicked(View view) {

        llCambioColor = (LinearLayout) findViewById(R.id.LayoutVertical);

        btCambioColorLayer = (ToggleButton) findViewById(R.id.toggleButton1);

        if(btCambioColorLayer.isChecked()) {
            llCambioColor.setBackgroundColor(Color.RED);
        } else {
            llCambioColor.setBackgroundColor(Color.WHITE);
        }
    }

    //Creamos el método onToggle2Clicked que será llamado desde
    //activity_main.xml, haciendo uso de la propiedad
    //android:onClick="onToggle2Clicked"
    public void onToggle2Clicked(View view) {

        btCambioColorTexto = (ToggleButton) findViewById(R.id.toggleButton2);

        if(btCambioColorTexto.isChecked()) {

            btCambioColorTexto.setTextColor(Color.RED);

        } else {

          btCambioColorTexto.setTextColor(Color.BLACK);
        }
    }

    //Creamos el método onCheckBoxChecked que será llamado desde
    //activity_main.xml, haciendo uso de la propiedad
    //android:onClick="onCheckBoxChecked"
    public void onCheckBoxChecked (View view){

        cB = (CheckBox) findViewById(R.id.checkBox);
        tView = (TextView) findViewById(R.id.textViewMensajeOculto);

        if(cB.isChecked()){
            tView.setText("Este es el mensaje oculto");
        }else{
            tView.setText("");
        }

    }

    //Sobreescribimos el método abstracto de la interfaz OnLongClickListener
    @Override
    public boolean onLongClick(View v) {

        //Definimos un Toast encargado de mostrar el mensaje que nos interese
        Toast.makeText(getApplicationContext(), "¡Muchas gracias! " , Toast.LENGTH_SHORT).show();

        return false;
    }


}
