package com.ailtonluiz.sgs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ailtonluiz.sgs.controller.page.PageWrapper;
import com.ailtonluiz.sgs.dto.ProdutoDTO;
import com.ailtonluiz.sgs.model.Produto;
import com.ailtonluiz.sgs.model.Unidade;
import com.ailtonluiz.sgs.repository.GruposProdutos;
import com.ailtonluiz.sgs.repository.Marcas;
import com.ailtonluiz.sgs.repository.Produtos;
import com.ailtonluiz.sgs.repository.filter.ProdutoFilter;
import com.ailtonluiz.sgs.service.CadastroProdutoService;
import com.ailtonluiz.sgs.service.exception.CodigoBarrasJaCadastradoException;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	@Autowired
	private GruposProdutos gruposProdutos;

	@Autowired
	private Marcas marcas;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private Produtos produtos;

	@RequestMapping("/novo")
	public ModelAndView novo(Produto produto) {
		ModelAndView mv = new ModelAndView("produto/CadastroProduto");
		mv.addObject("gruposProdutos", gruposProdutos.findAll());
		mv.addObject("marcas", marcas.findAll());
		mv.addObject("unidades", Unidade.values());

		return mv;
	}

	@RequestMapping(value = { "/novo", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Produto produto, BindingResult result, Model model,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {

			return novo(produto);

		}
		
		try {
			cadastroProdutoService.salvar(produto);
		} catch (CodigoBarrasJaCadastradoException e) {
			result.rejectValue("codigoBarras", e.getMessage(), e.getMessage());
			return novo(produto);

		}

		attributes.addFlashAttribute("mensagem", "Artículo guardado correctamente!");
		return new ModelAndView("redirect:/produtos/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(ProdutoFilter produtoFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("produto/PesquisaProdutos");
		mv.addObject("gruposProdutos", gruposProdutos.findAll());
		mv.addObject("marcas", marcas.findAll());

		PageWrapper<Produto> paginaWrapper = new PageWrapper<>(produtos.filtrar(produtoFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);

		return mv;
	}
	


	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ProdutoDTO> pesquisar(String codigoBarrasOuNomeOuReferencia) {
		return produtos.porCodigoBarrasOuNomeOuReferencia(codigoBarrasOuNomeOuReferencia);
	}
	

	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Produto produto) {
		try {
			cadastroProdutoService.excluir(produto);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Produto produto) {
		ModelAndView mv = novo(produto);
		mv.addObject(produto);
		return mv;
	}

}
