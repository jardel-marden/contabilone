package br.com.hexacta.desafio.contabilone.server.services.impl;

import br.com.hexacta.desafio.contabilone.server.domains.MovieDTO;
import br.com.hexacta.desafio.contabilone.server.services.CropperMovieService;
import br.com.hexacta.desafio.contabilone.server.utils.HtmlUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
public class ImdbCropperMovieServiceImpl implements CropperMovieService {

    private final static String API = "https://www.imdb.com/find?s=tt&ref_=fn_tt_pop";
    private final OkHttpClient client = new OkHttpClient();

    public final static String SELECTOR_DEFAULT = "findList";

    private Document document;

    @Override
    public List<MovieDTO> findByTitle(String title) throws IOException {

        Response response = client.newCall(request(title)).execute();
        parseHTML(response.body().string());

        Elements elements = getElements(SELECTOR_DEFAULT);

        return HtmlUtil.parse(elements);
    }

    /**
     * Usado para o teste unitário, corresponde ao mesmo processo em @{@link #findByTitle}
     *
     * @param title
     * @return
     * @throws IOException
     */
    @Override
    public String findByTitleAsString(String title) throws IOException {

        Response response = client.newCall(request(title)).execute();
        return response.body().string();
    }

    /**
     * Converter o HTML em {@link org.jsoup.nodes.Document}
     *
     * @param HTMLString
     */
    @Override
    public void parseHTML(String HTMLString) {
        this.document = Jsoup.parse(HTMLString);
    }

    /**
     * Antes de executar este metodo necessario escrever o valor do campo document @{@link Document},
     * apos isso ele filtra o HTML pela class findList que compoem a lista de titulos no site
     * https://www.imdb.com
     *
     * @param selector
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Elements getElements(String selector) throws IllegalArgumentException {

        if (Objects.isNull(this.document)) throw new IllegalArgumentException("Document não instanciado");

        return this.document.getElementsByClass(selector);
    }

    private Request request(String title) {
        return new Request.Builder()
                .url(API.concat("&q=").concat(title))
                .get()
                .build();
    }
}
