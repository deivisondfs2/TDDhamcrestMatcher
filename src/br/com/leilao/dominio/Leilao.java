package br.com.leilao.dominio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.management.RuntimeErrorException;

public class Leilao {

	private String descricao;
	private List<Lance> lances;
	
	private double maiorLance = Double.NEGATIVE_INFINITY;
	private double menorLance = Double.POSITIVE_INFINITY;
	private List<Lance> tresMaioresLances;
	
	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}
	
	public void propoe(Lance lance) {
		if (lance.getValor() <= 0) {
			throw new IllegalArgumentException();
		}
		
		int total = lanceDoMesmoUsuarioMaiorQueCinco(lance);
		
		if(lances.isEmpty() || !getUltimoLance().getUsuario().equals(lance.getUsuario()) && total < 5){
			lances.add(lance);			
		}
	}
	
	public void dobrarLance(Usuario usuario){
		double ultimoLanceUsuario = Double.MIN_VALUE;
		for (Lance lance : lances) {
			if (lance.getUsuario().equals(usuario)) {
				ultimoLanceUsuario = lance.getValor();
			}
		}
		
		double dobrarValor = ultimoLanceUsuario * 2;
		
		propoe(new Lance(usuario, dobrarValor));
	}
	
	private int lanceDoMesmoUsuarioMaiorQueCinco(Lance lanceRecebido){
		int cont = 0;
		for (Lance lance : lances) {
			if (lance.getUsuario().equals(lanceRecebido.getUsuario())) {
				cont++;
			}
		}
		return cont;
	}
	
	private Lance getUltimoLance() {
		return lances.get(lances.size() -1);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	public void verificadorLance(){
		if (lances.size() <= 0) {
			throw new RuntimeErrorException(null, "Lista de lances vazia");
		}
		
		for (Lance lance : lances) {
			if (lance.getValor() > maiorLance) maiorLance = lance.getValor();
			
			if(lance.getValor() < menorLance) menorLance = lance.getValor();
		}
		
		
	}

	private void ordenarMaioresLances() {
		tresMaioresLances = new ArrayList<Lance>(lances);
		
		Collections.sort(tresMaioresLances, new Comparator<Lance>() {
			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() < o2.getValor()) {
					return 1;
				}
				if(o1.getValor() > o2.getValor()){
					return -1;
				}
				return 0;
			}
		});
		tresMaioresLances = tresMaioresLances.subList(0, tresMaioresLances.size() > 3 ? 3 : tresMaioresLances.size());
		
		
	}
	
	
	
	public List<Lance> getTresMaioresLances() {
		ordenarMaioresLances();
		return tresMaioresLances;
	}
	
	public double getMediaDeLances(){
		return getSomaValoresLeilao() / lances.size();
	}
	
	public double getSomaValoresLeilao(){
		double somaValores = Double.MIN_VALUE;
		for (Lance lance : lances) {
			somaValores += lance.getValor();
		}
		return somaValores;
	}
	
	
	
	public double getMaiorLance() {
		verificadorLance();
		return maiorLance;
	}
	
	public double getMenorLance() {
		verificadorLance();
		return menorLance;
	}
	
}
