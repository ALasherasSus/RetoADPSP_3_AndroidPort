package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Municipio extends AppCompatActivity {

    private String txtBaseDatos,txtPuerto,txtServidor,txtUsuario,txtPass,Consulta;

    private ListView TextTarea;

    private Bundle bundle;
    private String[] datosConexion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipio);

        TextTarea=(ListView) findViewById(R.id.list);
        ArrayList<String> Nombre = new ArrayList<>();

        bundle = getIntent().getExtras();
        //Obtenemos los valores introducidos en la Activity principal
        txtServidor=(bundle.getString("servidor"));
        txtPuerto=(bundle.getString("puerto"));
        txtUsuario=(bundle.getString("usuario"));
        txtPass=(bundle.getString("password"));
        txtBaseDatos=(bundle.getString("datos"));
    }


    public void Pueblo(View view){
        Consulta="Select Nombre from Pueblo";
        mostrarResultados();
    }

    public void Provincia(View view){
        Consulta="Select Nombre from Provincia";
        mostrarResultados();
    }


    public void mostrarResultados()
    {

        String[] resultadoSQL = null;
        try{
            if(Consulta.equals(""))
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
                        Consulta
                };
                //Asignamos el driver a una variable de tipo String
                String driver = "com.mysql.jdbc.Driver";
                //Cargamos el driver del conector JDBC
                Class.forName(driver).newInstance ();
                resultadoSQL = new FiltroAsincrono().execute(datosConexion).get();
                Toast.makeText(Municipio.this,"Conexión Establecida", Toast.LENGTH_LONG).show();




                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultadoSQL);
                    TextTarea.setAdapter(adapter);



            }
        }catch(Exception ex)
        {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



}