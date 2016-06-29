package gerar.variavel.aleatoria;

import java.util.Random;

/**
 * Gera uma variavel aleatoria
 * */
public class GeradorVariavelAleatoria {
	private double variavel;

		public double nextRejectionMethod(double media, double desvioPadrao) {
		/**
		 * Retorna uma variavel aleatoria usando o método
		 * da rejeição
		 * 
		 * (a) Generate two uniform U(0, 1) variates u1 and u2;
		 * (b) Let x = –Ln(u1);
		 * (c) If u2 > e^–(((x–1)^2)/2), go back to step 4a;
		 * (d) Generate u3;
		 * (e) If u3 > 0.5, return μ + σ x; otherwise return μ – σx;
		 * 
		 * @return varialvel aleatoria double
		 */
		
		Random r = new Random();
		double u1, u2, u3, x;
		
		do{
			// Passo (a)
			u1 = r.nextDouble();		
			u2 = r.nextDouble();

			// Passo (b)
			x = -Math.log(u1); // retorna ln(u1)
			
			// Passo (c)
		}while(u2 > Math.exp(Math.pow(x-1, 2) / 2));
		
		u3 = r.nextDouble();
		
		if(u3 > 0.5)
			return media + (desvioPadrao * x);
		else
			return media - (desvioPadrao * x);
	}
		
	public double nextInverseTransformation(double media){
		/**
		 * Retorna uma variavel aleatoria usando o método
		 * da transformação inversa		 
		 */
		Random r = new Random();
		double u = r.nextDouble();
		
		double lambda = 1/media;
		
		return -((1/lambda)*Math.log(u));
	}
}
