package gerenciadorDeProjetos.Apresentação;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gerenciadorDeProjetos.Aplicação.DTOs.LoginRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.LoginResponse;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IAdministradorAppServiço;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IAlunoAppServiço;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IProfessorAppServiço;
import gerenciadorDeProjetos.Infraestrutura.JwtUtil;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private IAdministradorAppServiço administradorServiço;

	@Autowired
	private IProfessorAppServiço professorServiço;
	
	@Autowired
	private IAlunoAppServiço alunoServiço;

	@PostMapping("")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
	    if (administradorServiço.logar(request.getEmail(), request.getSenha())) {
	        Long id = administradorServiço.buscarEmail(request.getEmail());
	        String token = jwtUtil.generateToken(request.getEmail(), "ROLE_ADMIN");
	        return ResponseEntity.ok(new LoginResponse("ADMIN", "Bearer " + token, id));
	    }

	    if (professorServiço.logar(request.getEmail(), request.getSenha())) {
	        Long id = professorServiço.buscarEmail(request.getEmail());
	        String token = jwtUtil.generateToken(request.getEmail(), "ROLE_PROFESSOR");
	        return ResponseEntity.ok(new LoginResponse("PROFESSOR", "Bearer " + token, id));
	    }

	    if (alunoServiço.logar(request.getEmail(), request.getSenha())) {
	        Long id = alunoServiço.buscarEmail(request.getEmail());
	        String token = jwtUtil.generateToken(request.getEmail(), "ROLE_ALUNO");
	        return ResponseEntity.ok(new LoginResponse("ALUNO", "Bearer " + token, id));
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	}
}
