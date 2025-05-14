package br.insper.af.controller;

import br.insper.af.model.Tarefa;
import br.insper.af.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    // Campo para o e-mail no token JWT
    private static final String EMAIL_CLAIM = "https://musica-insper.com/email";

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Apenas ADMINs podem criar tarefas
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa, @AuthenticationPrincipal Jwt jwt) {
        // Extrai o e-mail do usuário do token JWT
        String emailUsuario = jwt.getClaimAsString(EMAIL_CLAIM);
        if (emailUsuario == null) {
            // Considerar lançar uma exceção ou retornar um erro apropriado
            // se o e-mail não estiver presente no token para um admin.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        tarefa.setEmailUsuarioCriador(emailUsuario);
        Tarefa novaTarefa = tarefaRepository.save(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTarefa);
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();
        return ResponseEntity.ok(tarefas);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Apenas ADMINs podem deletar tarefas
    public ResponseEntity<Void> deletarTarefa(@PathVariable String id) {
        if (!tarefaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tarefaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}