package br.com.ads.simulacao;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import gerar.arquivo.Arquivo;

public class SimulacaoFilaBanco {

	// 2) Tempo desde a �ltima chegada (minutos)
	private List<Double> tDeseAUltimaChegada;
	// 3) Tempo de chegada no rel�gio
	private List<Double> tChegadaRelogio;
	// 4) Tempo de servi�o ou tempo de atendimento (minutos)
	private List<Double> tServico;
	// 5) Tempo de in�cio do servi�o (minutos)
	private List<Double> tInicioServico;
	// 6) Tempo do cliente na fila do Banco (minutos)
	private List<Double> tNaFila;
	// 7) Tempo final do atendimento no rel�gio
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
	 * Construtor a onde ser� chamado os m�todos para fazer a simula��o.
	 * 
	 * @param numeroDeClientes
	 *            Quantidades de clientes que ser� feita asimula��o
	 */
//	public SimulacaoFilaBanco(int numeroDeClientes) {
//		if (numeroDeClientes <= this. MAXIMO_DE_CLIENTES)
//			this.numeroDeClientes = numeroDeClientes;
//		else
//			System.out.println("Tem que ser no m�ximo 20");
//	}

	/**
	 * Deine os valores dos tempos da ultima chegada.
	 * 
	 * @param tDeseAUltimaChegada
	 *            Lista com os tempos dos tempos da �ltima chegada.
	 */
	public void settDeseAUltimaChegada(List<Double> tDeseAUltimaChegada) {
		if (tDeseAUltimaChegada != null && tDeseAUltimaChegada.size() <= this.MAXIMO_DE_CLIENTES)
			this.tDeseAUltimaChegada = tDeseAUltimaChegada;
		else
			System.out.println("Foi passada uma lista vazia ou o tamnho da lista � maior que 20.");
	}

	/**
	 * Define os valores dos tempos de servi�o.
	 * 
	 * @param tServico
	 *            Lista com os tempos de servi�o.
	 */
	public void settServico(List<Double> tServico) {
		if (tServico != null && tServico.size() <= this.MAXIMO_DE_CLIENTES)
			this.tServico = tServico;
		else
			System.out.println("Foi passada uma lista vazia ou o tamnho da lista � maior que 20.");
	}

	/**
	 * Faz a simula��o de acordo com os tempos.
	 * 
	 * @param tServico
	 *            Tempo de servi�o.
	 * @param tDeseAUltimaChegada
	 *            Tempo de desde a ultima chegada.
	 */
	public void gerarSimulacao(List<Double> tDeseAUltimaChegada, List<Double> tServico) {
		settDeseAUltimaChegada(tDeseAUltimaChegada);
		settServico(tServico);
		gerarSimulacao();
	}

	/**
	 * Faz a simula��o
	 */
	public void gerarSimulacao() {
		gerarTChegadaRelogio(); // 3a coluna
		gerarTInicioDoServi�o(); // 5a coluna
		gerarTDoClienteNaFila(); // 6a coluna
		gerarTFinalDoAtendimentoNoRelogio(); // 7a coluna
		gerarTDoClienteNoSitema(); // 8a coluna
		gerarTLivreDoCaixa(); // 9a Coluna
	}

	/**
	 * Gera o tempo de chegada no rel�gio que � a 3a coluna da tabela.
	 */
	private void gerarTChegadaRelogio() {
		this.tChegadaRelogio = new ArrayList<>();

		// O tempo desde a �ltima chegada no rel�gio do 1o cliente � igual ao
		// tempo de chegada no rel�gio do 1o cliente
		this.tChegadaRelogio.add(tDeseAUltimaChegada.get(0));

		// TCR_i = TCU_i + TCR_i-1
		for (int i = 1; i < this.tDeseAUltimaChegada.size(); i++) {
			this.tChegadaRelogio.add(tDeseAUltimaChegada.get(i) + tChegadaRelogio.get(i - 1));
		}
	}

