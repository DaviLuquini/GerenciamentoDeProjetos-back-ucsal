package gerenciadorDeProjetos.Aplicação.Serviços.Interfaces;

import java.util.List;

import gerenciadorDeProjetos.Aplicação.DTOs.ProjetoRequest;
import gerenciadorDeProjetos.Dominio.Projeto;

public interface IProjetoAppServiço {
	public List<ProjetoRequest> listarProjetos();
	public List<ProjetoRequest> listarProjetosPorId(Long professorId);
	public Projeto atualizarProjeto(ProjetoRequest request);
	public void confirmarEntrega(Long Id);
	boolean projetoNomeEmUso(String nome);
}
