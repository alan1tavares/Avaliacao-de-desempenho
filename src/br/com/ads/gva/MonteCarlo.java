package br.com.ads.gva;

public class MonteCarlo implements GeradorVariavelAleatorio {

	private DistribuicaoDeFrequencia distribuicaoDeFrequencia;
	private double[] pontoMedio;
	private SorteioComProbabilidadesDiferentes gva;

	@Override
	public double gerarVariavel() {
		return gva.gerarVariavel();
	}

	public void classeFrequencias(String[] classe, double[] frequencia) {
		this.distribuicaoDeFrequencia = new DistribuicaoDeFrequencia();
		boolean deuCerto = this.distribuicaoDeFrequencia.adicionarClasseFrequencias(classe, frequencia);
		if(deuCerto == false) return;
		gerarPontoMedio();
		this.gva = new SorteioComProbabilidadesDiferentes();
		this.gva.valoresEsuasProbabilidades(this.pontoMedio, this.distribuicaoDeFrequencia.getFreqencia());
		
	}

	private void gerarPontoMedio() {
		int tamanho = distribuicaoDeFrequencia.getA().length;
		this.pontoMedio = new double[tamanho];
		for (int i = 0; i < tamanho; i++) {
			double a = this.distribuicaoDeFrequencia.getA()[i];
			double b = this.distribuicaoDeFrequencia.getB()[i];
			this.pontoMedio[i] = (a+b)/2;
		}
	}

}
