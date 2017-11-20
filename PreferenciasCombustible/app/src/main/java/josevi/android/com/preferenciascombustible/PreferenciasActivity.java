package josevi.android.com.preferenciascombustible;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by josevi on 20/11/2017.
 */

public class PreferenciasActivity extends Activity {

    //Objectos de XMl.
    EditText dia;
    EditText mes;
    EditText dinero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferencias);

        //Iniciamos Objetos del XML.
        dia = (EditText) findViewById(R.id.editText);
        mes = (EditText) findViewById(R.id.editText2);
        dinero = (EditText) findViewById(R.id.editText3);
    }


    public void recuperaDatos(){

        //Creamos un Objeto con las preferencias establecidas.
        SharedPreferences sp = getSharedPreferences("personalInformation", MODE_PRIVATE);

        //Añadimos a los TextView el valor de las preferencias recogidas.
        dia.setText(sp.getString("dia",""));
        mes.setText(sp.getString("mes",""));
        dinero.setText(sp.getString("dinero",""));

    }
}
