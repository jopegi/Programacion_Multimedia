package josevi.android.com.sqlitedatabase;

import android.app.ListActivity;
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
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import static josevi.android.com.sqlitedatabase.BaseDatosHelper.DATABASE_NAME;

public class ConsultasActivity extends AppCompatActivity {


    private Button botonMostrar, botonMostrarTodos, botonCiclo, botonCurso;
    private EditText cajaCiclo, cajaCurso;
    private RadioButton radioProfesores, radioAlumnos;
    private BaseDatosHelper miBBDDHelper = null;
    private String tableName = null;
    private String ciclo = null;
    private String curso = null;
    private ListView lista;
    private RadioGroup radiogrupo;

    private EstructuraBBDD estructura;
    private BaseDatosHelper helper;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    private ArrayAdapter<String> adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        //Instaciamos un objeto de tipo SQLiteOpenHelper
        miBBDDHelper = new BaseDatosHelper(this, DATABASE_NAME, null, BaseDatosHelper.DATABASE_VERSION);

        botonMostrar = (Button) findViewById(R.id.btnBuscarCicloCurso);
        botonMostrarTodos = (Button) findViewById(R.id.btnMostrarTodos);
        botonCiclo = (Button) findViewById(R.id.btnBuscarCiclo);
        botonCurso = (Button) findViewById(R.id.btnBuscarCurso);

        cajaCiclo = (EditText) findViewById(R.id.editTextCiclo);
        cajaCurso = (EditText) findViewById(R.id.editTextCurso);

        radioProfesores = (RadioButton) findViewById(R.id.radioBtnProfesores);
        radioAlumnos = (RadioButton) findViewById(R.id.radioBtnAlumnos);

        lista = (ListView) findViewById(R.id.listView);

        radiogrupo = (RadioGroup) findViewById(R.id.radioGroup);

