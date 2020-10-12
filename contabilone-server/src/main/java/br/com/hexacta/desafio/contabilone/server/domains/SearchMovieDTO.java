package br.com.hexacta.desafio.contabilone.server.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchMovieDTO {

    @JsonProperty("Search")
    private List<MovieDTO> movies;

    @JsonProperty("totalResults")
    private String totalResults;

    @JsonProperty("Response")
    private String response;
}
