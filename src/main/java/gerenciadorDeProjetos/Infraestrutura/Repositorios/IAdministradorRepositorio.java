package gerenciadorDeProjetos.Infraestrutura.Repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gerenciadorDeProjetos.Dominio.Administrador;

@Repository
public interface IAdministradorRepositorio extends JpaRepository<Administrador, Long> {

    Optional<Administrador> findByEmailAndSenha(String email, String senha);
    
    Optional<Administrador> findByEmail(String email);
}
