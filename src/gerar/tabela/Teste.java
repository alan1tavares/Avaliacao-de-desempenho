package gerar.tabela;

import java.io.IOException;

public class Teste {
	public static void main(String[] args) throws IOException {
		Tabela t = new Tabela();
		t.exibir();
		t.gerarArquivo();
	}
}
