package gerar.tabela;

import java.io.IOException;
import java.sql.NClob;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import gerar.arquivo.Arquivo;
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

	// Tabela
	private ArrayList<ArrayList<Double>> tabela;

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
		// Gerar a 8a coluna
		gerarTPacoteRoteador();
		// Gerar a 9a coluna
		gerarTLivreRoteador();

	}

	private void gerarTabela() {
		tabela = new ArrayList<>();

		this.tabela.add(tDesdeUltimaChegadaPacote);
		this.tabela.add(tChegadaRelogio);
		this.tabela.add(tServico);
		this.tabela.add(tInicioRoteamento);
		this.tabela.add(tPacoteFilaRoteador);
		this.tabela.add(tFinalRoteamentoRelogio);
		this.tabela.add(tPacoteRoteador);
		this.tabela.add(tLivreRoteador);
	}

	public ArrayList<ArrayList<Double>> getTabela() {
		return tabela;
	}

	// Gera a 2a coluna - Tempo desde a última chegada do pacote anterior
	// (microssegundos)
	private void gerarTDesdeUltimaChegadaPacote(double media) {

		GeradorVariavelAleatoria gva = new GeradorVariavelAleatoria();
		tDesdeUltimaChegadaPacote = new ArrayList<>(this.nPacotes);

		for (int i = 0; i < this.nPacotes; i++) {
			tDesdeUltimaChegadaPacote.add(gva.nextInverseTransformation(media));
		}

	}

	// Gera a 3a Coluna - Tempo de chegada no relógio
	private void gerarTChegadaRelogio() {
		tChegadaRelogio = new ArrayList<>(this.nPacotes);

		tChegadaRelogio.add(tDesdeUltimaChegadaPacote.get(0));

		for (int i = 1; i < this.nPacotes; i++) {
			tChegadaRelogio.add(tDesdeUltimaChegadaPacote.get(i) + tChegadaRelogio.get(i - 1));
		}
	}

	// Gera a 4a coluna - Tempo de serviço ou tempo de roteamento
	// (microssegundos)
	private void gerarTServico(double media, double desvioPadrao) {

		GeradorVariavelAleatoria gva = new GeradorVariavelAleatoria();
		tServico = new ArrayList<>(this.nPacotes);

		for (int i = 0; i < this.nPacotes; i++) {
			tServico.add(gva.nextRejectionMethod(media, desvioPadrao));
		}

	}

	// Gera a 5a coluna - Tempo de início do roteamento (microssegundos)
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

	// Gera a 6a coluna - Tempo do pacote na fila do roteador (microssegundos)
	private void gerarTPacoteFilaRoteador() {
		tPacoteFilaRoteador = new ArrayList<>(this.nPacotes);

		for (int i = 0; i < this.nPacotes; i++) {
			// O tempo do pacote na fila do roteador = tempo de inicio do
			// roteamento - tempo de dchegada no relogio
			tPacoteFilaRoteador.add(tInicioRoteamento.get(i) - tChegadaRelogio.get(i));
		}
	}

	// Gera a 7a coluna - Tempo final do roteamento no relógio
	private void gerarTFinalRoteamentoRelogio() {
		tFinalRoteamentoRelogio = new ArrayList<>(this.nPacotes);

		for (int i = 0; i < this.nPacotes; i++) {
			// Tempo final do roteamento no relogio = tempo de inicio do
			// roteamento + tempo de serviço
			tFinalRoteamentoRelogio.add(tInicioRoteamento.get(i) + tServico.get(i));

		}
	}

	// Gera a 8a coluna - 8) Tempo do pacote no roteador (microssegundos), ou
	// seja, fila + roteamento
	private void gerarTPacoteRoteador() {

		tPacoteRoteador = new ArrayList<>(this.nPacotes);

		for (int i = 0; i < this.nPacotes; i++) {
			// Tempo do pacote no roteador = Tempo do pacote na fila do roteador
			// + tempo de roteamento
			tPacoteRoteador.add(tPacoteFilaRoteador.get(i) + tServico.get(i));
		}
	}

	// Gera a 9a coluna - Tempo livre do servidor do roteador ou tempo que o
	// servidor do roteador ficou ocupado (microssegundos)
	private void gerarTLivreRoteador() {
		tLivreRoteador = new ArrayList<>(this.nPacotes);

		tLivreRoteador.add(tInicioRoteamento.get(0));
		for (int i = 1; i < this.nPacotes; i++) {
			// Tempo livre do servidor = Tempo de início do roteamento - Tempo
			// final do roteamento no relógio anterior
			tLivreRoteador.add(tInicioRoteamento.get(i) - tFinalRoteamentoRelogio.get(i - 1));
		}
	}

	// Exibi alguma coisa
	public void exibir() {
		for (int i = 0; i < this.nPacotes; i++) {
			DecimalFormat quatroCasas = new DecimalFormat("0.0000");
			System.out.print(i + "   ");
			System.out.print(quatroCasas.format(tDesdeUltimaChegadaPacote.get(i)) + "   ");
			System.out.print(quatroCasas.format(tChegadaRelogio.get(i)) + "   ");
			System.out.print(quatroCasas.format(tServico.get(i)) + "   ");
			System.out.print(quatroCasas.format(tInicioRoteamento.get(i)) + "   ");
			System.out.print(quatroCasas.format(tPacoteFilaRoteador.get(i)) + "   ");
			System.out.print(quatroCasas.format(tFinalRoteamentoRelogio.get(i)) + "   ");
			System.out.print(quatroCasas.format(tPacoteRoteador.get(i)) + "   ");
			System.out.print(quatroCasas.format(tLivreRoteador.get(i)) + "\n");

		}
	}

	public void gerarArquivo() throws IOException {
		Arquivo file = new Arquivo();
		DecimalFormat quatroCasas = new DecimalFormat("0.0000");

		file.escrverArquivo("No do pacote;" + "T desde a última chegada do P anterior;" + "T de chegada no relógio;"
				+ "T de serviço;" + "T de início do roteamento;" + "T do P na fila do roteador;"
				+ "T final do roteamento no relógio;" + "T do P no roteador;" + "T livre do servidor do roteador\n");

		for (int i = 0; i < this.nPacotes; i++) {

			file.escrverArquivo((i + 1 + ";"));
			file.escrverArquivo(quatroCasas.format(tDesdeUltimaChegadaPacote.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tChegadaRelogio.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tServico.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tInicioRoteamento.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tPacoteFilaRoteador.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tFinalRoteamentoRelogio.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tPacoteRoteador.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tLivreRoteador.get(i)) + "\n");
		}
		file.fechar();
	}
}
