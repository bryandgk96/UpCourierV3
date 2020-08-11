package com.example.upcourierv1.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.upcourierv1.Modelos.CustomerRequest;
import com.example.upcourierv1.Modelos.ServiciosRealizados;
import com.example.upcourierv1.Modelos.Usuario;

import java.util.ArrayList;

public class DBRequestsServices {

    Context context;
    ServiciosRealizados serviciosRealizados;
    CustomerRequest customerRequest;
    ArrayList<ServiciosRealizados> listaServicios;
    ArrayList<ServiciosRealizados> listaSolicitudes;
    SQLiteDatabase db;
    String nombredb = "BDUsuarios";
    String tabla = "CREATE TABLE IF NOT EXISTS servicios (id INTEGER PRIMARY KEY AUTOINCREMENT, driver TEXT, origen TEXT, destino TEXT, customer TEXT, estado TEXT)";
    String tabla2 = "CREATE TABLE IF NOT EXISTS solicitudes (id INTEGER PRIMARY KEY AUTOINCREMENT, correo TEXT, latitud TEXT, longitud TEXT, estado TEXT)";

    /*
    private int id;
    private String driver;
    private String origen;
    private String Destino;
    private String customer;
    private String estado;
    * */

    public DBRequestsServices(Context _context){
        context = _context;
        db = context.openOrCreateDatabase(nombredb, context.MODE_PRIVATE, null);
        db.execSQL(tabla);
        db.execSQL(tabla2);
        serviciosRealizados = new ServiciosRealizados();
        customerRequest = new CustomerRequest();
    }

    public boolean crearSolicitud(CustomerRequest _customerRequest){

        if(_customerRequest == null)
            return false;
        else{
            ContentValues c = new ContentValues();
            c.put("correo", _customerRequest.getCorreo());
            c.put("latitud", _customerRequest.getLatitud());
            c.put("longitud", _customerRequest.getLongitud());
            c.put("estado", _customerRequest.getEstado());

            db.insert("solicitudes", null, c);

            return true;
        }
    }


    public ArrayList<CustomerRequest> obtenerSolicitudes(){
        ArrayList<CustomerRequest> customerReq = new ArrayList<>();
        Cursor c = db.rawQuery("select * from solicitudes where estado='Espera' ", null);
        if(c != null && c.getCount()>0){ //c.moveToFirst()
            if(c.moveToFirst()){
                do{
                    int id = c.getInt(c.getColumnIndex("id"));
                    String correo = c.getString(c.getColumnIndex("correo"));
                    String latitud = c.getString(c.getColumnIndex("latitud"));
                    String longitud = c.getString(c.getColumnIndex("longitud"));
                    String estado = c.getString(c.getColumnIndex("estado"));


                    CustomerRequest getUser = new CustomerRequest(id, correo, latitud, longitud, estado);



                    customerReq.add(getUser);
                }while (c.moveToNext());
            }
        }

        c.close();

        return customerReq;

    }

}
