package uo.asw.inciManager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uo.asw.inciManager.service.AgentService;

@Controller
public class AgentController {
	
	@Autowired
	private AgentService agentsService;
	
	private String username;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet(Model model) {
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("idAgente", agentsService.getIdConnected());
		model.addAttribute("nombreUsuario", username);
		return comprobarConectado("home");
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String loginPost(Model model, @RequestParam String username, @RequestParam String password,
			@RequestParam String kind) {
		Map<String,Object> infoAgente = agentsService.communicationAgents(username, password, kind);
		if(infoAgente != null) {
			agentsService.setIdConnected((String)infoAgente.get("id"));
			this.username = username;
			return "redirect:/home";
		} else {
			agentsService.setIdConnected(null);
		}
		return "/login";
	}
	
	private String comprobarConectado(String destino) {
		if(agentsService.getIdConnected() == null) {
			return "redirect:/login";
		}else return destino;
	}
}
