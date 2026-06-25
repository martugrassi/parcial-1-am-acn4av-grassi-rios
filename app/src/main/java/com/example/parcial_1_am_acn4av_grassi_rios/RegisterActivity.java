package com.example.parcial_1_am_acn4av_grassi_rios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextInputEditText inputNombre = findViewById(R.id.inputNombre);
        TextInputEditText inputEmail = findViewById(R.id.inputEmail);
        TextInputEditText inputPassword = findViewById(R.id.inputPassword);
        TextInputEditText inputConfirmPassword = findViewById(R.id.inputConfirmPassword);

        MaterialButton btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        TextView tabLogin = findViewById(R.id.tabLogin);
        TextView txtCambiarModo = findViewById(R.id.txtCambiarModo);

        btnCrearCuenta.setOnClickListener(v -> {
            String nombre = inputNombre.getText() != null ? inputNombre.getText().toString().trim() : "";
            String email = inputEmail.getText() != null ? inputEmail.getText().toString().trim() : "";
            String pass = inputPassword.getText() != null ? inputPassword.getText().toString().trim() : "";
            String confirmPass = inputConfirmPassword.getText() != null ? inputConfirmPassword.getText().toString().trim() : "";

            boolean valido = true;

            if (nombre.isEmpty()) {
                inputNombre.setError("Ingresá tu nombre");
                valido = false;
            }
            if (email.isEmpty()) {
                inputEmail.setError("Ingresá tu correo");
                valido = false;
            }
            if (pass.isEmpty()) {
                inputPassword.setError("Ingresá una contraseña");
                valido = false;
            }
            if (!confirmPass.equals(pass) || confirmPass.isEmpty()) {
                inputConfirmPassword.setError("Las contraseñas no coinciden");
                valido = false;
            }

            if (!valido) return;

            firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {

                            Toast.makeText(
                                    RegisterActivity.this,
                                    "Cuenta creada con éxito",
                                    Toast.LENGTH_SHORT
                            ).show();

                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else {
                            inputEmail.setError("No se pudo crear la cuenta");
                            Toast.makeText(
                                    RegisterActivity.this,
                                    "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
        });

        // Volver al login
        tabLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        txtCambiarModo.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }
}