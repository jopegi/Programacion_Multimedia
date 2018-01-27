package josevi.android.com.drive_conecction;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;


/*
        //Según la información de android developers de Google, para recuperar una colección
        //con los metadatos de un directorio habría que implementar lo siguiente:
        Task<MetadataBuffer> queryTask = mDriveResourceClient
                .queryChildren(folder, query);

        */

//Pero, no explican como obtener una referencia al directorio el contenido del cual nos interesa
//listar.....

        /*La lógica me dice que:
        * - Primero debería de implementarse un método para acceder al nodo principal
        *   de directorios del Drive para obtener un listado de los mismos.
        * - A continuación, comprobar si el directorio que el usuario indica gráficamente
        *   se encuentra entre los existentes en la lista obtenida préviamente.
        * - Después, si el directorio indicado existe, obtener su DriveId y, a partir del
        *   mismo, mediante la Query definida y el DriveId (folder) obtener un objeto
        *   MetadataBuffer con sus metadatos para después recorrerlo obteniendo la información
        *   que se necesite. En este caso, los nombres de los archivos y directorios que contiene...
        *   ¿Pero cómo?
        */

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private final static String LOGTAG = "android-drive";
    private GoogleApiClient apiClient;


    private DriveId idFolder;

    private EditText cajaFichero, cajaDirectorio;
    private TextView labelFichero;
    private Button botonBuscar, botonMostrar;
    private ListView listaDirectorio;
    String directorioABuscar;

    private static final String TAG = "drive-quickstart";
    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;

    private GoogleSignInClient mGoogleSignInClient;
    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;

    private Task<MetadataBuffer> queryTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                //.addScope(Drive.SCOPE_APPFOLDER)
                .build();

        cajaFichero = (EditText) findViewById(R.id.editTextFichero);
        cajaDirectorio = (EditText) findViewById(R.id.editTextDirectorio);

        labelFichero = (TextView) findViewById(R.id.textViewResultadoBusqueda);

        botonBuscar = (Button) findViewById(R.id.btnBuscar);
        botonMostrar = (Button) findViewById(R.id.btnMostrar);

        listaDirectorio = (ListView) findViewById(R.id.listView);



        mGoogleSignInClient = buildGoogleSignInClient();

        Toast.makeText(this, "Google SignIn obtenido!!", Toast.LENGTH_SHORT).show();

        startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);

    }

    //Autorizar el GoogleDrive Api en nuestra aplicación
    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        //Toast.makeText(this, "Paso 1", Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:

                //Toast.makeText(this, "Paso 2", Toast.LENGTH_SHORT).show();

                Log.i(TAG, "Sign in request code");
                // Called after user is signed in.
                if (resultCode == RESULT_OK) {

                    //Toast.makeText(this, "Paso 3", Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "Signed in successfully.");
                    // Use the last signed in account here since it already have a Drive scope.
                    mDriveClient = Drive.getDriveClient(this, GoogleSignIn.getLastSignedInAccount(this));
                    // Build a drive resource client.
                    mDriveResourceClient =
                            Drive.getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this));

                    //Toast.makeText(this, "Paso 4", Toast.LENGTH_SHORT).show();

                    //Create File
                    final Task<DriveFolder> rootFolderTask = mDriveResourceClient.getRootFolder();
                    final Task<DriveContents> createContentsTask = mDriveResourceClient.createContents();
                    Tasks.whenAll(rootFolderTask, createContentsTask)
                            .continueWithTask(new Continuation<Void, Task<DriveFile>>() {
                                @Override
                                public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {
                                    DriveFolder parent = rootFolderTask.getResult();
                                    DriveContents contents = createContentsTask.getResult();
                                    OutputStream outputStream = contents.getOutputStream();
                                    try (Writer writer = new OutputStreamWriter(outputStream)) {
                                        writer.write("Hello World!");
                                    }

                                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                            .setTitle("HelloWorld.txt")
                                            .setMimeType("text/plain")
                                            .setStarred(true)
                                            .build();

                                    return mDriveResourceClient.createFile(parent, changeSet, contents);
                                }
                            })
                            .addOnSuccessListener(this,
                                    new OnSuccessListener<DriveFile>() {
                                        @Override
                                        public void onSuccess(DriveFile driveFile) {
                                            Toast.makeText(MainActivity.this, "Fichero creado con éxito!!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            .addOnFailureListener(this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, "Unable to create file", e);
                                    Toast.makeText(MainActivity.this, "Fichero no creado!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
            case REQUEST_CODE_CAPTURE_IMAGE:
                Log.i(TAG, "capture image request code");
                // Called after a photo has been taken.
                if (resultCode == Activity.RESULT_OK) {

                }
                break;
            case REQUEST_CODE_CREATOR:
                Log.i(TAG, "creator request code");
                // Called after a file is saved to Drive.
                if (resultCode == RESULT_OK) {

                }
                break;
        }


        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ficheroABuscar = cajaFichero.getText().toString();

                Query query = new Query.Builder()
                        .addFilter(Filters.eq(SearchableField.TITLE, ficheroABuscar))
                        .build();

                queryTask = mDriveResourceClient.query(query);

                //Toast.makeText(MainActivity.this, ""+mDriveResourceClient, Toast.LENGTH_SHORT).show();

                queryTask
                        .addOnSuccessListener(MainActivity.this,
                                new OnSuccessListener<MetadataBuffer>() {
                                    @Override
                                    public void onSuccess(MetadataBuffer metadataBuffer) {

                                        if(queryTask.getResult().getCount()>0){

                                            String existeFichero = "El fichero indicado existe en Google Drive";

                                            labelFichero.setText(existeFichero);

                                        }else{

                                            String noExisteFichero = "El fichero indicado no existe en Google Drive";

                                            labelFichero.setText(noExisteFichero);
                                        }

                                    }
                                })
                        .addOnFailureListener(MainActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                //Toast.makeText(MainActivity.this, "No se encuentra el fichero indicado!!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        });


        botonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                searchDrive(cajaDirectorio.getText().toString());

              /*  // Use the last signed in account here since it already have a Drive scope.
                mDriveClient = Drive.getDriveClient(getApplicationContext(), GoogleSignIn.getLastSignedInAccount(getApplicationContext()));

                mDriveResourceClient =
                        Drive.getDriveResourceClient(getApplicationContext(), GoogleSignIn.getLastSignedInAccount(getApplicationContext()));

                directorioABuscar = cajaDirectorio.getText().toString();

                //DriveFolder f= mDriveResourceClient.getRootFolder().getResult();

                Query query = new Query.Builder()
                        .addFilter(Filters.eq(SearchableField.TITLE, directorioABuscar))
                        .build();

                Task<MetadataBuffer> queryTask = mDriveResourceClient.queryChildren(f, query);



               // onDriveClientReady(directorioABuscar);

*/
            }
        });
    }

    //Método para buscar carpetas en la unidad completa de Google Drive.
    private DriveId searchDrive(String carpeta) {

        idFolder = null;

        String directorioABuscar = cajaDirectorio.getText().toString();

        //Definimos una consulta con varios criterios de búsqueda:
        Query query = new Query.Builder().addFilter(Filters.and(
                //por tipo de elemento. En este caso, solo queremos buscar directorios
                Filters.eq(SearchableField.MIME_TYPE, "application/vnd.google-apps.folder"),
                //por el nombre del directorio
                Filters.eq(SearchableField.TITLE, directorioABuscar)))
                .build();


/*
        //En su página Web (http://www.sgoliver.net/), Salvador Gómez Oliver trata el tema de Google
        //Dripe Api pero su código ya está obsoleto.

        queryTask = mDriveResourceClient.query(query);

        queryTask
                .addOnSuccessListener(MainActivity.this,
                        new OnSuccessListener<MetadataBuffer>() {
                            @Override
                            public void onSuccess(MetadataBuffer metadataBuffer) {

                                if(queryTask.getResult().getCount()>0){

                                    String existeDirectorio = "El directorio indicado existe en Google Drive";

                                    labelFichero.setText(existeDirectorio);

                                }else{

                                    String noExisteDirectorio = "El directorio indicado no existe en Google Drive";

                                    labelFichero.setText(noExisteDirectorio);
                                }

                            }
                        })
                .addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

*/

/*
        //Aplicamos la consulta anterior sobre la unidad completa de Google Drive (apiClient)
        //para buscar la carpeta indicada por el usuario y obtener su idFolder
        Drive.DriveApi.query(apiClient, query)
                .setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
                    @Override
                    public void onResult(DriveApi.MetadataBufferResult mdBufferResult) {
                        if (mdBufferResult.getStatus().isSuccess()) {
                            if(mdBufferResult.getMetadataBuffer().getCount() > 0)
                                //Log.i(LOGTAG, "Directorio encontrado!");
                                Toast.makeText(MainActivity.this, "Directorio encontrado!!", Toast.LENGTH_SHORT).show();

                            else
                                //Log.i(LOGTAG, "No se ha encontrado el directorio!");
                                Toast.makeText(MainActivity.this, "Directorio no encontrado!!", Toast.LENGTH_SHORT).show();

                            //Log.i(LOGTAG, "Directorio: " + mdBufferResult.getMetadataBuffer().getTitle() + " [" + mdBufferResult.getMetadataBuffer().getDriveId().encodeToString() + "]");

                            //Si la carpeta existe, guardamos su id en una variable
                            //idFolder = mdBufferResult.getMetadataBuffer().getDriveId();
                        }
                        else {
                            Log.e(LOGTAG, "Error al buscar en el directorio raíz!");
                        }
                    }
                });

*/
        return idFolder;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
