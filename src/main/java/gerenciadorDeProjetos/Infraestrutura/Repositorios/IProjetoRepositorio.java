package gerenciadorDeProjetos.Infraestrutura.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import gerenciadorDeProjetos.Dominio.Projeto;


public interface IProjetoRepositorio extends JpaRepository<Projeto, Long> {
	boolean existsByNome(String nome);
}