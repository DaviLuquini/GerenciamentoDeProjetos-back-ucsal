package gerenciadorDeProjetos.Apresentação;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gerenciadorDeProjetos.Aplicação.DTOs.GrupoRequest;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IGrupoAppServiço;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/administrador")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdministradorController {


	@Autowired
	private IGrupoAppServiço grupoServiço;

	@PostMapping("/cadastrarGrupo")
	public ResponseEntity<String> cadastrarGrupo(@RequestBody GrupoRequest request) {
		if (grupoServiço.grupoNomeEmUso(request.getNome())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome do Grupo já está em uso");
		}

		grupoServiço.cadastrarGrupo(request);

		return ResponseEntity.status(HttpStatus.CREATED).body("Grupo cadastrado com sucesso");
	}
	
    @PostMapping("/atualizarGrupo")
	public ResponseEntity<String> atualizarGrupo(@RequestBody GrupoRequest request) {
		if (!grupoServiço.grupoNomeEmUso(request.getNome())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome do Grupo não encotrado!");
		}

		grupoServiço.atualizarGrupo(request);

		return ResponseEntity.status(HttpStatus.CREATED).body("Grupo atualizado com sucesso");
	}
}
