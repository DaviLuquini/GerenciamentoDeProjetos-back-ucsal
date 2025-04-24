package gerenciadorDeProjetos.Aplicação.Serviços;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gerenciadorDeProjetos.Aplicação.DTOs.ProjetoRequest;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IProjetoAppServiço;
import gerenciadorDeProjetos.Dominio.Projeto;
import gerenciadorDeProjetos.Dominio.Status;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IGrupoRepositorio;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IProfessorRepositorio;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IProjetoRepositorio;

@Service
public class ProjetoAppServiço implements IProjetoAppServiço {
	@Autowired
	private IProjetoRepositorio projetoRepositorio;

	@Autowired
	private IProfessorRepositorio professorRepositorio;

	@Autowired
	private IGrupoRepositorio grupoRepositorio;
	
	@Override
	public Projeto atualizarProjeto(ProjetoRequest request) {
		Projeto projeto = projetoRepositorio.findById(request.getId())
				.orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

		projeto.setNome(request.getNome());
		projeto.setObjetivo(request.getObjetivo());
		projeto.setDataInicio(request.getDataInicio());
		projeto.setEscopo(request.getEscopo());
		projeto.setPublicoAlvo(request.getPublicoAlvo());
		projeto.setStatus(request.getStatus());

		if (request.getProfessorId() != null) {
			projeto.setProfessor(professorRepositorio.findById(request.getProfessorId())
					.orElseThrow(() -> new RuntimeException("Professor não encontrado")));
		}

		if (request.getGrupoId() != null) {
			projeto.setGrupo(grupoRepositorio.findById(request.getGrupoId())
					.orElseThrow(() -> new RuntimeException("Grupo não encontrado")));
		}

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
	public List<ProjetoRequest> listarProjetosPorId(Long professorId) {
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
	public void confirmarEntrega(Long projetoId) {
	    Projeto projeto = projetoRepositorio.findById(projetoId)
	            .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

	    projeto.setStatus(Status.FINALIZADO);

	    projetoRepositorio.save(projeto);
	}


	
    @Override
    public boolean projetoNomeEmUso(String nome) {
        return projetoRepositorio.findByNome(nome).isPresent();
    }

}
