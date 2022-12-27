package com.ailtonluiz.sgs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ailtonluiz.sgs.service.CadastroProdutoService;
import com.ailtonluiz.sgs.storage.FotoStorage;
import com.ailtonluiz.sgs.storage.local.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses = CadastroProdutoService.class)
public class ServiceConfig {

	@Bean
	public FotoStorage fotoStorage() {
		return new FotoStorageLocal();
	}
}
