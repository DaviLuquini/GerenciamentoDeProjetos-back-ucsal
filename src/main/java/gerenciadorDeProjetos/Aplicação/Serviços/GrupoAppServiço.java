package gerenciadorDeProjetos.Aplicação.Serviços;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gerenciadorDeProjetos.Aplicação.DTOs.GrupoRequest;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IGrupoAppServiço;
import gerenciadorDeProjetos.Dominio.Aluno;
import gerenciadorDeProjetos.Dominio.Grupo;
import gerenciadorDeProjetos.Dominio.Professor;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IAlunoRepositorio;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IGrupoRepositorio;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IProfessorRepositorio;

@Service
public class GrupoAppServiço implements IGrupoAppServiço {

    @Autowired
    private IGrupoRepositorio grupoRepositorio;
    
    @Autowired
    private IProfessorRepositorio professorRepositorio;
    
    @Autowired
    private IAlunoRepositorio alunoRepositorio;
    
    @Override
    public Grupo cadastrarGrupo(GrupoRequest grupoRequest) {
        Grupo grupo = new Grupo();
        grupo.setNome(grupoRequest.getNome());
        grupo.setDisponivel(true);

        Professor professor = professorRepositorio.findById(grupoRequest.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        List<Aluno> alunos = alunoRepositorio.findAllById(grupoRequest.getAlunosIds());
        
        grupo.setProfessor(professor);
        grupo.setAlunos(alunos);

        return grupoRepositorio.save(grupo);
    }

    @Override
    public boolean grupoNomeEmUso(String nome) {
        return grupoRepositorio.findByNome(nome).isPresent();
    }

    @Override
    public Grupo atualizarGrupo(GrupoRequest grupoAtualizado) {
        return grupoRepositorio.findByNome(grupoAtualizado.getNome()).map(grupo -> {
            grupo.setNome(grupoAtualizado.getNome());
            grupo.setDisponivel(grupoAtualizado.isDisponivel());
            Professor professor = professorRepositorio.findById(grupoAtualizado.getProfessorId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

            List<Aluno> alunos = alunoRepositorio.findAllById(grupoAtualizado.getAlunosIds());
            
            grupo.setProfessor(professor);
            grupo.setAlunos(alunos);

            return grupoRepositorio.save(grupo); 
        }).orElseThrow(() -> new RuntimeException("Grupo não encontrado"));
    }
    
    @Override
    public List<GrupoRequest> listarGrupos() {
        List<Grupo> grupos = grupoRepositorio.findByDisponivelTrue();
        List<GrupoRequest> grupoRequests = new ArrayList<>();

        for (Grupo grupo : grupos) {
            GrupoRequest request = new GrupoRequest();
            request.setId(grupo.getId());
            request.setNome(grupo.getNome());
            request.setDisponivel(grupo.isDisponivel());

            if (grupo.getProjeto() != null) {
                request.setProjetoId(grupo.getProjeto().getId());
            }

            if (grupo.getProfessor() != null) {
                request.setProfessorId(grupo.getProfessor().getId());
            }

            if (grupo.getAlunos() != null) {
                List<Long> alunosIds = grupo.getAlunos().stream()
                    .map(Aluno::getId)
                    .collect(Collectors.toList());
                request.setAlunosIds(alunosIds);
            }

            grupoRequests.add(request);
        }

        return grupoRequests;
    }
    
    @Override
    public GrupoRequest listarGrupoPorAlunoId(Long alunoId) {
        List<Grupo> gruposDisponiveis = grupoRepositorio.findByDisponivelTrue();

        for (Grupo grupo : gruposDisponiveis) {
            boolean alunoPresente = grupo.getAlunos().stream()
                .anyMatch(aluno -> aluno.getId().equals(alunoId));

            if (alunoPresente) {
                GrupoRequest request = new GrupoRequest();
                request.setId(grupo.getId());
                request.setNome(grupo.getNome());
                request.setDisponivel(grupo.isDisponivel());

                if (grupo.getProjeto() != null) {
                    request.setProjetoId(grupo.getProjeto().getId());
                }

                if (grupo.getProfessor() != null) {
                    request.setProfessorId(grupo.getProfessor().getId());
                }

                if (grupo.getAlunos() != null) {
                    List<Long> alunosIds = grupo.getAlunos().stream()
                        .map(Aluno::getId)
                        .collect(Collectors.toList());
                    request.setAlunosIds(alunosIds);
                }

                return request; 
            }
        }

        return null; 
    }

    @Override
    public void desativarGrupo(String nome) {
        Optional<Grupo> grupo = grupoRepositorio.findByNome(nome);
        if (grupo.isPresent()) {
            Grupo g = grupo.get();
            g.setDisponivel(false);
            grupoRepositorio.save(g);
        } else {
            throw new RuntimeException("Grupo não encontrado");
        }
    }

}
