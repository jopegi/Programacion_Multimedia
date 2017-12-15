package josevi.android.com.sqlitecentroeducativo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import static josevi.android.com.sqlitecentroeducativo.R.id.lista;

public class ConsultasActivity extends AppCompatActivity {

    private EditText cajaCiclo, cajaCurso;
    private Button botonBuscarXCiclo, botonBuscarXCurso, botonBuscarXCicloXCurso,
                   botonMostrarTodos, botonMostrarTablas;
    private RadioButton radioBtnProfesores, radioBtnAlumnos;
    private ListView miLista;
    private DataBaseHelper miDataBaseHelper;
    private Cursor cursor;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        cajaCiclo = (EditText) findViewById(R.id.cajaCiclo);
        cajaCurso = (EditText) findViewById(R.id.cajaCurso);

        botonBuscarXCiclo = (Button) findViewById(R.id.btnBuscarXCiclo);
        botonBuscarXCurso = (Button) findViewById(R.id.btnBuscarXCurso);
        botonBuscarXCicloXCurso = (Button) findViewById(R.id.btnBuscarXCicloXCurso);
        botonMostrarTodos = (Button) findViewById(R.id.btnMostrarTodos);
        botonMostrarTablas = (Button) findViewById(R.id.btnMostrarTablas);

        radioBtnProfesores = (RadioButton) findViewById(R.id.rbProfesores);
        radioBtnAlumnos = (RadioButton) findViewById(R.id.rbAlumnos);

        miLista = (ListView) findViewById(lista);

        //Instaciamos un objeto de tipo SQLiteOpenHelper
        miDataBaseHelper = new DataBaseHelper(this, DataBaseHelper.DATABASE_NAME, null, DataBaseHelper.DATABASE_VERSION);


        botonBuscarXCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioBtnProfesores.isChecked()) {

                    ArrayList<String> profesores = new ArrayList<String>();

                    SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

                    Cursor cursor = buscarProfesoresPorCiclo(cajaCiclo.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            profesores.add(cursor.getString(1) + "  " + cursor.getString(2) + "  " +
                                    cursor.getString(3) + "  " + cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                    /*String[] miarray = new String[profesores.size()];
                    miarray = profesores.toArray(miarray);

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, miarray);*/

                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, profesores);

                    //Mostramos los resultados en un listView
                    miLista.setAdapter(adaptador);
                }

                if(radioBtnAlumnos.isChecked()){

                    ArrayList<String> alumnos = new ArrayList<String>();

                    SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

                    Cursor cursor = buscarAlumnosPorCiclo(cajaCiclo.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            alumnos.add(cursor.getString(1) + "  " + cursor.getString(2) + "  " +
                                    cursor.getString(3) + "  " + cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                    /*String[] miarray = new String[alumnos.size()];
                    miarray = alumnos.toArray(miarray);

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, miarray);*/

                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, alumnos);

                    //Mostramos los resultados en un listView
                    miLista.setAdapter(adaptador);
                }


            }
        });

        botonBuscarXCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioBtnProfesores.isChecked()) {

                    ArrayList<String> profesores = new ArrayList<String>();

                    SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

                    Cursor cursor = buscarProfesoresPorCurso(cajaCurso.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            profesores.add(cursor.getString(1) + "  " + cursor.getString(2) + "  " +
                                    cursor.getString(4) + "  " + cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado los registros!", Toast.LENGTH_SHORT).show();

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, profesores);

                    //Mostramos los resultados en un listView
                    miLista.setAdapter(adaptador);
                }

                if(radioBtnAlumnos.isChecked()){

                    ArrayList<String> alumnos = new ArrayList<String>();

                    SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

                    Cursor cursor = buscarAlumnosPorCurso(cajaCurso.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            alumnos.add(cursor.getString(1) + "  " + cursor.getString(2) + "  " +
                                    cursor.getString(4) + "  " + cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado los registros!", Toast.LENGTH_SHORT).show();

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, alumnos);

                    //Mostramos los resultados en un listView
                    miLista.setAdapter(adaptador);
                }

            }
        });

        botonBuscarXCicloXCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioBtnProfesores.isChecked()){

                    ArrayList<String> profesores = new ArrayList<String>();

                    SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

                    Cursor cursor = buscarProfesoresPorCursoYCiclo(cajaCurso.getText().toString(),cajaCiclo.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            profesores.add(cursor.getString(1) + "  " + cursor.getString(2)+"  "+ cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado los registros!", Toast.LENGTH_SHORT).show();

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, profesores);

                    //Mostramos los resultados en un listView
                    miLista.setAdapter(adaptador);
                }

                if(radioBtnAlumnos.isChecked()){

                    ArrayList<String> alumnos = new ArrayList<String>();

                    SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

                    Cursor cursor = buscarAlumnosPorCursoYCiclo(cajaCurso.getText().toString(),cajaCiclo.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            alumnos.add(cursor.getString(1) + "  " + cursor.getString(2) + "  " +cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado todos registros!", Toast.LENGTH_SHORT).show();

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, alumnos);

                    //Mostramos los resultados en un listView
                    miLista.setAdapter(adaptador);
                }

            }
        });

        botonMostrarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioBtnProfesores.isChecked()) {
                    //Recuperamos en un ArrayList el resultado del método recuperarProfesores()
                    ArrayList<String> prof = recuperarProfesores();

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, prof);

                    //Mostramos los resultados en un listView
                    miLista.setAdapter(adaptador);
                }

                if(radioBtnAlumnos.isChecked()){

                    //Recuperamos en un ArrayList el resultado del método recuperarProfesores()
                    ArrayList<String> al = recuperarAlumnos();

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, al);

                    //Mostramos los resultados en un listView
                    miLista.setAdapter(adaptador);

                }

            }
        });

        botonMostrarTablas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> tabla1 = recuperarProfesores();
                ArrayList<String> tabla2 = recuperarAlumnos();

                ArrayList<String> tablas = new ArrayList<String>();

                tablas.addAll(tabla1);
                tablas.addAll(tabla2);

                //Insertamos en un adaptador el Array
                adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, tablas);

                //Mostramos los resultados en un listView
                miLista.setAdapter(adaptador);
            }
        });

    }

    public Cursor buscarProfesoresPorCiclo(String ciclo){

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_PROFESOR,EstructuraBBDD.EDAD_PROFESOR,EstructuraBBDD.CICLO_PROFESOR,
                EstructuraBBDD.CURSO_PROFESOR,EstructuraBBDD.DESPACHO_PROFESOR};

        SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.TABLE_PROFESORES,columnas,EstructuraBBDD.CICLO_PROFESOR + "=?",new String []{ciclo},null,null,null);

    }

    public Cursor buscarAlumnosPorCiclo(String ciclo){

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_ALUMNO,EstructuraBBDD.EDAD_ALUMNO,EstructuraBBDD.CICLO_ALUMNO,
                EstructuraBBDD.CURSO_ALUMNO,EstructuraBBDD.NOTA_MEDIA_ALUMNO};

        SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.TABLE_ALUMNOS,columnas,EstructuraBBDD.CURSO_ALUMNO + "=?",new String []{ciclo},null,null,null);

    }

    public Cursor buscarProfesoresPorCurso(String curso){

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_PROFESOR,EstructuraBBDD.EDAD_PROFESOR,EstructuraBBDD.CICLO_PROFESOR,
                EstructuraBBDD.CURSO_PROFESOR,EstructuraBBDD.DESPACHO_PROFESOR};

        SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.TABLE_PROFESORES,columnas,EstructuraBBDD.CURSO_PROFESOR + "=?",new String []{curso},null,null,null);
    }

    public Cursor buscarAlumnosPorCurso(String curso){

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_ALUMNO,EstructuraBBDD.EDAD_ALUMNO,EstructuraBBDD.CICLO_ALUMNO,
                EstructuraBBDD.CURSO_ALUMNO,EstructuraBBDD.NOTA_MEDIA_ALUMNO};

        SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.TABLE_ALUMNOS,columnas,EstructuraBBDD.CURSO_ALUMNO + "=?",new String []{curso},null,null,null);
    }

    public Cursor buscarProfesoresPorCursoYCiclo(String curso, String ciclo){

        SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM profesores WHERE curso = ? AND ciclo= ?", new String[] {curso, ciclo});

        return cursor;
    }

    public Cursor buscarAlumnosPorCursoYCiclo(String curso, String ciclo){

        SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM alumnos WHERE curso = ? AND ciclo= ?", new String[] {curso, ciclo});

        return cursor;
    }

    public ArrayList<String> recuperarProfesores() {

        ArrayList<String> profesores = new ArrayList<String>();

        SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

        Cursor cursor = db.query("profesores",null,null,null,null,null,null);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                profesores.add(cursor.getString(1)+" "+ cursor.getString(2) +" "+
                        cursor.getString(3)+" "+ cursor.getString(4));

            } while (cursor.moveToNext());
        }

        db.close();

        //Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

        return profesores;
    }

    public ArrayList<String> recuperarAlumnos() {

        ArrayList<String> alumnos = new ArrayList<String>();

        SQLiteDatabase db = miDataBaseHelper.getReadableDatabase();

        Cursor cursor = db.query("alumnos",null,null,null,null,null,null);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                alumnos.add(cursor.getString(1)+" "+ cursor.getString(2) +" "+
                        cursor.getString(3)+" "+ cursor.getString(4));

            } while (cursor.moveToNext());
        }

        db.close();

        //Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

        return alumnos;
    }
}
