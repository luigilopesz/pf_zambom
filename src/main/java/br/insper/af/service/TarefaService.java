package br.insper.af.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.insper.af.model.Tarefa;
import br.insper.af.repository.TarefaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public Tarefa save(Tarefa tarefa) {
        tarefa.setId(UUID.randomUUID().toString());
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> list() {
        return tarefaRepository.findAll();
    }

    public void delete(String id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isPresent()) {
            tarefaRepository.delete(tarefa.get());
        } else {
            throw new RuntimeException("Tarefa não encontrada com id: " + id);
        }
    }
}