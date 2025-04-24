package gerenciadorDeProjetos.Infraestrutura.Repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gerenciadorDeProjetos.Dominio.Aluno;

public interface IAlunoRepositorio extends JpaRepository<Aluno, Long> {
	Optional<Aluno> findByEmailAndSenha(String email, String senha);
	Optional<Aluno> findByEmail(String email);
}
