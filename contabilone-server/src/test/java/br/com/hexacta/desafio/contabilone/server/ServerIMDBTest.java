package br.com.hexacta.desafio.contabilone.server;

import br.com.hexacta.desafio.contabilone.client.socket.ClientIMDB;
import br.com.hexacta.desafio.contabilone.server.socket.ClientHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
class ServerIMDBTest {

    private static final int PORT = 8887;

    private Semaphore lock = new Semaphore(0);

    @DisplayName("Teste inicial de conexão ao socket")
    @Test
    public void testSocketConnection() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        ClientHandler clientHandler = new ClientHandler(server.accept());
        Thread serverThread = new Thread(clientHandler);
        serverThread.start();

        ClientIMDB client = new ClientIMDB();
        String response = client.connect("127.0.0.1", PORT).published("bela");

        Assertions.assertNotNull(response);
        server.close();
    }
}