package gerenciadorDeProjetos.Dominio;


import jakarta.persistence.*;

@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "grupo_id") 
    private Grupo grupo;

	private String email;
    
	private String nome;

	private String senha;

    private String curso;
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    public Long getId() {
  		return id;
  	}

  	public void setId(Long id) {
  		this.id = id;
  	}

  	public Grupo getGrupo() {
  		return grupo;
  	}

  	public void setGrupo(Grupo grupo) {
  		this.grupo = grupo;
  	}

  	public String getNome() {
  		return nome;
  	}

  	public void setNome(String nome) {
  		this.nome = nome;
  	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
  	
  	public String getCurso() {
  		return curso;
  	}

  	public void setCurso(String curso) {
  		this.curso = curso;
  	}
}