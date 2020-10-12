package br.com.hexacta.desafio.contabilone.server.services.impl;

import br.com.hexacta.desafio.contabilone.server.domains.MovieDTO;
import br.com.hexacta.desafio.contabilone.server.services.CropperMovieService;
import br.com.hexacta.desafio.contabilone.server.utils.HtmlUtil;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static br.com.hexacta.desafio.contabilone.server.services.impl.ImdbCropperMovieServiceImpl.SELECTOR_DEFAULT;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
class ImdbCropperMovieServiceTest {

    final CropperMovieService service = new ImdbCropperMovieServiceImpl();

    @Test
    void findByTitle() throws IOException {

        String HTMLString = this.service.findByTitleAsString("bela");
        this.service.parseHTML(HTMLString);

        Elements table = this.service.getElements(SELECTOR_DEFAULT);
        List<MovieDTO> result = HtmlUtil.parse(table);

        Assertions.assertNotNull(table);
        Assertions.assertNotNull(result);
}
}