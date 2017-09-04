package com.algaworks.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.dto.FotoDTO;
import com.algaworks.brewer.storage.FotoStorage;
import com.algaworks.brewer.storage.FotoStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {

    @Autowired
    private FotoStorage fotoStorage;

    @PostMapping
    public DeferredResult<FotoDTO> upload(@RequestParam("files[]") final MultipartFile[] files) {
        final DeferredResult<FotoDTO> resultado = new DeferredResult<>();

        final Thread thread = new Thread(new FotoStorageRunnable(files, resultado, this.fotoStorage));
        thread.start();

        return resultado;
    }

    @GetMapping("/temp/{nome:.*}")
    public byte[] recuperarFotoTemporaria(@PathVariable final String nome) {
        return this.fotoStorage.recuperarFotoTemporaria(nome);
    }

    @GetMapping("/{nome:.*}")
    public byte[] recuperarFoto(@PathVariable final String nome) {
        return this.fotoStorage.recuperarFoto(nome);
    }

}
