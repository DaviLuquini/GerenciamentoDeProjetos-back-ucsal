package gerenciadorDeProjetos.Aplicação.Serviços;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IAdministradorAppServiço;
import gerenciadorDeProjetos.Dominio.Administrador;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IAdministradorRepositorio;

@Service
public class AdministradorAppServiço implements IAdministradorAppServiço {

    @Autowired
    private IAdministradorRepositorio administradorRepo;

    @Override
    public Long buscarEmail(String email) {
    	  return administradorRepo.findByEmail(email)
    	            .map(Administrador::getId)
    	            .orElseThrow(() -> new RuntimeException("Administrador não encontrado com o e-mail: " + email));
    }

    @Override
    public Administrador registrar(String nome, String email, String senha) {
        Administrador novoAdmin = new Administrador();
        novoAdmin.setNome(nome);
        novoAdmin.setEmail(email);
        novoAdmin.setSenha(senha); 

        return administradorRepo.save(novoAdmin);
    }
    
    public boolean logar(String email, String senha) {
    	Optional<Administrador> adm = administradorRepo.findByEmailAndSenha(email, senha);
    	
        if (!adm.isPresent()) {
            return false;
        }
    	
        return true;
    }
}
