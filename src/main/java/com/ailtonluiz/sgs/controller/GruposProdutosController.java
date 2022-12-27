package com.ailtonluiz.sgs.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ailtonluiz.sgs.controller.page.PageWrapper;
import com.ailtonluiz.sgs.model.GrupoProdutos;
import com.ailtonluiz.sgs.repository.GruposProdutos;
import com.ailtonluiz.sgs.repository.filter.GrupoProdutosFilter;
import com.ailtonluiz.sgs.service.CadastroGrupoProdutosService;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.NomeGrupoProdutosJaCadastradoException;

@Controller
@RequestMapping("/gruposProdutos")
public class GruposProdutosController {

	@Autowired
	private CadastroGrupoProdutosService cadastroGrupoProdutosService;

	@Autowired
	private GruposProdutos gruposProdutos;

	@RequestMapping("/novo")
	public ModelAndView novo(GrupoProdutos grupoProdutos) {
		ModelAndView mv = new ModelAndView("grupo/CadastroGrupoProdutos");

		return mv;
	}

	@RequestMapping(value = { "/novo", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid GrupoProdutos grupoProdutos, BindingResult result, Model model,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(grupoProdutos);
		}

		try {
			cadastroGrupoProdutosService.salvar(grupoProdutos);
		} catch (NomeGrupoProdutosJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(grupoProdutos);
		}
		attributes.addFlashAttribute("mensagem", "Grupo de productos guardados con éxito!");
		return new ModelAndView("redirect:/gruposProdutos/novo");

	}

	// Método salvar do cadastro rápido
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid GrupoProdutos grupoProdutos,
			BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());

		}

		grupoProdutos = cadastroGrupoProdutosService.salvar(grupoProdutos);

		return ResponseEntity.ok(grupoProdutos);
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") GrupoProdutos grupoProdutos) {
		try {
			cadastroGrupoProdutosService.excluir(grupoProdutos);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ModelAndView pesquisar(GrupoProdutosFilter grupoProdutosFilter, BindingResult result,
			@PageableDefault(size = 7) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("grupo/PesquisaGrupos");

		PageWrapper<GrupoProdutos> paginaWrapper = new PageWrapper<>(
				gruposProdutos.filtrar(grupoProdutosFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		GrupoProdutos grupoProdutos = gruposProdutos.findOne(codigo);
		ModelAndView mv = novo(grupoProdutos);
		mv.addObject(grupoProdutos);
		return mv;
	}
}
