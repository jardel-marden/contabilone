package br.com.hexacta.desafio.contabilone.server.services;

import br.com.hexacta.desafio.contabilone.server.domains.MovieDTO;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Captura a pagina https://www.imdb.com via HTTP-GET e recortar os titulos.
 *
 * Na requisição é passado alguns parametros, por exemplo ?s=tt&q=<title> que define
 * uma consulta de filmes pelo titulo.
 *
 * Client Http: {@link com.squareup.okhttp.OkHttpClient}
 * Content-Type: text/html
 * Method-Type: Get
 *
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
public interface CropperMovieService extends MovieService<List<MovieDTO>> {

    String findByTitleAsString(String title) throws IOException;

    Elements getElements(String selector) throws IllegalArgumentException;

    void parseHTML(String HTMLString);
}
