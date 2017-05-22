package br.com.ads.gva;

public class DistribuicaoDeFrequencia {
	private double[] a;
	private double[] b;
	private double[] freqencia;

	public boolean adicionarClasseFrequencias(String[] classe, double[] frequencia) {
		// Verifica se n�o apresenta algum erro as entradas
		if (classe == null && frequencia == null) {
			System.out.println("Classe ou frequencia est� nula");
			return false;
		} else if (classe.length != frequencia.length) {
			System.out.println("Classe e frequencia n�o tem o mesmo n�mero de elementos");
			return false;
		} else {
			double contador = 0;
			for (int i = 0; i < frequencia.length; i++)
				contador += frequencia[i];
			if (contador != 1.0) {
				System.out.println("As frequencias n�o dao 1");
				return false;
			}
		}
		
		// Se n�o tiver erro faz iso aqui.
		this.a = new double[frequencia.length];
		this.b = new double[frequencia.length];
		this.freqencia = frequencia;

		for (int i = 0; i < frequencia.length; i++) {
			String[] str = classe[i].split(" -> ");
			this.a[i] = Double.parseDouble(str[0]);
			this.b[i] = Double.parseDouble(str[1]);
		}
		return true;
	}

	public double[] getA() {
		return a;
	}

	public double[] getB() {
		return b;
	}

	public double[] getFreqencia() {
		return freqencia;
	}
}
