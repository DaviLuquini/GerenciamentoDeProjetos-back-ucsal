package gerenciadorDeProjetos.Apresentação;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
    @GetMapping("/listarProjetosPorId")
    public ResponseEntity<List<ProjetoRequest>> listarProjetosPorId(Long professorId) {
        List<ProjetoRequest> projetos = projetoServiço.listarProjetosPorId(professorId);
        return ResponseEntity.ok(projetos);
    }
}
