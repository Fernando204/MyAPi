package com.example.meuPrimeiroProjeto;

public class UserDTO {

    private String name;
    private String email;
    private long id;

    public UserDTO(){

    }
    public UserDTO(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.id = user.getId();
    }
}
