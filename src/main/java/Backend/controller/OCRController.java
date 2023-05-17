package Backend.controller;

import Backend.service.OCRService;
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

@Controller
public class OCRController {
    @Autowired
    private OCRService ocrService;

    @RequestMapping  ("/clovaOCR")
    public String clovaOCR() {
        ocrService.clovaOCRService();
        return "result";
    }

}


//프론트에서 넘어오면 작성할 코드
//@RestController
//public class OCRController {
//    @Autowired
//    private OCRService ocrService;
//    @RequestMapping("/clovaOCR")
//    // value = clovaOCR
//    public String clovaOCR(@RequestPart("uploadFile") MultipartFile file){
//        String result = "";
//        try {
//            result = ocrService.clovaOCRService(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//}



