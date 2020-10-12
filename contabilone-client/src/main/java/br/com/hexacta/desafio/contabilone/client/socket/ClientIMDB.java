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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.hexacta.desafio.contabilone.client.utils.StringUtil.LINE_SEPARATOR;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
@Slf4j
public class ClientIMDB {

    private PrintWriter saida;
    private BufferedReader leitura;

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
            leitura = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return this;
    }

    public void keySearchTitle() throws IOException {
        System.out.print("Qual titulo deseja buscar? ");
        Scanner teclado = new Scanner(System.in);

        try {
            if (teclado.hasNextLine()) {
                String term = teclado.nextLine();
                log.info("Buscando \"{}\" aguarde...", term);

                String message = published(term);
                System.out.printf("%n");
                System.out.println(message);
            }
        } catch (IOException e) {
            log.error("Socket read Error");
        } finally {
            saida.close();
            leitura.close();
        }
    }

    /**
     * Solicita ao server tcp/ip uma consulta de filmes por titulo, o template
     * <query> sera auto identificado antes de gerar um template. Caso contrário
     * cria-se um template para a requisição.
     *
     * @param body
     * @return Uma list de filmes por título
     * @throws IOException
     */
    public String published(String body) throws IOException {

        Pattern pattern = Pattern.compile("<query ([0-9])>.*\\tTitle+:([0-9a-zA-Z]).*<query>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(body);

        if (!matcher.find()) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("<query %s>%s", body.length(), LINE_SEPARATOR));
            builder.append(String.format("\tTitle:%s%s", body, LINE_SEPARATOR));
            builder.append("<query>");
            body = builder.toString();
        }

        saida.println(body);
        return onMessage();
    }

    public String onMessage() throws IOException {
        String line = leitura.readLine();
        StringBuilder builder = new StringBuilder();

        builder.append(line).append(LINE_SEPARATOR);

        while (line != null && !line.isEmpty()) {
            line = leitura.readLine();
            builder.append(line).append(LINE_SEPARATOR);
        }

        return builder.toString();
    }
}
