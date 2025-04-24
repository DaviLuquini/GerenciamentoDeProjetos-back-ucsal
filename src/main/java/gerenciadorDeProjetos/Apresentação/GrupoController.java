package gerenciadorDeProjetos.Apresentação;

import gerenciadorDeProjetos.Aplicação.DTOs.GrupoRequest;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IGrupoAppServiço;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupo")
public class GrupoController {

    @Autowired
    private IGrupoAppServiço grupoServiço;

    @GetMapping("/listarGrupos")
    public ResponseEntity<List<GrupoRequest>> listarGrupos() {
        List<GrupoRequest> grupos = grupoServiço.listarGrupos();
        return ResponseEntity.ok(grupos);
    }
    
    @GetMapping("/listarGrupoPorAlunoId")
    public ResponseEntity<GrupoRequest> listarGrupoPorAlunoId(Long AlunoId) {
    	GrupoRequest grupo = grupoServiço.listarGrupoPorAlunoId(AlunoId);
        return ResponseEntity.ok(grupo);
    }

    @DeleteMapping("/desativar")
    public ResponseEntity<Void> desativarGrupo(@RequestParam String nome) {
        grupoServiço.desativarGrupo(nome);
        return ResponseEntity.noContent().build();
    }
}
