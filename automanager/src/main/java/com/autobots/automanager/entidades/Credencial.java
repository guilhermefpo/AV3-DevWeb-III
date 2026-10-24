package com.autobots.automanager.entidades;

import javax.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Entity
@Table(name = "credencial")
public class Credencial extends RepresentationModel<Credencial> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Login é obrigatório") private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    @NotBlank(message = "Senha é obrigatória") private String senha;
}
