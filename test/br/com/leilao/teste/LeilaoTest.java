package br.com.leilao.teste;


import static org.junit.Assert.assertEquals;
import static br.com.leilao.teste.NewMatcher.temUmLance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.leilao.dominio.Lance;
import br.com.leilao.dominio.Leilao;
import br.com.leilao.dominio.Usuario;


public class LeilaoTest {

	private Leilao leilao;
	
	private Lance lance1;
	private Lance lance2;
	private Lance lance3;
	private Lance lance4;
	private Lance lance5;
	
	private Usuario usu1;
	private Usuario usu2;
	private Usuario usu3;
	private Usuario usu4;
	private Usuario usu5;

	@Before
	public void inicializar() {
		
		//Usuario
		usu1 = new Usuario("Dfs");
		usu2 = new Usuario("Davi");
		usu3 = new Usuario("Chico");
		usu4 = new Usuario("Chico2");
		usu5 = new Usuario("Chico3");
		
		//Lance Usuario
		lance1 = new Lance(usu1, 300);
		lance2 = new Lance(usu2, 200);
		lance3 = new Lance(usu3, 400);
		lance4 = new Lance(usu4, 500);
		lance5 = new Lance(usu5, 350);
		
		//Leilao
		leilao = new Leilao("Tropa de elite 3");
		
	}
	
	
	@Test
	public void deveReceberUmLance(){
		
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(lance1);
		
		assertThat(leilao.getLances().size(), is(1));
		
		assertEquals(300, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void deveConterUmLance(){
		leilao.propoe(lance1);
		
		assertThat(leilao, temUmLance(lance1));
	}
	
	@Test
	public void naoDeveConterDoisLancesDoMesmoUsuarioSeguidos(){
		leilao.propoe(lance1);
		leilao.propoe(lance1);
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(300, leilao.getLances().get(0).getValor(), 0.001);
	}
	
	@Test(expected = RuntimeException.class)
	public void oLeilaoDeveConterLances(){
		leilao.getMaiorLance();
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void oValorDoLanceNaoPodeSerMenorOuIgualAZero(){
		leilao.propoe(new Lance(usu1, 0));
		
	}
	
	@Test
	public void naoDeveConterMaisQueCincoLancesDoMesmoUsuario(){
		
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		
		//nao deve add
		leilao.propoe(lance1);
		
		assertEquals(10, leilao.getLances().size());
	}
	
	@Test
	public void dobrarLance(){
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		
		leilao.dobrarLance(usu1);
		
		
		assertThat(leilao.getLances().size(), equalTo(3));
		
		
		assertEquals(600, leilao.getLances().get(leilao.getLances().size() -1).getValor(), 0.00001);
	}
	
	@Test
	public void maiorValor() {
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		leilao.propoe(lance3);
		
		double maiorValorEsperado = 400;
		
		Assert.assertEquals(maiorValorEsperado, leilao.getMaiorLance(), 0.00001);
	}
	
	@Test
	public void menorValor(){
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		leilao.propoe(lance3);
		
		double menorValor = 200;
		assertEquals(menorValor, leilao.getMenorLance(), 0.00001);
	}
	
	@Test
	public void valorMedioLeilao(){
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		leilao.propoe(lance3);
		
		assertEquals((double) 300, leilao.getMediaDeLances(), 0.001);
		
	}
	
	@Test
	public void encontrarOsTresMaioresLances(){
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		leilao.propoe(lance3);
		leilao.propoe(lance4);
		leilao.propoe(lance5);
		
		List<Lance> maiores = leilao.getTresMaioresLances();
		assertEquals(3, maiores.size());
		
		assertThat(maiores, hasItems(lance4, lance3, lance5));
		
	}
	
	@Test
	public void encontrarOsTresMaioresLancesCajoNaoAjaTresElementos(){
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		
		List<Lance> maiores = leilao.getTresMaioresLances();
		assertEquals(2, maiores.size(), 0.00001);
		assertEquals(300, maiores.get(0).getValor(), 0.00001);
		assertEquals(200, maiores.get(1).getValor(), 0.00001);
	}
	
	@Test
	public void encontrarOsTresMaioresLancesCasoAListaEstejaVazia(){
		List<Lance> maiores = leilao.getTresMaioresLances();
		assertEquals(0, maiores.size(), 0.00001);
	}
	
	
	
}
