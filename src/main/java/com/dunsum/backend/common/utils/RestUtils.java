package com.dunsum.backend.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class RestUtils {

    final HttpMethod DEFAULT_METHOD = HttpMethod.POST;

    @Value("${api.timeout}")
    private int apiTimeout;

    public void setApiTimeOut(int timeoutMs) {
        this.apiTimeout = timeoutMs;
    }

    private HttpHeaders settingHeader() {
        return settingHeader(null);
    }

    @SuppressWarnings({ "rawtypes"})
    private HttpHeaders settingHeader(HashMap<String, String> requestProperties) {

        HttpHeaders headers = new HttpHeaders();
        String contentType = MediaType.APPLICATION_JSON_VALUE;

        if(requestProperties != null && requestProperties.size() > 0) {
            Set set = requestProperties.entrySet();

            for (Object o : set) {
                Map.Entry entry = (Map.Entry) o;
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if ("contentType".equals(key)) {
                    contentType = value;
                } else {
                    headers.set(key, value);
                }
            }
        }

        switch (contentType) {
            case MediaType.APPLICATION_XML_VALUE:
                headers.setContentType(MediaType.APPLICATION_XML);
                break;
            case MediaType.TEXT_XML_VALUE:
                headers.setContentType(MediaType.TEXT_XML);
                break;
            case MediaType.APPLICATION_FORM_URLENCODED_VALUE:
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                break;
            case MediaType.APPLICATION_JSON_VALUE:
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                break;
            case MediaType.MULTIPART_FORM_DATA_VALUE:
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                break;
        }
        return headers;
    }

    @SuppressWarnings({ "rawtypes"})
    public Object sendPostApi(String uriAddr, Object params, Class clas) throws Exception {
        return this.sendRestApi(uriAddr, params, clas, DEFAULT_METHOD, null, false);
    }

    @SuppressWarnings({ "rawtypes"})
    public Object sendPostApi(String uriAddr, Object params, Class clas, HttpMethod httpMethod) throws Exception {
        return this.sendRestApi(uriAddr, params, clas, httpMethod, null, false);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object sendRestApi(String uriAddr, Object params, Class clas, HttpMethod httpMethod, HttpHeaders headers, boolean isParam) throws Exception {
        Object rtnObj = null;

        StringBuilder log = new StringBuilder();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {

            if(null == headers) {
                headers = settingHeader();
            }

            URI url = URI.create(uriAddr);

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(apiTimeout);
            factory.setReadTimeout(apiTimeout);

            RestTemplate re = new RestTemplate(factory);
            ObjectMapper om = new ObjectMapper();

            String strParam = "";

            if(HttpMethod.GET.equals(httpMethod)) {
                if(params instanceof String) {
                    url = UriComponentsBuilder.fromUriString(url.toString() + "?" + params.toString())
                            .build().encode()
                            .toUri();
                }else {
                    MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
                    Map<String, String> map = om.convertValue(params, new TypeReference<Map<String, String>>() {});
                    paramMap.setAll(map);

                    url = UriComponentsBuilder.fromUri(url)
                            .queryParams(paramMap)
                            .build().encode()
                            .toUri();
                }
            }else {
                if(isParam) {
                    MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
                    Map<String, String> map = om.convertValue(params, new TypeReference<Map<String, String>>() {});
                    paramMap.setAll(map);

                    url = UriComponentsBuilder.fromUri(url)
                            .queryParams(paramMap)
                            .build().encode()
                            .toUri();
                } else if(params instanceof String) {
                    strParam = params.toString();
                } else {
                    strParam = om.writeValueAsString(params);
                }
            }

            System.out.println("req Url :: " + url);
            System.out.println("strParam Url :: " + strParam);

            HttpEntity<String> request = new HttpEntity<String>(strParam, headers);
            re.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            ResponseEntity<Object> response = re.exchange(url, httpMethod, request, clas);
            rtnObj = response.getBody();

        } catch (Exception e) {

            System.out.println("###### Exception ######");
            System.out.println(e.getMessage());

            throw e;
        } finally {
            stopWatch.stop();
            System.out.println("- Response -\n");

            if(rtnObj != null) {
                ObjectMapper om = new ObjectMapper();
                String resString = om.writeValueAsString(rtnObj);
                System.out.println(resString);
            } else {
                System.out.println("- Response is null !!!");
            }

            System.out.println("- TimeSeconds : "+stopWatch.getTotalTimeSeconds());
        }
        return rtnObj;
    }
}
