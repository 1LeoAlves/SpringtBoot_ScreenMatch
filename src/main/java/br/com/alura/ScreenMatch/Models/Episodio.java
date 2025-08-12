package br.com.alura.ScreenMatch.Models;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numero;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio){
        this.numero = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.temporada = numeroTemporada;
        try {
            this.dataLancamento =  LocalDate.parse(dadosEpisodio.dataLancamento());
        } catch (DateTimeException e){
            this.dataLancamento = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    @Override
    public String toString() {
        return "Episodio{" +
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numero +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento +
                '}';
    }
}