package gerenciadorDeProjetos.Aplicação.Serviços.Interfaces;



import java.util.List;

import gerenciadorDeProjetos.Aplicação.DTOs.ProfessorRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.ProjetoRequest;
import gerenciadorDeProjetos.Dominio.Professor;
import gerenciadorDeProjetos.Dominio.Projeto;

public interface IProfessorAppServiço {
	Long buscarEmail(String email);
	public List<Professor> listarProfessores();
    Professor cadastrarProfessor(ProfessorRequest request);
    boolean logar(String email, String senha);
    Projeto solicitarProjeto(ProjetoRequest projetoRequest);
    void deletarProfessor(String email);
}
