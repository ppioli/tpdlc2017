package com.gaston.tpdlc2017.web;

import java.util.*;

import com.gaston.tpdlc2017.model.Documento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gaston.tpdlc2017.service.SearchService;

@Controller
public class WelcomeController {

	private final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
        private final SearchService searchService;

	@Autowired
	public WelcomeController(SearchService searchService) {
		this.searchService = searchService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Map<String, Object> model) {
		/*
		logger.debug("index() is executed!");
		List<Documento> documentos = searchService.search(new String[] {"murmaider", "the", "water", "god"});
		logger.debug("Cantidad de documentos"+documentos.size());
		for(Documento doc : documentos) {
			logger.info("Encontro un documento " + doc.getPath());
		}
		model.put("documentos", documentos);*/

		return "index";
	}
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView index(@RequestParam("query") String query) {
		ModelAndView model = new ModelAndView();
		model.setViewName("result");
		
		logger.debug("search is executed! Query " + query );
		String[] listPal = Arrays.stream(query.split("[^a-zA-Záéíóúñ0-9]"))
				.map( val -> val.toLowerCase())
				.filter(val -> val.length() > 1)
				.filter(val -> !val.matches(".*[0-9].*"))
				.toArray(value -> new String[value] );

		if(listPal.length == 0){
			model.addObject("error", "Query invalida");
		}
		List<Documento> documentos = searchService.search(listPal);
		logger.debug("Cantidad de documentos "+documentos.size());
		for(Documento doc : documentos) {
			logger.info("Encontro un documento " + doc.getPath() + " Score: " + doc.getScore());
		}
		if(documentos.size() > 0){
			model.addObject("documentos", documentos);
		} else {
			model.addObject("error", "No se encontraron documentos relacionados");
		}

		return model;
	}

	@RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		logger.debug("hello() is executed - $name {}", name);

		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		searchService.search(new String[] {name.toLowerCase()});
		
		return model;

	}

}