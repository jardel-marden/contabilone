package br.com.hexacta.desafio.contabilone.server.utils;

import br.com.hexacta.desafio.contabilone.server.domains.MovieDTO;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
public abstract class ResponseSocketUtil {

    public static String payload(List<MovieDTO> movies) {
        final String lineSeparator = System.getProperty("line.separator");
        AtomicInteger length = new AtomicInteger(0);

        String titles = movies.stream()
                .map(movie -> {
                    length.addAndGet(movie.getTitle().length());
                    return String.format("%s:%s%s", "Title", movie.getTitle(), lineSeparator);
                })
                .collect(Collectors.joining());

        StringBuilder builder = new StringBuilder();

        builder.append(String.format("<payload %s>%s", "" + length.get(), lineSeparator));
        builder.append(titles);
        builder.append(String.format("<payload>%s", lineSeparator));

        return builder.toString();
    }

}
