package com.example.parcial_1_am_acn4av_grassi_rios;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Map;

public class CarritoActivity extends AppCompatActivity {

    private LinearLayout contenedorProductosCarrito;
    private TextView txtCarritoVacio;
    private TextView txtTotalProductos;
    private ImageView imgVolverCarrito;
    private Button btnFinalizarCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carrito);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contenedorProductosCarrito = findViewById(R.id.contenedorProductosCarrito);
        txtCarritoVacio = findViewById(R.id.txtCarritoVacio);
        txtTotalProductos = findViewById(R.id.txtTotalProductos);
        imgVolverCarrito = findViewById(R.id.imgVolverCarrito);
        btnFinalizarCompra = findViewById(R.id.btnFinalizarCompra);

        imgVolverCarrito.setOnClickListener(v -> finish());

        btnFinalizarCompra.setOnClickListener(v -> {
            if (CarritoManager.obtenerTotalProductos() == 0) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Compra finalizada correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        mostrarProductosCarrito();
    }

    private void mostrarProductosCarrito() {
        contenedorProductosCarrito.removeAllViews();

        int total = CarritoManager.obtenerTotalProductos();
        txtTotalProductos.setText("Total productos: " + total);

        if (total == 0) {
            txtCarritoVacio.setVisibility(View.VISIBLE);
            return;
        }

        txtCarritoVacio.setVisibility(View.GONE);

        for (Map.Entry<String, Integer> producto : CarritoManager.productos.entrySet()) {
            agregarCardProducto(producto.getKey(), producto.getValue());
        }
    }

    private void agregarCardProducto(String nombre, int cantidad) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(16, 16, 16, 16);
        card.setBackgroundResource(R.drawable.bg_product_card);

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, 14);
        card.setLayoutParams(cardParams);

        ImageView imagen = new ImageView(this);
        imagen.setImageResource(obtenerImagenProducto(nombre));
        imagen.setScaleType(ImageView.ScaleType.FIT_CENTER);

        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(80, 80);
        imgParams.setMargins(0, 0, 14, 0);
        imagen.setLayoutParams(imgParams);

        LinearLayout info = new LinearLayout(this);
        info.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        info.setLayoutParams(infoParams);

        TextView txtNombre = new TextView(this);
        txtNombre.setText(nombre);
        txtNombre.setTextSize(16);
        txtNombre.setTextColor(0xFF111111);
        txtNombre.setTypeface(null, android.graphics.Typeface.BOLD);

        LinearLayout contador = new LinearLayout(this);
        contador.setOrientation(LinearLayout.HORIZONTAL);
        contador.setGravity(Gravity.CENTER_VERTICAL);
        contador.setPadding(0, 10, 0, 0);

        Button btnMenos = new Button(this);
        btnMenos.setText("-");
        btnMenos.setTextSize(14);
        btnMenos.setMinWidth(0);
        btnMenos.setMinHeight(0);

        TextView txtCantidad = new TextView(this);
        txtCantidad.setText(String.valueOf(cantidad));
        txtCantidad.setTextSize(16);
        txtCantidad.setTextColor(0xFF111111);
        txtCantidad.setPadding(16, 0, 16, 0);

        Button btnMas = new Button(this);
        btnMas.setText("+");
        btnMas.setTextSize(14);
        btnMas.setMinWidth(0);
        btnMas.setMinHeight(0);

        contador.addView(btnMenos);
        contador.addView(txtCantidad);
        contador.addView(btnMas);

        info.addView(txtNombre);
        info.addView(contador);

        ImageView imgEliminar = new ImageView(this);
        imgEliminar.setImageResource(android.R.drawable.ic_menu_delete);
        imgEliminar.setPadding(12, 12, 12, 12);

        card.addView(imagen);
        card.addView(info);
        card.addView(imgEliminar);

        contenedorProductosCarrito.addView(card);

        btnMas.setOnClickListener(v -> {
            CarritoManager.agregarProducto(nombre);
            mostrarProductosCarrito();
        });

        btnMenos.setOnClickListener(v -> {
            CarritoManager.restarProducto(nombre);
            mostrarProductosCarrito();
        });

        imgEliminar.setOnClickListener(v -> {
            CarritoManager.eliminarProducto(nombre);
            Toast.makeText(this, nombre + " eliminado del carrito", Toast.LENGTH_SHORT).show();
            mostrarProductosCarrito();
        });
    }

    private int obtenerImagenProducto(String nombre) {
        if (nombre.equals("Creatina")) {
            return R.drawable.creatina;
        } else if (nombre.equals("Omega-3")) {
            return R.drawable.omega3;
        } else if (nombre.equals("Pre-entreno")) {
            return R.drawable.preentreno;
        } else if (nombre.equals("Proteína")) {
            return R.drawable.proteina;
        }

        return R.drawable.carro;
    }
}