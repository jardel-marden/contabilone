package br.com.hexacta.desafio.contabilone.client;

import br.com.hexacta.desafio.contabilone.client.socket.ClientIMDB;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jardel Marden on 10/10/2020
 * @project ContabilOne
 */
public class MainClient {
    public static void main(String[] args) throws IOException {
        ClientIMDB client = new ClientIMDB();

        client.connect("127.0.0.1", portServer(args))
                .keySearchTitle();
    }

    /**
     * Captura o parametro <b>--port</b> ao executar o projeto, caso contrario
     * retorna a porta padrão 1024.
     *
     * @param args {@link String[]}
     * @return a porta para o server
     */
    private static int portServer(String[] args) {
        AtomicInteger port = new AtomicInteger(3322); // default port

        Arrays.asList(args).forEach(command -> {
            if (command.startsWith("--port=")) {
                String value = command.substring(command.indexOf("--port="), command.length() - 1);

                try {
                    if (!value.isEmpty()) {
                        port.set(Integer.parseInt(value));
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        });

        return port.get();
    }
}
