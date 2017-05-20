package gerar.tabela;

import java.io.IOException;

import br.com.ads.simulacao.SimulacaoFilaBanco;

public class Teste {
	public static void main(String[] args) throws IOException {
		SimulacaoFilaBanco t = new SimulacaoFilaBanco();
		t.exibir();
		t.gerarArquivo();
	}
}
