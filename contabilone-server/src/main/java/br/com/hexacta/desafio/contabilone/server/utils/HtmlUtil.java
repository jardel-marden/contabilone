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
 * Utilitario usado para fazer os recortes da pagina consultada, após isso será filtrado
 * os titulos da pagina e realizado o parse do mesmo para uma {@link List<MovieDTO>}
 *
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
public abstract class HtmlUtil {

    /**
     * Realiza o parse da list de {@link Element} que serão filtrados conforme a condicional,
     * td.result_text, não podem ser isEmpty e dentro deste elemento deve existir uma tag <a>
     * após isso o valor dentro da tag <a> será recuperado e que irá definir o <b>title</b>,
     * caso exista dentro da tag <td> um valores que represente uma data o mesmo será recuperado
     * também.
     *
     * @param table
     * @return
     */
    public static List<MovieDTO> toMovie(Elements table) {
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
                            String year = td.get(0).select(":not(a)").text();

                            Matcher matcher = Pattern.compile("([0-9])").matcher(year);

                            if (matcher.find()) {
                                year = year.replaceAll("[^0-9]", "");
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
