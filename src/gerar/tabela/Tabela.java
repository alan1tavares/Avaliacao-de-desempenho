package gerar.tabela;

import java.sql.NClob;
import java.util.ArrayList;
import java.util.Iterator;

import gerar.variavel.aleatoria.GeradorVariavelAleatoria;

public class Tabela {

	// 2) Tempo desde a última chegada do pacote anterior (microssegundos)
	private ArrayList<Double> tDesdeUltimaChegadaPacote;
	// 3) Tempo de chegada no relógio
	private ArrayList<Double> tChegadaRelogio;
	// 4) Tempo de serviço ou tempo de roteamento (microssegundos)
	private ArrayList<Double> tServico;
	// 5) Tempo de início do roteamento (microssegundos)
	private ArrayList<Double> tInicioRoteamento;
	// 6) Tempo do pacote na fila do roteador (microssegundos)
	private ArrayList<Double> tPacoteFilaRoteador;
	// 7) Tempo final do roteamento no relógio
	private ArrayList<Double> tFinalRoteamentoRelogio;
	// 8) Tempo do pacote no roteador (microssegundos), ou seja, fila +
	// roteamento
	private ArrayList<Double> tPacoteRoteador;
	// 9) Tempo livre do servidor do roteador ou tempo que o servidor do
	// roteador ficou ocupado (microssegundos)
	private ArrayList<Double> tLivreRoteador;

	private int nPacotes = 20;

	public Tabela() {
		// Gerar 2a coluna
		gerarTDesdeUltimaChegadaPacote(12);
		// Gerar 3a coluna
		gerarTChegadaRelogio();
		// Gerar a 4a coluna
		gerarTServico(10, 1);
		// Gerar a 5a coluna
		gerarTInicioRoteamento();
		// Gerar a 6a coluna
		gerarTPacoteFilaRoteador();
		// Gerar a 7a coluna
		gerarTFinalRoteamentoRelogio();
	}

	// Gera a 2a coluna
	private void gerarTDesdeUltimaChegadaPacote(double media) {

		GeradorVariavelAleatoria gva = new GeradorVariavelAleatoria();
		tDesdeUltimaChegadaPacote = new ArrayList<>(this.nPacotes);

		for (int i = 0; i < this.nPacotes; i++) {
			tDesdeUltimaChegadaPacote.add(gva.nextInverseTransformation(media));
		}

	}

	// Gera a 3a Coluna
	private void gerarTChegadaRelogio() {
		tChegadaRelogio = new ArrayList<>(this.nPacotes);

		tChegadaRelogio.add(tDesdeUltimaChegadaPacote.get(0));

		for (int i = 1; i < this.nPacotes; i++) {
			tChegadaRelogio.add(tDesdeUltimaChegadaPacote.get(i) + tChegadaRelogio.get(i - 1));
		}
	}

	// Gera a 4a coluna
	private void gerarTServico(double media, double desvioPadrao) {

		GeradorVariavelAleatoria gva = new GeradorVariavelAleatoria();
		tServico = new ArrayList<>(this.nPacotes);

		for (int i = 0; i < this.nPacotes; i++) {
			tServico.add(gva.nextRejectionMethod(media, desvioPadrao));
		}

	}

	// Gera a 5a coluna
	private void gerarTInicioRoteamento() {
		tInicioRoteamento = new ArrayList<>(this.nPacotes);

		tInicioRoteamento.add(tChegadaRelogio.get(0));

		for (int i = 1; i < this.nPacotes; i++) {
			// Se a soma do tempo de serviço mais o tempo do inicio do
			// roteamento, anteriror, for <= ao tempo de chegada no relogio
			// então
			if (tServico.get(i - 1) + tInicioRoteamento.get(i - 1) <= tChegadaRelogio.get(i)) {
				// o tempo de inicio do roteamento é igual ao tempo de chegada
				// do relogio
				tInicioRoteamento.add(tChegadaRelogio.get(i));
			} else {
				// Se não, tempo do inicio do roteamento é a soma do tempo de
				// serviço anterior mais o tempo de inicio do roteamento
				// anterior
				tInicioRoteamento.add(tServico.get(i - 1) + tInicioRoteamento.get(i - 1));
			}

		}
	}

	// Gera a 6a coluna
	private void gerarTPacoteFilaRoteador() {
		tPacoteFilaRoteador = new ArrayList<>(this.nPacotes);

		for (int i = 0; i < this.nPacotes; i++) {
			// O tempo do pacote na fila do roteador = tempod de inicio do
			// roteamento - tempo do serviço
			tPacoteFilaRoteador.add(tInicioRoteamento.get(i) - tChegadaRelogio.get(i));
		}
	}

	// Gerar a 7a coluna
	private void gerarTFinalRoteamentoRelogio() {
		tFinalRoteamentoRelogio = new ArrayList<>(this.nPacotes);

		for (int i = 0; i < this.nPacotes; i++) {
			// Tempo final do roteamento no relogio = tempo de inicio do
			// roteamento + tempo de serviço
			tFinalRoteamentoRelogio.add(tInicioRoteamento.get(i) + tServico.get(i));

		}
	}

	// Exibi alguma coisa
	public void exibir() {
		for (int i = 0; i < this.nPacotes; i++) {
			System.out.print(i + "   ");
			System.out.print(tDesdeUltimaChegadaPacote.get(i) + "   ");
			System.out.print(tChegadaRelogio.get(i) + "   ");
			System.out.print(tServico.get(i) + "   ");
			System.out.print(tInicioRoteamento.get(i) + "   ");
			System.out.print(tPacoteFilaRoteador.get(i) + "   ");
			System.out.print(tFinalRoteamentoRelogio.get(i) + "\n");

		}
	}
}
