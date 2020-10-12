package br.com.hexacta.desafio.contabilone.server.utils;

import br.com.hexacta.desafio.contabilone.server.domains.MovieDTO;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Classe utilitaria para montar template das requisições
 * entre servidor socket e cliente.
 *
 * @author Jardel Marden on 11/10/2020
 */
public abstract class ResponseSocketUtil {

    public static String payload(List<MovieDTO> movies) {
        final String lineSeparator = System.getProperty("line.separator");
        AtomicInteger length = new AtomicInteger(0);
        StringBuilder builder = new StringBuilder();

        String titles = movies.stream()
                .map(movie -> {
                    length.addAndGet(movie.getTitle().length());
                    return String.format("\t%s:%s%s", "Title", movie.getTitle(), lineSeparator);
                })
                .collect(Collectors.joining());

        builder.append(String.format("<payload %s>%s", length.get(), lineSeparator));
        builder.append(titles);
        builder.append(String.format("<payload>%s", lineSeparator));

        return builder.toString();
    }

}
