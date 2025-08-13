package br.com.alura.ScreenMatch.principal;

import br.com.alura.ScreenMatch.Models.DadosEpisodio;
import br.com.alura.ScreenMatch.Models.DadosSerie;
import br.com.alura.ScreenMatch.Models.DadosTemporada;
import br.com.alura.ScreenMatch.Models.Episodio;
import br.com.alura.ScreenMatch.Services.ComsumoAPI;
import br.com.alura.ScreenMatch.Services.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
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
//        System.out.println("Top 5 Episodios");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator
//                        .comparing(DadosEpisodio::avaliacao)
//                        .reversed())
//                .limit(5)
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                            .map(e -> new Episodio(t.numero(), e)))
                        .collect(Collectors.toList());
//        episodios.forEach(System.out::println);
//
//        System.out.println("A partir de que ano deseja ver os episodios? ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();


//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        LocalDate dataBusca = LocalDate.of(ano,1,1);
//        episodios.stream()
//                .filter(e ->e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> {
//                    System.out.println("Temporada:" + e.getTemporada() +
//                            "Episodio:" + e.getNumero() +
//                            "Data Lançamento:" + e.getDataLancamento().format(formatter));
//                });

//        System.out.println("Digite o nome do episodio que deseja encontrar:");
//        String trechoTitulo = leitura.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo()
//                        .toUpperCase()
//                        .contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episodio Encontrado!");
//            System.out.println(episodioBuscado.get().getTemporada());
//        }
//        else{
//            System.out.println("Episodio não Encontrado!");
//        }
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> !e.getAvaliacao().equals(0.0))
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor Episódio: " + est.getMax());
        System.out.println("Pior Episódio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());
    }
}
