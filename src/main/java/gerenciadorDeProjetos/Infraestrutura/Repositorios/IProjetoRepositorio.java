package gerenciadorDeProjetos.Infraestrutura.Repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gerenciadorDeProjetos.Dominio.Projeto;


public interface IProjetoRepositorio extends JpaRepository<Projeto, Long> {
	boolean existsByNome(String nome);
	Optional<Projeto> findByNome(String nome);
}