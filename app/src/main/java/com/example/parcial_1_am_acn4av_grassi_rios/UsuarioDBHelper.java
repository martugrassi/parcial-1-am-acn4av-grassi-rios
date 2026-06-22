package com.example.parcial_1_am_acn4av_grassi_rios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuarioDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fitmarket.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_USUARIOS = "usuarios";
    private static final String COL_ID = "id";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";

    public UsuarioDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOMBRE + " TEXT NOT NULL, " +
                COL_EMAIL + " TEXT UNIQUE NOT NULL, " +
                COL_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    // Devuelve true si se pudo registrar, false si el email ya existe
    public boolean registrarUsuario(String nombre, String email, String password) {
        if (existeEmail(email)) return false;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOMBRE, nombre);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);

        long resultado = db.insert(TABLE_USUARIOS, null, values);
        db.close();
        return resultado != -1;
    }

    public boolean existeEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USUARIOS, new String[]{COL_ID},
                COL_EMAIL + "=?", new String[]{email}, null, null, null);
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return existe;
    }

    // Devuelve true si el email y password coinciden con un usuario guardado
    public boolean validarLogin(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USUARIOS, new String[]{COL_ID},
                COL_EMAIL + "=? AND " + COL_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        boolean valido = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return valido;
    }

    // Opcional: obtener el nombre del usuario logueado, para saludarlo en MainActivity
    public String obtenerNombre(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USUARIOS, new String[]{COL_NOMBRE},
                COL_EMAIL + "=?", new String[]{email}, null, null, null);
        String nombre = "";
        if (cursor.moveToFirst()) {
            nombre = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return nombre;
    }
}