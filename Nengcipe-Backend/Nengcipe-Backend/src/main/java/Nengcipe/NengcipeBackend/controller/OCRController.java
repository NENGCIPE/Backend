package Nengcipe.NengcipeBackend.controller;


import Nengcipe.NengcipeBackend.dto.ResultResponse;
import Nengcipe.NengcipeBackend.service.OCRService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class OCRController {
    @Autowired
    private OCRService ocrService;
    @PostMapping("/api/clovaOCR")
    public ResponseEntity<Object> clovaOCR(@RequestPart("uploadFile") MultipartFile file){
        String tempDir = System.getProperty("java.io.tmpdir");
        System.out.println("임시 디렉토리 경로: " + tempDir);
        Object result = null;

        try {
            result=ocrService.clovaOCRService(multipartFileToFile(file));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("컨트롤러 에러");
        }
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("영수증 정보 가져오기 성공.")
                .result(result).build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    public File multipartFileToFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        File convertedFile = File.createTempFile("temp", null);
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        IOUtils.copy(inputStream, fileOutputStream);
        fileOutputStream.close();
        inputStream.close();
        return convertedFile;
    }
}