package br.insper.af.repository;

import br.insper.af.model.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TarefaRepository extends MongoRepository<Tarefa, String> {
}