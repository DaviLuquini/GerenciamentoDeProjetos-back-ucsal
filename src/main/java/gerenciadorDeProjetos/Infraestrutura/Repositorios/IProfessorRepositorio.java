package gerenciadorDeProjetos.Infraestrutura.Repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gerenciadorDeProjetos.Dominio.Professor;

public interface IProfessorRepositorio extends JpaRepository<Professor, Long> {
	Optional<Professor> findByEmailAndSenha(String email, String senha);
	   
	Optional<Professor> findByEmail(String email);
}