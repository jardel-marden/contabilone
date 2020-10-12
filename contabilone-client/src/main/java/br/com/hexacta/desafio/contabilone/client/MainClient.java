package br.com.hexacta.desafio.contabilone.client;

import br.com.hexacta.desafio.contabilone.client.socket.ClientIMDB;

import java.io.IOException;

/**
 * @author Jardel Marden on 10/10/2020
 * @project ContabilOne
 */
public class MainClient {
    public static void main(String[] args) throws IOException {
        ClientIMDB client = new ClientIMDB();

        client.connect("127.0.0.1", 3322)
                .keySearchTitle();
    }
}
