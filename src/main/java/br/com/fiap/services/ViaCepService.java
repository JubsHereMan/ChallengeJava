package br.com.fiap.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import br.com.fiap.model.Endereco;

public class ViaCepService {
    public Endereco buscarEnderecoPorCep(String cep) throws Exception {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String jsonResponse = response.toString();
        System.out.println("Resposta da API: " + jsonResponse); 

       
        return jsonParaEndereco(jsonResponse);
    }

    private Endereco jsonParaEndereco(String json) {
        
        Endereco endereco = new Gson().fromJson(json, Endereco.class);
        
       
        endereco.setLocalidade(endereco.getLocalidade()); 
        endereco.setUf(endereco.getUf()); 

        return endereco;
    }

}
