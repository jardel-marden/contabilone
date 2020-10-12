package br.com.hexacta.desafio.contabilone.server;

import br.com.hexacta.desafio.contabilone.server.socket.ServerIMDB;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
public class MainServer {

    public static void main(String[] args) throws IOException {
        new ServerIMDB().start(portServer(args));
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
