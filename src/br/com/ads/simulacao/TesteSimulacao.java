package br.com.ads.simulacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TesteSimulacao {

	public static void main(String[] args) throws IOException {
		SimulacaoFilaBanco simulacao = new SimulacaoFilaBanco();
		simulacao.settDeseAUltimaChegada(coluna2());
		simulacao.settServico(coluna4());
		simulacao.gerarSimulacao();
		simulacao.gerarArquivo();
		
	}
	
	private static List<Double> coluna2(){
		ArrayList<Double> lista = new ArrayList<>();
		lista.add(15.0);
		lista.add(12.0);
		lista.add(10.0);
		lista.add(10.0);
		lista.add(12.0);
		lista.add(15.0);
		lista.add(10.0);
		lista.add(12.0);
		lista.add(10.0);
		lista.add(10.0);
		lista.add(10.0);
		lista.add(12.0);
		lista.add(15.0);
		lista.add(12.0);
		lista.add(12.0);
		return lista;
	}
	
	private static List<Double> coluna4(){
		ArrayList<Double> lista = new ArrayList<>();
		lista.add(11.0);
		lista.add(10.0);
		lista.add(9.0);
		lista.add(10.0);
		lista.add(9.0);
		lista.add(10.0);
		lista.add(11.0);
		lista.add(9.0);
		lista.add(11.0);
		lista.add(10.0);
		lista.add(11.0);
		lista.add(9.0);
		lista.add(10.0);
		lista.add(9.0);
		lista.add(11.0);
		return lista;
	}

}
