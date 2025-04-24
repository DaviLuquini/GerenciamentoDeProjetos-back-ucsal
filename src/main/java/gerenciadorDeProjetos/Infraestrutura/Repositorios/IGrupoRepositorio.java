package gerenciadorDeProjetos.Infraestrutura.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gerenciadorDeProjetos.Dominio.Grupo;

public interface IGrupoRepositorio extends JpaRepository<Grupo, Long> {
    
	Optional<Grupo> findByNome(String nome);
	List<Grupo> findByDisponivelTrue();
}