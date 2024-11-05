package br.com.fiap.main;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import br.com.fiap.resource.ClienteResource; // Importe seu recurso

public class Grizzly {
    public static void main(String[] args) {
        URI baseUri = URI.create("http://localhost:8080/ProjetoAtlasChallengeFinal/");

        // Configurando o ResourceConfig explicitamente com ClienteResource
        ResourceConfig config = new ResourceConfig().register(ClienteResource.class);

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("Servidor Grizzly iniciado em " + baseUri);

        try {
            server.start();
            System.in.read(); // Mantém o servidor ativo até pressionar Enter
            server.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
