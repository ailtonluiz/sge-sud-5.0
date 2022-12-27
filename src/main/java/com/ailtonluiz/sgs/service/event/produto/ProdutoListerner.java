package com.ailtonluiz.sgs.service.event.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ailtonluiz.sgs.storage.FotoStorage;

@Component
public class ProdutoListerner {

	@Autowired
	private FotoStorage fotoStorage;

	@EventListener(condition = "#evento.temFoto() and #evento.novaFoto")
	public void produtoSalva(ProdutoSalvaEvent evento) {
		fotoStorage.salvar(evento.getProduto().getFoto());
	}
}
