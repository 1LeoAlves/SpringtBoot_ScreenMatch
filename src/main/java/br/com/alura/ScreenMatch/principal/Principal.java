package br.com.alura.ScreenMatch.principal;

import br.com.alura.ScreenMatch.Models.DadosEpisodio;
import br.com.alura.ScreenMatch.Models.DadosSerie;
import br.com.alura.ScreenMatch.Models.DadosTemporada;
import br.com.alura.ScreenMatch.Models.Episodio;
import br.com.alura.ScreenMatch.Services.ComsumoAPI;
import br.com.alura.ScreenMatch.Services.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final String PATH = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=96e5f722";
    private ComsumoAPI consumoAPI = new ComsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibirMenu(){
        Scanner leitura = new Scanner(System.in);
        System.out.println("Digite o nome da série: ");
        var nomeSerie = leitura.nextLine();
        String endpoint = PATH + nomeSerie.replace(" ", "+") + API_KEY;

        consumoAPI = new ComsumoAPI();
        var json = consumoAPI.obterDados(endpoint);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        json = consumoAPI.obterDados(endpoint);
        DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            json = consumoAPI.obterDados(endpoint + "&season=" + i);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        List<DadosEpisodio> dadosEpisodios= temporadas.stream()
                .flatMap(t -> t
                        .episodios()
                        .stream())
                        .toList();
        System.out.println("Top 5 Episodios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator
                        .comparing(DadosEpisodio::avaliacao)
                        .reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                            .map(e -> new Episodio(t.numero(), e)))
                        .collect(Collectors.toList());
        episodios.forEach(System.out::println);
    }
}
