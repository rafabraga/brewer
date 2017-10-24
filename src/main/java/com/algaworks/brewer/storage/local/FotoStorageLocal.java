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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Profile("local")
@Component
public class FotoStorageLocal implements FotoStorage {

    private static final String THUMBNAIL_PREFIX = "thumbnail.";

    private static final Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocal.class);

    private final Path local;

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

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Pastas criadas para guardar fotos.");
                LOGGER.debug("Pasta default: " + this.local.toAbsolutePath());
            }
        } catch (final IOException e) {
            throw new RuntimeException("Erro criando pastas para salvar as fotos.", e);
        }

    }

    @Override
    public String salvar(final MultipartFile[] files) {
        String novoNome = null;
        if ((files != null) && (files.length > 0)) {
            final MultipartFile arquivo = files[0];
            novoNome = this.renomearArquivo(arquivo.getOriginalFilename());
            try {
                arquivo.transferTo(new File(this.local.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome));
            } catch (final IOException e) {
                throw new RuntimeException("Erro ao salvar foto.", e);
            }
        }

        try {
            Thumbnails.of(this.local.resolve(novoNome).toFile()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
        } catch (final IOException e) {
            throw new RuntimeException("Erro ao gerar thumbnail.", e);
        }
        return novoNome;
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
    public byte[] recuperarThumbnail(final String foto) {
        return this.recuperarFoto(THUMBNAIL_PREFIX + foto);
    }

    @Override
    public void excluir(final String foto) {
        try {
            Files.deleteIfExists(this.local.resolve(foto));
            Files.deleteIfExists(this.local.resolve(THUMBNAIL_PREFIX + foto));
        } catch (final IOException e) {
            LOGGER.warn(String.format("Erro ao excluir foto '%s'. Mensagem: %s", foto, e.getMessage()));
        }
    }

    @Override
    public String getUrl(final String foto) {
        return "http://localhost:8080/brewer/fotos/" + foto;
    }

    private String renomearArquivo(final String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

}
