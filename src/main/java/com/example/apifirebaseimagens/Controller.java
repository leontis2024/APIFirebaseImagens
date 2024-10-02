package com.example.apifirebaseimagens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/upload")
public class Controller {
    private final FirebaseStorageService firebaseService;
    @Autowired
    public Controller(FirebaseStorageService firebaseService) {
        this.firebaseService = firebaseService;
    }


    @PostMapping
    public String upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("nomeTabela") String nomeTabela, @RequestParam("id") String id) {
        return firebaseService.upload(multipartFile,nomeTabela,id);
    }

}
