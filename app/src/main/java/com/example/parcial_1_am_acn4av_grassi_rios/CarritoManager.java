package com.example.parcial_1_am_acn4av_grassi_rios;

import java.util.HashMap;

public class CarritoManager {

    public static HashMap<String, Integer> productos = new HashMap<>();

    public static void agregarProducto(String nombre) {
        if (productos.containsKey(nombre)) {
            int cantidadActual = productos.get(nombre);
            productos.put(nombre, cantidadActual + 1);
        } else {
            productos.put(nombre, 1);
        }
    }

    public static void restarProducto(String nombre) {
        if (productos.containsKey(nombre)) {
            int cantidadActual = productos.get(nombre);

            if (cantidadActual > 1) {
                productos.put(nombre, cantidadActual - 1);
            } else {
                productos.remove(nombre);
            }
        }
    }

    public static void eliminarProducto(String nombre) {
        productos.remove(nombre);
    }

    public static int obtenerCantidad(String nombre) {
        if (productos.containsKey(nombre)) {
            return productos.get(nombre);
        }

        return 0;
    }

    public static int obtenerTotalProductos() {
        int total = 0;

        for (int cantidad : productos.values()) {
            total += cantidad;
        }

        return total;
    }
}