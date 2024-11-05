package br.com.fiap.resource;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.fiap.beans.Cliente;
import br.com.fiap.bo.ClienteBO;

@Path("/cliente")
public class ClienteResource {
    
    private ClienteBO clienteBO = new ClienteBO();
  
    
    // Selecionar todos os clientes
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response selecionarRs() {
        try {
            ArrayList<Cliente> clientes = (ArrayList<Cliente>) clienteBO.selecionarBo();
            return Response.ok(clientes).build();
        } catch (Exception e) {
            String mensagemErro = "{\"message\": \"Erro ao listar clientes: " + e.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mensagemErro).build();
        }
    }
    
    
 // Inserir cliente
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirRs(Cliente cliente) {
        try {
            clienteBO.inserirBo(cliente); // Chama o método de inserção da camada BO
            String mensagemSucesso = "{\"message\": \"Cliente inserido com sucesso.\"}";
            return Response.status(Response.Status.CREATED).entity(mensagemSucesso).build();
        } catch (Exception e) {
            String mensagemErro = "{\"message\": \"Erro ao inserir cliente: " + e.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mensagemErro).build();
        }
    }


    
    // Atualizar cliente
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarRs(Cliente cliente, @PathParam("id") int id) {
        try {
            cliente.setId(id); // Define o ID a partir da URL
            clienteBO.atualizarBo(cliente); // Atualiza o cliente

            String mensagemSucesso = "{\"message\": \"Cliente atualizado com sucesso.\"}";
            return Response.ok(mensagemSucesso).build();
        } catch (Exception e) {
            String mensagemErro = "{\"message\": \"Erro ao atualizar cliente: " + e.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mensagemErro).build();
        }
    }


    
    // Deletar cliente
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletarRs(@PathParam("id") int id) {
        try {
            clienteBO.deletarBo(id);
            String mensagemSucesso = "{\"message\": \"Cliente deletado com sucesso.\"}";
            return Response.ok(mensagemSucesso).build();
        } catch (Exception e) {
            String mensagemErro = "{\"message\": \"Erro ao deletar cliente: " + e.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mensagemErro).build();
        }
    }
}
