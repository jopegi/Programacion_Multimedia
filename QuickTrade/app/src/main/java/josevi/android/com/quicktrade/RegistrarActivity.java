package josevi.android.com.quicktrade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarActivity extends AppCompatActivity {

    private EditText cajaEmail;
    private EditText cajaPassword;
    private EditText cajaNick;
    private EditText cajaNombre;
    private EditText cajaApellidos;
    private EditText cajaDireccion;

    private Button botonRegistro;
    private Button botonLogin;

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        cajaEmail = (EditText) findViewById(R.id.editTextEmail);
        cajaPassword = (EditText) findViewById(R.id.editTextPassword);
        cajaNick = (EditText) findViewById(R.id.editTextNick);
        cajaNombre = (EditText) findViewById(R.id.editTextNombre);
        cajaApellidos = (EditText) findViewById(R.id.editTextApellidos);
        cajaDireccion = (EditText) findViewById(R.id.editTextDireccion);

        botonRegistro = (Button) findViewById(R.id.btnRegistro);
        botonLogin = (Button) findViewById(R.id.btnLogin);

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = cajaEmail.getText().toString();
                String password = cajaPassword.getText().toString();

                registrar(email,password);

            }
        });

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = cajaEmail.getText().toString();
                String password = cajaPassword.getText().toString();

                login(email,password);

            }
        });
    }

    public void registrar (String email,String password){

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(RegistrarActivity.this, "Register success."+"User: "+user.getUid(),
                                    Toast.LENGTH_SHORT).show();

                            mAuth.signOut();

                        } else {

                            Toast.makeText(RegistrarActivity.this, "Register failed. "+task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    public void login (String email,String password){

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(RegistrarActivity.this, "Signin successful."+"User: "+user.getUid(),
                                    Toast.LENGTH_SHORT).show();

                            //Guardamos el Uid del usuario logeado en una variable
                            String UidUsuario = user.getUid();

                            Intent intentoLoginOk = new Intent(RegistrarActivity.this, MainActivity.class);
                            //Pasamos el Uid del usuario al MainActivity  mediante el método putExtra
                            //del intent
                            intentoLoginOk.putExtra("Uid", UidUsuario);

                            //Lanzamos en intent
                            startActivity(intentoLoginOk);

                        } else {

                            Toast.makeText(RegistrarActivity.this, "Signin failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
}
