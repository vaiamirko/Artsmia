package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private Graph<ArtObject,DefaultWeightedEdge> grafo;
	//creo un hash map dove memorizzo gli ogg. creati quando mi serve un oggetto prima di crearlo controllo se esiste gia,cosi si otimizza il programma
	
	private Map<Integer,ArtObject> idMap;
	
	public Model() {
		idMap=new HashMap<Integer,ArtObject>();
		grafo=new SimpleWeightedGraph <> (DefaultWeightedEdge.class);
		
	}
	public void CreaGrafo() {
		ArtsmiaDAO dao = new ArtsmiaDAO();
		dao.listObjects(idMap);
		
		// aggiungo i vertici 
		Graphs.addAllVertices(grafo, idMap.values());//uso la classe graphs per aggiungere tutti i vertici
		
		
		//aggiungo gli archi
		
		List<Adiacenza> adj = dao.listAdiacenze();
		
		for(Adiacenza adc:adj) {
			ArtObject source = idMap.get(adc.getO1());
			ArtObject dest=idMap.get(adc.getO2());
			try {
			Graphs.addEdge(grafo, source, dest,adc.getPeso());
			}
			catch(Throwable t ) {
				
			}
		}
		System.out.println("Grafo creato "+ grafo.vertexSet().size()+" vertici e "+ grafo.edgeSet().size()+" archi");
		
		
	}
public int getVertexSize() {
	return grafo.vertexSet().size();
}
public int getEdgeSize() {
	return grafo.edgeSet().size();
}

}
