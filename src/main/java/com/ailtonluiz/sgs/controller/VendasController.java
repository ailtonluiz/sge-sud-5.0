package com.ailtonluiz.sgs.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ailtonluiz.sgs.controller.page.PageWrapper;
import com.ailtonluiz.sgs.controller.validator.VendaValidator;
import com.ailtonluiz.sgs.dto.VendaMes;
import com.ailtonluiz.sgs.mail.Mailer;
import com.ailtonluiz.sgs.model.ItemVenda;
import com.ailtonluiz.sgs.model.Produto;
import com.ailtonluiz.sgs.model.StatusVenda;
import com.ailtonluiz.sgs.model.Venda;
import com.ailtonluiz.sgs.repository.Produtos;
import com.ailtonluiz.sgs.repository.Vendas;
import com.ailtonluiz.sgs.repository.filter.VendaFilter;
import com.ailtonluiz.sgs.security.UsuarioSistema;
import com.ailtonluiz.sgs.service.CadastroVendaService;
import com.ailtonluiz.sgs.session.TabelasItensSession;

@Controller
@RequestMapping("/vendas")
public class VendasController {

	@Autowired
	private Produtos produtos;

	@Autowired
	private TabelasItensSession tabelaItens;

	@Autowired
	private CadastroVendaService cadastroVendaService;

	@Autowired
	private VendaValidator vendaValidator;

	@Autowired
	private Vendas vendas;

	@Autowired
	private Mailer mailer;

	@InitBinder("venda")
	public void inicializarValidador(WebDataBinder binder) {
		binder.setValidator(vendaValidator);
	}

	@GetMapping("/novo")
	public ModelAndView novo(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/CadastroVenda");

		if (StringUtils.isEmpty(venda.getUuid())) {
			venda.setUuid(UUID.randomUUID().toString());
		}

		mv.addObject("itens", venda.getItens());
		mv.addObject("valorTotalItens", tabelaItens.getValorTotal(venda.getUuid()));

		return mv;
	}

	@PostMapping(value = "/novo", params = "salvar")
	public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validarVenda(venda, result);
		if (result.hasErrors()) {
			return novo(venda);
		}

		venda.setUsuario(usuarioSistema.getUsuario());
		

		cadastroVendaService.salvar(venda);
		attributes.addFlashAttribute("mensagem", String.format("Presupuesto n. %d exitosa ", venda.getCodigo()));
		return new ModelAndView("redirect:/vendas/novo");

	}

	@PostMapping(value = "/novo", params = "emitir")
	public ModelAndView emitir(Venda venda, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validarVenda(venda, result);
		if (result.hasErrors()) {
			return novo(venda);
		}

		venda.setUsuario(usuarioSistema.getUsuario());

		cadastroVendaService.emitir(venda);
		attributes.addFlashAttribute("mensagem", String.format("Salida n. %d emitida con éxito ", venda.getCodigo()));
		return new ModelAndView("redirect:/vendas/novo");

	}

	@PostMapping(value = "/novo", params = "baixaMercadoria")
	public ModelAndView baixaMercadoria(Venda venda, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validarVenda(venda, result);
		if (result.hasErrors()) {
			return novo(venda);
		}

		venda.setUsuario(usuarioSistema.getUsuario());

		cadastroVendaService.baixaMercadoria(venda);

		attributes.addFlashAttribute("mensagem",
				String.format("Baja de mercancías rota n. %d exitosa ", venda.getCodigo()));
		return new ModelAndView("redirect:/vendas/novo");

	}

	@PostMapping(value = "/novo", params = "enviarEmail")
	public ModelAndView enviarEmail(Venda venda, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) throws UnsupportedEncodingException {
		validarVenda(venda, result);
		if (result.hasErrors()) {
			return novo(venda);
		}

		venda.setUsuario(usuarioSistema.getUsuario());

		venda = cadastroVendaService.salvar(venda);
		mailer.enviar(venda);

		attributes.addFlashAttribute("mensagem", String.format("Operación n. %d exitosa! Lista enviada a la impresora ", venda.getCodigo()));
		return new ModelAndView("redirect:/vendas/novo");
	}

	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoProduto, String uuid) {
		Produto produto = produtos.findOne(codigoProduto);
		tabelaItens.adicionarItem(uuid, produto, 1);
		return mvTabelaItensVenda(uuid);
	}

	@PutMapping("/item/{codigoProduto}")
	public ModelAndView alterarQuantidadeItem(@PathVariable("codigoProduto") Produto produto, Integer quantidade,
			String uuid) {
		tabelaItens.alterarQuantidadeItens(uuid, produto, quantidade);
		return mvTabelaItensVenda(uuid);
	}

	@DeleteMapping("/item/{uuid}/{codigoProduto}")
	public ModelAndView excluirItem(@PathVariable("codigoProduto") Produto produto, @PathVariable String uuid) {
		tabelaItens.excluirItem(uuid, produto);
		return mvTabelaItensVenda(uuid);
	}

	@GetMapping("/totalPorMes")
	public @ResponseBody List<VendaMes> listarTotalVendaPorMes() {
		return vendas.totalPorMes();
	}
	

	@GetMapping
	public ModelAndView pesquisar(VendaFilter vendaFilter, @PageableDefault(size = 20) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/venda/PesquisaVendas");
		mv.addObject("todosStatus", StatusVenda.values());

		PageWrapper<Venda> paginaWrapper = new PageWrapper<>(vendas.filtrar(vendaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Venda venda = vendas.buscarComItens(codigo);

		setUuid(venda);
		for (ItemVenda item : venda.getItens()) {
			tabelaItens.adicionarItem(venda.getUuid(), item.getProduto(), item.getQuantidade());
		}

		ModelAndView mv = novo(venda);
		mv.addObject(venda);
		return mv;
	}

	@PostMapping(value = "/novo", params = "cancelar")
	public ModelAndView cancelar(Venda venda, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		try {
			cadastroVendaService.cancelar(venda);
		} catch (AccessDeniedException e) {
			return new ModelAndView("/403");
		}

		attributes.addFlashAttribute("mensagem", String.format("Salida n. %d cancelada con éxito", venda.getCodigo()));

		return new ModelAndView("redirect:/vendas/" + venda.getCodigo());
	}

	private ModelAndView mvTabelaItensVenda(String uuid) {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));
		return mv;
	}

	private void validarVenda(Venda venda, BindingResult result) {
		venda.adicionarItens(tabelaItens.getItens(venda.getUuid()));
		venda.calcularValorTotal();

		vendaValidator.validate(venda, result);
	}

	private void setUuid(Venda venda) {
		if (StringUtils.isEmpty(venda.getUuid())) {
			venda.setUuid(UUID.randomUUID().toString());
		}
	}

}
