package com.autobots.automanager.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import javax.validation.constraints.NotBlank;

@Table(name = "email")
@Data 
@Entity 
public class Email extends RepresentationModel<Email> {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


    @Column(nullable = false, unique = true)
    @NotBlank(message = "Endereço de e-mail é obrigatório")
    @javax.validation.constraints.Email(message = "Endereço de e-mail inválido")
    private String endereco;
}
