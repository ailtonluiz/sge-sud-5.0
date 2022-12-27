package com.ailtonluiz.sgs.controller;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ailtonluiz.sgs.controller.page.PageWrapper;
import com.ailtonluiz.sgs.controller.validator.CompraValidator;
import com.ailtonluiz.sgs.mail.Mailer;
import com.ailtonluiz.sgs.model.Compra;
import com.ailtonluiz.sgs.model.ItemCompra;
import com.ailtonluiz.sgs.model.Produto;
import com.ailtonluiz.sgs.model.StatusCompra;
import com.ailtonluiz.sgs.repository.Compras;
import com.ailtonluiz.sgs.repository.Produtos;
import com.ailtonluiz.sgs.repository.filter.CompraFilter;
import com.ailtonluiz.sgs.security.UsuarioSistema;
import com.ailtonluiz.sgs.service.CadastroCompraService;
import com.ailtonluiz.sgs.session.TabelasItensSession;

@Controller
@RequestMapping("/compras")
public class ComprasController {

	@Autowired
	private Produtos produtos;

	@Autowired
	private TabelasItensSession tabelaItensCompra;

	@Autowired
	private CadastroCompraService cadastroCompraService;

	@Autowired
	private CompraValidator compraValidator;

	@Autowired
	private Compras compras;

	@Autowired
	private Mailer mailer;

	@InitBinder("compra")
	public void inicializarValidador(WebDataBinder binder) {
		binder.setValidator(compraValidator);
	}

	@GetMapping("/novo")
	public ModelAndView novo(Compra compra) {
		ModelAndView mv = new ModelAndView("compra/CadastroCompra");

		if (StringUtils.isEmpty(compra.getUuid())) {
			compra.setUuid(UUID.randomUUID().toString());
		}

		mv.addObject("itensCompra", compra.getItens());

		mv.addObject("valorIgi", compra.getValorIgi());
		mv.addObject("valorDesconto", compra.getValorDesconto());

		mv.addObject("valorTotalItens", tabelaItensCompra.getValorTotalCompra(compra.getUuid()));

		return mv;
	}

	@PostMapping(value = "/novo", params = "salvar")
	public ModelAndView salvar(Compra compra, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {

		validarCompra(compra, result);

		if (result.hasErrors()) {
			return novo(compra);
		}
		compra.setUsuario(usuarioSistema.getUsuario());

		cadastroCompraService.salvar(compra);
		attributes.addFlashAttribute("mensagem", String.format("Entrada n. %d guardada con éxito", compra.getCodigo()));
		return new ModelAndView("redirect:/compras/novo");

	}

	@PostMapping(value = "/novo", params = "emitir")
	public ModelAndView emitir(Compra compra, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {

		validarCompra(compra, result);

		if (result.hasErrors()) {
			return novo(compra);
		}
		compra.setUsuario(usuarioSistema.getUsuario());

		cadastroCompraService.emitir(compra);
		attributes.addFlashAttribute("mensagem",
				String.format("Entrada n. %d guardada con éxito con el estado entregado", compra.getCodigo()));
		return new ModelAndView("redirect:/compras/novo");

	}

	

	@PostMapping(value = "/novo", params = "enviarEmail")
	public ModelAndView enviarEmail(Compra compra, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) throws UnsupportedEncodingException {

		validarCompra(compra, result);

		if (result.hasErrors()) {
			return novo(compra);
		}
		compra.setUsuario(usuarioSistema.getUsuario());
		cadastroCompraService.salvar(compra);
		mailer.enviarEmail(compra);

		attributes.addFlashAttribute("mensagem",
				String.format("Entrada n. %d guardada con éxito con el estado presupuesto", compra.getCodigo()));
		return new ModelAndView("redirect:/compras/novo");

	}

	@PostMapping(value = "/novo", params = "pendente")
	public ModelAndView pendente(Compra compra, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {

		validarCompra(compra, result);

		if (result.hasErrors()) {
			return novo(compra);
		}
		compra.setUsuario(usuarioSistema.getUsuario());

		cadastroCompraService.pendente(compra);
		attributes.addFlashAttribute("mensagem",
				String.format("Entrada n. %d guardada con éxito con el estado pendiente", compra.getCodigo()));
		return new ModelAndView("redirect:/compras/novo");

	}

	@PostMapping("/itemCompra")
	public ModelAndView adicionarItem(Long codigoProduto, String uuid) {
		Produto produto = produtos.findOne(codigoProduto);
		tabelaItensCompra.adicionarItemCompra(uuid, produto, 1);
		return mvTabelaItensCompra(uuid);
	}

	@PutMapping("/itemCompra/{codigoProduto}")
	public ModelAndView alterarQuantidadeItem(@PathVariable("codigoProduto") Produto produto, Integer quantidade,
			String uuid) {
		tabelaItensCompra.alterarQuantidadeItensCompra(uuid, produto, quantidade);
		return mvTabelaItensCompra(uuid);
	}

	@DeleteMapping("/itemCompra/{uuid}/{codigoProduto}")
	public ModelAndView excluirItem(@PathVariable("codigoProduto") Produto produto, @PathVariable String uuid) {
		tabelaItensCompra.excluirItemCompra(uuid, produto);
		return mvTabelaItensCompra(uuid);
	}

	@GetMapping
	public ModelAndView pesquisar(CompraFilter compraFilter, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/compra/PesquisaCompras");
		mv.addObject("todosStatus", StatusCompra.values());

		PageWrapper<Compra> paginaWrapper = new PageWrapper<>(compras.filtrar(compraFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Compra compra = compras.buscarComItens(codigo);

		setUuid(compra);
		for (ItemCompra item : compra.getItens()) {
			tabelaItensCompra.adicionarItem(compra.getUuid(), item.getProduto(), item.getQuantidade());
		}

		ModelAndView mv = novo(compra);
		mv.addObject(compra);
		return mv;
	}

	@PostMapping(value = "/novo", params = "cancelar")
	public ModelAndView cancelar(Compra compra, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		try {
			cadastroCompraService.cancelar(compra);
		} catch (AccessDeniedException e) {
			return new ModelAndView("/403");
		}

		attributes.addFlashAttribute("mensagem",
				String.format("Entrada n. %d cancelada con éxito", compra.getCodigo()));
		return new ModelAndView("redirect:/compras/" + compra.getCodigo());
	}

	private ModelAndView mvTabelaItensCompra(String uuid) {
		ModelAndView mv = new ModelAndView("compra/TabelaItensCompra");
		mv.addObject("itensCompra", tabelaItensCompra.getItensCompra(uuid));
		mv.addObject("valorTotalCompra", tabelaItensCompra.getValorTotalCompra(uuid));
		return mv;
	}

	private void validarCompra(Compra compra, BindingResult result) {
		compra.adicionarItens(tabelaItensCompra.getItensCompra(compra.getUuid()));
		compra.calcularValorTotalCompra();
		compra.getDataAlbara();
		compra.getNrAlbara();

		compraValidator.validate(compra, result);
	}

	private void setUuid(Compra compra) {
		if (StringUtils.isEmpty(compra.getUuid())) {
			compra.setUuid(UUID.randomUUID().toString());
		}
	}
}
