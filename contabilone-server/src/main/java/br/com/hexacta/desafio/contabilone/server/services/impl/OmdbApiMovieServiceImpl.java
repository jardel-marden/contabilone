package br.com.hexacta.desafio.contabilone.server.services.impl;

import br.com.hexacta.desafio.contabilone.server.domains.MovieDTO;
import br.com.hexacta.desafio.contabilone.server.domains.SearchMovieDTO;
import br.com.hexacta.desafio.contabilone.server.services.ApiImdbMovieService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
public class OmdbApiMovieServiceImpl implements ApiImdbMovieService {

    private final static String API = "http://www.omdbapi.com/?type=movie&r=json&apikey=2bce8b09";
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Consulta via cliente Http IMDBs pelo titulo.
     * Como não consegui acesso a API do site imdb.com optei por usar
     * uma outra base conforme link http://www.omdbapi.com.
     *
     * @param title {@link String}
     * @return List<MovieDTO>
     * @throws IOException
     */
    @Override
    public List<MovieDTO> findByTitle(String title) throws IOException {

        Request request = new Request.Builder()
                .url(API.concat("&s=").concat(title))
                .get()
                .build();

        Response response = client.newCall(request).execute();

        return toMovies(response.body().string());
    }

    /**
     * Realiza o parse(decode) do response body em json para {@link List<MovieDTO>}
     *
     * @param json {@link String}
     * @return List<MovieDTO>
     * @throws IOException
     */
    private List<MovieDTO> toMovies(String json) throws IOException {

        if (json.isEmpty()) {
            return new ArrayList<>();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        SearchMovieDTO search = objectMapper.readValue(json, new TypeReference<SearchMovieDTO>() {
        });

        return search.getMovies();
    }
}
