package gerenciadorDeProjetos.Aplicação.DTOs;

import java.util.Date;

import gerenciadorDeProjetos.Dominio.Status;

public class ProjetoRequest {
	private Long id;
    private String nome;
    private String objetivo;
    private Date dataInicio;
    private String escopo;
    private String publicoAlvo;
    private Status status;
    private Long professorId;
    private Long grupoId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

    public String getEscopo() { return escopo; }
    public void setEscopo(String escopo) { this.escopo = escopo; }

    public String getPublicoAlvo() { return publicoAlvo; }
    public void setPublicoAlvo(String publicoAlvo) { this.publicoAlvo = publicoAlvo; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Long getProfessorId() { return professorId; }
    public void setProfessorId(Long professorId) { this.professorId = professorId; }

    public Long getGrupoId() { return grupoId; }
    public void setGrupoId(Long grupoId) { this.grupoId = grupoId; }
}
