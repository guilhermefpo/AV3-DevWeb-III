package com.autobots.automanager.entidades;

import javax.persistence.*;
import  com.autobots.automanager.enumeracao.TipoVeiculo;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "veiculo")
@Data
@Entity
public class Veiculo extends RepresentationModel<Veiculo> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column 
    private TipoVeiculo tipo;

    private String marca;
    private String modelo;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Placa é obrigatória") private String placa;
    private Integer ano;
    @ManyToOne @JoinColumn(name = "usuario_id") @JsonIgnore private Usuario usuario;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getUsuarioId() {
        return usuario == null ? null : usuario.getId();
    }

}
