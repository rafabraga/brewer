package com.algaworks.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

    public final String THUMBNAIL_PREFIX = "thumbnail.";

    String salvar(MultipartFile[] files);

    byte[] recuperarFoto(String nome);

    byte[] recuperarThumbnail(String foto);

    void excluir(String foto);

    String getUrl(String foto);

}
