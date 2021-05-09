package com.example.fp_predictor.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadsController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/uploads")
    public String open() {
        return "uploads";
    }

    @PostMapping("/uploads")
    public String add(
            @RequestParam("file")MultipartFile file,
            RequestParam title
    ) throws IOException {
        file.transferTo(new File(uploadPath + "/" + title + ".png"));
        return "main";
    }
}
