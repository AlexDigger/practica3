/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketpractica;
import java.sql.*;
/**
 *
 * @author jhona
 */
public class Conexion {
    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Conexion conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String cadenaCocexion = "jdbc:mysql://localhost/datos?user=root&password=";
            setConexion(DriverManager.getConnection(cadenaCocexion));
            if(getConexion()!=null){
                System.out.println("Conexion Exitosa!");
            }else{
                System.out.println("Conexion Fallida!");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
    
    //Ejecutar
    public boolean ejec(String sql){
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            sentencia.executeUpdate(sql);
            sentencia.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    //Consultar
    public ResultSet consultar(String sql) {
        ResultSet resultado;
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }        return resultado;
    }
}
