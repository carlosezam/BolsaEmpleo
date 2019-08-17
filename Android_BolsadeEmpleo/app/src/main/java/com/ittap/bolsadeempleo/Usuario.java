package com.ittap.bolsadeempleo;



public class Usuario {

    public String username, email, auth_key;
    public int id;

    public Usuario(int id, String username, String email, String auth_key){
        this.id = id;
        this.username = username;
        this.email = email;
        this.auth_key = auth_key;
    }

    public Usuario(String username, String email)
    {
        this.username = username;
        this.email = email;
    }

}
