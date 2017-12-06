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


    private Button botonMostrar, botonMostrarTodos;
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

        botonMostrar = (Button) findViewById(R.id.btnMostrar);
        botonMostrarTodos = (Button) findViewById(R.id.btnMostrarTodos);

        cajaCiclo = (EditText) findViewById(R.id.editTextCiclo);
        cajaCurso = (EditText) findViewById(R.id.editTextCurso);

        radioProfesores = (RadioButton) findViewById(R.id.radioBtnProfesores);
        radioAlumnos = (RadioButton) findViewById(R.id.radioBtnAlumnos);


        botonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        botonMostrarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public ArrayList<String> recuperarTodos(){

        ArrayList<String> rtn = null;

        if(radioProfesores.isChecked()){
            tableName = EstructuraBBDD.DATABASE_TABLE_PROFESORES;
        }

        if(radioAlumnos.isChecked()){
            tableName = EstructuraBBDD.DATABASE_TABLE_ALUMNOS;
        }

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();


        if(tableName.equals("profesores")) {

            ArrayList<String> profesores = new ArrayList<String>();

            //Array que indica las columnas a mostrar tras la consulta
            String[] projection = {
                    EstructuraBBDD.NOMBRE_PROFESOR,
                    EstructuraBBDD.EDAD_PROFESOR,
                    EstructuraBBDD.CICLO_PROFESOR,
                    EstructuraBBDD.CURSO_PROFESOR,
                    EstructuraBBDD.DESPACHO_PROFESOR
            };

            try {

                //En un objeto de tipo Cursor se almacenarán los resultados de la consulta
                Cursor cursor = db.query(
                        EstructuraBBDD.DATABASE_TABLE_PROFESORES,   // La tabla de consulta
                        null,                                       // Las columnas a devolver
                        null,                                       // Las columnas para la cláusula Where
                        null,                                       // Los valores para la cláusula Where
                        null,                                       // Sin agrupamiento de las filas
                        null,                                       // sin filtro sobre el agrupamiento de las filas
                        null                                        // Sin ordenamiento
                );

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        profesores.add(cursor.getString(1));
                        profesores.add(cursor.getString(2));
                        profesores.add(cursor.getString(3));
                        profesores.add(cursor.getString(4));
                    } while (cursor.moveToNext());
                }

                db.close();


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "No hubo resultados de búsqueda!", Toast.LENGTH_SHORT).show();

            }

            rtn = profesores;
        }

        if(tableName.equals("alumnos")) {

            ArrayList<String> alumnos = new ArrayList<String>();

            //Array que indica las columnas a mostrar tras la consulta
            String[] projection = {
                    EstructuraBBDD.NOMBRE_ALUMNO,
                    EstructuraBBDD.EDAD_ALUMNO,
                    EstructuraBBDD.CICLO_ALUMNO,
                    EstructuraBBDD.CURSO_ALUMNO,
                    EstructuraBBDD.NOTA_MEDIA_ALUMNO
            };

            try {

                //En un objeto de tipo Cursor se almacenarán los resultados de la consulta
                Cursor cursor = db.query(
                        EstructuraBBDD.DATABASE_TABLE_ALUMNOS,      // La tabla de consulta
                        null,                                       // Las columnas a devolver
                        null,                                       // Las columnas para la cláusula Where
                        null,                                       // Los valores para la cláusula Where
                        null,                                       // Sin agrupamiento de las filas
                        null,                                       // sin filtro sobre el agrupamiento de las filas
                        null                                        // Sin ordenamiento
                );

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        alumnos.add(cursor.getString(1));
                        alumnos.add(cursor.getString(2));
                        alumnos.add(cursor.getString(3));
                        alumnos.add(cursor.getString(4));
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


    public ArrayList<String> recuperar(){

        ArrayList<String> rtn = null;

        //String ciclo = "";
        //String curso = "";

        String ciclo = cajaCiclo.getText().toString();
        String curso = cajaCurso.getText().toString();


        if(radioProfesores.isChecked()){
            tableName = EstructuraBBDD.DATABASE_TABLE_PROFESORES;
        }

        if(radioAlumnos.isChecked()){
            tableName = EstructuraBBDD.DATABASE_TABLE_ALUMNOS;
        }

        SQLiteDatabase db = miBBDDHelper.getReadableDatabase();


        if(tableName.equals("profesores") && ciclo.toString().length()>0 && curso.toString().length()>0) {

            ArrayList<String> profesores = new ArrayList<String>();

            //Array que indica las columnas a mostrar tras la consulta
            String[] projection = {
                    EstructuraBBDD.NOMBRE_PROFESOR,
                    EstructuraBBDD.EDAD_PROFESOR,
                    //EstructuraBBDD.CICLO_PROFESOR,
                    //EstructuraBBDD.CURSO_PROFESOR,
                    EstructuraBBDD.DESPACHO_PROFESOR
            };

            //Indica el campo por el cual se filtrará la consulta. En esta caso por _id
            String selection = EstructuraBBDD.CICLO_PROFESOR + " = ?, "+EstructuraBBDD.CURSO_PROFESOR+" = ?";
            //Indica de donde se obtiene el valor del campo anterior
            String[] selectionArgs = {cajaCiclo.getText().toString(), cajaCurso.getText().toString()};

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
                        profesores.add(cursor.getString(1));
                        profesores.add(cursor.getString(2));
                        profesores.add(cursor.getString(5));
                    } while (cursor.moveToNext());
                }

                db.close();


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "No hubo resultados de búsqueda!", Toast.LENGTH_SHORT).show();

            }

            rtn = profesores;
        }

        if(tableName.equals("alumnos")&& ciclo.toString().length()>0 && curso.toString().length()>0) {

            ArrayList<String> alumnos = new ArrayList<String>();

            //Array que indica las columnas a mostrar tras la consulta
            String[] projection = {
                    EstructuraBBDD.NOMBRE_ALUMNO,
                    EstructuraBBDD.EDAD_ALUMNO,
                    //EstructuraBBDD.CICLO_ALUMNO,
                    //EstructuraBBDD.CURSO_ALUMNO,
                    EstructuraBBDD.NOTA_MEDIA_ALUMNO
            };

            //Indica el campo por el cual se filtrará la consulta. En esta caso por _id
            String selection = EstructuraBBDD.CICLO_ALUMNO + " = ?, "+EstructuraBBDD.CURSO_ALUMNO+" = ?";
            //Indica de donde se obtiene el valor del campo anterior
            String[] selectionArgs = {cajaCiclo.getText().toString(),cajaCurso.getText().toString()};

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
                        alumnos.add(cursor.getString(1));
                        alumnos.add(cursor.getString(2));
                        alumnos.add(cursor.getString(5));
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


}
