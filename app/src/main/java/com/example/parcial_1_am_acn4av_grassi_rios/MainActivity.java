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
import android.content.Intent;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.content.res.ColorStateList;
import android.view.ViewGroup;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private HashMap<String, Button> botonesAgregar = new HashMap<>();
    private HashMap<String, LinearLayout> padresProducto = new HashMap<>();
    private HashMap<String, Integer> posicionesBotones = new HashMap<>();
    private HashMap<String, LinearLayout> contadoresProducto = new HashMap<>();
    private TextView txtResumenCarritoInicio;
    private TextView txtBadgeCarrito;

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

        Button btnAgregarCreatina = findViewById(R.id.btnAgregarCreatina);
        Button btnAgregarOmega = findViewById(R.id.btnAgregarOmega);
        Button btnAgregarPreentreno = findViewById(R.id.btnAgregarPreentreno);
        Button btnAgregarProteina = findViewById(R.id.btnAgregarProteina);

        txtResumenCarritoInicio = findViewById(R.id.txtResumenCarritoInicio);
        txtBadgeCarrito = findViewById(R.id.txtBadgeCarrito);

        configurarProducto(btnAgregarCreatina, "Creatina");
        configurarProducto(btnAgregarOmega, "Omega-3");
        configurarProducto(btnAgregarPreentreno, "Pre-entreno");
        configurarProducto(btnAgregarProteina, "Proteína");

        actualizarResumenCarritoInicio();
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
                Intent intent = new Intent(MainActivity.this, CarritoActivity.class);
                startActivity(intent);
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

    private void configurarProducto(Button boton, String producto) {
        botonesAgregar.put(producto, boton);

        LinearLayout padre = (LinearLayout) boton.getParent();
        padresProducto.put(producto, padre);
        posicionesBotones.put(producto, padre.indexOfChild(boton));

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarritoManager.agregarProducto(producto);
                Toast.makeText(MainActivity.this, producto + " agregado al carrito", Toast.LENGTH_SHORT).show();
                actualizarVistaProducto(producto);
                actualizarResumenCarritoInicio();
            }
        });
    }

    private void actualizarVistaProducto(String producto) {
        int cantidad = CarritoManager.obtenerCantidad(producto);

        Button botonAgregar = botonesAgregar.get(producto);
        LinearLayout padre = padresProducto.get(producto);
        int posicion = posicionesBotones.get(producto);

        LinearLayout contadorActual = contadoresProducto.get(producto);

        if (contadorActual != null && contadorActual.getParent() != null) {
            padre.removeView(contadorActual);
        }

        if (botonAgregar.getParent() != null) {
            padre.removeView(botonAgregar);
        }

        if (cantidad == 0) {
            padre.addView(botonAgregar, Math.min(posicion, padre.getChildCount()));
        } else {
            LinearLayout contadorNuevo = crearContadorProducto(producto, cantidad);
            contadoresProducto.put(producto, contadorNuevo);
            padre.addView(contadorNuevo, Math.min(posicion, padre.getChildCount()));
        }
    }

    private LinearLayout crearContadorProducto(String producto, int cantidad) {
        LinearLayout contador = new LinearLayout(this);
        contador.setOrientation(LinearLayout.HORIZONTAL);
        contador.setGravity(android.view.Gravity.CENTER);
        contador.setPadding(0, 10, 0, 0);

        Button btnMenos = new Button(this);
        btnMenos.setText("-");
        btnMenos.setTextSize(16);
        btnMenos.setMinWidth(0);
        btnMenos.setMinHeight(0);
        btnMenos.setBackgroundTintList(ColorStateList.valueOf(0xFFFF3B30));
        btnMenos.setTextColor(0xFFFFFFFF);

        TextView txtCantidad = new TextView(this);
        txtCantidad.setText(String.valueOf(cantidad));
        txtCantidad.setTextSize(14);
        txtCantidad.setTextColor(0xFF111111);
        txtCantidad.setPadding(12, 0, 12, 0);

        Button btnMas = new Button(this);
        btnMas.setText("+");
        btnMas.setTextSize(16);
        btnMas.setMinWidth(0);
        btnMas.setMinHeight(0);
        btnMas.setBackgroundTintList(ColorStateList.valueOf(0xFFFF3B30));
        btnMas.setTextColor(0xFFFFFFFF);

        btnMenos.setLayoutParams(new LinearLayout.LayoutParams(dp(34), dp(30)));
        btnMas.setLayoutParams(new LinearLayout.LayoutParams(dp(34), dp(30)));

        contador.addView(btnMenos);
        contador.addView(txtCantidad);
        contador.addView(btnMas);

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarritoManager.restarProducto(producto);
                actualizarVistaProducto(producto);
                actualizarResumenCarritoInicio();
            }
        });

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarritoManager.agregarProducto(producto);
                actualizarVistaProducto(producto);
                actualizarResumenCarritoInicio();
            }
        });

        return contador;
    }


    private void actualizarResumenCarritoInicio() {
        int total = CarritoManager.obtenerTotalProductos();

        if (total == 0) {
            txtResumenCarritoInicio.setText("Continua agregando productos...");
            txtBadgeCarrito.setVisibility(View.GONE);
        } else {
            txtBadgeCarrito.setVisibility(View.VISIBLE);
            txtBadgeCarrito.setText(String.valueOf(total));

            if (total == 1) {
                txtResumenCarritoInicio.setText("Tenés 1 producto agregado en el carrito");
            } else {
                txtResumenCarritoInicio.setText("Tenés " + total + " productos agregados en el carrito");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        actualizarVistaProducto("Creatina");
        actualizarVistaProducto("Omega-3");
        actualizarVistaProducto("Pre-entreno");
        actualizarVistaProducto("Proteína");
        actualizarResumenCarritoInicio();
    }

    private int dp(int valor) {
        return (int) (valor * getResources().getDisplayMetrics().density);
    }

}