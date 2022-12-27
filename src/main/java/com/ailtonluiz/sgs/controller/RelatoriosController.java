package com.ailtonluiz.sgs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ailtonluiz.sgs.dto.FiltrosRelatorio;
import com.ailtonluiz.sgs.model.StatusCompra;
import com.ailtonluiz.sgs.model.StatusVenda;
import com.ailtonluiz.sgs.repository.GruposProdutos;
import com.ailtonluiz.sgs.service.RelatorioService;

@Controller
@RequestMapping("/relatorios")
public class RelatoriosController {

	@Autowired
	private RelatorioService relatorioService;

	@Autowired
	private GruposProdutos gruposProdutos;
	
	
	@GetMapping
	public ModelAndView relatorios() {
		ModelAndView mv = new ModelAndView("relatorio/Relatorios");
	

		return mv;
	}
	

	@GetMapping("/vendasEmitidas")
	public ModelAndView relatorioVendasEmitidas() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioVendasEmitidas");
		mv.addObject(new FiltrosRelatorio());
		mv.addObject("todosStatus", StatusVenda.values());
		return mv;
	}

	@GetMapping("/comprasEntradas")
	public ModelAndView relatorioComprasEntrada() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioEntradas");
		mv.addObject(new FiltrosRelatorio());
		mv.addObject("todosStatus", StatusCompra.values());
		return mv;
	}

	@GetMapping("/estoquesGrupoMarca")
	public ModelAndView relatorioEstoquesGrupoMarca() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioEstoquesGrupoMarca");
		mv.addObject("gruposProdutos", gruposProdutos.findAll());
		mv.addObject(new FiltrosRelatorio());

		return mv;
	}

	@GetMapping("/estoquesGeral")
	public ModelAndView relatorioEstoquesGeral() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioEstoques");
		mv.addObject(new FiltrosRelatorio());

		return mv;
	}

	@GetMapping("/estoquesGeralSemEstoque")
	public ModelAndView relatorioEstoquesGeralSemEstoque() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioEstoquesGeralSemEstoque");
		mv.addObject(new FiltrosRelatorio());

		return mv;
	}

	@GetMapping("/estoquesPendentes")
	public ModelAndView relatorioEstoquesPendentes() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioEstoquesPendentes");
		mv.addObject(new FiltrosRelatorio());

		return mv;
	}

	@GetMapping("/estoqueTabaco")
	public ModelAndView relatorioEstoqueTabaco() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioEstoqueTabaco");
		mv.addObject(new FiltrosRelatorio());

		return mv;
	}

	@GetMapping("/relatorioClientes")
	public ModelAndView relatorioClientes() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioClientes");
		mv.addObject(new FiltrosRelatorio());

		return mv;
	}

	@GetMapping("/relatorioFornecedor")
	public ModelAndView relatorioFornecedor() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioFornecedor");
		mv.addObject(new FiltrosRelatorio());

		return mv;
	}

	@GetMapping("/relatorioUsuario")
	public ModelAndView relatorioUsuario() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioUsuario");
		mv.addObject(new FiltrosRelatorio());

		return mv;
	}

	@GetMapping("/relatorioSugestaoCompra")
	public ModelAndView relatorioEstoquesSugestaoCompra() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioSugestaoCompra");
		mv.addObject(new FiltrosRelatorio());
		mv.addObject("gruposProdutos", gruposProdutos.findAll());
		return mv;
	}

	@GetMapping("/relatorioSugestaoCompraGeral")
	public ModelAndView relatorioEstoquesSugestaoCompraGeral() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioSugestaoCompraGeral");
		mv.addObject(new FiltrosRelatorio());
		return mv;
	}

	@GetMapping("/relatorioSaidaProduto")
	public ModelAndView relatorioSaidaProduto() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioSaidaProduto");
		mv.addObject(new FiltrosRelatorio());
		mv.addObject("gruposProdutos", gruposProdutos.findAll());
		return mv;
	}

	@GetMapping("/relatorioSaidaProdutoGeral")
	public ModelAndView relatorioSaidaProdutoGeral() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioSaidaProdutoGeral");
		mv.addObject(new FiltrosRelatorio());
		mv.addObject("gruposProdutos", gruposProdutos.findAll());
		return mv;
	}

	@PostMapping("/vendasEmitidas")
	public ResponseEntity<byte[]> gerarRelatorioVendasEmitidas(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioVendasEmitidas(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

	@PostMapping("/comprasEntradas")
	public ResponseEntity<byte[]> gerarRelatorioEntrada(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioEntrada(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

	@PostMapping("/estoquesGrupoMarca")
	public ResponseEntity<byte[]> gerarRelatorioEstoquesGrupoMarca(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioEstoquesGrupoMarca(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

	@PostMapping("/estoquesGeral")
	public ResponseEntity<byte[]> gerarRelatorioEstoquesGeral(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioEstoquesGeral(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

	@PostMapping("/estoquesGeralSemEstoque")
	public ResponseEntity<byte[]> gerarRelatorioEstoquesGeralSemEstoque(FiltrosRelatorio filtrosRelatorio)
			throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioEstoquesGeralSemEstoque(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

	@PostMapping("/estoquesPendentes")
	public ResponseEntity<byte[]> gerarRelatorioEstoquesPendentes(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioEstoquesPendentes(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

	@PostMapping("/estoqueTabaco")
	public ResponseEntity<byte[]> gerarRelatorioEstoqueTabaco(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioEstoqueTabaco(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

	@PostMapping("/relatorioClientes")
	public ResponseEntity<byte[]> gerarRelatorioCliente(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioCliente(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

	@PostMapping("/relatorioUsuario")
	public ResponseEntity<byte[]> gerarRelatorioUsuario(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioUsuario(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

	@PostMapping("/relatorioFornecedor")
	public ResponseEntity<byte[]> gerarRelatorioFornecedor(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioFornecedor(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

	@PostMapping("/relatorioSugestaoCompra")
	public ResponseEntity<byte[]> gerarRelatorioSugestaoCompra(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioSugestaoCompra(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}
	@PostMapping("/relatorioSugestaoCompraGeral")
	public ResponseEntity<byte[]> gerarRelatorioSugestaoCompraGeral(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioSugestaoCompraGeral(filtrosRelatorio);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);
		
	}

	@PostMapping("/relatorioSaidaProduto")
	public ResponseEntity<byte[]> gerarRelatorioSaidaProduto(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioSaidaProduto(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

	@PostMapping("/relatorioSaidaProdutoGeral")
	public ResponseEntity<byte[]> gerarRelatorioSaidaProdutoGeral(FiltrosRelatorio filtrosRelatorio) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioSaidaProdutoGeral(filtrosRelatorio);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

}
