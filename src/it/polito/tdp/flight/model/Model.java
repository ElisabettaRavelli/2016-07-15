package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private FlightDAO dao;
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private List<Airport> aeroporti;
	private Map<Integer, Airport> idMap;
	private List<Airport> raggiungibili;
	
	public Model() {
		this.dao = new FlightDAO();
		this.aeroporti = new ArrayList<>();
		this.idMap = new HashMap<>();
		this.raggiungibili= new ArrayList<>();
	}
	
	public void creaGrafo(Double distanzaMax) {
		this.grafo = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.aeroporti = this.dao.getAllAirports(idMap);
				
					
		List<LatLon> latlon = this.dao.getCollegamento(idMap);
		for(LatLon tmp: latlon) {
			
			double distanza = LatLngTool.distance(
					new LatLng(tmp.getLatitudine1(), tmp.getLongitudine1()),
					new LatLng(tmp.getLatitudine2(), tmp.getLongitudine2()),
					LengthUnit.KILOMETER);
			
			if(distanza<distanzaMax) {
				double durata = distanza/800*3600;
				Graphs.addEdgeWithVertices(this.grafo, tmp.getA1(), tmp.getA2(), durata);
				System.out.println("aggiunto arco tra vertici "+ tmp.getA1().getAirportId() + " -> "+ tmp.getA2().getAirportId());
			}
		}
				
		
		
	}

	public int getVertici() {
		return this.grafo.vertexSet().size();
	}

	public int getArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public int calcolaCC() {
		ConnectivityInspector<Airport, DefaultWeightedEdge> ci = new ConnectivityInspector<Airport, DefaultWeightedEdge>(grafo);
		return ci.connectedSets().size();
	}
	
	public List<Airport> aeroportiRaggiungibili(Airport source){
		List<Airport> result = new ArrayList<>();
		GraphIterator<Airport, DefaultWeightedEdge> it = new BreadthFirstIterator<>(this.grafo, source);
		
		while(it.hasNext()) {
			result.add(it.next());
		} return result;
	}
	
	public Airport piuLondanoDaFiumicino() {
		Integer idFiumicino = this.dao.getFiumicino();
		Airport fiumicino = idMap.get(idFiumicino);
		System.out.println("Airport source: " + fiumicino.toString());
		
		Airport airportMax = null;
		Double distanzaMax = 0.0;
		this.raggiungibili = aeroportiRaggiungibili(fiumicino);
		for(Airport a: this.raggiungibili) {
			Double distanza = this.dao.getDistanza(fiumicino, a);
			if(distanza>distanzaMax) {
				distanzaMax = distanza;
				airportMax = a;
				System.out.println("Airport destinazione: "+ a.toString());
			}
			
		}
		return airportMax;
	}
}
