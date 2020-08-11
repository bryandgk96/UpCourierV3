package com.example.upcourierv1.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.upcourierv1.Controlador.CustomerMapActivity;
import com.example.upcourierv1.DBHelpers.DBUsuario;
import com.example.upcourierv1.Modelos.Usuario;
import com.example.upcourierv1.R;

public class CustomerLoginRegisterActivity extends AppCompatActivity {
    private EditText mEmail, mPassword, mNombre, mApellido;
    private Button btnLogin, btnRegistrar;
    final String customer = "customer";
    private DBUsuario dbUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);

        mNombre = (EditText) findViewById(R.id.et_nombreCustomer);
        mApellido = (EditText) findViewById(R.id.et_apellidoCustomer);
        mEmail = (EditText) findViewById(R.id.emailC);
        mPassword = (EditText) findViewById(R.id.passwordC);

        dbUsuario = new DBUsuario(this);

        //btnLogin = (Button) findViewById(R.id.btnLoginC);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrarC);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario nuevo = new Usuario();
                nuevo.setNombre(mNombre.getText().toString());
                nuevo.setApellido(mApellido.getText().toString());
                nuevo.setCorreo(mEmail.getText().toString());
                nuevo.setContra(mPassword.getText().toString());
                nuevo.setTipoUsuario(customer);

                if(nuevo.isNull())
                    Toast.makeText(CustomerLoginRegisterActivity.this, "Campos vacios", Toast.LENGTH_SHORT).show();
                else if(dbUsuario.insertarUsuario(nuevo)){
                    Toast.makeText(CustomerLoginRegisterActivity.this, "Registro EXITOSO", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CustomerLoginRegisterActivity.this, CustomerMapActivity.class);

                    i.putExtra("idUser", nuevo.getId());
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(CustomerLoginRegisterActivity.this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                    mNombre.setText("");
                    mApellido.setText("");
                    mEmail.setText("");
                    mPassword.setText("");
                }
            }
        });

        /*btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerLoginRegisterActivity.this, "No habilitado", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}