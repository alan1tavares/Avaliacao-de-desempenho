package variavel.aleatoria;

import java.util.Random;

/**
 * Gera uma variavel aleatoria
 * */
public class VariavelAleatoria {
	private double variavel;

	public double getVariavelAleatoria() {
		/**
		 * Retorna uma variavel aleatoria de acordo com esses passos
		 * (a) Generate two uniform U(0, 1) variates u1 and u2.
		 * (b) Let x = –Ln(u1).
		 * (c) If u2 > e^–(((x–1)^2)/2), go back to step 4a.
		 * (d) Generate u3.
		 * (e) If u3 > 0.5, return μ + σ x; otherwise return μ – σx.
		 * 
		 * @return varialvel aleatoria double
		 */
		
		Random r = new Random();
		double u1 = r.nextDouble();
		double u2 = r.nextDouble();

		double x = -Math.log(u1);
		
		System.out.println("u1 = " + u1 + "\nu2 = " + u2);
		return 0;
	}
}
