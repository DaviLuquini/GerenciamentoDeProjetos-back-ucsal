package gerenciadorDeProjetos.Aplicação.Serviços.Interfaces;

import java.util.List;

import gerenciadorDeProjetos.Aplicação.DTOs.AtualizarProjetoRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.ProjetoRequest;
import gerenciadorDeProjetos.Dominio.Projeto;

public interface IProjetoAppServiço {
	public List<ProjetoRequest> listarProjetos();
	public List<ProjetoRequest> listarProjetosPorProfessorId(Long professorId);
	public List<ProjetoRequest> listarProjetosPorAlunoId(Long alunoId);
	public Projeto atualizarProjeto(AtualizarProjetoRequest request);
	public void confirmarEntrega(Long Id);
	boolean projetoNomeEmUso(String nome);
	void deletarProjeto(String nome);
}
