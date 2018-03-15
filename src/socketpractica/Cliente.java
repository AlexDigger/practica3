package socketpractica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;

public class Cliente {

    public String enviar(String sentencia) {

        Socket socketCliente = null;
        BufferedReader entrada = null;
        PrintWriter salida = null;
        String resp = "" ;

        try {
            socketCliente = new Socket("localhost", 3000);
            // Obtenemos el canal de entrada
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            // Obtenemos el canal de salida
            salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
            salida.println(sentencia);

        } catch (IOException e) {
            System.err.println("No puede establer canales de E/S para la conexión");
            System.exit(-1);
        }

        try {
            resp = entrada.readLine();
            entrada.close();
            salida.close();
            socketCliente.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        salida.close();
        return resp;
    }

}
