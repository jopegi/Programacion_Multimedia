package josevi.android.com.sqlite_proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InsertarProfesoresActivity extends AppCompatActivity {

    private Button botonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_profesores);

        botonVolver = (Button) findViewById(R.id.btnVolver);

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentVolver = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intentVolver);
            }
        });
    }
}
