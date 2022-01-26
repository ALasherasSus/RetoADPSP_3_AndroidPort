package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Municipio extends AppCompatActivity {

    private String txtBaseDatos,txtPuerto,txtServidor,txtUsuario,txtPass,Consulta,Ubicacion,NombreUbicacion;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Nombre);
        TextTarea.setAdapter(adapter);

        TextTarea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                NombreUbicacion = (String) TextTarea.getItemAtPosition(i);
                Ubicacion= Consulta;
                siguiente();
            }


        });
    }
    public void siguiente(){

            Intent i = new Intent(this, Pueblo.class);

            i.putExtra("Nombre", NombreUbicacion);
            i.putExtra("Ubicacion", Ubicacion);
            i.putExtra("servidor", txtServidor);
            i.putExtra("puerto", txtPuerto);
            i.putExtra("usuario", txtUsuario);
            i.putExtra("password", txtPass);
            i.putExtra("datos", txtBaseDatos);
            startActivity(i);

    }

    public void Pueblo(View view){
        Consulta="Select Nombre from Pueblo";
        mostrarResultados();
    }

    public void Provincia(View view){
        Consulta="Select Nombre from Provincia";
        mostrarResultados();
    }

    public void Estacion(View view){
        Consulta="Select Nombre from Estaciones";
        mostrarResultados();
    }
    public void Espacio(View view){
        Consulta="Select Nombre from Espacionatural";
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