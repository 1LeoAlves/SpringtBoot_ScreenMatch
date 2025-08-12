package br.com.alura.ScreenMatch;

import br.com.alura.ScreenMatch.Models.DadosEpisodio;
import br.com.alura.ScreenMatch.Models.DadosSerie;
import br.com.alura.ScreenMatch.Services.ComsumoAPI;
import br.com.alura.ScreenMatch.Services.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var comsumoApi = new ComsumoAPI();
		var json = comsumoApi.obterDados("http://www.omdbapi.com/?apikey=96e5f722&t=Gilmore+Girls");

		ConverteDados conversor = new ConverteDados();
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);

		json = comsumoApi.obterDados("http://www.omdbapi.com/?apikey=96e5f722&t=Gilmore+Girls");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);
	}
}
