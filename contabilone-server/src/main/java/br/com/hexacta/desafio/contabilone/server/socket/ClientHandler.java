package br.com.hexacta.desafio.contabilone.server.socket;

import br.com.hexacta.desafio.contabilone.server.domains.MovieDTO;
import br.com.hexacta.desafio.contabilone.server.services.ApiImdbMovieService;
import br.com.hexacta.desafio.contabilone.server.services.CropperMovieService;
import br.com.hexacta.desafio.contabilone.server.services.impl.ImdbCropperMovieServiceImpl;
import br.com.hexacta.desafio.contabilone.server.services.impl.OmdbApiMovieServiceImpl;
import br.com.hexacta.desafio.contabilone.server.utils.ResponseSocketUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class ClientHandler implements Runnable {

    private String payload;
    private final Socket client;
    private final ApiImdbMovieService apiImdbMovieService;
    private final CropperMovieService cropperMovieService;

    public ClientHandler(Socket client) {

        if (Objects.isNull(client)) {
            throw new IllegalArgumentException("Socket is not null");
        }

        this.client = client;
        this.apiImdbMovieService = new OmdbApiMovieServiceImpl();
        this.cropperMovieService = new ImdbCropperMovieServiceImpl();
    }

    @Override
    public void run() {
        try {
            PrintWriter saida = new PrintWriter(client.getOutputStream(), true);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8)
            );

            String title;

            while ((title = reader.readLine()) != null) {
                System.out.printf("Client search movie title: %s%n", title);

                if (!title.isEmpty()) {
                    delegate(title);
                    saida.println(payload);
                }
            }

            saida.close();
            reader.close();
            client.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Adicionei dois serviços de consulta para os titulos, um deles por meio
     * de uma API Rest e o segundo fluxo fazendo recortes no site https://www.imdb.com
     * <p>
     * Apos realizar a consulta é feito um merge dos resultados e também filtrado
     * por titulos duplicados.
     * <p>
     * A lista com os titulos é tratada para gerar um novo template e o mesmo
     * será usado como resposta para o cliente. O template gerado é setado na
     * variavel payload {@link String}
     *
     * @param title {@link String}
     * @throws IOException
     */
    private void delegate(String title) throws IOException {
        List<MovieDTO> movies = apiImdbMovieService.findByTitle(title);

        if (Objects.nonNull(movies)) {
            movies.addAll(cropperMovieService.findByTitle(title));

            movies = movies.stream()
                    .filter(distinctBy(MovieDTO::getTitle))
                    .sorted(Comparator.comparing(MovieDTO::getTitle))
                    .collect(Collectors.toList());
        } else {
            movies = cropperMovieService.findByTitle(title);
        }

        this.payload = ResponseSocketUtil.payload(movies);
    }

    public static <T> Predicate<T> distinctBy(Function<? super T, ?> f) {
        Set<Object> objects = new ConcurrentSkipListSet<>();
        return t -> objects.add(f.apply(t));
    }
}