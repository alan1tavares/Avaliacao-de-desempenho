package br.com.ads.simulacao;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import gerar.arquivo.Arquivo;

public class SimulacaoFilaBanco {

	// 2) Tempo desde a última chegada (minutos)
	private List<Double> tDeseAUltimaChegada;
	// 3) Tempo de chegada no relógio
	private List<Double> tChegadaRelogio;
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
//	public SimulacaoFilaBanco(int numeroDeClientes) {
//		if (numeroDeClientes <= this. MAXIMO_DE_CLIENTES)
//			this.numeroDeClientes = numeroDeClientes;
//		else
//			System.out.println("Tem que ser no máximo 20");
//	}

	/**
	 * Deine os valores dos tempos da ultima chegada.
	 * 
	 * @param tDeseAUltimaChegada
	 *            Lista com os tempos dos tempos da última chegada.
	 */
	public void settDeseAUltimaChegada(List<Double> tDeseAUltimaChegada) {
		if (tDeseAUltimaChegada != null && tDeseAUltimaChegada.size() <= this.MAXIMO_DE_CLIENTES)
			this.tDeseAUltimaChegada = tDeseAUltimaChegada;
		else
			System.out.println("Foi passada uma lista vazia ou o tamnho da lista é maior que 20.");
	}

	/**
	 * Define os valores dos tempos de serviço.
	 * 
	 * @param tServico
	 *            Lista com os tempos de serviço.
	 */
	public void settServico(List<Double> tServico) {
		if (tServico != null && tServico.size() <= this.MAXIMO_DE_CLIENTES)
			this.tServico = tServico;
		else
			System.out.println("Foi passada uma lista vazia ou o tamnho da lista é maior que 20.");
	}

	/**
	 * Faz a simulação de acordo com os tempos.
	 * 
	 * @param tServico
	 *            Tempo de serviço.
	 * @param tDeseAUltimaChegada
	 *            Tempo de desde a ultima chegada.
	 */
	public void gerarSimulacao(List<Double> tDeseAUltimaChegada, List<Double> tServico) {
		settDeseAUltimaChegada(tDeseAUltimaChegada);
		settServico(tServico);
		gerarSimulacao();
	}

	/**
	 * Faz a simulação
	 */
	public void gerarSimulacao() {
		gerarTChegadaRelogio(); // 3a coluna
		gerarTInicioDoServiço(); // 5a coluna
		gerarTDoClienteNaFila(); // 6a coluna
		gerarTFinalDoAtendimentoNoRelogio(); // 7a coluna
		gerarTDoClienteNoSitema(); // 8a coluna
		gerarTLivreDoCaixa(); // 9a Coluna
	}

	/**
	 * Gera o tempo de chegada no relógio que é a 3a coluna da tabela.
	 */
	private void gerarTChegadaRelogio() {
		this.tChegadaRelogio = new ArrayList<>();

		// O tempo desde a última chegada no relógio do 1o cliente é igual ao
		// tempo de chegada no relógio do 1o cliente
		this.tChegadaRelogio.add(tDeseAUltimaChegada.get(0));

		// TCR_i = TCU_i + TCR_i-1
		for (int i = 1; i < this.tDeseAUltimaChegada.size(); i++) {
			this.tChegadaRelogio.add(tDeseAUltimaChegada.get(i) + tChegadaRelogio.get(i - 1));
		}
	}

	/**
	 * Gera o tempo de inicio do serviço é a 5a coluna na tabela.
	 */
	private void gerarTInicioDoServiço() {
		this.tInicioServico = new ArrayList<>();

		// O tempo de inicio de serviço do 1o cliente é igual ao tempo de
		// chegada no relógio do 1o cliente.
		this.tInicioServico.add(this.tChegadaRelogio.get(0));

		for (int i = 1; i < this.tChegadaRelogio.size(); i++) {
			// Calcula o tempo de inicio do serviço
			double tis = this.tInicioServico.get(i - 1) + this.tServico.get(i - 1);

			// Se o tempo de inicio do serviço for menor que o tempo tempo de
			// chegada no relógio.
			if (tis <= tChegadaRelogio.get(i)) {
				// Então o tempo de inicio do serviço é igual ao tempo de
				// chegada no relógio.
				tInicioServico.add(tChegadaRelogio.get(i));
			} else {
				// Se não o tempo incio de serviço é igual ao que foi calculado
				// na varáivel tis.
				tInicioServico.add(tis);
			}
		}
	}

	/**
	 * Tempo do cliente na fila é a 6a coluna na tabela.
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
	 * Gera o tempo final do atendimento no relógio, é a 7a coluna na tabela.
	 */
	private void gerarTFinalDoAtendimentoNoRelogio() {
		tFinalRelogio = new ArrayList<>();

		for (int i = 0; i < this.tDeseAUltimaChegada.size(); i++) {
			// Tempo final do atendimento no relogio = tempo de inicio do
			// serviço + tempo de serviço
			this.tFinalRelogio.add(this.tInicioServico.get(i) + this.tServico.get(i));
		}
	}

	/**
	 * Gera o tempo do cliente no banco, é a 8a coluna na tabela.
	 */
	private void gerarTDoClienteNoSitema() {

		tClienteNoBanco = new ArrayList<>();

		for (int i = 0; i < this.tDeseAUltimaChegada.size(); i++) {
			// Tempo do cliente no banco é igual o tempo de serviço mais o tempo
			// do cliente na fila.
			this.tClienteNoBanco.add(tNaFila.get(i) + tServico.get(i));
		}
	}

	/**
	 * Gera Tempo livre do Caixa do banco ou tempo que o Caixa ficou ocupado, é
	 * 9a coluna da tabela.
	 */
	private void gerarTLivreDoCaixa() {
		this.tLivreDoCaixa = new ArrayList<>(this.numeroDeClientes);

		this.tLivreDoCaixa.add(tInicioServico.get(0));
		for (int i = 1; i < this.tDeseAUltimaChegada.size(); i++) {
			// Tempo livre é igual ao tempo do inicio do serviço menos o tempo
			// final do serviço do cliente anterior.
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
		
		file.escrverArquivo("Número do cliente;" + "Tempo desde a ultima chegada;" + "Tempo de chegada no relógio;"
				+ "Tempo de serviço;" + "Tempo de início do serviço;" + "Tempo do cliente na fila do Banco;"
				+ "Tempo final do atendimento relógio;" + "Tmpo do cliente no banco;" + "Tempo livre do caixa\n");

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
