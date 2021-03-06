package com.gaston.tpdlc2017.web;

import java.io.IOException;
import java.util.Map;

import com.gaston.tpdlc2017.service.DocumentoService;
import com.gaston.tpdlc2017.service.HashingService;
import com.gaston.tpdlc2017.service.IndexadorDocumentos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FileUploadController{
    private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private DocumentoService documentoService;
    private HashingService hashingService;
    private IndexadorDocumentos indexadorDocumentos;
    @Autowired
    public void setIndexadorDocumentos(IndexadorDocumentos indexadorDocumentos) {
        this.indexadorDocumentos = indexadorDocumentos;
    }

    @Autowired
    public void setHashingService(HashingService hashingService) {
        this.hashingService = hashingService;
    }

    @Autowired
    public void setDocumentoService(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public String uploadFormView(Map<String, Object> model) {
        logger.debug("uploadFormView() is executed!");
        return "upload";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
        logger.info("Got file upload request: " + file.getOriginalFilename());

        if (!file.isEmpty()) {
            
            try {
                byte[] bytes = file.getBytes();
                byte[] hash = hashingService.hash(bytes);
                if(documentoService.find(hash) != null){
                    return "{\"done\": false, \"message\": \"El documento ya habia sido cargado anteriormente\" , \"file\":\"" + file.getOriginalFilename() + "\"}";
                } else {
                    //indexar el documento
                    String err = indexadorDocumentos.indexar(bytes, file.getOriginalFilename(), hash);
                    if(err == null){
                        return "{\"done\": true, \"message\": \"Documento agregado!\" , \"file\":\"" + file.getOriginalFilename() + "\"}";
                    } else {
                        return "{\"done\": false, \"message\":\"" + err + "\" , \"file\":\"" + file.getOriginalFilename() + "\"}";
                    }

                }
            } catch (IOException e) {
                return "{\"done\": false, \"message\":\"" + e.getMessage() + "\" , \"file\":\"" + file.getOriginalFilename() + "\"}";
            }
            
        } else {
            return "{'done': false, 'message': 'seleccion vacia (?)'}";
        }

    }

}