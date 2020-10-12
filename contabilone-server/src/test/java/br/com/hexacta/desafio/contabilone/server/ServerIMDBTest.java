package br.com.hexacta.desafio.contabilone.server;

import br.com.hexacta.desafio.contabilone.client.socket.ClientIMDB;
import br.com.hexacta.desafio.contabilone.server.socket.ClientHandler;
import br.com.hexacta.desafio.contabilone.server.utils.StringUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
class ServerIMDBTest {

    private static final int PORT = 8887;

    @AfterEach
    public void setUp() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        ClientHandler clientHandler = new ClientHandler(server.accept());
        Thread serverThread = new Thread(clientHandler);
        serverThread.start();
    }

    @DisplayName("Testa consulta com auto gerador de template")
    @Test
    public void testSocketConnectionComGeradorTemplate() {
        listen("bela");
    }

    @DisplayName("Testa consulta com template")
    @Test
    public void testSocketConnectionSemGeradorTemplate() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<query %s>%s", "bela".length(), StringUtil.LINE_SEPARATOR));
        builder.append(String.format("\tTitle:%s%s", "bela", StringUtil.LINE_SEPARATOR));
        builder.append("<query>");

        listen(builder.toString());
    }

    public void listen(String body) {
        new Thread(() -> {
            try {
                ClientIMDB client = new ClientIMDB();
                String response = client.connect("127.0.0.1", PORT).published(body);
                Assertions.assertNotNull(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}