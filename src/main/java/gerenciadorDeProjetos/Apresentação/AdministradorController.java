package gerenciadorDeProjetos.Apresentação;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gerenciadorDeProjetos.Aplicação.DTOs.GrupoRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.ProfessorRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.ProjetoRequest;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IAdministradorAppServiço;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IAlunoAppServiço;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IGrupoAppServiço;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IProfessorAppServiço;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IProjetoAppServiço;
import gerenciadorDeProjetos.Dominio.Projeto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/administrador")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdministradorController {

	@Autowired
	private IAdministradorAppServiço administradorServiço;
	
	@Autowired
	private IGrupoAppServiço grupoServiço;

	@Autowired
	private IProfessorAppServiço professorServiço;

	@Autowired
	private IProjetoAppServiço projetoServiço;
	
    @Autowired
    private IAlunoAppServiço alunoServiço;

	
	   private boolean emailJaCadastrado(String email) {
	        try {
	            administradorServiço.buscarEmail(email);
	            return true;
	        } catch (RuntimeException e) {
	        }

	        try {
	            professorServiço.buscarEmail(email);
	            return true;
	        } catch (RuntimeException e) {
	        }

	        try {
	            alunoServiço.buscarEmail(email);
	            return true;
	        } catch (RuntimeException e) {
	        }

	        return false;
	    }
	   
	@PostMapping("/cadastrarGrupo")
	public ResponseEntity<String> cadastrarGrupo(@RequestBody GrupoRequest request) {
		if (grupoServiço.grupoNomeEmUso(request.getNome())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome do Grupo já está em uso");
		}

		grupoServiço.cadastrarGrupo(request);

		return ResponseEntity.status(HttpStatus.CREATED).body("Grupo cadastrado com sucesso");
	}

	@PostMapping("/cadastrarProjeto")
	public ResponseEntity<Projeto> solicitarProjeto(@RequestBody ProjetoRequest projetoRequest) {
		try {
			Projeto projeto = professorServiço.solicitarProjeto(projetoRequest);
			return new ResponseEntity<>(projeto, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	 @PostMapping("/cadastrarProfessor")
	    public ResponseEntity<String> cadastrarProfessor(@RequestBody ProfessorRequest request) {
	        if (emailJaCadastrado(request.getEmail())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já está em uso");
	        }

	        professorServiço.cadastrarProfessor(request);
	        return ResponseEntity.status(HttpStatus.CREATED).body("Professor cadastrado com sucesso");
	    }
	 
	 @DeleteMapping("/desativarGrupo")
	    public ResponseEntity<Void> desativarGrupo(@RequestParam String nome) {
	        grupoServiço.desativarGrupo(nome);
	        return ResponseEntity.noContent().build();
	    }
	 
	 @DeleteMapping("/deletarProjeto")
	    public ResponseEntity<Void> deletarProjeto(@RequestParam String nome) {
		 	projetoServiço.deletarProjeto(nome);
	        return ResponseEntity.noContent().build();
	    }
}
