package com.vidaplus.vidaplustrabalho.dto;

import com.vidaplus.vidaplustrabalho.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignUpRequest {
    @Email(message = "O email deve ser válido.")
    @NotBlank(message = "O campo email é obrigatório.")
    private String email;
    @NotBlank(message = "O campo senha é obrigatório.")
    private String senha;
    @NotNull(message = "O campo perfil é obrigatório.")
    private Role perfil;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Role getPerfil() { return perfil; }
    public void setPerfil(Role perfil) { this.perfil = perfil; }
}
