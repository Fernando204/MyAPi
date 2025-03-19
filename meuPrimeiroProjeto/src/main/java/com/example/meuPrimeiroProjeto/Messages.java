package com.example.meuPrimeiroProjeto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mensagens")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
 
    @JsonProperty("saida")
    private String saida;

    @JsonProperty("destino")
    private String destino;

    @JsonProperty("mensagem")
    private String mensagem;
    
    private String data;

    public Messages() {
    }

    public Messages(String saida,String destino,String mensagem){
        this.destino = destino;
        this.saida = saida;
        this.mensagem = mensagem;
    }

    public String getDestino() {return destino;}
    public String getSaida() {return saida;}
    public String mensagem() {return mensagem;}
    public String getData() {return data;}

    public void setMensagem(String mensagem){
        this.mensagem = mensagem;
    }

    public void setData(String data){
        this.data = data;
    }
}
