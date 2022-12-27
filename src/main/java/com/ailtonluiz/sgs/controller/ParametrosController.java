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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ailtonluiz.sgs.controller.page.PageWrapper;
import com.ailtonluiz.sgs.model.Parametro;
import com.ailtonluiz.sgs.repository.Parametros;
import com.ailtonluiz.sgs.repository.filter.ParametroFilter;
import com.ailtonluiz.sgs.service.CadastroParametroService;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.NomeEmpresaJaCadastradoException;

@Controller
@RequestMapping("/parametros")
public class ParametrosController {

	@Autowired
	private CadastroParametroService cadastroParametroService;

	@Autowired
	private Parametros parametros;

	@RequestMapping("/novo")
	public ModelAndView novo(Parametro parametro) {
		ModelAndView mv = new ModelAndView("parametro/CadastroParametro");

		return mv;
	}

	@RequestMapping(value = { "/novo", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Parametro parametro, BindingResult result, Model model,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(parametro);
		}

		try {
			cadastroParametroService.salvar(parametro);
		} catch (NomeEmpresaJaCadastradoException e) {
			result.rejectValue("nomeEmpresa", e.getMessage(), e.getMessage());
			return novo(parametro);
		}
		attributes.addFlashAttribute("mensagem", "Realizado con éxito!");
		return new ModelAndView("redirect:/parametros/novo");

	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Parametro parametro) {
		try {
			cadastroParametroService.excluir(parametro);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ModelAndView pesquisar(ParametroFilter parametroFilter, BindingResult result,
			@PageableDefault(size = 7) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("parametro/PesquisaParametros");

		PageWrapper<Parametro> paginaWrapper = new PageWrapper<>(parametros.filtrar(parametroFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Parametro> pesquisar(String nomeEmpresa) {
		validarTamanhoNome(nomeEmpresa);
		return parametros.findByNomeEmpresaStartingWithIgnoreCase(nomeEmpresa);
	}

	private void validarTamanhoNome(String nomeEmpresa) {
		if (StringUtils.isEmpty(nomeEmpresa) || nomeEmpresa.length() < 3) {
			throw new IllegalArgumentException();
		}
	}
	
	
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Parametro parametro = parametros.findOne(codigo);
		ModelAndView mv = novo(parametro);
		mv.addObject(parametro);
		return mv;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}

}
