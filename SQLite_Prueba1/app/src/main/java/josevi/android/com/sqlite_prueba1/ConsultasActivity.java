package josevi.android.com.sqlite_prueba1;

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

public class ConsultasActivity extends AppCompatActivity {

    private RadioButton radioBProfesores, radioBAlumnos, radioBCiclo, radioBCurso;
    private EditText cajaCurso, cajaCiclo;
    private ListView lista;
    private Button botonBuscar, botonLimpiar;

    DBHelper dbHelper;

    private ArrayAdapter<String> adaptador;



    /*
    RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
    radioGroup.clearCheck();
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        radioBProfesores = (RadioButton) findViewById(R.id.rbProfesores);
        radioBAlumnos = (RadioButton) findViewById(R.id.rbAlumnos);
        radioBCiclo = (RadioButton) findViewById(R.id.rbCiclo);
        radioBCurso = (RadioButton) findViewById(R.id.rbCurso);
        cajaCurso = (EditText) findViewById(R.id.editTextCurso);
        cajaCiclo = (EditText) findViewById(R.id.editTextCiclo);
        lista = (ListView) findViewById(R.id.listView1);
        botonBuscar = (Button) findViewById(R.id.btnBuscar);
        botonLimpiar = (Button) findViewById(R.id.btnLimpiar);

        //Instaciamos un objeto de tipo SQLiteOpenHelper
        dbHelper = new DBHelper(this, DBHelper.NOMBRE_DATABASE, null, DBHelper.VERSION_DATABASE);

        botonLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                limpiarRadioButtons();
            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//*******************************LÓGICA PROFESORES************************************************************************************

                //Todos los profesores y alumnos
                if (radioBProfesores.isChecked() && radioBAlumnos.isChecked()) {

                    ArrayList<String> tabla1 = recuperarProfesores();
                    ArrayList<String> tabla2 = recuperarAlumnos();

                    ArrayList<String> tablas = new ArrayList<String>();

                    tablas.addAll(tabla1);
                    tablas.addAll(tabla2);

                    //Insertamos en un adaptador el Array
                    adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, tablas);

                    //Mostramos los resultados en un listView
                    lista.setAdapter(adaptador);

                }

                if (radioBProfesores.isChecked() && !radioBAlumnos.isChecked()) {

                    //Profesores por curso
                    if (radioBCurso.isChecked() && !cajaCurso.getText().toString().isEmpty() && !radioBCiclo.isChecked()) {

                        Toast.makeText(ConsultasActivity.this, "Entramos!!", Toast.LENGTH_SHORT).show();

                        ArrayList<String> profesores = new ArrayList<String>();

                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        Cursor cursor = buscarProfesoresPorCurso(cajaCurso.getText().toString());

                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                profesores.add(cursor.getString(1) + "  " + cursor.getString(2) + "  " +
                                        cursor.getString(4) + "  " + cursor.getString(5));

                            } while (cursor.moveToNext());
                        }

                        db.close();

                        Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                        adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, profesores);

                        //Mostramos los resultados en un listView
                        lista.setAdapter(adaptador);


                    //Profesores por ciclo
                    }else if (radioBCiclo.isChecked() && !cajaCiclo.getText().toString().isEmpty() && !radioBCurso.isChecked()) {

                        ArrayList<String> profesores = new ArrayList<String>();

                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        Cursor cursor = buscarProfesoresPorCiclo(cajaCiclo.getText().toString());

                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                profesores.add(cursor.getString(1) + "  " + cursor.getString(2) + "  " +
                                        cursor.getString(3) + "  " + cursor.getString(5));

                            } while (cursor.moveToNext());
                        }

                        db.close();

                        Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                        adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, profesores);

                        //Mostramos los resultados en un listView
                        lista.setAdapter(adaptador);

                    //Todos los profesores
                    } else if (radioBProfesores.isChecked() && !radioBCurso.isChecked() && !radioBCiclo.isChecked()) {

                        //Recuperamos en un ArrayList el resultado del método recuperarProfesores()
                        ArrayList<String> prof = recuperarProfesores();

                        //Insertamos en un adaptador el Array
                        adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, prof);

                        //Mostramos los resultados en un listView
                        lista.setAdapter(adaptador);

                    //Profesores por ciclo y curso
                    } else if (radioBProfesores.isChecked() && radioBCurso.isChecked() && radioBCiclo.isChecked() &&
                            !cajaCurso.getText().toString().isEmpty() && !cajaCiclo.getText().toString().isEmpty()) {

                        ArrayList<String> profesores = new ArrayList<String>();

                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        Cursor cursor = buscarProfesoresPorCursoYCiclo(cajaCurso.getText().toString(), cajaCiclo.getText().toString());

                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                profesores.add(cursor.getString(1) + "  " + cursor.getString(2) + "  " +
                                        cursor.getString(3) + "  " + cursor.getString(5));

                            } while (cursor.moveToNext());
                        }

                        db.close();

                        Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                        adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, profesores);

                        //Mostramos los resultados en un listView
                        lista.setAdapter(adaptador);

                    }

                }


//*******************************LÓGICA ALUMNOS************************************************************************************

                if (!radioBProfesores.isChecked() && radioBAlumnos.isChecked()) {

                    //Alumnos por curso
                    if (radioBCurso.isChecked() && !cajaCurso.getText().toString().isEmpty() && !radioBCiclo.isChecked()) {

                        ArrayList<String> alumnos = new ArrayList<String>();

                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        Cursor cursor = buscarAlumnosPorCurso(cajaCurso.getText().toString());

                        if(cursor == null){

                        }

                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                alumnos.add(cursor.getString(1) + "  " + cursor.getString(2) + "  " +
                                        cursor.getString(3) + "  " + cursor.getString(5));

                            } while (cursor.moveToNext());

                        }

                        db.close();

                        Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!!!", Toast.LENGTH_SHORT).show();

                        adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, alumnos);

                        //Mostramos los resultados en un listView
                        lista.setAdapter(adaptador);


                    //Alumnos por ciclo
                    }else if (radioBCiclo.isChecked() && !cajaCiclo.getText().toString().isEmpty() && !radioBCurso.isChecked()) {

                        ArrayList<String> alumnos = new ArrayList<String>();

                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        Cursor cursor = buscarAlumnosPorCiclo(cajaCiclo.getText().toString());

                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                alumnos.add(cursor.getString(1) + "  " + cursor.getString(2) + "  " +
                                        cursor.getString(3) + "  " + cursor.getString(5));

                            } while (cursor.moveToNext());
                        }

                        db.close();

                        Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                        adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, alumnos);

                        //Mostramos los resultados en un listView
                        lista.setAdapter(adaptador);

                    //Todos los alumnos
                    } else if (radioBAlumnos.isChecked() && !radioBCurso.isChecked() && !radioBCiclo.isChecked()) {

                        //Recuperamos en un ArrayList el resultado del método recuperarAlumnos()
                        ArrayList<String> alum = recuperarAlumnos();

                        //Insertamos en un adaptador el Array
                        adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, alum);

                        //Mostramos los resultados en un listView
                        lista.setAdapter(adaptador);

                    //Alumnos por ciclo y curso
                    } else if (radioBAlumnos.isChecked() && radioBCurso.isChecked() && radioBCiclo.isChecked() &&
                            !cajaCurso.getText().toString().isEmpty() && !cajaCiclo.getText().toString().isEmpty()) {

                        ArrayList<String> alumnos = new ArrayList<String>();

                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        Cursor cursor = buscarAlumnosPorCursoYCiclo(cajaCurso.getText().toString(), cajaCiclo.getText().toString());

                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                alumnos.add(cursor.getString(1) + "  " + cursor.getString(2) + "  " +
                                        cursor.getString(3) + "  " + cursor.getString(5));

                            } while (cursor.moveToNext());
                        }

                        db.close();

                        Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

                        adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, alumnos);

                        //Mostramos los resultados en un listView
                        lista.setAdapter(adaptador);

                    }

                }

            }
        });

    }

    //*************************MÉTODOS DE LA CLASE******************************************************************************************

    public Cursor buscarProfesoresPorCiclo(String ciclo){

        String[] columnas = new String[]{"_id",DBEstructura.NOMBRE_PROFESOR,DBEstructura.EDAD_PROFESOR,DBEstructura.CICLO_PROFESOR,
                DBEstructura.CURSO_PROFESOR,DBEstructura.DESPACHO_PROFESOR};

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.query(DBEstructura.TABLA_PROFESORES,columnas,DBEstructura.CICLO_PROFESOR + "=?",new String []{ciclo},null,null,null);

    }

    public Cursor buscarAlumnosPorCiclo(String ciclo){

        String[] columnas = new String[]{"_id",DBEstructura.NOMBRE_ALUMNO,DBEstructura.EDAD_ALUMNO,DBEstructura.CICLO_ALUMNO,
                DBEstructura.CURSO_ALUMNO,DBEstructura.NOTA_ALUMNO};

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.query(DBEstructura.TABLA_ALUMNOS,columnas,DBEstructura.CICLO_ALUMNO + "=?",new String []{ciclo},null,null,null);

    }

    public Cursor buscarProfesoresPorCurso(String curso){

        String[] columnas = new String[]{"_id",DBEstructura.NOMBRE_PROFESOR,DBEstructura.EDAD_PROFESOR,DBEstructura.CICLO_PROFESOR,
                DBEstructura.CURSO_PROFESOR,DBEstructura.DESPACHO_PROFESOR};

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.query(DBEstructura.TABLA_PROFESORES,columnas,DBEstructura.CURSO_PROFESOR + "=?",new String []{curso},null,null,null);
    }

    public Cursor buscarAlumnosPorCurso(String curso){

        String[] columnas = new String[]{"_id",DBEstructura.NOMBRE_ALUMNO,DBEstructura.EDAD_ALUMNO,DBEstructura.CICLO_ALUMNO,
                DBEstructura.CURSO_ALUMNO,DBEstructura.NOTA_ALUMNO};

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.query(DBEstructura.TABLA_ALUMNOS,columnas,DBEstructura.CURSO_ALUMNO + "=?",new String []{curso},null,null,null);
    }

    public Cursor buscarProfesoresPorCursoYCiclo(String curso, String ciclo){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM profesores WHERE curso = ? AND ciclo= ?", new String[] {curso, ciclo});

        return cursor;
    }

    public Cursor buscarAlumnosPorCursoYCiclo(String curso, String ciclo){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM alumnos WHERE curso = ? AND ciclo= ?", new String[] {curso, ciclo});

        return cursor;
    }

    public ArrayList<String> recuperarProfesores() {

        ArrayList<String> profesores = new ArrayList<String>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

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

        SQLiteDatabase db = dbHelper.getReadableDatabase();

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

    public void limpiarRadioButtons(){

        radioBProfesores.setChecked(false);
        radioBAlumnos.setChecked(false);
        radioBCurso.setChecked(false);
        radioBCiclo.setChecked(false);
        cajaCurso.setText("");
        cajaCiclo.setText("");
        lista.setAdapter(null);

    }
}
