package gerenciadorDeProjetos.Aplicação.Serviços;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gerenciadorDeProjetos.Aplicação.DTOs.ProfessorRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.ProjetoRequest;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IProfessorAppServiço;
import gerenciadorDeProjetos.Dominio.Grupo;
import gerenciadorDeProjetos.Dominio.Professor;
import gerenciadorDeProjetos.Dominio.Projeto;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IGrupoRepositorio;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IProfessorRepositorio;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IProjetoRepositorio;

@Service
public class ProfessorAppServiço implements IProfessorAppServiço {

    @Autowired
    private IProjetoRepositorio projetoRepo;
	
	@Autowired
    private IProfessorRepositorio professorRepo;
   
	@Autowired
    private IGrupoRepositorio grupoRepo;

	@Override
    public Long buscarEmail(String email) {
    	  return professorRepo.findByEmail(email)
    	            .map(Professor::getId)
    	            .orElseThrow(() -> new RuntimeException("Professor não encontrado com o e-mail: " + email));
    }
	
    @Override
    public Professor cadastrarProfessor(ProfessorRequest request) {
        Professor novoProfessor = new Professor();
        
        novoProfessor.setNome(request.getNome());
        novoProfessor.setEmail(request.getEmail());
        novoProfessor.setSenha(request.getSenha());

        return professorRepo.save(novoProfessor);
    }

    
    @Override
    public boolean logar(String email, String senha) {
    	Optional<Professor> prof = professorRepo.findByEmailAndSenha(email, senha);
    	
        if (!prof.isPresent()) {
        	 return false;
        }
    	
        return true;
    }
    
    @Override
    public Projeto solicitarProjeto(ProjetoRequest projetoRequest) {
        Projeto projeto = new Projeto();
        projeto.setNome(projetoRequest.getNome());
        projeto.setObjetivo(projetoRequest.getObjetivo());
        projeto.setDataInicio(projetoRequest.getDataInicio());
        projeto.setEscopo(projetoRequest.getEscopo());
        projeto.setPublicoAlvo(projetoRequest.getPublicoAlvo());
        projeto.setStatus(projetoRequest.getStatus());

        if (projetoRequest.getProfessorId() != null) {
            Professor professor = professorRepo.findById(projetoRequest.getProfessorId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            projeto.setProfessor(professor);
        }

        if (projetoRequest.getGrupoId() != null) {
            Grupo grupo = grupoRepo.findById(projetoRequest.getGrupoId())
                    .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));
            projeto.setGrupo(grupo);
        }

        return projetoRepo.save(projeto);
    }
    
    @Override
    public List<Professor> listarProfessores() {

        List<Professor> professores = professorRepo.findAll();

        return professores.stream().map(professor -> {
            Professor dto = new Professor();
            dto.setId(professor.getId());
            dto.setNome(professor.getNome());
            dto.setEmail(professor.getEmail());



            return dto;
        }).collect(Collectors.toList());
    }
    
    @Override
    public void deletarProfessor(String email) {
        Professor professor = professorRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado com o email: " + email));
        
        professorRepo.delete(professor);
    }


}
