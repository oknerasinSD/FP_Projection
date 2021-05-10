package com.example.fp_predictor.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class DownloadController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/download_csv_file")
    public void downloadCsv(@RequestParam String filename, HttpServletResponse response) throws IOException {
        File file = new File(uploadPath + File.separator + "csv" + File.separator + filename);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + file.getName();
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[8192];
        int bytesRead= -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
    }
}
