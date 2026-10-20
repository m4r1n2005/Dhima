package org.example.restserver.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.example.restserver.http.Method;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {

    private final Method method;
    private final String path;
    private final List<String> pathParts;
    private final Map<String, String> queryParameters;
    private final Headers headers;
    private final String body;


    public Request(HttpExchange exchange) throws IOException {
        this.method = Method.valueOf(exchange.getRequestMethod());

        URI url = exchange.getRequestURI();


        this.path = url.getPath();

        this.pathParts = parsePathParts(path);

        this.queryParameters = parseQueryParameters(url.getRawQuery());


        this.headers = exchange.getRequestHeaders();


        this.body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

    }


    private List<String> parsePathParts(String path){
        return Arrays.stream(path.split("/")).filter(part -> !part.isEmpty()).toList();
    }


    private Map<String, String> parseQueryParameters(String rawQuery){
        if(rawQuery == null || rawQuery.isEmpty()){
            return Map.of();
        }



        return Arrays.stream(rawQuery.split("&")).map(pair -> pair.split("=", 2)).collect(
                Collectors.toMap(
                        parts -> URLDecoder.decode(parts[0], StandardCharsets.UTF_8),
                        parts -> URLDecoder.decode(parts.length > 1 ? parts[1] : "", StandardCharsets.UTF_8)
                )
        );
    }




    //Getters


    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public List<String> getPathParts() {
        return pathParts;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }


    public String getQueryParameter(String name){
        return queryParameters.get(name);
    }


    public String getHeader(String name){
        return headers.getFirst(name);
    }
}
