package com.algaworks.brewer.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.ItemVenda;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.storage.FotoStorage;

@Component
public class Mailer {

    private static Logger logger = LoggerFactory.getLogger(Mailer.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine thymeleaf;

    @Autowired
    private FotoStorage fotoStorage;

    @Async
    public void enviar(final Venda venda) {

        final Context context = new Context(new Locale("pt", "BR"));
        context.setVariable("venda", venda);
        context.setVariable("logo", "logo");

        final Map<String, String> fotos = new HashMap<>();
        boolean adicionarMockCerveja = false;
        for (final ItemVenda item : venda.getItens()) {
            final Cerveja cerveja = item.getCerveja();
            if (cerveja.temFoto()) {
                final String cid = "foto-" + cerveja.getCodigo();
                context.setVariable(cid, cid);

                fotos.put(cid, cerveja.getFoto() + "|" + cerveja.getContentType());
            } else {
                adicionarMockCerveja = true;
                context.setVariable("mockCerveja", "mockCerveja");
            }
        }

        try {
            final String email = this.thymeleaf.process("mail/ResumoVenda", context);

            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("rafabraga90@gmail.com");
            helper.setTo(venda.getCliente().getEmail());
            helper.setSubject(String.format("Brewer - Venda nº %d", venda.getCodigo()));
            helper.setText(email, true);

            helper.addInline("logo", new ClassPathResource("static/images/logo-gray.png"));

            if (adicionarMockCerveja) {
                helper.addInline("mockCerveja", new ClassPathResource("static/images/cerveja-mock.png"));
            }

            for (final String cid : fotos.keySet()) {
                final String[] fotoContentType = fotos.get(cid).split("\\|");
                final String foto = fotoContentType[0];
                final String contentType = fotoContentType[1];
                final byte[] arrayFoto = this.fotoStorage.recuperarThumbnail(foto);
                helper.addInline(cid, new ByteArrayResource(arrayFoto), contentType);
            }

            this.mailSender.send(mimeMessage);
        } catch (final MessagingException e) {
            logger.error("Erro enviando e-mail", e);
        }
    }

}