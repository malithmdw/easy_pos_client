package webService;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import serverResponseDataModel.BillPaymentServerResponse;
import serverResponseDataModel.CommonResponse;

/**
 * HTTP client for the EasyReload bill-payment API.
 *
 * The EasyReload API uses {"status":"SUCCESS"|"ERROR","message":"...","data":...}
 * instead of the EasyPOS {"response_code":"00","response_msg":"...","data":...}
 * envelope, so a separate web-service class is needed to parse responses correctly.
 */
public class BillPaymentWebService {

    public static final String IMAGE_FOLDER_PATH = "https://moosero.lk/EASYRELOAD-images";
    private static final String BASE_URL = "https://www.moosero.lk/EASY-RELOAD-BACKEND/api";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public CommonResponse sendPOSTRequest(String apiPath, Map<String, String> header, Object bodyData) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            URL url = new URL(BASE_URL + "/" + apiPath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Content-Type", "application/json");
            for (Map.Entry<String, String> e : header.entrySet()) {
                con.setRequestProperty(e.getKey(), e.getValue());
            }
            con.setConnectTimeout(30_000);
            con.setReadTimeout(30_000);

            String requestBody = MAPPER.writeValueAsString(bodyData);
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                os.write(requestBody.getBytes("utf-8"));
            }

            int httpCode = con.getResponseCode();
            InputStream is = (httpCode >= 200 && httpCode < 300)
                    ? con.getInputStream() : con.getErrorStream();
            String result = readStream(is);

            BillPaymentServerResponse resp = MAPPER.readValue(result, BillPaymentServerResponse.class);
            commonResponse.setData(resp.data);
            commonResponse.setAPIResponse(toResponseCode(httpCode, resp.status));

        } catch (SocketTimeoutException ex) {
            commonResponse.setAPIResponse(ResponseCodes.get("97"));
        } catch (UnknownHostException | ConnectException ex) {
            commonResponse.setAPIResponse(ResponseCodes.get("96"));
        } catch (IOException ex) {
            commonResponse.setAPIResponse(ResponseCodes.get("98"));
        }
        return commonResponse;
    }

    public CommonResponse sendGETRequest(String apiPath, Map<String, String> header) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            URL url = new URL(BASE_URL + "/" + apiPath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Content-Type", "application/json");
            for (Map.Entry<String, String> e : header.entrySet()) {
                con.setRequestProperty(e.getKey(), e.getValue());
            }
            con.setConnectTimeout(30_000);
            con.setReadTimeout(30_000);

            int httpCode = con.getResponseCode();
            InputStream is = (httpCode >= 200 && httpCode < 300)
                    ? con.getInputStream() : con.getErrorStream();
            String result = readStream(is);

            BillPaymentServerResponse resp = MAPPER.readValue(result, BillPaymentServerResponse.class);
            commonResponse.setData(resp.data);
            commonResponse.setAPIResponse(toResponseCode(httpCode, resp.status));

        } catch (SocketTimeoutException ex) {
            commonResponse.setAPIResponse(ResponseCodes.get("97"));
        } catch (UnknownHostException | ConnectException ex) {
            commonResponse.setAPIResponse(ResponseCodes.get("96"));
        } catch (IOException ex) {
            commonResponse.setAPIResponse(ResponseCodes.get("98"));
        }
        return commonResponse;
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
        }
        String result = sb.toString();
        if (result.startsWith("﻿")) {
            result = result.substring(1);
        }
        return result;
    }

    /** Maps the EasyReload HTTP status + status string to a ResponseCodes entry. */
    private ResponseCodes.ApiResponse toResponseCode(int httpCode, String status) {
        if ("SUCCESS".equalsIgnoreCase(status)) {
            return ResponseCodes.get("00");
        }
        switch (httpCode) {
            case 202: return ResponseCodes.get("00"); // PENDING: has transaction data, treat as success so receipt can be printed
            case 401: return ResponseCodes.get("04"); // auth / JWT expired
            case 400: return ResponseCodes.get("08"); // bad request
            case 409: return ResponseCodes.get("01"); // clientRef conflict
            case 503: return ResponseCodes.get("11"); // service unavailable
            case 500: return ResponseCodes.get("05"); // system error
            default:  return ResponseCodes.get("99");
        }
    }
}
