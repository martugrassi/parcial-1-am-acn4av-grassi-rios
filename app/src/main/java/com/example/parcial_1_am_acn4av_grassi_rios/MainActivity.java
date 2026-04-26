package com.example.parcial_1_am_acn4av_grassi_rios;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito);
        LinearLayout contenedorCarrito = findViewById(R.id.contenedorCarrito);

        btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contenedorCarrito.removeAllViews();

                TextView productoAgregado = new TextView(MainActivity.this);
                productoAgregado.setText("prueba");
                productoAgregado.setTextSize(16);
                productoAgregado.setPadding(0, 12, 0, 0);

                contenedorCarrito.addView(productoAgregado);
            }
        });
    }
}