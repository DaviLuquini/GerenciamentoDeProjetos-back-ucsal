package gerenciadorDeProjetos.Aplicação.Serviços;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gerenciadorDeProjetos.Aplicação.DTOs.AtualizarProjetoRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.ProjetoRequest;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IProjetoAppServiço;
import gerenciadorDeProjetos.Dominio.Projeto;
import gerenciadorDeProjetos.Dominio.Status;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IProjetoRepositorio;

@Service
public class ProjetoAppServiço implements IProjetoAppServiço {
	@Autowired
	private IProjetoRepositorio projetoRepositorio;

	@Override
	public Projeto atualizarProjeto(AtualizarProjetoRequest request) {
		Projeto projeto = projetoRepositorio.findById(request.getId())
				.orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

		projeto.setNome(request.getNome());
		projeto.setStatus(request.getStatus());


		return projetoRepositorio.save(projeto);
	}

	@Override
	public List<ProjetoRequest> listarProjetos() {
	    return projetoRepositorio.findAll().stream().map(projeto -> {
	    	ProjetoRequest dto = new ProjetoRequest();
	        dto.setId(projeto.getId());
	        dto.setNome(projeto.getNome());
	        dto.setObjetivo(projeto.getObjetivo());
	        dto.setDataInicio(projeto.getDataInicio());
	        dto.setEscopo(projeto.getEscopo());
	        dto.setPublicoAlvo(projeto.getPublicoAlvo());
	        dto.setStatus(projeto.getStatus());

	        if (projeto.getGrupo() != null) {
	            dto.setGrupoId(projeto.getGrupo().getId());


	            if (projeto.getGrupo().getProfessor() != null) {
	                dto.setProfessorId(projeto.getGrupo().getProfessor().getId());
	            }
	        }

	        if (dto.getProfessorId() == null && projeto.getProfessor() != null) {
	            dto.setProfessorId(projeto.getProfessor().getId());
	        }

	        return dto;
	    }).collect(Collectors.toList());
	}
	
	@Override
	public List<ProjetoRequest> listarProjetosPorProfessorId(Long professorId) {
	    return projetoRepositorio.findAll().stream()
	        .filter(projeto -> {
	            if (projeto.getProfessor() != null && projeto.getProfessor().getId().equals(professorId)) {
	                return true;
	            }
	            if (projeto.getGrupo() != null && projeto.getGrupo().getProfessor() != null &&
	                projeto.getGrupo().getProfessor().getId().equals(professorId)) {
	                return true;
	            }
	            return false;
	        })
	        .map(projeto -> {
	            ProjetoRequest dto = new ProjetoRequest();
	            dto.setId(projeto.getId());
	            dto.setNome(projeto.getNome());
	            dto.setObjetivo(projeto.getObjetivo());
	            dto.setDataInicio(projeto.getDataInicio());
	            dto.setEscopo(projeto.getEscopo());
	            dto.setPublicoAlvo(projeto.getPublicoAlvo());
	            dto.setStatus(projeto.getStatus());

	            if (projeto.getGrupo() != null) {
	                dto.setGrupoId(projeto.getGrupo().getId());

	                if (projeto.getGrupo().getProfessor() != null) {
	                    dto.setProfessorId(projeto.getGrupo().getProfessor().getId());
	                }
	            }

	            if (dto.getProfessorId() == null && projeto.getProfessor() != null) {
	                dto.setProfessorId(projeto.getProfessor().getId());
	            }

	            return dto;
	        })
	        .collect(Collectors.toList());
	}
	
	@Override
	public List<ProjetoRequest> listarProjetosPorAlunoId(Long alunoId) {
	    return projetoRepositorio.findAll().stream()
	        .filter(projeto -> 
	            projeto.getGrupo() != null &&
	            projeto.getGrupo().getAlunos() != null &&
	            projeto.getGrupo().getAlunos().stream()
	                .anyMatch(aluno -> aluno.getId().equals(alunoId))
	        )
	        .map(projeto -> {
	            ProjetoRequest dto = new ProjetoRequest();
	            dto.setId(projeto.getId());
	            dto.setNome(projeto.getNome());
	            dto.setObjetivo(projeto.getObjetivo());
	            dto.setDataInicio(projeto.getDataInicio());
	            dto.setEscopo(projeto.getEscopo());
	            dto.setPublicoAlvo(projeto.getPublicoAlvo());
	            dto.setStatus(projeto.getStatus());

	            if (projeto.getGrupo() != null) {
	                dto.setGrupoId(projeto.getGrupo().getId());

	                if (projeto.getGrupo().getProfessor() != null) {
	                    dto.setProfessorId(projeto.getGrupo().getProfessor().getId());
	                }
	            }

	            if (dto.getProfessorId() == null && projeto.getProfessor() != null) {
	                dto.setProfessorId(projeto.getProfessor().getId());
	            }

	            return dto;
	        })
	        .collect(Collectors.toList());
	}

	
	@Override
	public void confirmarEntrega(Long projetoId) {
	    Projeto projeto = projetoRepositorio.findById(projetoId)
	            .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

	    projeto.setStatus(Status.FINALIZADO);

	    projetoRepositorio.save(projeto);
	}

	
	  @Override
	    public void deletarProjeto(String nome) {
		    Projeto projeto = projetoRepositorio.findByNome(nome)
	            .orElseThrow(() -> new RuntimeException("Professor não encontrado com o email: " + nome));
	        
		    projetoRepositorio.delete(projeto);
	    }

	
    @Override
    public boolean projetoNomeEmUso(String nome) {
        return projetoRepositorio.existsByNome(nome);
    }

}
