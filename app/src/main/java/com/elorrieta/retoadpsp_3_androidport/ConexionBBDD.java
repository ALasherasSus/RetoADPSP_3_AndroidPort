package com.elorrieta.retoadpsp_3_androidport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

public class ConexionBBDD extends Activity {

    private EditText edServidor, edPuerto;
    private String baseDatos = "euskmet3";
    String user;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_bbdd);

        edServidor = (EditText)findViewById(R.id.edServidor);
        edPuerto  = (EditText)findViewById(R.id.edPuerto);

        Bundle bundle = getIntent().getExtras();
        user=bundle.getString("Usuario");
        password=bundle.getString("Contraseña");

    }

    public boolean conectarMySQL()
    {
        /*Variable boolean que almacenará si el estado de la conexión es true o false.*/
        boolean estadoConexion = false;

        /*Se declaran varias variables de tipo String
        que almacenarán los valores introducidos por pantalla.*/

        String puerto = edPuerto.getText().toString();
        String ip = edServidor.getText().toString();

        /*Asignamos el driver a una variable de tipo String.*/
        String driver = "com.mysql.jdbc.Driver";

        try
        {
            /*Cargamos el driver del conector JDBC.*/
            Class.forName(driver).newInstance();

            if(user.equals("") || password.equals("") || puerto.equals("") || ip.equals(""))
            {
                Toast.makeText(ConexionBBDD.this,"Debe indicar todos los datos solicitados.", Toast.LENGTH_LONG).show();
            }else
            {
                String[] datos = new String[]{ip,puerto,baseDatos,user,password};
                estadoConexion = new ConexionAsincrona().execute(datos).get();
                Toast.makeText(ConexionBBDD.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex)
        {
            Toast.makeText(ConexionBBDD.this,"Error al comprobar las credenciales: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return estadoConexion;
    }

    //Evento On Click que realiza la llamada a la función conectarMySQL() obteniendo el valor de verdadero
    //o falso para la petición de conexión
    public void abrirConexion(View view)
    {
        Intent intent = new Intent(this,ConsultaBD.class);
        /*Si el valor devuelto por la función es true, pasaremos los datos de la conexión a la siguiente Activity.*/
        if(conectarMySQL())
        {
            Toast.makeText(this, "Los datos de conexión introducidos son correctos."
                    , Toast.LENGTH_LONG).show();
            intent.putExtra("servidor", edServidor.getText().toString());
            intent.putExtra("puerto", edPuerto.getText().toString());
            intent.putExtra("usuario", user);
            intent.putExtra("password", password);
            intent.putExtra("datos", baseDatos);
            startActivity(intent);
        }
    }



}