	/**
	 * Gera o tempo de inicio do servi�o � a 5a coluna na tabela.
	 */
	private void gerarTInicioDoServi�o() {
		this.tInicioServico = new ArrayList<>();

		// O tempo de inicio de servi�o do 1o cliente � igual ao tempo de
		// chegada no rel�gio do 1o cliente.
		this.tInicioServico.add(this.tChegadaRelogio.get(0));

		for (int i = 1; i < this.tChegadaRelogio.size(); i++) {
			// Calcula o tempo de inicio do servi�o
			double tis = this.tInicioServico.get(i - 1) + this.tServico.get(i - 1);

			// Se o tempo de inicio do servi�o for menor que o tempo tempo de
			// chegada no rel�gio.
			if (tis <= tChegadaRelogio.get(i)) {
				// Ent�o o tempo de inicio do servi�o � igual ao tempo de
				// chegada no rel�gio.
				tInicioServico.add(tChegadaRelogio.get(i));
			} else {
				// Se n�o o tempo incio de servi�o � igual ao que foi calculado
				// na var�ivel tis.
				tInicioServico.add(tis);
			}
		}
	}

	/**
	 * Tempo do cliente na fila � a 6a coluna na tabela.
	 */
	private void gerarTDoClienteNaFila() {
		tNaFila = new ArrayList<>();

		for (int i = 0; i < this.tDeseAUltimaChegada.size(); i++) {
			// O tempo do pacote na fila do roteador = tempo de inicio do
			// roteamento - tempo de de chegada no relogio
			tNaFila.add(tInicioServico.get(i) - tChegadaRelogio.get(i));
		}
	}

	/**
	 * Gera o tempo final do atendimento no rel�gio, � a 7a coluna na tabela.
	 */
	private void gerarTFinalDoAtendimentoNoRelogio() {
		tFinalRelogio = new ArrayList<>();

		for (int i = 0; i < this.tDeseAUltimaChegada.size(); i++) {
			// Tempo final do atendimento no relogio = tempo de inicio do
			// servi�o + tempo de servi�o
			this.tFinalRelogio.add(this.tInicioServico.get(i) + this.tServico.get(i));
		}
	}

	/**
	 * Gera o tempo do cliente no banco, � a 8a coluna na tabela.
	 */
	private void gerarTDoClienteNoSitema() {

		tClienteNoBanco = new ArrayList<>();

		for (int i = 0; i < this.tDeseAUltimaChegada.size(); i++) {
			// Tempo do cliente no banco � igual o tempo de servi�o mais o tempo
			// do cliente na fila.
			this.tClienteNoBanco.add(tNaFila.get(i) + tServico.get(i));
		}
	}

	/**
	 * Gera Tempo livre do Caixa do banco ou tempo que o Caixa ficou ocupado, �
	 * 9a coluna da tabela.
	 */
	private void gerarTLivreDoCaixa() {
		this.tLivreDoCaixa = new ArrayList<>(this.numeroDeClientes);

		this.tLivreDoCaixa.add(tInicioServico.get(0));
		for (int i = 1; i < this.tDeseAUltimaChegada.size(); i++) {
			// Tempo livre � igual ao tempo do inicio do servi�o menos o tempo
			// final do servi�o do cliente anterior.
			this.tLivreDoCaixa.add(this.tInicioServico.get(i) - this.tFinalRelogio.get(i - 1));
		}
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
	
	// Exibi alguma coisa
	public void exibir() {
		System.out.println(this.tChegadaRelogio);
		for (int i = 0; i < this.tDeseAUltimaChegada.size(); i++) {
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
		DecimalFormat quatroCasas = new DecimalFormat("0.0");
		
		file.escrverArquivo("N�mero do cliente;" + "Tempo desde a ultima chegada;" + "Tempo de chegada no rel�gio;"
				+ "Tempo de servi�o;" + "Tempo de in�cio do servi�o;" + "Tempo do cliente na fila do Banco;"
				+ "Tempo final do atendimento rel�gio;" + "Tmpo do cliente no banco;" + "Tempo livre do caixa\n");

		for (int i = 0; i < this.tDeseAUltimaChegada.size(); i++) {

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

	public List<List<Double>> getTabela() {
		return tabela;
	}
}
