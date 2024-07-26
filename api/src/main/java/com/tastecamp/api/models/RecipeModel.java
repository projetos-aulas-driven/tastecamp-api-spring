package com.tastecamp.api.models;

import com.tastecamp.api.dtos.RecipeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // cria getters e setters de tudo
@AllArgsConstructor // cria um construtor com todos os argumentos da classe
@NoArgsConstructor // cria um construtor sem argumentos
@Entity // identifica o Model como uma entidade a ser mapeada no banco de dados
@Table(name = "tb-recipes") // determinar o nome da tabela no banco
public class RecipeModel {

    @Id // identifica que é o id, a chave primária dessa tabela
    @GeneratedValue(strategy = GenerationType.AUTO) // a estratégia de gração automática desse valor
    private Long id;

    @Column(length = 150, nullable = false)
    private String title;

    @Column(nullable = false)
    private String ingredients;

    @Column(nullable = false)
    private String steps;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private UserModel author;

    public RecipeModel (RecipeDTO dto, UserModel author) {
        this.title = dto.getTitle();
        this.ingredients = dto.getIngredients();
        this.steps = dto.getSteps();
        this.author = author;
    }
}
