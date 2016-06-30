package gerar.arquivo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class Arquivo {
	private FileWriter file;
	private PrintWriter escreve;

	ArrayList<ArrayList<Double>> tabela;

	public Arquivo() throws IOException {
		this.tabela = tabela;

		this.file = new FileWriter("tabela-ads.ods");
		this.escreve = new PrintWriter(file);
	}

	public void escrverArquivo(String s) {
		escreve.print(s);
	}
	
	public void fechar() throws IOException{
		file.close();
	}
}
