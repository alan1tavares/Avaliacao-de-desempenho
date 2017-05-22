package br.com.ads.gva;

import java.util.Arrays;
import java.util.Random;

public class SorteioComProbabilidadesDiferentes implements GeradorVariavelAleatorio {

	private double[] probabilidades;
	private double[] valores;
	private double[] probabilidadeAcumulada;

	@Override
	public double gerarVariavel() {
		if (this.probabilidades != null && this.valores != null) {
			// Gera um valor ramdomico
			double random = new Random().nextDouble();
			
			for (int indice = 0; indice < this.probabilidadeAcumulada.length; indice++)
				if (random < this.probabilidadeAcumulada[indice])
					return (double) this.valores[indice];
		} else {
			System.out.println("Defina os valores e as probabilidades antes");
			return -1.0;
		}
		System.out.println("Erro ao gerar a variável aleatória");
		return -1.0;
	}

	public void valoresEsuasProbabilidades(double[] valores, double[] probabilidades) {
		// Se as duas listas não forem iguais retorna uma menssagem de erro.
		if (valores.length != probabilidades.length) {
			System.out.println("O tamanho da lista de valores é difenrente do tamnho da lista de probabilidades");
			return;
		}

		// Se a soma das probabilidades não der um retorna um menssagem de erro
		double somaDasProbabilidades = 0;
		for (Double d : probabilidades)
			somaDasProbabilidades += d;
		if (somaDasProbabilidades != 1) {
			System.out.println("A soma das probabilidades não da 1");
			return;
		}

		this.probabilidades = probabilidades;
		this.valores = valores;
		gerarProbabilidadeAcumulada();
	}

	/**
	 * Gera as probabilidades acumuladas.
	 * 
	 */
	private void gerarProbabilidadeAcumulada() {
		Arrays.sort(this.probabilidades);
		double[] probabilidadeAcumulada = new double[this.probabilidades.length];

		probabilidadeAcumulada[0] = this.probabilidades[this.probabilidades.length - 1];
		for (int i = this.probabilidades.length - 2, j = 1; i >= 0; i--, j++) {
			probabilidadeAcumulada[j] = this.probabilidades[i] + probabilidadeAcumulada[j-1];
		}
		this.probabilidadeAcumulada = probabilidadeAcumulada;
	}

}
