package Nengcipe.NengcipeBackend.service;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.json.JSONArray;

@Service
public class OCRService {

    @Value("${ocr.url}")
    private String apiURL;
    @Value("${ocr.secretKey}")
    private String secretKey;

    public Object clovaOCRService(File file) { //clovaOCRService

        List<List<String>> result;

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);
            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            System.out.println(response); // API 호출 결과를 콘솔에 출력
            // jsonToString() 메소드 호출하고 결과 받아옴
            result = jsonToString(response.toString());
            System.out.println(result);
            return result;

        } catch (Exception e) {
            System.out.println("서비스에러");
            System.out.println(e);

        }
        return null;
    }

    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
            IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }

    public List<List<String>> jsonToString(String jsonResultStr) throws JSONException {
        String result = "";

        List<List<String>> mainResult = new ArrayList<>();

        JSONObject jsonObj = new JSONObject(jsonResultStr);

        JSONArray imageArray = jsonObj.getJSONArray("images");

        JSONObject tempObj = imageArray.getJSONObject(0);
        JSONObject receiptObj = tempObj.getJSONObject("receipt");

        JSONObject resultObj = receiptObj.getJSONObject("result");
        JSONArray subResultArray = resultObj.getJSONArray("subResults");
        JSONObject temp2Obj = subResultArray.getJSONObject(0);
        JSONArray itemsArray = temp2Obj.getJSONArray("items");

        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject sparseObj = (JSONObject) itemsArray.get(i);
            String nameResultStr = sparseObj.getJSONObject("name").getString("text");
            String countResultStr = sparseObj.getJSONObject("count").getString("text");
            List<String> subResult = new ArrayList<>();
            subResult.add(nameResultStr);
            subResult.add(countResultStr);
            mainResult.add(subResult);
        }

        System.out.println(mainResult);
        //[[생삼겹살, 25], [맥주, 4], [음료수, 4], [밥+된장, 13]]

        return mainResult;
    }
}
