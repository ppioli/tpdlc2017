package com.gaston.tpdlc2017.web;

import com.gaston.tpdlc2017.model.Documento;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by ppioli on 07/05/17.
 */
public class HashSomething {
    MessageDigest md5;
    Base64.Encoder encoder;

    public HashSomething() {
        try {
            this.md5 = MessageDigest.getInstance("SHA-1");
            this.encoder = Base64.getUrlEncoder();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getHash(byte[] bytes){
        byte[] hashBytes = md5.digest(bytes);
        return encoder.encodeToString(hashBytes);
    }


    public static void main(String... args) throws IOException {
        HashSomething hashSomething = new HashSomething();
        String palabra = "gato";
        String hash = hashSomething.getHash(palabra.getBytes());
        Path path = Paths.get("/home/ppioli/uploads/test.txt");
        byte[] data = Files.readAllBytes(path);
        String fileHash = hashSomething.getHash(data);

        System.out.println(String.format("El hash de %s es %s", palabra, hash));
        System.out.println(String.format("El hash de el archivo %s es %s", path.toString(), fileHash));

        Path destino = Paths.get("/home/ppioli/uploads/" + fileHash + ".dat");
        Files.write(destino, data);

    }

    public void showFile(Documento doc) throws IOException {
        Path path = Paths.get("/home/ppioli/uploads/" + doc.getHash() + ".dat");
        byte[] bytes = Files.readAllBytes(path);
    }
}
