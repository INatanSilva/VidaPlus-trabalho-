package com.vidaplus.vidaplustrabalho.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senhaCriptografada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role perfil;

    public UserAccount() {}

    public UserAccount(String email, String senhaCriptografada, Role perfil) {
        this.email = email;
        this.senhaCriptografada = senhaCriptografada;
        this.perfil = perfil;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenhaCriptografada() { return senhaCriptografada; }
    public void setSenhaCriptografada(String senhaCriptografada) { this.senhaCriptografada = senhaCriptografada; }
    public Role getPerfil() { return perfil; }
    public void setPerfil(Role perfil) { this.perfil = perfil; }
}