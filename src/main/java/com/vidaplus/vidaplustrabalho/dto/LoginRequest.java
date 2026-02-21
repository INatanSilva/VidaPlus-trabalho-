package com.vidaplus.vidaplustrabalho.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @Email
    @NotBlank(message = "O campo email é obrigatório.")
    private String email;
    @NotBlank(message = "O campo senha é obrigatório.")
    private String senha;


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}