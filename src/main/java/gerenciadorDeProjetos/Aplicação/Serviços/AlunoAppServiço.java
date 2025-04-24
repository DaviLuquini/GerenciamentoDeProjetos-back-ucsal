package gerenciadorDeProjetos.Aplicação.Serviços;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gerenciadorDeProjetos.Aplicação.DTOs.AlunoRequest;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IAlunoAppServiço;
import gerenciadorDeProjetos.Dominio.Aluno;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IAlunoRepositorio;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IGrupoRepositorio;

@Service
public class AlunoAppServiço implements IAlunoAppServiço {

    @Autowired
    private IAlunoRepositorio alunoRepositorio;

    @Autowired
    private IGrupoRepositorio grupoRepositorio;

    @Override
    public Aluno cadastrarAluno(AlunoRequest request) {
        Aluno aluno = new Aluno();
        aluno.setNome(request.getNome());
        aluno.setCurso(request.getCurso());

        if (request.getGrupoId() != null) {
            aluno.setGrupo(grupoRepositorio.findById(request.getGrupoId())
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado")));
        }

        return alunoRepositorio.save(aluno);
    }
    
    @Override
    public boolean logar(String email, String senha) {
    	Optional<Aluno> aluno = alunoRepositorio.findByEmailAndSenha(email, senha);
    	
        if (!aluno.isPresent()) {
        	 return false;
        }
    	
        return true;
    }
    
	@Override
    public Long buscarEmail(String email) {
    	  return alunoRepositorio.findByEmail(email)
    	            .map(Aluno::getId)
    	            .orElseThrow(() -> new RuntimeException("Aluno não encontrado com o e-mail: " + email));
    }
	
}

