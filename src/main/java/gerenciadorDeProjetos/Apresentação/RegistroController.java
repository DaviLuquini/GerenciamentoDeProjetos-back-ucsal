package gerenciadorDeProjetos.Apresentação;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gerenciadorDeProjetos.Aplicação.DTOs.AlunoRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.ProfessorRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.RegistroRequest;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IAdministradorAppServiço;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IAlunoAppServiço;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IProfessorAppServiço;


@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private IAdministradorAppServiço administradorServiço;
    
    @Autowired
    private IProfessorAppServiço professorServiço;
    
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


    @PostMapping("/admin")
    public ResponseEntity<String> registroAdmin(@RequestBody RegistroRequest request) {
        if (emailJaCadastrado(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já está em uso");
        }

        administradorServiço.registrar(request.getNome(), request.getEmail(), request.getSenha());
        return ResponseEntity.status(HttpStatus.CREATED).body("Administrador registrado com sucesso");
    }

    @PostMapping("/professor")
    public ResponseEntity<String> cadastrarProfessor(@RequestBody ProfessorRequest request) {
        if (emailJaCadastrado(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já está em uso");
        }

        professorServiço.cadastrarProfessor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Professor cadastrado com sucesso");
    }

    @PostMapping("/aluno")
    public ResponseEntity<String> cadastrarAluno(@RequestBody AlunoRequest request) {
        if (emailJaCadastrado(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já está em uso");
        }

        alunoServiço.cadastrarAluno(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Aluno cadastrado com sucesso");
    }
}


