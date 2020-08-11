package com.example.upcourierv1.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.upcourierv1.Controlador.DriverMapActivity;
import com.example.upcourierv1.DBHelpers.DBUsuario;
import com.example.upcourierv1.Modelos.Usuario;
import com.example.upcourierv1.R;

public class DriverLoginRegisterActivity extends AppCompatActivity {
    private EditText mEmail, mPassword , mNombre, mApellido;
    private Button btnLogin, btnRegistrar;
    final String driver = "driver";
    private DBUsuario dbUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);

        mNombre = (EditText) findViewById(R.id.et_nombreDriver);
        mApellido = (EditText) findViewById(R.id.et_apellidoDriver);
        mEmail = (EditText) findViewById(R.id.emailD);
        mPassword = (EditText) findViewById(R.id.passwordD);

//        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        dbUsuario = new DBUsuario(this);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario nuevo = new Usuario();
                nuevo.setNombre(mNombre.getText().toString());
                nuevo.setApellido(mApellido.getText().toString());
                nuevo.setCorreo(mEmail.getText().toString());
                nuevo.setContra(mPassword.getText().toString());
                nuevo.setTipoUsuario(driver);

                if(nuevo.isNull())
                    Toast.makeText(DriverLoginRegisterActivity.this, "Campos vacios", Toast.LENGTH_SHORT).show();
                else if(dbUsuario.insertarUsuario(nuevo)){
                    Toast.makeText(DriverLoginRegisterActivity.this, "Registro EXITOSO", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(DriverLoginRegisterActivity.this, DriverMapActivity.class);
                    i.putExtra("idUser", nuevo.getId());
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(DriverLoginRegisterActivity.this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                    mNombre.setText("");
                    mApellido.setText("");
                    mEmail.setText("");
                    mPassword.setText("");
                }
            }
        });

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(DriverLoginRegisterActivity.this, "No habilitado", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}