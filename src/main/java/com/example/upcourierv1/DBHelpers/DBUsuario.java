package com.example.upcourierv1.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.upcourierv1.Modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DBUsuario {

    Context context ;
    Usuario usuario;
    List<Usuario> listaUsuario;
    SQLiteDatabase db;
    String nombreDB = "BDUsuarios";
    String tabla = "CREATE TABLE IF NOT EXISTS usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, correo TEXT, contra TEXT, tipUsuario TEXT)";

    public DBUsuario (Context _context){
        context = _context;
        db = context.openOrCreateDatabase(nombreDB, context.MODE_PRIVATE, null);
        db.execSQL(tabla);
        usuario = new Usuario();
    }

    // usuario (correo TEXT, contra TEXT, tipUsuario TEXT)
    public boolean insertarUsuario(Usuario _user){
        if(buscarUsuario(_user.getCorreo()) == 0){
            ContentValues c = new ContentValues();
            c.put("nombre", _user.getNombre());
            c.put("apellido", _user.getApellido());
            c.put("correo", _user.getCorreo());
            c.put("contra", _user.getContra());
            c.put("tipUsuario", _user.getTipoUsuario());

            db.insert("usuario", null, c);

            return true;
        }else
            return  false;
    }

    public int buscarUsuario(String _correo){
        int x = 0;
        listaUsuario = obtenerUsuarios();

        for(Usuario us : listaUsuario){
            if(us.getCorreo().equals(_correo))
                x++;
        }
        return x;

    }

    public List<Usuario> obtenerUsuarios(){
        List<Usuario> list = new ArrayList<Usuario>();
        Cursor c = db.rawQuery("select * from usuario", null);
        if(c != null && c.getCount()>0){ //c.moveToFirst()
            if(c.moveToFirst()){
                do{
                    Usuario getUser = new Usuario();
                    getUser.setId(c.getInt(0));
                    getUser.setNombre(c.getString(1));
                    getUser.setApellido(c.getString(2));
                    getUser.setCorreo(c.getString(3));
                    getUser.setContra(c.getString(4));
                    getUser.setTipoUsuario(c.getString(5));
                    list.add(getUser);
                }while (c.moveToNext());
            }
        }

        c.close();

        return list;
    }
    public Usuario getUsuarioById(int id){
        listaUsuario = obtenerUsuarios();
        for (Usuario usd : listaUsuario){
            if(usd.getId() == id )
                return usd;
        }
        return null;
    }

}
