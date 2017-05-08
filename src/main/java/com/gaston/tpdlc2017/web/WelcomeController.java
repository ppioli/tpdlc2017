package com.gaston.tpdlc2017.web;

import java.util.List;
import java.util.Map;

import com.gaston.tpdlc2017.model.Documento;
import com.gaston.tpdlc2017.service.ServicioPalabra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gaston.tpdlc2017.service.HelloWorldService;
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

		logger.debug("index() is executed!");
		List<Documento> documentos = searchService.search(new String[] {"hold", "the", "door", "engine"});
		logger.debug("Cantidad de documentos"+documentos.size());
		for(Documento doc : documentos) {
			logger.info("Encontro un documento " + doc.getPath());
		}
		model.put("documentos", documentos);

		return "result";
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