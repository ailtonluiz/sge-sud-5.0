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
import com.ailtonluiz.sgs.model.Fornecedor;
import com.ailtonluiz.sgs.repository.Fornecedores;
import com.ailtonluiz.sgs.repository.filter.FornecedorFilter;
import com.ailtonluiz.sgs.service.CadastroFornecedorService;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.NomeFornecedorJaCadastradoException;

@Controller
@RequestMapping("/fornecedores")
public class FornecedoresController {

	@Autowired
	private CadastroFornecedorService cadastroFornecedorService;

	@Autowired
	Fornecedores fornecedores;

	@RequestMapping("/novo")
	public ModelAndView novo(Fornecedor fornecedor) {
		ModelAndView mv = new ModelAndView("fornecedor/CadastroFornecedor");

		return mv;
	}

	@RequestMapping(value = { "/novo", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Fornecedor fornecedor, BindingResult result, Model model,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(fornecedor);
		}

		try {
			cadastroFornecedorService.salvar(fornecedor);
		} catch (NomeFornecedorJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(fornecedor);
		}
		attributes.addFlashAttribute("mensagem", "Proveedor guardados con éxito!");
		return new ModelAndView("redirect:/fornecedores/novo");

	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Fornecedor fornecedor) {
		try {
			cadastroFornecedorService.excluir(fornecedor);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ModelAndView pesquisar(FornecedorFilter fornecedorFilter, BindingResult result,
			@PageableDefault(size = 7) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("fornecedor/PesquisaFornecedores");

		PageWrapper<Fornecedor> paginaWrapper = new PageWrapper<>(fornecedores.filtrar(fornecedorFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Fornecedor> pesquisar(String nome) {
		validarTamanhoNome(nome);
		return fornecedores.findByNomeStartingWithIgnoreCase(nome);
	}

	private void validarTamanhoNome(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Fornecedor fornecedor = fornecedores.findOne(codigo);
		ModelAndView mv = novo(fornecedor);
		mv.addObject(fornecedor);
		return mv;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}

}
