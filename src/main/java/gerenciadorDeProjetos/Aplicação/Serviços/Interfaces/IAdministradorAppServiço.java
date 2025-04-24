package gerenciadorDeProjetos.Aplicação.Serviços.Interfaces;



import gerenciadorDeProjetos.Dominio.Administrador;

public interface IAdministradorAppServiço {
	Long buscarEmail(String email);
    Administrador registrar(String nome, String email, String senha);
    boolean logar(String email, String senha);
}
