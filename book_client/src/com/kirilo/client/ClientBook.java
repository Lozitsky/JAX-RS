package com.kirilo.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

// https://howtodoinjava.com/resteasy/solved-java-lang-classnotfoundexception-org-glassfish-jersey-client-jerseyclientbuilder/
// https://www.vogella.com/tutorials/REST/article.html#jaxb_client
public class ClientBook {
    public static void main(String[] args) {

        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(getBaseURI());

        String xmlResponse = target.path("books").path("async_stream").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        System.out.println(xmlResponse);

    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(
                "http://localhost:8080/jakarta_book/root/").build();
    }
}
