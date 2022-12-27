package com.ailtonluiz.sgs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ailtonluiz.sgs.controller.page.PageWrapper;
import com.ailtonluiz.sgs.model.SubgrupoProdutos;
import com.ailtonluiz.sgs.repository.GruposProdutos;
import com.ailtonluiz.sgs.repository.SubgruposProdutos;
import com.ailtonluiz.sgs.repository.filter.SubgrupoProdutosFilter;
import com.ailtonluiz.sgs.service.CadastroSubgruposProdutosService;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.NomeSubgrupoProdutosJaCadastradoException;

@Controller
@RequestMapping("/subgruposProdutos")
public class SubgruposProdutosController {

	@Autowired
	private SubgruposProdutos subgruposProdutos;

	@Autowired
	private GruposProdutos gruposProdutos;

	@Autowired
	private CadastroSubgruposProdutosService cadastroSubgruposProdutosService;

	@RequestMapping("/novo")
	public ModelAndView novo(SubgrupoProdutos subgrupoProdutos) {
		ModelAndView mv = new ModelAndView("subgrupo/CadastroSubgrupoProdutos");
		mv.addObject("gruposProdutos", gruposProdutos.findAll());

		return mv;
	}

	@Cacheable(value = "subgrupos", key = "#codigoGrupoProduto")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<SubgrupoProdutos> pesquisarPorCodigoGrupoProduto(
			@RequestParam(name = "grupoProdutos", defaultValue = "-1") Long codigoGrupoProduto) {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}

		return subgruposProdutos.findByGrupoProdutosCodigo(codigoGrupoProduto);

	}

	@PostMapping("/novo")
	@CacheEvict(value = "subgrupos", key = "#subgrupoProdutos.grupoProdutos.codigo", condition = "#subgrupoProdutos.temGrupoProdutos()")
	public ModelAndView salvar(@Valid SubgrupoProdutos subgrupoProdutos, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return novo(subgrupoProdutos);
		}

		try {
			cadastroSubgruposProdutosService.salvar(subgrupoProdutos);
		} catch (NomeSubgrupoProdutosJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(subgrupoProdutos);
		}

		attributes.addFlashAttribute("mensagem", "Subgrupo registrado con exito!");
		return new ModelAndView("redirect:/subgruposProdutos/novo");

	}

	@GetMapping
	public ModelAndView pesquisar(SubgrupoProdutosFilter subgrupoProdutosFilter, BindingResult result,
			@PageableDefault(size = 13) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("subgrupo/PesquisaSubgrupos");
		mv.addObject("gruposProdutos", gruposProdutos.findAll());

		PageWrapper<SubgrupoProdutos> paginaWrapper = new PageWrapper<>(
				subgruposProdutos.filtrar(subgrupoProdutosFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		SubgrupoProdutos subgrupoProdutos = subgruposProdutos.buscarComGrupoProdutos(codigo);
		ModelAndView mv = novo(subgrupoProdutos);
		mv.addObject(subgrupoProdutos);

		return mv;
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") SubgrupoProdutos subgrupoProdutos) {

		try {
			cadastroSubgruposProdutosService.excluir(subgrupoProdutos);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.ok().build();
	}

}
