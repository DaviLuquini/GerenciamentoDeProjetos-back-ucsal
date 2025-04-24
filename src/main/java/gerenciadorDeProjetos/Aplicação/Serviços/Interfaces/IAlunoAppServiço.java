package gerenciadorDeProjetos.Aplicação.Serviços.Interfaces;

import gerenciadorDeProjetos.Aplicação.DTOs.AlunoRequest;
import gerenciadorDeProjetos.Dominio.Aluno;

public interface IAlunoAppServiço {
	Long buscarEmail(String email);
    Aluno cadastrarAluno(AlunoRequest request);
    public boolean logar(String email, String senha);
}
