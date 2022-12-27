package com.ailtonluiz.sgs.mail;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
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

import com.ailtonluiz.sgs.model.Compra;
import com.ailtonluiz.sgs.model.ItemCompra;
import com.ailtonluiz.sgs.model.Produto;
import com.ailtonluiz.sgs.model.Venda;
import com.ailtonluiz.sgs.storage.FotoStorage;

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
	public void enviar(Venda venda) throws UnsupportedEncodingException {
		Context context = new Context(new Locale("pt", "BR"));

		context.setVariable("venda", venda);
		context.setVariable("logo", "logo");



		try {
			String email = thymeleaf.process("mail/ResumoVenda", context);
			
		

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom(new InternetAddress("ailtonluiz@outlook.com", "SGS - Supermercat DEL SUD"));
			helper.setBcc(venda.getUsuario().getParametro().getEmail());
			helper.setReplyTo("contato@ailtonluiz.com");
//			helper.setPriority(0);
			helper.setTo(venda.getCliente().getEmail());
			helper.setSubject(String.format("Supermercat DEL SUD - Comanda nº %d", venda.getCodigo()));
			helper.setText(email, true);

//			helper.addInline("logo", new ClassPathResource("static/images/logo_sud-gray.png"));


			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error("Erro enviando e-mail", e);
		}
	}

	@Async
	public void enviarEmail(Compra compra) throws UnsupportedEncodingException {
		Context context = new Context(new Locale("pt", "BR"));

		context.setVariable("venda", compra);
		context.setVariable("logo", "logo");

		Map<String, String> fotos = new HashMap<>();
		boolean adicionarMockProduto = false;
		for (ItemCompra item : compra.getItens()) {
			Produto produto = item.getProduto();
			if (produto.temFoto()) {
				String cid = "foto-" + produto.getCodigo();
				context.setVariable(cid, cid);

				fotos.put(cid, produto.getFoto() + "|" + produto.getContentType());
			} else {
				adicionarMockProduto = true;
				context.setVariable("mockProduto", "mockProduto");
			}
		}

		try {
			String email = thymeleaf.process("mail/ResumoCompra", context);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom(new InternetAddress("stocksud@sgs.com", "SGS - SUD"));
			helper.setBcc("contato@ailtonluiz.com");
			helper.setTo(compra.getFornecedor().getEmail());
			helper.setReplyTo("sgs@ailtonluiz.com");
			helper.setSubject(String.format("SGS - Comanda nº %d", compra.getCodigo()));
			helper.setText(email, true);

			helper.addInline("logo", new ClassPathResource("static/images/logo-gray.png"));

			if (adicionarMockProduto) {
				helper.addInline("mockProduto", new ClassPathResource("static/images/login.png"));
			}

			for (String cid : fotos.keySet()) {
				String[] fotoContentType = fotos.get(cid).split("\\|");
				String foto = fotoContentType[0];
				String contentType = fotoContentType[1];
				byte[] arrayFoto = fotoStorage.recuperarThumbnail(foto);
				helper.addInline(cid, new ByteArrayResource(arrayFoto), contentType);
			}
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error("Erro enviando e-mail", e);
		}
	}

}