package com.gaston.tpdlc2017.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.gaston.tpdlc2017.model.Documento;
import com.gaston.tpdlc2017.service.DocumentoService;
import com.gaston.tpdlc2017.service.HashingService;
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
    private static String UPLOADED_FOLDER = "/home/ppioli/uploads/";

    private DocumentoService documentoService;
    private HashingService hashingService;

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
        logger.info("Got file upload request: " + file.getName());
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                byte[] hash = hashingService.hash(bytes);
                if(documentoService.exists(hash)){
                    return "{status: \"error\", message: \"Documento ya fue indexado\"}";
                } else {
                    //indexar el documento
                    String docName = hashingService.encodedHash(hash);
                    Path path = Paths.get(UPLOADED_FOLDER + docName + ".dat" );
                    Files.write(path, bytes);
                    Documento documento = new Documento(null, file.getOriginalFilename(), hash);
                    documentoService.create(documento);
                    return "{status: \"ok\", result: \""+ file.getOriginalFilename() +"\"}";
                }

            } catch (IOException e) {
                return "{status: \"error\", message: \"Can't write file to disk\"}";
            }
        } else {
            return "{status: \"error\", message: \"Seleccion vacia\"}";
        }

    }

}