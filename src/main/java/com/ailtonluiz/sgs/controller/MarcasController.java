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
import com.ailtonluiz.sgs.model.Marca;
import com.ailtonluiz.sgs.repository.Marcas;
import com.ailtonluiz.sgs.repository.filter.MarcaFilter;
import com.ailtonluiz.sgs.service.CadastroMarcaService;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.NomeMarcaJaCadastradoException;

@Controller
@RequestMapping("/marcas")
public class MarcasController {

	@Autowired
	private CadastroMarcaService cadastroMarcaService;

	@Autowired
	private Marcas marcas;

	@RequestMapping("/novo")
	public ModelAndView novo(Marca marca) {
		ModelAndView mv = new ModelAndView("marca/CadastroMarca");
		return mv;
	}

	@RequestMapping(value = { "/novo", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Marca marca, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(marca);
		}
		try {
			cadastroMarcaService.salvar(marca);
		} catch (NomeMarcaJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(marca);
		}
		attributes.addFlashAttribute("mensagem", "La marca se guarda con éxito!");
		return new ModelAndView("redirect:/marcas/novo");

	}

	// Método salvar do cadastro rápido
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Marca marca, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		marca = cadastroMarcaService.salvar(marca);
		return ResponseEntity.ok(marca);
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Marca marca) {
		try {
			cadastroMarcaService.excluir(marca);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ModelAndView pesquisar(MarcaFilter marcaFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("marca/PesquisaMarcas");

		PageWrapper<Marca> paginaWrapper = new PageWrapper<>(marcas.filtrar(marcaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Marca marca = marcas.findOne(codigo);
		ModelAndView mv = novo(marca);
		mv.addObject(marca);
		return mv;
	}
}