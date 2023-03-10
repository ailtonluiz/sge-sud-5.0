package com.ailtonluiz.sgs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ailtonluiz.sgs.repository.Compras;
import com.ailtonluiz.sgs.repository.Produtos;
import com.ailtonluiz.sgs.repository.Vendas;

@Controller
public class DashboardController {

	@Autowired
	private Vendas vendas;

	@Autowired
	private Compras compras;
	
	@Autowired
	private Produtos produtos;

	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");

		mv.addObject("vendasNoAno", vendas.valorTotalNoAno());
		mv.addObject("vendasNoMes", vendas.valorTotalNoMes());
		mv.addObject("ticketMedio", vendas.valorTicketMedioNoAno());
		mv.addObject("vendasDia", vendas.valorDia());
		mv.addObject("quantidadePedidosPendentes", vendas.quantidadePedidosPendentes());
		
		mv.addObject("quantidadePedidosCompraPendentes", compras.quantidadePedidosCompraPendentes());


		
		mv.addObject("valorItensEstoque", produtos.valorItensEstoque());
		mv.addObject("totalProdutos", produtos.count());
		return mv;
	}

}