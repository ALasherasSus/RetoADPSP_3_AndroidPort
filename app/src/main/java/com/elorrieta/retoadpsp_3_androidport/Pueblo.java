package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pueblo extends Activity {

    private String txtBaseDatos,txtPuerto,txtServidor,txtUsuario,txtPass,Pueblo,Tipo;
    private EditText edResultado;
    private Bundle bundle;
    private String[] datosConexion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pueblo);

        bundle = getIntent().getExtras();
        txtServidor=(bundle.getString("servidor"));
        txtPuerto=(bundle.getString("puerto"));
        txtUsuario=(bundle.getString("usuario"));
        txtPass=(bundle.getString("password"));
        txtBaseDatos=(bundle.getString("datos"));
        Pueblo=(bundle.getString("Nombre"));
        edResultado = (EditText)findViewById(R.id.edResultado);
        Tipo="Pueblo";

        mostrarResultados();
    }

    public void gps(View view){
        Intent i = new Intent(this, GPS.class);
        i.putExtra("Nombre", Pueblo);
        startActivity(i);
    }

    public void mostrarResultados()
    {
        ArrayList<String> NombreP = new ArrayList<>();
        String consulta ="Select * from Pueblo where Nombre= '"+Pueblo+"'";
        String[] resultadoSQL = null;
        try{
            if(consulta.equals(""))
            {
                Toast.makeText(this, "Debe indicar una consulta Transact-SQL válida.", Toast.LENGTH_LONG).show();
            }else
            {
                datosConexion = new String[]{
                        txtServidor,
                        txtPuerto,
                        txtBaseDatos,
                        txtUsuario,
                        txtPass,
                        consulta,
                        Tipo
                };
                //Asignamos el driver a una variable de tipo String
                String driver = "com.mysql.jdbc.Driver";
                //Cargamos el driver del conector JDBC
                Class.forName(driver).newInstance ();
                resultadoSQL = new ConsultasAsincrona().execute(datosConexion).get();
                Toast.makeText(Pueblo.this,"Conexión Establecida", Toast.LENGTH_LONG).show();

                String resultadoConsulta = resultadoSQL[0];

                edResultado.setText(resultadoConsulta);
            }
        }catch(Exception ex)
        {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}