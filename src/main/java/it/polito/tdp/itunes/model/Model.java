package it.polito.tdp.itunes.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge> grafo;
	ItunesDAO dao;
	List<Album> listaAlbum;
	List<Album> parziale;
	List<Album> soluzioneMigliore = new LinkedList<>();
	
	public Model() {
		
		grafo = new SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		dao = new ItunesDAO();
	}
	
	public void creaGrafo(int n) {
		
		grafo = new SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
//		AGGIUNTA VERTICI
		Graphs.addAllVertices(grafo, dao.getVertici(n));
		listaAlbum = new LinkedList<>(grafo.vertexSet());
		
//		AGGIUNTA ARCHI
		for(Album a1 : grafo.vertexSet()) {
			for(Album a2 : grafo.vertexSet()) {
//				if(a1.getAlbumId()>a2.getAlbumId()) {
					if(a1.getnCanzoni()-a2.getnCanzoni()>0) {  //a1 ha più canzoni di a2
						Graphs.addEdge(grafo, a2, a1, a1.getnCanzoni()-a2.getnCanzoni());
					} else if (a1.getnCanzoni()-a2.getnCanzoni()<0) {
						Graphs.addEdge(grafo, a1, a2, a2.getnCanzoni()-a1.getnCanzoni());
					}
//				}
			}
		}
		
		for(Album a : grafo.vertexSet()) {
//			archi entranti e archi uscenti
			int peso = 0;
			for(Album a1 : Graphs.predecessorListOf(grafo, a)) {
				peso += grafo.getEdgeWeight(grafo.getEdge(a1, a));
			}
			for(Album a2 : Graphs.successorListOf(grafo, a)) {
				a.aggiungiAlbum(a2);
				peso -= grafo.getEdgeWeight(grafo.getEdge(a, a2));
			}
			a.setBilancio(peso);
		}
		
	}
	
	public boolean verificaPercorso(Album partenza, Album arrivo) {
		
		BreadthFirstIterator<Album, DefaultWeightedEdge> ispector = new BreadthFirstIterator<Album, DefaultWeightedEdge>(grafo, partenza);
		List<Album> raggiungibili = new LinkedList<>();
		while(ispector.hasNext()) {
			Album a = ispector.next();
			raggiungibili.add(a);
		}
		return raggiungibili.contains(arrivo);
	}
	
	public void cercaPercorso(List<Album> parziale, int soglia, Album partenza, Album arrivo, Album origine) {
		
		if(!verificaPercorso(partenza, arrivo)) {
			return;
		}
		
		if(partenza.equals(arrivo)) {
			if(gradoTotale(parziale, origine) >= gradoTotale(soluzioneMigliore, origine)) {
				soluzioneMigliore.clear();
				soluzioneMigliore.addAll(parziale);
			}
			return;
		}
		
		for(Album a : Graphs.successorListOf(grafo, partenza)) {
			if(grafo.getEdgeWeight(grafo.getEdge(partenza, a)) >= soglia && !parziale.contains(a)) {
				parziale.add(a);
				cercaPercorso(parziale, soglia, a, arrivo, origine);
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	
	private int gradoTotale(List<Album> parziale, Album origine) {
		
		int cnt = 0;
		for(Album a : parziale) {
			if(a.getBilancio() > origine.getBilancio()) {
				cnt++;
			}
		}
		return cnt;
	}
	
	public List<Album> percorsoMigliore(int soglia, Album partenza, Album arrivo){
		
		parziale = new LinkedList<Album>();
		soluzioneMigliore = new LinkedList<Album>();
		cercaPercorso(parziale, soglia, partenza, arrivo, partenza);
		
		return soluzioneMigliore;
	}

	public List<Album> listaSuccessori(Album a) {
		Collections.sort(a.getListaSuccessori(), new ComparatorePerBilancio());
		return a.getListaSuccessori();
	}
	
	public List<Album> getListaVertici(){
		Collections.sort(listaAlbum);
		return listaAlbum;
	}
	
	public int vertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int archi() {
		return this.grafo.edgeSet().size();
	}
	
}
