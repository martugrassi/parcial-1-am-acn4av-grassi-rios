package com.example.parcial_1_am_acn4av_grassi_rios;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


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

        ImageView imgPromociones = findViewById(R.id.imgPromociones);
        ImageView imgInicio = findViewById(R.id.imgInicio);
        ImageView imgProgreso = findViewById(R.id.imgProgreso);
        ImageView imgPedidos = findViewById(R.id.imgPedidos);

        ImageView imgMenu = findViewById(R.id.imgMenu);
        ImageView imgCart = findViewById(R.id.imgCart);
        ImageView imgPromo = findViewById(R.id.imgPromo);

        ImageView imgCreatina = findViewById(R.id.imgCreatina);
        ImageView imgOmega = findViewById(R.id.imgomega);
        ImageView imgPreentreno = findViewById(R.id.imgpreentreno);
        ImageView imgProteina = findViewById(R.id.imgproteina);



        // NAVBAR

        imgPromociones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Acceso a promociones", Toast.LENGTH_SHORT).show();
            }
        });

        imgInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Acceso a inicio", Toast.LENGTH_SHORT).show();
            }
        });

        imgProgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Acceso a progreso", Toast.LENGTH_SHORT).show();
            }
        });

        imgPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Acceso a pedidos", Toast.LENGTH_SHORT).show();
            }
        });


// HEADER

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Menú principal", Toast.LENGTH_SHORT).show();
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Carrito de compras", Toast.LENGTH_SHORT).show();
            }
        });


// PROMO

        imgPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Promociones disponibles", Toast.LENGTH_SHORT).show();
            }
        });


// PRODUCTOS

        imgCreatina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Producto seleccionado: Creatina", Toast.LENGTH_SHORT).show();
            }
        });

        imgOmega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Producto seleccionado: Omega 3", Toast.LENGTH_SHORT).show();
            }
        });

        imgPreentreno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Producto seleccionado: Pre-entreno", Toast.LENGTH_SHORT).show();
            }
        });

        imgProteina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Producto seleccionado: Proteína", Toast.LENGTH_SHORT).show();
            }
        });
    }
}