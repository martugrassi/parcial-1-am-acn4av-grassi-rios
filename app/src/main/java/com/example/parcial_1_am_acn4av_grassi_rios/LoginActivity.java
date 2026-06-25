package com.example.parcial_1_am_acn4av_grassi_rios;
import android.widget.LinearLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private static final String WEB_CLIENT_ID = "597018930760-onp4bov8vb2abis1ap1ihp22d6cha4td.apps.googleusercontent.com";

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private ActivityResultLauncher<Intent> googleLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inputs
        TextInputEditText inputEmail = findViewById(R.id.inputEmail);
        TextInputEditText inputPassword = findViewById(R.id.inputPassword);

        // Botón principal
        MaterialButton btnLoginPrincipal = findViewById(R.id.btnLoginPrincipal);
        LinearLayout btnGoogle = findViewById(R.id.btnGoogle);

        // Tabs
        TextView tabLogin = findViewById(R.id.tabLogin);
        TextView tabRegistro = findViewById(R.id.tabRegistro);
        TextView txtCambiarModo = findViewById(R.id.txtCambiarModo);


        // Firebase + Google Sign-In
        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_CLIENT_ID)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        firebaseAuthWithGoogle(account.getIdToken());
                    } catch (ApiException e) {
                        Toast.makeText(this, "Error con Google (código " + e.getStatusCode() + ")", Toast.LENGTH_SHORT).show();
                    }
                });

        btnGoogle.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            googleLauncher.launch(signInIntent);
        });

        // Login con Firebase email/contraseña
        btnLoginPrincipal.setOnClickListener(v -> {
            String email = inputEmail.getText() != null ? inputEmail.getText().toString().trim() : "";
            String pass = inputPassword.getText() != null ? inputPassword.getText().toString().trim() : "";

            if (email.isEmpty() || pass.isEmpty()) {
                inputEmail.setError(email.isEmpty() ? "Ingresá tu correo" : null);
                inputPassword.setError(pass.isEmpty() ? "Ingresá tu contraseña" : null);
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Inicio de sesión exitoso",
                                    Toast.LENGTH_SHORT
                            ).show();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            inputPassword.setError("Correo o contraseña incorrectos");
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
        });

        // Navegar a Registro
        tabRegistro.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        txtCambiarModo.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String nombre = firebaseAuth.getCurrentUser() != null
                                ? firebaseAuth.getCurrentUser().getDisplayName()
                                : "";
                        Toast.makeText(this, "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Falló la autenticación con Google", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}