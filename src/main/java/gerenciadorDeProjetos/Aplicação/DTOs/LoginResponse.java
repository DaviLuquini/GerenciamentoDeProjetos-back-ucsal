package gerenciadorDeProjetos.Aplicação.DTOs;
public class LoginResponse {
    private String role;

	private String token;
    private Long id;

    public LoginResponse(String role, String token, Long id) {
        this.role = role;
        this.token = token;
        this.id = id;
    }

    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

