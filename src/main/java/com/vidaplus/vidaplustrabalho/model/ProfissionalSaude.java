package com.vidaplus.vidaplustrabalho.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profissionais_saude")
public class ProfissionalSaude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProfissional categoria;

    @Column(nullable = false, unique = true)
    private String crmCoren;

    public ProfissionalSaude() {}

    public ProfissionalSaude(String nome, CategoriaProfissional categoria, String crmCoren) {
        this.nome = nome;
        this.categoria = categoria;
        this.crmCoren = crmCoren;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public CategoriaProfissional getCategoria() { return categoria; }
    public void setCategoria(CategoriaProfissional categoria) { this.categoria = categoria; }
    public String getCrmCoren() { return crmCoren; }
    public void setCrmCoren(String crmCoren) { this.crmCoren = crmCoren; }
}