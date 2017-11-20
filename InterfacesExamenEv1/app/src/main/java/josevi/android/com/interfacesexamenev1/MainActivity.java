package josevi.android.com.interfacesexamenev1;

import android.graphics.Color;
import android.hardware.camera2.params.BlackLevelPattern;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    private TextView cajaTexto;
    private RadioButton botonFondoNegro;
    private RadioButton botonFondoVerde;
    private RadioButton botonFondoRojo;
    private RadioButton botonTextoBlanco;
    private RadioButton botonTextoAmarillo;
    private RadioButton botonTextoAzul;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cajaTexto = (TextView) findViewById(R.id.textView);
        botonFondoNegro = (RadioButton) findViewById(R.id.radioButton7);
        botonFondoVerde = (RadioButton) findViewById(R.id.radioButton8);
        botonFondoRojo = (RadioButton) findViewById(R.id.radioButton9);

        botonTextoBlanco = (RadioButton) findViewById(R.id.radioButton6);
        botonTextoAmarillo = (RadioButton) findViewById(R.id.radioButton5);
        botonTextoAzul = (RadioButton) findViewById(R.id.radioButton4);

    }

    //Creamos el método onCheckBoxChecked que será llamado desde
    //activity_main.xml, haciendo uso de la propiedad
    //android:onClick="onCheckBox"
    public void onCheckBox (View view){

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        cajaTexto = (TextView) findViewById(R.id.textView);

        if(checkBox.isChecked()){
            cajaTexto.setText("TextoPrueba");
        }else{
            cajaTexto.setText("");
        }
    }

    public void cambioColorTexto (View view){

        cajaTexto = (TextView) findViewById(R.id.textView);
        botonTextoBlanco = (RadioButton) findViewById(R.id.radioButton6);
        botonTextoAmarillo = (RadioButton) findViewById(R.id.radioButton5);
        botonTextoAzul = (RadioButton) findViewById(R.id.radioButton4);

        if(botonTextoBlanco.isChecked()){
            cajaTexto.setTextColor(Color.parseColor("#FFFFFF"));
        }

        if(botonTextoAmarillo.isChecked()){
            cajaTexto.setTextColor(Color.parseColor("#ffffbb33"));
        }

        if(botonTextoAzul.isChecked()){
            cajaTexto.setTextColor(Color.parseColor("#d7ecfd"));
        }

    }

    public void cambioColorFondo (View view){

        cajaTexto = (TextView) findViewById(R.id.textView);
        botonFondoNegro = (RadioButton) findViewById(R.id.radioButton7);
        botonFondoVerde = (RadioButton) findViewById(R.id.radioButton8);
        botonFondoRojo = (RadioButton) findViewById(R.id.radioButton9);

        if(botonFondoNegro.isChecked()){
            cajaTexto.setBackgroundColor(Color.parseColor("#000000"));
        }

        if(botonFondoVerde.isChecked()){
            cajaTexto.setBackgroundColor(Color.parseColor("#04B404"));
        }

        if(botonFondoRojo.isChecked()){
            cajaTexto.setBackgroundColor(Color.parseColor("#FF0000"));
        }

    }
}



