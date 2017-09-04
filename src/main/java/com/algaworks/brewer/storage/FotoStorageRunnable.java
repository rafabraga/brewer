package com.algaworks.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable {

    private final MultipartFile[] files;
    private final DeferredResult<FotoDTO> resultado;
    private final FotoStorage fotoStorage;

    public FotoStorageRunnable(final MultipartFile[] files, final DeferredResult<FotoDTO> resultado, final FotoStorage fotoStorage) {
        this.files = files;
        this.resultado = resultado;
        this.fotoStorage = fotoStorage;
    }

    @Override
    public void run() {
        final String nomeFoto = this.fotoStorage.salvarTemporariamente(this.files);
        final String contentType = this.files[0].getContentType();
        this.resultado.setResult(new FotoDTO(nomeFoto, contentType));
    }

}
