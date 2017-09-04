package com.algaworks.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocal.class);

    private final Path local;
    private Path localTemporario;

    public FotoStorageLocal() {
        this(Paths.get(System.getenv("HOME"), ".brewerfotos"));
    }

    public FotoStorageLocal(final Path path) {
        this.local = path;
        this.criarPastas();
    }

    private void criarPastas() {
        try {
            Files.createDirectories(this.local);
            this.localTemporario = Paths.get(this.local.toString(), "temp");
            Files.createDirectories(this.localTemporario);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Pastas criadas para guardar fotos.");
                LOGGER.debug("Pasta default: " + this.local.toAbsolutePath());
                LOGGER.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
            }
        } catch (final IOException e) {
            throw new RuntimeException("Erro criando pastas para salvar as fotos.", e);
        }

    }

    @Override
    public String salvarTemporariamente(final MultipartFile[] files) {
        String novoNome = null;
        if ((files != null) && (files.length > 0)) {
            final MultipartFile arquivo = files[0];
            novoNome = this.renomearArquivo(arquivo.getOriginalFilename());
            try {
                arquivo.transferTo(
                        new File(this.localTemporario.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome));
            } catch (final IOException e) {
                throw new RuntimeException("Erro ao salvar foto.", e);
            }
        }
        return novoNome;
    }

    private String renomearArquivo(final String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    @Override
    public byte[] recuperarFotoTemporaria(final String nome) {
        try {
            return Files.readAllBytes(this.localTemporario.resolve(nome));
        } catch (final IOException e) {
            throw new RuntimeException("Erro ao obter foto temporária.");
        }
    }

    @Override
    public byte[] recuperarFoto(final String nome) {
        try {
            return Files.readAllBytes(this.local.resolve(nome));
        } catch (final IOException e) {
            throw new RuntimeException("Erro ao obter foto.");
        }
    }

    @Override
    public void salvar(final String foto) {
        try {
            Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
        } catch (final IOException e) {
            throw new RuntimeException("Erro ao mover a foto para o destino final.", e);
        }

        try {
            Thumbnails.of(this.local.resolve(foto).toFile()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
        } catch (final IOException e) {
            throw new RuntimeException("Erro ao gerar thumbnail.", e);
        }
    }

}
