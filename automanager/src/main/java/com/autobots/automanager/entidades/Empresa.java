package com.autobots.automanager.entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "empresa")
public class Empresa extends RepresentationModel<Empresa> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "Nome da empresa é obrigatório")
    private String nome;
    @Column(unique = true)
    private String cnpj;
    @OneToMany(mappedBy = "empresa")
    @JsonIgnore
    private List<Usuario> usuarios = new ArrayList<>();
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Mercadoria> mercadorias = new ArrayList<>();
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Servico> servicos = new ArrayList<>();
}
