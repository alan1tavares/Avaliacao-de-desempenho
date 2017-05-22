package br.com.ads.main;

import java.io.IOException;
import java.util.ArrayList;

import br.com.ads.gva.SorteioComProbabilidadesDiferentes;
import br.com.ads.simulacao.SimulacaoFilaBanco;

public class MainA {

	public static void main(String[] args) throws IOException {
		SorteioComProbabilidadesDiferentes gvaColuna2 = new SorteioComProbabilidadesDiferentes();
		gvaColuna2.valoresEsuasProbabilidades(new double[] { 10.0, 12.0, 14.0 }, new double[] { 0.35, 0.40, 0.25 });
		
		SorteioComProbabilidadesDiferentes gvaColuna4 = new SorteioComProbabilidadesDiferentes();
		gvaColuna4.valoresEsuasProbabilidades(new double[] { 9.0, 10.0, 11.0}, new double[] { 0.30, 0.50, 0.20 });
		
		ArrayList<Double> coluna2 = new ArrayList<>();
		ArrayList<Double> coluna4 = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			coluna2.add(gvaColuna2.gerarVariavel());
			coluna4.add(gvaColuna4.gerarVariavel());
		}
		
		SimulacaoFilaBanco simulacaoFilaBanco = new SimulacaoFilaBanco();
		simulacaoFilaBanco.settDeseAUltimaChegada(coluna2);
		simulacaoFilaBanco.settServico(coluna4);
		simulacaoFilaBanco.gerarSimulacao();
		
		simulacaoFilaBanco.gerarArquivo();

	}

}
