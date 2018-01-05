package josevi.android.com.drive_conecction;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "drive-quickstart";
    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;

    private GoogleSignInClient mGoogleSignInClient;
    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleSignInClient = buildGoogleSignInClient();

        Toast.makeText(this, "Google SignIn obtenido!!", Toast.LENGTH_SHORT).show();

        startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);

    }

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        Toast.makeText(this, "Paso 1", Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:

                Toast.makeText(this, "Paso 2", Toast.LENGTH_SHORT).show();

                Log.i(TAG, "Sign in request code");
                // Called after user is signed in.
                if (resultCode == RESULT_OK) {

                    Toast.makeText(this, "Paso 3", Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "Signed in successfully.");
                    // Use the last signed in account here since it already have a Drive scope.
                    mDriveClient = Drive.getDriveClient(this, GoogleSignIn.getLastSignedInAccount(this));
                    // Build a drive resource client.
                    mDriveResourceClient =
                            Drive.getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this));

                    Toast.makeText(this, "Paso 4", Toast.LENGTH_SHORT).show();

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
    }
}