        //*****BOTON FILTRAR POR CICLO**************************************************
        botonCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioProfesores.isChecked()) {

                    ArrayList<String> profesores = new ArrayList<String>();

                    SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

                    Cursor cursor = buscarProfesoresPorCiclo(cajaCiclo.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            profesores.add(cursor.getString(1) + " " + cursor.getString(2) + " " +
                                    cursor.getString(3) + " " + cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                    String[] miarray = new String[profesores.size()];
                    miarray = profesores.toArray(miarray);

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, miarray);

                    //Mostramos los resultados en un listView
                    lista.setAdapter(adaptador);
                }

                if(radioAlumnos.isChecked()){

                    ArrayList<String> alumnos = new ArrayList<String>();

                    SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

                    Cursor cursor = buscarAlumnosPorCiclo(cajaCiclo.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            alumnos.add(cursor.getString(1) + " " + cursor.getString(2) + " " +
                                    cursor.getString(3) + " " + cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                    String[] miarray = new String[alumnos.size()];
                    miarray = alumnos.toArray(miarray);

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, miarray);

                    //Mostramos los resultados en un listView
                    lista.setAdapter(adaptador);
                }

            }
        });

        //*****BOTON FILTRAR POR CURSO**************************************************
        botonCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioProfesores.isChecked()) {

                    ArrayList<String> profesores = new ArrayList<String>();

                    SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

                    Cursor cursor = buscarProfesoresPorCurso(cajaCurso.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            profesores.add(cursor.getString(1) + " " + cursor.getString(2) + " " +
                                    cursor.getString(4) + " " + cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                    String[] miarray = new String[profesores.size()];
                    miarray = profesores.toArray(miarray);

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, miarray);

                    //Mostramos los resultados en un listView
                    lista.setAdapter(adaptador);
                }

                if(radioAlumnos.isChecked()){

                    ArrayList<String> alumnos = new ArrayList<String>();

                    SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

                    Cursor cursor = buscarAlumnosPorCurso(cajaCurso.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            alumnos.add(cursor.getString(1) + " " + cursor.getString(2) + " " +
                                    cursor.getString(4) + " " + cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                    String[] miarray = new String[alumnos.size()];
                    miarray = alumnos.toArray(miarray);

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, miarray);

                    //Mostramos los resultados en un listView
                    lista.setAdapter(adaptador);
                }

            }

        });

        //*****BOTON FILTRAR POR CURSO Y CICLO*******************************************
        botonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioProfesores.isChecked()){

                    ArrayList<String> profesores = new ArrayList<String>();

                    SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

                    Cursor cursor = buscarProfesoresPorCursoYCiclo(cajaCurso.getText().toString(),cajaCiclo.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            profesores.add(cursor.getString(1) + " " + cursor.getString(2)+" "+ cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                    String[] miarray = new String[profesores.size()];
                    miarray = profesores.toArray(miarray);

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, miarray);

                    //Mostramos los resultados en un listView
                    lista.setAdapter(adaptador);
                }

                if(radioAlumnos.isChecked()){

                    ArrayList<String> alumnos = new ArrayList<String>();

                    SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

                    Cursor cursor = buscarAlumnosPorCursoYCiclo(cajaCurso.getText().toString(),cajaCiclo.getText().toString());

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            alumnos.add(cursor.getString(1) + " " + cursor.getString(2) + " " +cursor.getString(5));

                        } while (cursor.moveToNext());
                    }

                    db.close();

                    Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                    String[] miarray = new String[alumnos.size()];
                    miarray = alumnos.toArray(miarray);

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, miarray);

                    //Mostramos los resultados en un listView
                    lista.setAdapter(adaptador);
                }

            }
        });

        //*****BOTON MOSTRAR TODOS**************************************************
        botonMostrarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioProfesores.isChecked()) {
                    //Recuperamos en un ArrayList el resultado del método recuperarProfesores()
                    ArrayList<String> prof = recuperarProfesores();

                    //Transformamos el ArrayList anterior en un Array
                    String[] miarray = new String[prof.size()];
                    miarray = prof.toArray(miarray);

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, miarray);

                    //Mostramos los resultados en un listView
                    lista.setAdapter(adaptador);
                }

                if(radioAlumnos.isChecked()){

                    //Recuperamos en un ArrayList el resultado del método recuperarProfesores()
                    ArrayList<String> al = recuperarAlumnos();

                    //Transformamos el ArrayList anterior en un Array
                    String[] miarray = new String[al.size()];
                    miarray = al.toArray(miarray);

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, miarray);

                    //Mostramos los resultados en un listView
                    lista.setAdapter(adaptador);

                }

            }
        });
    }



    //**********MÉTODO PARA RECUPERAR TODOS LOS PROFESORES*****************************
    public ArrayList<String> recuperarProfesores() {

        ArrayList<String> profesores = new ArrayList<String>();

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        Cursor cursor = db.query("profesores",null,null,null,null,null,null);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                profesores.add(cursor.getString(1)+" "+ cursor.getString(2) +" "+
                        cursor.getString(3)+" "+ cursor.getString(4));

            } while (cursor.moveToNext());
        }

        db.close();

        Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

        return profesores;
    }

    //**********MÉTODO PARA RECUPERAR TODOS LOS ALUMNOS*****************************
    public ArrayList<String> recuperarAlumnos() {

        ArrayList<String> alumnos = new ArrayList<String>();

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        Cursor cursor = db.query("alumnos",null,null,null,null,null,null);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                alumnos.add(cursor.getString(1)+" "+ cursor.getString(2) +" "+
                        cursor.getString(3)+" "+ cursor.getString(4));

            } while (cursor.moveToNext());
        }

        db.close();

        Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

        return alumnos;
    }


    //***************************************************
    //***************************************************
    public Cursor cargarCursorProfesores(){

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_PROFESOR,EstructuraBBDD.EDAD_PROFESOR,EstructuraBBDD.CICLO_PROFESOR,
                EstructuraBBDD.CURSO_PROFESOR,EstructuraBBDD.DESPACHO_PROFESOR};

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.DATABASE_TABLE_PROFESORES,columnas,null,null,null,null,null);

    }
    //********************************************************
    //********************************************************

    //***************************************************
    //***************************************************
    public Cursor cargarCursorAlumnos(){

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_ALUMNO,EstructuraBBDD.EDAD_ALUMNO,EstructuraBBDD.CICLO_ALUMNO,
                EstructuraBBDD.CURSO_ALUMNO,EstructuraBBDD.NOTA_MEDIA_ALUMNO};

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.DATABASE_TABLE_PROFESORES,columnas,null,null,null,null,null);

    }
    //********************************************************
    //********************************************************

    //***************************************************
    //***************************************************
    public Cursor buscarProfesoresPorCurso(String curso){

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_PROFESOR,EstructuraBBDD.EDAD_PROFESOR,EstructuraBBDD.CICLO_PROFESOR,
                EstructuraBBDD.CURSO_PROFESOR,EstructuraBBDD.DESPACHO_PROFESOR};

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.DATABASE_TABLE_PROFESORES,columnas,EstructuraBBDD.CURSO_PROFESOR + "=?",new String []{curso},null,null,null);

    }
    //********************************************************
    //********************************************************

    public Cursor buscarProfesoresPorCiclo(String ciclo){

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_PROFESOR,EstructuraBBDD.EDAD_PROFESOR,EstructuraBBDD.CICLO_PROFESOR,
                EstructuraBBDD.CURSO_PROFESOR,EstructuraBBDD.DESPACHO_PROFESOR};

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.DATABASE_TABLE_PROFESORES,columnas,EstructuraBBDD.CICLO_PROFESOR + "=?",new String []{ciclo},null,null,null);

    }
    //********************************************************
    //********************************************************

    //***************************************************
    //***************************************************
    public Cursor buscarAlumnosPorCurso(String curso){

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_ALUMNO,EstructuraBBDD.EDAD_ALUMNO,EstructuraBBDD.CICLO_ALUMNO,
                EstructuraBBDD.CURSO_ALUMNO,EstructuraBBDD.NOTA_MEDIA_ALUMNO};

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.DATABASE_TABLE_ALUMNOS,columnas,EstructuraBBDD.CURSO_ALUMNO + "=?",new String []{curso},null,null,null);

    }
    //********************************************************
    //********************************************************

    public Cursor buscarAlumnosPorCiclo(String ciclo){

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_ALUMNO,EstructuraBBDD.EDAD_ALUMNO,EstructuraBBDD.CICLO_ALUMNO,
                EstructuraBBDD.CURSO_ALUMNO,EstructuraBBDD.NOTA_MEDIA_ALUMNO};

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.DATABASE_TABLE_ALUMNOS,columnas,EstructuraBBDD.CURSO_ALUMNO + "=?",new String []{ciclo},null,null,null);

    }
    //********************************************************
    //********************************************************

    //***************************************************
    //***************************************************
    public Cursor buscarProfesoresPorCursoYCiclo(String curso, String ciclo){

        /*String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_PROFESOR,EstructuraBBDD.EDAD_PROFESOR,EstructuraBBDD.CURSO_PROFESOR,
                EstructuraBBDD.CICLO_PROFESOR,EstructuraBBDD.DESPACHO_PROFESOR};*/

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM profesores WHERE curso = ? AND ciclo= ?", new String[] {curso, ciclo});

        //return db.query(EstructuraBBDD.DATABASE_TABLE_PROFESORES,columnas,EstructuraBBDD.CURSO_PROFESOR + " = '?' AND " + EstructuraBBDD.CICLO_PROFESOR+" = '?'",new String []{curso,ciclo},null,null,null);

        return cursor;
    }
    //********************************************************
    //********************************************************

    //***************************************************
    //***************************************************
    public Cursor buscarAlumnosPorCursoYCiclo(String curso, String ciclo){

        /*String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_ALUMNO,EstructuraBBDD.EDAD_ALUMNO,EstructuraBBDD.CURSO_ALUMNO,
                EstructuraBBDD.CICLO_ALUMNO,EstructuraBBDD.NOTA_MEDIA_ALUMNO};*/

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM alumnos WHERE curso = ? AND ciclo= ?", new String[] {curso, ciclo});



        //return db.query(EstructuraBBDD.DATABASE_TABLE_ALUMNOS,columnas,EstructuraBBDD.CURSO_ALUMNO + " = '?' AND " + EstructuraBBDD.CICLO_ALUMNO + " = '?'",new String []{curso,ciclo},null,null,null);

        return cursor;
    }
    //********************************************************
    //********************************************************
}
