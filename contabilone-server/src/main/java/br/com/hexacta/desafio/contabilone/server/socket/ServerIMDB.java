package br.com.hexacta.desafio.contabilone.server.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */

public class ServerIMDB {

    private ServerSocket server;

    /**
     * Inicia o serviço socket TPC/IP
     *
     * @param port int
     */
    public void start(int port) throws IOException {

        if (port < 1024) {
            throw new IllegalArgumentException("A porta do serviço deve ser maior ou igual a 1024");
        }

        try {
            server = new ServerSocket(port);

            if (!server.isBound()){
                server.bind(new InetSocketAddress("192.168.0.1", 0));
            }

            InetAddress inet = server.getInetAddress();

            System.out.println("HostAddress=" + inet.getHostAddress());
            System.out.println("HostName=" + inet.getHostName());
            System.out.println("Port=" + server.getLocalPort());

            while (true) {
                ClientHandler clientHandler = new ClientHandler(server.accept());
                Thread serverThread = new Thread(clientHandler);
                serverThread.start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            server.close();
            System.out.println("Server stop");
        }
    }

    public void close() throws IOException {
        if (server.isBound()) {
            server.close();
        }
    }
}
