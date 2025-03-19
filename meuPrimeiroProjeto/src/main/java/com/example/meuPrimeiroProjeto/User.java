package com.example.meuPrimeiroProjeto;

import jakarta.persistence.*;

@Entity
@Table(name = "usersdata")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    
    private String name;
    private String senha;
    private String email;


    public User() {}

    public User(String name, String email,String senha) {
        this.senha = senha;
        this.name = name;
        this.email = email;
        
    }

    public Long getId() { return id; }

    public String getSenha(){ return senha; }
    public void setSenha(String senha ){ this.senha = senha;}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
