package josevi.android.com.sqlitedatabase;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class VisorConsultasActivity extends AppCompatActivity {

    private AlumnoActivity aa;
    private ProfesorActivity pa;
    private EstructuraBBDD ebd;
    private BaseDatosHelper db;
    private Cursor cursor;

    private ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_consultas);

        lista = (ListView) findViewById(R.id.listView);
    }
}
