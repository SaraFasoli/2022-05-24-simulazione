package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	private ItunesDAO dao;
	private Graph<Track,DefaultWeightedEdge> grafo;
	private Map<Integer,Track> canzoni;
	
	
	//ricorsione
	private List<Track> canzoniValide;
	private List<Track> listaBest;
	private Track preferita;
	
	public Model() {
		super();
		dao=new ItunesDAO();
		canzoni=new HashMap<>();
	}

	public String creaGrafo(int idGenre) {
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		canzoni=new HashMap<>();
		
		List<Track> vertici=new ArrayList<>(dao.getTrackbyGenre(idGenre));
		Graphs.addAllVertices(this.grafo, vertici);
		
		for(Track t: vertici) {
			canzoni.put(t.getTrackId(), t);
		}
		List<Arco> archi=dao.getArchi(idGenre);
		for(Arco a: archi) {
			Graphs.addEdge(this.grafo, canzoni.get(a.getT1()), canzoni.get(a.getT2()), a.getPeso());
		}
		
		return "#VERTICI: "+this.grafo.vertexSet().size()+ " #ARCHI: "+this.grafo.edgeSet().size();
	}


	public List<Genre> getGenre(){
		return dao.getAllGenres();
	}
	public List<Arco> getMax() {
		double max=0;
		List<Arco> archiMax=new ArrayList<>();
		Arco amax=null;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			double peso=this.grafo.getEdgeWeight(e);
			
			Track source=this.grafo.getEdgeSource(e);
			Track target=this.grafo.getEdgeTarget(e);
			amax=new Arco(source.getTrackId(),target.getTrackId(),this.grafo.getEdgeWeight(e));
			amax.setTitolo1(source.getName());
			amax.setTitolo2(target.getName());
			if(peso>max) {
				max=peso;
				archiMax=new ArrayList<>();
				archiMax.add(amax);
			}
			else if(peso==max) {
				archiMax.add(amax);
			}
		}
		return archiMax;
	}
	
	public List<Track> getLista(int soglia, Track partenza){
		
		listaBest=new LinkedList<>();
		this.preferita=partenza;
		
		List<Track> canzoniValide=new ArrayList<>();
		canzoniValide=this.getCanzonivalide();
		
		List<Track> parziale=new ArrayList<>();
		parziale.add(partenza);
		cerca(parziale, canzoniValide,soglia);
		
		return listaBest;
	}

	private void cerca(List<Track> parziale, List<Track> valide, int soglia) {
		// TODO Auto-generated method stub
		
	   if(parziale.size()>listaBest.size()) {
				listaBest=new LinkedList<>(parziale);
				System.out.println(listaBest.toString());
		}
		
		
		for(Track next: valide) {
			if(!parziale.contains(next)) {
				if(calcolaSomma(parziale,next)<=soglia) {
					parziale.add(next);
					cerca(parziale,valide,soglia);
					parziale.remove(parziale.size()-1);
				}
				
			}
		}
	}

	private double calcolaSomma(List<Track> parziale,Track next) {
		// TODO Auto-generated method stub
		double somma=0;
		somma+=next.getBytes();
		for(Track t: parziale) {
			somma+=t.getBytes();
			
		}
		return somma;
	}

	private List<Track> getCanzonivalide() {
		// TODO Auto-generated method stub

		ConnectivityInspector<Track, DefaultWeightedEdge> ci = new ConnectivityInspector<Track, DefaultWeightedEdge>(this.grafo);
		List<Track> connesse=new ArrayList<>(ci.connectedSetOf(this.preferita));
		
		return connesse;
	}
	
	public List<Track> getVertici(){
		return new ArrayList<>(canzoni.values());
	}
	
	
}


