package br.com.hexacta.desafio.contabilone.server.services.impl;

import br.com.hexacta.desafio.contabilone.server.domains.MovieDTO;
import br.com.hexacta.desafio.contabilone.server.services.ApiImdbMovieService;
import br.com.hexacta.desafio.contabilone.server.utils.ResponseSocketUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
class OmdbApiImdbMovieServiceImplTest {

    final ApiImdbMovieService service = new OmdbApiMovieServiceImpl();

    @Test
    void findByTitle() throws IOException {
        List<MovieDTO> movies = this.service.findByTitle("bela");
        String payload = ResponseSocketUtil.payload(movies);

        assertNotNull(movies);
        assertNotNull(payload);
    }
}