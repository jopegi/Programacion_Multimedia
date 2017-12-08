package josevi.android.com.sqlitedatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class ConsultasActivity extends AppCompatActivity {


    private Button botonMostrar, botonMostrarTodos, botonCiclo, botonCurso;
    private EditText cajaCiclo, cajaCurso;
    private RadioButton radioProfesores, radioAlumnos;
    private BaseDatosHelper miBBDDHelper = null;
    private String tableName = null;
    private String ciclo = null;
    private String curso =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        botonMostrar = (Button) findViewById(R.id.btnBuscarCicloCurso);
        botonMostrarTodos = (Button) findViewById(R.id.btnMostrarTodos);
        botonCiclo = (Button) findViewById(R.id.btnBuscarCiclo);
        botonCurso = (Button) findViewById(R.id.btnBuscarCurso);

        cajaCiclo = (EditText) findViewById(R.id.editTextCiclo);
        cajaCurso = (EditText) findViewById(R.id.editTextCurso);

        radioProfesores = (RadioButton) findViewById(R.id.radioBtnProfesores);
        radioAlumnos = (RadioButton) findViewById(R.id.radioBtnAlumnos);

        botonCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarPorCiclo(Integer.valueOf(cajaCiclo.getText().toString()));

            }
        });

        botonCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarPorCurso(cajaCurso.getText().toString());
            }
        });

        botonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        botonMostrarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarTodos();

            }
        });
    }


    public Cursor cargarCursor(){

        Cursor c = null;

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        if(radioProfesores.isChecked()){

            String[] columnas = new String[] {"_id","nombre","edad","ciclo","curso","despacho"};

            c = db.query("profesores",columnas,null,null,null,null,null);
        }

        if(radioAlumnos.isChecked()){

            String[] columnas = new String[] {"_id","nombre","edad","ciclo","curso","nota_media"};

            c = db.query("alumnos",columnas,null,null,null,null,null);

        }
        return c;
    }



    public ArrayList<String> recuperarTodos(){

        ArrayList<String> rtn = null;

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        if(radioProfesores.isChecked()){

            ArrayList<String> profesores = new ArrayList<String>();

            try {

                Cursor cursor = db.rawQuery(" SELECT * FROM profesores", null);

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        profesores.add(cursor.getString(0)+cursor.getString(1)+cursor.getString(2)+
                                cursor.getString(3)+cursor.getString(4));

                    } while (cursor.moveToNext());
                }


                db.close();

                Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "No hubo resultados de búsqueda!", Toast.LENGTH_SHORT).show();

            }

            rtn = profesores;
        }

        if(radioAlumnos.isChecked()){

            ArrayList<String> alumnos = new ArrayList<String>();


            try {

                Cursor cursor = db.rawQuery(" SELECT * FROM alumnos", null);

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        alumnos.add(cursor.getString(0)+cursor.getString(1)+cursor.getString(2)+
                                cursor.getString(3)+cursor.getString(4));

                    } while (cursor.moveToNext());
                }

                db.close();

                Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!!", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "No hubo resultados de búsqueda!", Toast.LENGTH_SHORT).show();

            }

            rtn = alumnos;
        }

        return rtn;

        }




    public ArrayList<String> recuperarPorCurso(String curso){

        ArrayList<String> rtn = null;
        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        //String curso = cajaCurso.getText().toString();


        if(radioProfesores.isChecked()){

            tableName = EstructuraBBDD.DATABASE_TABLE_PROFESORES;

            ArrayList<String> profesores = new ArrayList<String>();

            //Array que indica las columnas a mostrar tras la consulta
            String[] projection = {
                    EstructuraBBDD.NOMBRE_PROFESOR,
                    EstructuraBBDD.EDAD_PROFESOR,
                    EstructuraBBDD.CICLO_PROFESOR,
                    //EstructuraBBDD.CURSO_PROFESOR,
                    EstructuraBBDD.DESPACHO_PROFESOR
            };

            //Indica el campo por el cual se filtrará la consulta. En esta caso por _id
            String selection = EstructuraBBDD.CICLO_PROFESOR + " = ?";
            //Indica de donde se obtiene el valor del campo anterior
            String[] selectionArgs = {curso};

            //Podemos indicar si deseamos que se ordene el resultado de la consulta de filtrado
        /*String sortOrder =
                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";*/

            try {

                //En un objeto de tipo Cursor se almacenarán los resultados de la consulta
                Cursor cursor = db.query(
                        EstructuraBBDD.DATABASE_TABLE_PROFESORES,  // La tabla de consulta
                        projection,                               // Las columnas a devolver
                        selection,                                // Las columnas para la cláusula Where
                        selectionArgs,                            // Los valores para la cláusula Where
                        null,                                     // Sin agrupamiento de las filas
                        null,                                     // sin filtro sobre el agrupamiento de las filas
                        null                                      // Sin ordenamiento
                );

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        profesores.add(cursor.getString(0)+cursor.getString(1)+cursor.getString(2)+cursor.getString(4));
                    } while (cursor.moveToNext());
                }

                db.close();

                Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!!", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "No hubo resultados de búsqueda!", Toast.LENGTH_SHORT).show();

            }

            rtn = profesores;
        }

        if(radioAlumnos.isChecked()){
            tableName = EstructuraBBDD.DATABASE_TABLE_ALUMNOS;

            ArrayList<String> alumnos = new ArrayList<String>();

            //Array que indica las columnas a mostrar tras la consulta
            String[] projection = {
                    EstructuraBBDD.NOMBRE_ALUMNO,
                    EstructuraBBDD.EDAD_ALUMNO,
                    EstructuraBBDD.CICLO_ALUMNO,
                    //EstructuraBBDD.CURSO_ALUMNO,
                    EstructuraBBDD.NOTA_MEDIA_ALUMNO
            };

            //Indica el campo por el cual se filtrará la consulta. En esta caso por _id
            String selection = EstructuraBBDD.CICLO_ALUMNO + " = ?";
            //Indica de donde se obtiene el valor del campo anterior
            String[] selectionArgs = {curso};

            //Podemos indicar si deseamos que se ordene el resultado de la consulta de filtrado
        /*String sortOrder =
                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";*/

            try {

                //En un objeto de tipo Cursor se almacenarán los resultados de la consulta
                Cursor cursor = db.query(
                        EstructuraBBDD.DATABASE_TABLE_ALUMNOS,  // La tabla de consulta
                        projection,                               // Las columnas a devolver
                        selection,                                // Las columnas para la cláusula Where
                        selectionArgs,                            // Los valores para la cláusula Where
                        null,                                     // Sin agrupamiento de las filas
                        null,                                     // sin filtro sobre el agrupamiento de las filas
                        null                                      // Sin ordenamiento
                );

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        alumnos.add(cursor.getString(0)+cursor.getString(1)+cursor.getString(2)+cursor.getString(4));
                    } while (cursor.moveToNext());
                }

                db.close();


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "No hubo resultados de búsqueda!", Toast.LENGTH_SHORT).show();

            }

            rtn = alumnos;
        }

        return rtn;
    }


    public ArrayList<String> recuperarPorCiclo(int ciclo){

        ArrayList<String> rtn = null;
        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        //String curso = cajaCurso.getText().toString();


        if(radioProfesores.isChecked()){

            tableName = EstructuraBBDD.DATABASE_TABLE_PROFESORES;

            ArrayList<String> profesores = new ArrayList<String>();

            //Array que indica las columnas a mostrar tras la consulta
            String[] projection = {
                    EstructuraBBDD.NOMBRE_PROFESOR,
                    EstructuraBBDD.EDAD_PROFESOR,
                    //EstructuraBBDD.CICLO_PROFESOR,
                    EstructuraBBDD.CURSO_PROFESOR,
                    EstructuraBBDD.DESPACHO_PROFESOR
            };

            //Indica el campo por el cual se filtrará la consulta. En esta caso por _id
            String selection = EstructuraBBDD.CICLO_PROFESOR + " = ?";
            //Indica de donde se obtiene el valor del campo anterior
            String[] selectionArgs = {String.valueOf(ciclo)};

            //Podemos indicar si deseamos que se ordene el resultado de la consulta de filtrado
        /*String sortOrder =
                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";*/

            try {

                //En un objeto de tipo Cursor se almacenarán los resultados de la consulta
                Cursor cursor = db.query(
                        EstructuraBBDD.DATABASE_TABLE_PROFESORES,  // La tabla de consulta
                        projection,                               // Las columnas a devolver
                        selection,                                // Las columnas para la cláusula Where
                        selectionArgs,                            // Los valores para la cláusula Where
                        null,                                     // Sin agrupamiento de las filas
                        null,                                     // sin filtro sobre el agrupamiento de las filas
                        null                                      // Sin ordenamiento
                );

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        profesores.add(cursor.getString(0)+cursor.getString(1)+cursor.getString(3)+cursor.getString(4));
                    } while (cursor.moveToNext());
                }

                db.close();

                Toast.makeText(getApplicationContext(), "Se han recuperado todos los registros!!", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "No hubo resultados de búsqueda!", Toast.LENGTH_SHORT).show();

            }

            rtn = profesores;
        }

        if(radioAlumnos.isChecked()){
            tableName = EstructuraBBDD.DATABASE_TABLE_ALUMNOS;

            ArrayList<String> alumnos = new ArrayList<String>();

            //Array que indica las columnas a mostrar tras la consulta
            String[] projection = {
                    EstructuraBBDD.NOMBRE_ALUMNO,
                    EstructuraBBDD.EDAD_ALUMNO,
                    //EstructuraBBDD.CICLO_ALUMNO,
                    EstructuraBBDD.CURSO_ALUMNO,
                    EstructuraBBDD.NOTA_MEDIA_ALUMNO
            };

            //Indica el campo por el cual se filtrará la consulta. En esta caso por _id
            String selection = EstructuraBBDD.CICLO_ALUMNO + " = ?";
            //Indica de donde se obtiene el valor del campo anterior
            String[] selectionArgs = {String.valueOf(ciclo)};

            //Podemos indicar si deseamos que se ordene el resultado de la consulta de filtrado
        /*String sortOrder =
                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";*/

            try {

                //En un objeto de tipo Cursor se almacenarán los resultados de la consulta
                Cursor cursor = db.query(
                        EstructuraBBDD.DATABASE_TABLE_ALUMNOS,  // La tabla de consulta
                        projection,                               // Las columnas a devolver
                        selection,                                // Las columnas para la cláusula Where
                        selectionArgs,                            // Los valores para la cláusula Where
                        null,                                     // Sin agrupamiento de las filas
                        null,                                     // sin filtro sobre el agrupamiento de las filas
                        null                                      // Sin ordenamiento
                );

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        alumnos.add(cursor.getString(0)+cursor.getString(1)+cursor.getString(3)+cursor.getString(4));
                    } while (cursor.moveToNext());
                }

                db.close();


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "No hubo resultados de búsqueda!", Toast.LENGTH_SHORT).show();

            }

            rtn = alumnos;
        }

        return rtn;
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

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_PROFESOR,EstructuraBBDD.EDAD_PROFESOR,EstructuraBBDD.CICLO_PROFESOR,
                EstructuraBBDD.CURSO_PROFESOR,EstructuraBBDD.DESPACHO_PROFESOR};

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.DATABASE_TABLE_PROFESORES,columnas,EstructuraBBDD.CURSO_PROFESOR + " = '?' AND " + EstructuraBBDD.CICLO_PROFESOR+" = '?'",new String []{curso,ciclo},null,null,null);

    }
    //********************************************************
    //********************************************************

    //***************************************************
    //***************************************************
    public Cursor buscarAlumnosPorCursoYCiclo(String curso, String ciclo){

        String[] columnas = new String[]{"_id",EstructuraBBDD.NOMBRE_ALUMNO,EstructuraBBDD.EDAD_ALUMNO,EstructuraBBDD.CICLO_ALUMNO,
                EstructuraBBDD.CURSO_ALUMNO,EstructuraBBDD.NOTA_MEDIA_ALUMNO};

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();

        return db.query(EstructuraBBDD.DATABASE_TABLE_ALUMNOS,columnas,EstructuraBBDD.CURSO_ALUMNO + " = '?' AND " + EstructuraBBDD.CICLO_ALUMNO + " = '?'",new String []{curso,ciclo},null,null,null);

    }
    //********************************************************
    //********************************************************
}
