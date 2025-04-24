package gerenciadorDeProjetos.Apresentação;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gerenciadorDeProjetos.Aplicação.DTOs.AtualizarProjetoRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.ProjetoRequest;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IProjetoAppServiço;

@RestController
@RequestMapping("/projeto")
public class ProjetoController {

    @Autowired
    private IProjetoAppServiço projetoServiço;

    @GetMapping("/listar")
    public ResponseEntity<List<ProjetoRequest>> listarProjetos() {
        List<ProjetoRequest> projetos = projetoServiço.listarProjetos();
        return ResponseEntity.ok(projetos);
    }
    
    @GetMapping("/listarProjetosPorProfessorId")
    public ResponseEntity<List<ProjetoRequest>> listarProjetosPorProfessorId(Long professorId) {
        List<ProjetoRequest> projetos = projetoServiço.listarProjetosPorProfessorId(professorId);
        return ResponseEntity.ok(projetos);
    }
    
    @GetMapping("/listarProjetosPorAlunoId")
    public ResponseEntity<List<ProjetoRequest>> listarProjetosPorAlunoId(Long alunoId) {
        List<ProjetoRequest> projetos = projetoServiço.listarProjetosPorAlunoId(alunoId);
        return ResponseEntity.ok(projetos);
    }
    
	@PostMapping("/atualizarProjeto")
	public ResponseEntity<String> atualizarProjeto(@RequestBody AtualizarProjetoRequest request) {
		if (!projetoServiço.projetoNomeEmUso(request.getNome())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome do Projeto não encotrado!");
		}

		projetoServiço.atualizarProjeto(request);

		return ResponseEntity.status(HttpStatus.CREATED).body("Projeto atualizado com sucesso");
	}
    
}
