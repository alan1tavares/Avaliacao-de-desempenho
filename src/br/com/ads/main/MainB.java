package br.com.ads.main;

import java.io.IOException;
import java.util.ArrayList;

import br.com.ads.gva.MonteCarlo;
import br.com.ads.simulacao.SimulacaoFilaBanco;

public class MainB {

	public static void main(String[] args) throws IOException {
		String[] classes2 = { "0 -> 5", "5 -> 10", "10 -> 15", "15 -> 20", "20 -> 25", "25 -> 30", "30 -> 35",
				"35 -> 40", "40 -> 45" };
		double[] frequencias2 = { 0.35, 0.19, 0.19, 0.13, 0.03, 0.07, 0.01, 0.02, 0.01 };

		String[] classe4 = { "9.0 -> 9.55", "9.55 -> 10.10", "10.10 -> 10.65", "10.65 -> 11.20", "11.20 -> 11.75",
				"11.75 -> 11.30", "12.30 -> 12.85", "12.85 -> 13.40", "13.40 -> 13.95", "13.95 -> 14.50" };
		double[] frequencias4 = {0.06, 0.05, 0.23, 0.20, 0.21, 0.12, 0.09, 0.01, 0.02, 0.01};

		MonteCarlo monteCarloColuna2 = new MonteCarlo();
		monteCarloColuna2.classeFrequencias(classes2, frequencias2);
		
		MonteCarlo monteCarloColuna4 = new MonteCarlo();
		monteCarloColuna4.classeFrequencias(classe4, frequencias4);

		ArrayList<Double> coluna22 = new ArrayList<>();
		ArrayList<Double> coluna44 = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			coluna22.add(monteCarloColuna2.gerarVariavel());
			coluna44.add(monteCarloColuna4.gerarVariavel());
		}

		 SimulacaoFilaBanco simulacaoFilaBanco = new SimulacaoFilaBanco();
		 simulacaoFilaBanco.settDeseAUltimaChegada(coluna22);
		 simulacaoFilaBanco.settServico(coluna44);
		 simulacaoFilaBanco.gerarSimulacao();
		
		 simulacaoFilaBanco.gerarArquivo();

	}

}
