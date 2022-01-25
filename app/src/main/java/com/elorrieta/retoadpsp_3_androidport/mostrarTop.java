package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class mostrarTop extends AppCompatActivity {

    private Button pueblos,provincias;
    private Bundle bundle;
    private String txtBaseDatos,txtPuerto,txtServidor,txtUsuario,txtPass,Consulta;
    private String[] datosConexion = null;
    private EditText edResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_top);

        bundle = getIntent().getExtras();
        txtServidor=(bundle.getString("servidor"));
        txtPuerto=(bundle.getString("puerto"));
        txtUsuario=(bundle.getString("usuario"));
        txtPass=(bundle.getString("password"));
        txtBaseDatos=(bundle.getString("datos"));

        pueblos = (Button)findViewById(R.id.btnmunicipios);
        provincias = (Button)findViewById(R.id.btnprovincias);

    }
    public void Consultapueblo(View view)
    {
        Consulta="Select idPueblo from pueblo";
        mostrarResultados();
    }

    public void Consultaprovincias(View view)
    {
        Consulta="Select idProvincia from provincias";
        mostrarResultados();
    }

    //Evento On Click que conecta con el servidor MySQL y procesa las consultas mostrando los resultados
    public void mostrarResultados()
    {
        ArrayList<String> NombreP = new ArrayList<>();

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
                        Consulta,

                };
                //Asignamos el driver a una variable de tipo String
                String driver = "com.mysql.jdbc.Driver";
                //Cargamos el driver del conector JDBC
                Class.forName(driver).newInstance ();
                resultadoSQL = new TopAsincrono().execute(datosConexion).get();
                Toast.makeText(mostrarTop.this,"Conexión Establecida", Toast.LENGTH_LONG).show();

                String resultadoConsulta = resultadoSQL[0];
                String numFilas = resultadoSQL[1];
                String numColumnas = resultadoSQL[2];
                edResultado.setText(resultadoConsulta + "Número de filas devueltas: " +
                        numFilas + "\nNúmero de columnas devueltas: " + numColumnas);
            }
        }catch(Exception ex)
        {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}