package com.gaston.tpdlc2017.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.gaston.tpdlc2017.model.Documento;
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
public class FileUploadController {
    private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private Path docRepo;
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

    @Autowired
    public void setDocRepo(Path docRepo) {
        this.docRepo = docRepo;
    }

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public String uploadFormView(Map<String, Object> model) {
        logger.debug("uploadFormView() is executed!");
        return "upload";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
        logger.info("Got file upload request: " + file.getName());
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                byte[] hash = hashingService.hash(bytes);
                if(documentoService.exists(hash)){
                    return "{done: false, message: \"Documento ya fue indexado\" , file:\"" + file.getOriginalFilename() + "\"}";
                } else {
                    //indexar el documento
                    Documento documento = new Documento(null, file.getOriginalFilename(), hash);
                    Path path = docRepo.resolve( hashingService.hashToFileName(hash));
                    documentoService.create(documento);
                    boolean result = indexadorDocumentos.indexar(documento);
                    if(result){
                        Files.write(path, bytes);
                    }
                    return "{done: true, message: \"Documento indexado\" , file:\"" + file.getOriginalFilename() + "\"}";
                }

            } catch (IOException e) {
                return "{done: false, message: \""+e.getMessage()+"\", file:\"" + file.getOriginalFilename() + "\"}";
            }
        } else {
            return "{done: false, message: \"Seleccion vacia\"}";
        }

    }

}