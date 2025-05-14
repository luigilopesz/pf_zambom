package br.insper.af.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.insper.af.model.Tarefa;
import br.insper.af.service.TarefaService;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/tarefa")
    public List<Tarefa> getTarefas() {
        return tarefaService.list();
    }

    @PostMapping("/tarefa")
    public Tarefa saveTarefa(@AuthenticationPrincipal Jwt jwt, @RequestBody Tarefa tarefa) {
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        String email = jwt.getClaimAsString("https://musica-insper.com/email");

        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        tarefa.setEmail(email);
        return tarefaService.save(tarefa);
    }

    @DeleteMapping("/tarefa/{id}")
    public void deleteTarefa(@AuthenticationPrincipal Jwt jwt, @PathVariable String id) {
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");

        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        tarefaService.delete(id);
    }
}