package com.ailtonluiz.sgs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ailtonluiz.sgs.controller.page.PageWrapper;
import com.ailtonluiz.sgs.model.Usuario;
import com.ailtonluiz.sgs.repository.Parametros;
import com.ailtonluiz.sgs.repository.GruposUsuarios;
import com.ailtonluiz.sgs.repository.Usuarios;
import com.ailtonluiz.sgs.repository.filter.UsuarioFilter;
import com.ailtonluiz.sgs.service.CadastroUsuarioService;
import com.ailtonluiz.sgs.service.StatusUsuario;
import com.ailtonluiz.sgs.service.exception.EmailUsuarioJaCadastradoException;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.SenhaObrigatoriaUsuarioException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	@Autowired
	private GruposUsuarios gruposUsuarios;
	
	@Autowired
	private Parametros parametros;
	
	@Autowired
	private Usuarios usuarios;

	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("gruposUsuarios", gruposUsuarios.findAll());
		mv.addObject("parametros", parametros.findAll());
		return mv;
	}


	@PostMapping({ "/novo", "{\\+d}" })
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(usuario);
		}
		
		try {
			cadastroUsuarioService.salvar(usuario);
		} catch (EmailUsuarioJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		} catch (SenhaObrigatoriaUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		attributes.addFlashAttribute("mensagem", "Usuario guardado correctamente");
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Usuario usuario) {
		try {
			cadastroUsuarioService.excluir(usuario);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result,
			@PageableDefault(size = 5) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuarios");
		
		mv.addObject("gruposUsuarios", gruposUsuarios.findAll());
		
		

		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(usuarios.filtrar(usuarioFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);

		return mv;
	}

	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario statusUsuario) {
		cadastroUsuarioService.alterarStatus(codigos, statusUsuario);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Usuario usuario = usuarios.buscarComGruposUsuarios(codigo);
		ModelAndView mv = novo(usuario);
		mv.addObject(usuario);
		return mv;
	}
}
