package org.example.restserver.server;

import com.sun.net.httpserver.HttpExchange;
import org.example.restserver.http.ContentType;
import org.example.restserver.http.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Response {

    private final HttpStatus status;
    private final ContentType contentType;
    private final String body;

    public Response(HttpStatus status, ContentType contentType, String body){
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }




    public void send(HttpExchange exchange) throws IOException {
        byte[] bodyBytes = (body == null) ? new Byte[0] : body.getBytes(StandardCharsets.UTF_8);


        exchange.getResponseHeaders().set(
                "Content-Type",
                contentType.getValue();
        );


        boolean hasBody = status != HttpStatus.NO_CONTENT && bodyBytes.length > 0;

        if (hasBody) {
            exchange.sendResponseHeaders(status.getCode(), bodyBytes.length);
        } else {
            exchange.sendResponseHeaders(status.getCode(), -1);
        }

        OutputStream output = exchange.getResponseBody();

        try {
            if (hasBody) {
                output.write(bodyBytes);
            }
        } finally {
            output.close();
        }
    }


    //Getters


    public HttpStatus getStatus() {
        return status;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getBody() {
        return body;
    }
}
