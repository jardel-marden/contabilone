package br.com.hexacta.desafio.contabilone.client.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
@Slf4j
public class ClientIMDB {

    private PrintWriter saida;
    private BufferedReader reader;

    public ClientIMDB connect(String ip, int port) {

        try {
            InetAddress inet = InetAddress.getLocalHost();
            Socket client = new Socket(ip, port);

            if (client.isConnected()) {
                System.out.println("HostAddress=" + inet.getHostAddress());
                System.out.println("HostName=" + inet.getHostName());
                System.out.println("Port=" + client.getLocalPort());
            }

            saida = new PrintWriter(client.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return this;
    }

    public void keySearchTitle() throws IOException {
        System.out.print("Qual titulo deseja buscar? ");
        Scanner teclado = new Scanner(System.in);

        try {
            while (teclado.hasNextLine()) {
                String term = teclado.nextLine();
                log.info("Buscando \"{}\" aguarde...", term);

                String message = published(term);
                System.out.printf("%n %n");
                System.out.println(message);

                if (message.isEmpty()) {
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Socket read Error");
        } finally {
            saida.close();
            reader.close();
        }
    }

    /**
     * Solicita ao server tcp/ip uma consulta de filmes por titulo
     *
     * @param title
     * @return Uma list de filmes por título
     * @throws IOException
     */
    public String published(String title) throws IOException {
        saida.println(title);
        return onMessage();
    }

    public String onMessage() throws IOException {
        String lineSeparator = System.getProperty("line.separator");
        String line = reader.readLine();
        StringBuilder builder = new StringBuilder();

        builder.append(line).append(lineSeparator);

        while (line != null && !line.isEmpty()) {
            line = reader.readLine();
            builder.append(line).append(lineSeparator);
        }

        return builder.toString();
    }
}
