package gerenciadorDeProjetos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "gerenciadorDeProjetos")
public class GerenciadorDeProjetosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorDeProjetosApplication.class, args);
	}

}
