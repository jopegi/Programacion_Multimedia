package josevi.android.com.proyectosplashimage;

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

public class RegistroActivity extends AppCompatActivity {

    private EditText cajaEmail, cajaPassword;
    private Button botonRegistro, botonLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        cajaEmail = (EditText) findViewById(R.id.editTextEmail);
        cajaPassword = (EditText) findViewById(R.id.editTextPassword);
        botonRegistro = (Button) findViewById(R.id.btnRegistrar);
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

                            Toast.makeText(RegistroActivity.this, "Register successful."+"User: "+user.getUid(),
                                    Toast.LENGTH_SHORT).show();

                            mAuth.signOut();


                        } else {

                            Toast.makeText(RegistroActivity.this, "Register failed. "+task.getException(),
                                    Toast.LENGTH_LONG).show();

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
                            String UidUsuario = user.getUid();
                            Intent intentDrawer = new Intent(RegistroActivity.this, NDrawerActivity.class);
                            intentDrawer.putExtra("Uid", UidUsuario);
                            startActivity(intentDrawer);

                            Toast.makeText(RegistroActivity.this, "Signin successful."+"User: "+user.getUid(),
                                    Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(RegistroActivity.this, "Signin failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
}
