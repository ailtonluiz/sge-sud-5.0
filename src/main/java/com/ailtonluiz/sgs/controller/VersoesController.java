package com.ailtonluiz.sgs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VersoesController {

	@GetMapping("/versoes")
	public ModelAndView versoes() {
		ModelAndView mv = new ModelAndView("versao/Versao");

		return mv;
	}

}
