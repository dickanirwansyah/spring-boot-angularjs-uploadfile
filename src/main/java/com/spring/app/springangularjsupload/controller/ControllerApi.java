package com.spring.app.springangularjsupload.controller;

import com.spring.app.springangularjsupload.request.UploadRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ControllerApi {

    private static String upload_dir = System.getProperty("user.home")+"/test";

    @PostMapping(value = "/api/rest/multifiles")
    public ResponseEntity<?> uploadFileMultipart(@ModelAttribute UploadRequest request) throws Exception{
        System.out.println("Description : " + request.getDescription());
        String result = null;
        try{
            result = this.saveUpload(request.getFiles());
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>("Error : "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Uploaded to : "+result, HttpStatus.OK);
    }

    private String saveUpload(MultipartFile[] files) throws Exception{
        File uploadDir = new File(upload_dir);
        uploadDir.mkdirs();
        StringBuilder sb = new StringBuilder();

        for (MultipartFile file: files){
            if (file.isEmpty()){
                continue;
            }
            String uploadFilePath = upload_dir + "/" + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            sb.append(uploadFilePath).append(", ");
        }
        return sb.toString();
    }

    @GetMapping("/api/rest/list")
    public List<String> getMapping(){
        File uploadDir = new File(upload_dir);
        File[] files = uploadDir.listFiles();
        List<String> list = new ArrayList<>();
        for(File file : files){
            list.add(file.getName());
        }
        return list;
    }

    @GetMapping("/api/rest/{filename:..+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException{
        File file = new File(upload_dir+"/"+filename);
        if (!file.exists()){
            throw new RuntimeException("File Not Found !");
        }
        Resource resource = new UrlResource(file.toURI());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
}
