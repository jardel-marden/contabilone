package br.com.hexacta.desafio.contabilone.server.services;

import br.com.hexacta.desafio.contabilone.server.domains.MovieDTO;

import java.util.List;

/**
 * Service usado para consumir uma API Rest de filmes e outros, apenas
 * realizo uma consulta HTTP-GET com os parametros ?type=movie&r=json&apikey=2bce8b09&s=<title>
 * para o host http://www.omdbapi.com.
 *
 * Client Http: {@link com.squareup.okhttp.OkHttpClient}
 * Content-Type: application/json
 * Method-Type: Get
 *
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
public interface ApiImdbMovieService extends MovieService<List<MovieDTO>> {
}
