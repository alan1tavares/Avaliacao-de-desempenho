package br.com.ads.simulacao;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import gerar.arquivo.Arquivo;
import gerar.variavel.aleatoria.GeradorVariavelAleatoria;

public class SimulacaoFilaBanco {

	// 2) Tempo desde a última chegada (minutos)
	private List<Double> tDeseAUltimaChegada;
	// Tempo de chegada no relógio
	private ArrayList<Double> tChegadaRelogio;
	// 4) Tempo de serviço ou tempo de atendimento (minutos)
	private List<Double> tServico;
	// 5) Tempo de início do serviço (minutos)
	private List<Double> tInicioServico;
	// 6) Tempo do cliente na fila do Banco (minutos)
	private List<Double> tNaFila;
	// 7) Tempo final do atendimento no relógio
	private List<Double> tFinalRelogio;
	// 8) Tempo do cliente no Banco (minutos), ou seja fila + atendimento
	private List<Double> tClienteNoBanco;
	// 9) Tempo livre do Caixa do banco ou tempo que o Caixa ficou ocupado
	// (minutos)
	private List<Double> tLivreDoCaixa;

	private int numeroDeClientes = 20;
	private final int MAXIMO_DE_CLIENTES = 20;

	// Tabela
	private List<List<Double>> tabela;

	/**
	 * Construtor a onde será chamado os métodos para fazer a simulação.
	 * 
	 * @param numeroDeClientes
	 *            Quantidades de clientes que será feita asimulação
	 */
	public SimulacaoFilaBanco(int numeroDeClientes) {
		if (numeroDeClientes <= 20)
			this.numeroDeClientes = numeroDeClientes;
		else
			System.out.println("Tem que ser no máximo 20");

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

		this.tabela.add(tDeseAUltimaChegada);
		this.tabela.add(tChegadaRelogio);
		this.tabela.add(tServico);
		this.tabela.add(tInicioServico);
		this.tabela.add(tNaFila);
		this.tabela.add(tFinalRelogio);
		this.tabela.add(tClienteNoBanco);
		this.tabela.add(tLivreDoCaixa);
	}

	public List<List<Double>> getTabela() {
		return tabela;
	}

	// Gera a 2a coluna - Tempo desde a Ãºltima chegada do pacote anterior
	// (microssegundos)
	private void gerarTDesdeUltimaChegadaPacote(double media) {

		GeradorVariavelAleatoria gva = new GeradorVariavelAleatoria();
		tDeseAUltimaChegada = new ArrayList<>(this.numeroDeClientes);

		for (int i = 0; i < this.numeroDeClientes; i++) {
			tDeseAUltimaChegada.add(gva.nextInverseTransformation(media));
		}

	}

	// Gera a 3a Coluna - Tempo de chegada no relÃ³gio
	private void gerarTChegadaRelogio() {
		tChegadaRelogio = new ArrayList<>(this.numeroDeClientes);

		tChegadaRelogio.add(tDeseAUltimaChegada.get(0));

		for (int i = 1; i < this.numeroDeClientes; i++) {
			tChegadaRelogio.add(tDeseAUltimaChegada.get(i) + tChegadaRelogio.get(i - 1));
		}
	}

	// Gera a 4a coluna - Tempo de serviÃ§o ou tempo de roteamento
	// (microssegundos)
	private void gerarTServico(double media, double desvioPadrao) {

		GeradorVariavelAleatoria gva = new GeradorVariavelAleatoria();
		tServico = new ArrayList<>(this.numeroDeClientes);

		for (int i = 0; i < this.numeroDeClientes; i++) {
			tServico.add(gva.nextRejectionMethod(media, desvioPadrao));
		}

	}

	// Gera a 5a coluna - Tempo de inÃ­cio do roteamento (microssegundos)
	private void gerarTInicioRoteamento() {
		tInicioServico = new ArrayList<>(this.numeroDeClientes);

		tInicioServico.add(tChegadaRelogio.get(0));

		for (int i = 1; i < this.numeroDeClientes; i++) {
			// Se a soma do tempo de serviÃ§o mais o tempo do inicio do
			// roteamento, anteriror, for <= ao tempo de chegada no relogio
			// entÃ£o
			if (tServico.get(i - 1) + tInicioServico.get(i - 1) <= tChegadaRelogio.get(i)) {
				// o tempo de inicio do roteamento Ã© igual ao tempo de chegada
				// do relogio
				tInicioServico.add(tChegadaRelogio.get(i));
			} else {
				// Se nÃ£o, tempo do inicio do roteamento Ã© a soma do tempo de
				// serviÃ§o anterior mais o tempo de inicio do roteamento
				// anterior
				tInicioServico.add(tServico.get(i - 1) + tInicioServico.get(i - 1));
			}

		}
	}

