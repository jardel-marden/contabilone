package br.com.hexacta.desafio.contabilone.server.services;

import java.io.IOException;

/**
 * @author Jardel Marden on 11/10/2020
 * @project ContabilOne
 */
public interface MovieService<T> {
    T findByTitle(String title) throws IOException;
}
