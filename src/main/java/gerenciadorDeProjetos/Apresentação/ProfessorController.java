package gerenciadorDeProjetos.Apresentação;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gerenciadorDeProjetos.Aplicação.DTOs.ConfirmarEntregaRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.ProjetoRequest;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IProfessorAppServiço;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IProjetoAppServiço;
import gerenciadorDeProjetos.Dominio.Professor;
import gerenciadorDeProjetos.Dominio.Projeto;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private IProfessorAppServiço professorServiço;
    
    @Autowired
    private IProjetoAppServiço projetoServiço;

    
    @GetMapping("/listarProfessores")
    public ResponseEntity<List<Professor>> listarProfessores() {
    	 List<Professor> professores = professorServiço.listarProfessores();
         return ResponseEntity.ok(professores);
    }
    
    @PostMapping("/solicitarProjeto")
    public ResponseEntity<Projeto> solicitarProjeto(@RequestBody ProjetoRequest projetoRequest) {
        try {
            Projeto projeto = professorServiço.solicitarProjeto(projetoRequest);
            return new ResponseEntity<>(projeto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); 
        }
    }

    @PostMapping("/confirmarEntregaProjeto")
    public ResponseEntity<String> confirmarEntregaProjeto(@RequestBody ConfirmarEntregaRequest request) {
        if (!projetoServiço.projetoNomeEmUso(request.getNome())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome do Projeto não encontrado!");
        }

        projetoServiço.confirmarEntrega(request.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Projeto confirmado entrega com sucesso");
    }

}