	// Gera a 6a coluna - Tempo do pacote na fila do roteador (microssegundos)
	private void gerarTPacoteFilaRoteador() {
		tNaFila = new ArrayList<>(this.numeroDeClientes);

		for (int i = 0; i < this.numeroDeClientes; i++) {
			// O tempo do pacote na fila do roteador = tempo de inicio do
			// roteamento - tempo de dchegada no relogio
			tNaFila.add(tInicioServico.get(i) - tChegadaRelogio.get(i));
		}
	}

	// Gera a 7a coluna - Tempo final do roteamento no relÃ³gio
	private void gerarTFinalRoteamentoRelogio() {
		tFinalRelogio = new ArrayList<>(this.numeroDeClientes);

		for (int i = 0; i < this.numeroDeClientes; i++) {
			// Tempo final do roteamento no relogio = tempo de inicio do
			// roteamento + tempo de serviÃ§o
			tFinalRelogio.add(tInicioServico.get(i) + tServico.get(i));

		}
	}

	// Gera a 8a coluna - 8) Tempo do pacote no roteador (microssegundos), ou
	// seja, fila + roteamento
	private void gerarTPacoteRoteador() {

		tClienteNoBanco = new ArrayList<>(this.numeroDeClientes);

		for (int i = 0; i < this.numeroDeClientes; i++) {
			// Tempo do pacote no roteador = Tempo do pacote na fila do roteador
			// + tempo de roteamento
			tClienteNoBanco.add(tNaFila.get(i) + tServico.get(i));
		}
	}

	// Gera a 9a coluna - Tempo livre do servidor do roteador ou tempo que o
	// servidor do roteador ficou ocupado (microssegundos)
	private void gerarTLivreRoteador() {
		tLivreDoCaixa = new ArrayList<>(this.numeroDeClientes);

		tLivreDoCaixa.add(tInicioServico.get(0));
		for (int i = 1; i < this.numeroDeClientes; i++) {
			// Tempo livre do servidor = Tempo de inÃ­cio do roteamento - Tempo
			// final do roteamento no relÃ³gio anterior
			tLivreDoCaixa.add(tInicioServico.get(i) - tFinalRelogio.get(i - 1));
		}
	}

	// Exibi alguma coisa
	public void exibir() {
		for (int i = 0; i < this.numeroDeClientes; i++) {
			DecimalFormat quatroCasas = new DecimalFormat("0.0000");
			System.out.print(i + "   ");
			System.out.print(quatroCasas.format(tDeseAUltimaChegada.get(i)) + "   ");
			System.out.print(quatroCasas.format(tChegadaRelogio.get(i)) + "   ");
			System.out.print(quatroCasas.format(tServico.get(i)) + "   ");
			System.out.print(quatroCasas.format(tInicioServico.get(i)) + "   ");
			System.out.print(quatroCasas.format(tNaFila.get(i)) + "   ");
			System.out.print(quatroCasas.format(tFinalRelogio.get(i)) + "   ");
			System.out.print(quatroCasas.format(tClienteNoBanco.get(i)) + "   ");
			System.out.print(quatroCasas.format(tLivreDoCaixa.get(i)) + "\n");

		}
	}

	public void gerarArquivo() throws IOException {
		Arquivo file = new Arquivo();
		DecimalFormat quatroCasas = new DecimalFormat("0.0000");

		file.escrverArquivo("No do pacote;" + "T desde a Ãºltima chegada do P anterior;" + "T de chegada no relÃ³gio;"
				+ "T de serviÃ§o;" + "T de inÃ­cio do roteamento;" + "T do P na fila do roteador;"
				+ "T final do roteamento no relÃ³gio;" + "T do P no roteador;" + "T livre do servidor do roteador\n");

		for (int i = 0; i < this.numeroDeClientes; i++) {

			file.escrverArquivo((i + 1 + ";"));
			file.escrverArquivo(quatroCasas.format(tDeseAUltimaChegada.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tChegadaRelogio.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tServico.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tInicioServico.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tNaFila.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tFinalRelogio.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tClienteNoBanco.get(i)) + ";");
			file.escrverArquivo(quatroCasas.format(tLivreDoCaixa.get(i)) + "\n");
		}
		file.fechar();
	}
}
