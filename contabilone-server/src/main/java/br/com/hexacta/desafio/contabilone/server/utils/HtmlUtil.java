package br.com.hexacta.desafio.contabilone.server.utils;

import br.com.hexacta.desafio.contabilone.server.domains.MovieDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
public abstract class HtmlUtil {

    public static List<MovieDTO> parse(Elements table) {
        List<MovieDTO> result = new ArrayList<>();

        try {
            for (Element element : table) {
                List<MovieDTO> td1 = element.getElementsByTag("td")
                        .stream()
                        .map(td -> td.select("td.result_text"))
                        .filter(td -> !td.isEmpty())
                        .filter(td -> !td.get(0).getElementsByTag("a").isEmpty())
                        .flatMap(td -> {
                            String title = td.get(0).getElementsByTag("a").text();
                            String year = td.get(0).html().substring(td.get(0).html().indexOf("</a>") + 3, td.get(0).html().length() - 1);

                            Matcher matcher = Pattern.compile("([0-9])").matcher(year);

                            if (matcher.find()) {
                                year = matcher.group(0);
                            }

                            MovieDTO movie = new MovieDTO();
                            movie.setTitle(title);
                            movie.setYear(year);

                            return Stream.of(movie);
                        }).collect(Collectors.toList());

                result.addAll(td1);
            }
        } catch (Exception e) {
            System.err.println("Error ao realizar o parse de HTML para MovieDTO");
        }

        return result;
    }
}
