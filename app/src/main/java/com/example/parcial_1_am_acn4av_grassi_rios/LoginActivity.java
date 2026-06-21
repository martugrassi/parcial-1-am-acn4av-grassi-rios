package com.example.parcial_1_am_acn4av_grassi_rios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

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

        // Inputs (ahora son TextInputEditText, no EditText)
        TextInputEditText inputEmail = findViewById(R.id.inputEmail);
        TextInputEditText inputPassword = findViewById(R.id.inputPassword);

        // Botón principal (ahora es MaterialButton, no Button)
        MaterialButton btnLoginPrincipal = findViewById(R.id.btnLoginPrincipal);

        // Tabs
        TextView tabLogin = findViewById(R.id.tabLogin);
        TextView tabRegistro = findViewById(R.id.tabRegistro);

        // Texto de abajo
        TextView txtCambiarModo = findViewById(R.id.txtCambiarModo);

        btnLoginPrincipal.setOnClickListener(v -> {
            String email = inputEmail.getText() != null ? inputEmail.getText().toString().trim() : "";
            String pass = inputPassword.getText() != null ? inputPassword.getText().toString().trim() : "";

            if (email.isEmpty() || pass.isEmpty()) {
                inputEmail.setError(email.isEmpty() ? "Ingresá tu correo" : null);
                inputPassword.setError(pass.isEmpty() ? "Ingresá tu contraseña" : null);
                return;
            }

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Navegar a Registro (Opción A: Activity separada)
        tabRegistro.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        txtCambiarModo.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
        // tabLogin no necesita listener porque ya estás en esa pantalla
    }
}