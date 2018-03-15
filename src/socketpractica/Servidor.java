package socketpractica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.sql.ResultSet;

public class Servidor {

    public static void main(String args[]) {
        ServerSocket socketServidor = null;
        try {
            socketServidor = new ServerSocket(3000);
        } catch (IOException e) {
            System.out.println("No puede escuchar en el puerto: " + 3000);
            System.exit(-1);
        }

        Socket socketCliente = null;
        BufferedReader entrada = null;
        PrintWriter salida = null;

        Conexion con = new Conexion();
        con.conectar();
        try {
            socketCliente = socketServidor.accept();
            System.out.println("Connexion acceptada: " + socketCliente);
            // Establece canal de entrada
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            // Establece canal de salida
            salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
            //leo lo que el cliente me envia
            String sql = entrada.readLine();
            System.out.println(sql);
            String[] porptcoma = sql.split(";");
            for (int i = 0; i < porptcoma.length; i++) {
                String[] separar = sql.split(" ");
                if (separar[0].equals("CREATE") || separar[0].equals("INSERT") || separar[0].equals("UPDATE") || separar[0].equals("DELETE")) {
                    if (con.ejec(sql)) {
                        salida.print(separar[0] + " ejecutado Exitosamente!");
                    } else {
                        salida.print("Error al ejecutar el " + separar[0]);
                    }
                }
                if (separar[0].equals("SELECT") || separar[0].equals("SHOW")) {
                    ResultSet resultados = con.consultar(sql);
                    String lista = "";
                    if (resultados != null) {
                        try {
                            while (resultados.next()) {
                                lista = lista + resultados.getString(1) + " ";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            salida.print("Error");
                        }
                    }
                    salida.print(lista);
                }
            }
            /**/
            salida.print(sql);

            salida.close();
            entrada.close();
            socketCliente.close();
            socketServidor.close();

        } catch (Exception e) {

        }

    }
}
