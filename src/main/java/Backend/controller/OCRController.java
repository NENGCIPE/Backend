package Backend.controller;

import Backend.service.OCRService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class OCRController {
    @Autowired
    private OCRService ocrService;
    @PostMapping("/api/clovaOCR")
    public Object clovaOCR(@RequestPart("uploadFile") MultipartFile file){
        String tempDir = System.getProperty("java.io.tmpdir");
        System.out.println("임시 디렉토리 경로: " + tempDir);

        try {
            return ocrService.clovaOCRService(multipartFileToFile(file));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("컨트롤러 에러");
        }
        return "hello";
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





