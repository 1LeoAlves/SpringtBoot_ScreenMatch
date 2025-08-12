package br.com.alura.ScreenMatch.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(
        @JsonAlias("Episodes") List<DadosEpisodio> episodios,
        @JsonAlias("Season") Integer numero){
}
