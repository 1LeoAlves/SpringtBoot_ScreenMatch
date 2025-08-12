package br.com.alura.ScreenMatch.Services;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classType);
}
