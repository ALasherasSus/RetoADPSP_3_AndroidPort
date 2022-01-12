package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConexionBBDD extends AppCompatActivity {
    private TextView txtResultados;
    private EditText edServidor;
    private EditText edPuerto;
    private EditText edConsulta;
    private String baseDatos = "euskmet3";
    private Statement st;
    private ResultSet rs;
    String usuario="root";
    String contraseña=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_bbdd);
        edServidor = (EditText)findViewById(R.id.editTextServidor);
        edPuerto  = (EditText)findViewById(R.id.editTextPuerto);
        txtResultados  = (TextView)findViewById(R.id.txtResultado);
        edConsulta  = (EditText)findViewById(R.id.editTextConsulta);


        Bundle bundle = getIntent().getExtras();
        //usuario=bundle.getString("Usuario");
        //contraseña=bundle.getString("Contraseña");
    }


    public void conectarMySQL(View v) {
        {
            //Variable boolean que almacenará si el estado de la conexión es true o false
            boolean estadoConexion = false;
            //Inicializamos la Clase Connection encarada de conectar con la base de datos.
            Connection conexionMySQL = null;

            //Se declaran varias variables de tipo String que almacenarán los valores introducidos por pantalla
            String user = usuario;
            String password = contraseña;
            String puerto = edPuerto.getText().toString();
            String ip = edServidor.getText().toString();

            //Asignamos el driver a una variable de tipo String
            String driver = "com.mysql.jdbc.Driver";

            //Construímos la url para establecer la conexión
            String urlMySQL = "jdbc:mysql://" + ip + ":" + puerto + "/"+baseDatos;
            try {
                //Cargamos el driver del conector JDBC
                Class.forName(driver).newInstance();
                //Establecemos la conexión con el Servidor MySQL indicándole como parámetros la url construida,
                //la Base de Datos a la que vamos a conectarnos, y el usuario y contraseña de acceso al servidor
                conexionMySQL = DriverManager.getConnection(urlMySQL , user, password);
                //Comprobamos que la conexión se ha establecido
                if (!conexionMySQL.isClosed()) {
                    estadoConexion = true;
                    Toast.makeText(ConexionBBDD.this, "Conexión Establecida", Toast.LENGTH_LONG).show();
                    st = conexionMySQL.createStatement();
                    //Se ejecutará la consulta indicada en el campo edConsulta
                    rs = st.executeQuery(edConsulta.getText().toString());
                    String resultadoSQL = "";
                    Integer numColumnas = 0;

                    //Variable que almacenará el número de columnas obtenidas de la consulta Transact-SQL
                    numColumnas = rs.getMetaData().getColumnCount();

                    //Búcle encargado de recorrer y mostrar los resultados a partir de la consulta ejecutada
                    while (rs.next())
                    {
                        for (int i = 1; i <= numColumnas; i++)
                        {
                            if (rs.getObject(i) != null)
                            {
                                if (resultadoSQL != "")
                                    if (i < numColumnas)
                                        resultadoSQL = resultadoSQL + rs.getObject(i).toString() + ";";
                                    else
                                        resultadoSQL = resultadoSQL + rs.getObject(i).toString();
                                else
                                if (i < numColumnas)
                                    resultadoSQL = rs.getObject(i).toString() + ";";
                                else
                                    resultadoSQL = rs.getObject(i).toString();
                            }
                            else
                            {
                                if (resultadoSQL != "")
                                    resultadoSQL = resultadoSQL + "null;";
                                else
                                    resultadoSQL = "null;";
                            }
                        }
                        resultadoSQL = resultadoSQL + "n";
                    }
                    txtResultados.setText(resultadoSQL);
                    st.close();
                    rs.close();
                }
            }catch(Exception ex)
            {
                Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                        + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        }


}