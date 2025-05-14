package br.insper.af.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "tarefas")
public class Tarefa {
    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private String emailUsuarioCriador; // Armazena o e-mail do usuário que criou

    
}