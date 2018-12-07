package ativ.atg;

import com.opencsv.CSVReader;

import org.jgrapht.graph.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public class App {

	static {
	    String currentDir = System.getProperty("user.dir");

        Q1_CSV_FILE_PATH_NODES = currentDir + "/resource/q1/nodes.csv";
        Q1_CSV_FILE_PATH_EDGES = currentDir + "/resource/q1/edges.csv";
        Q2_CSV_FILE_PATH_NODES = currentDir + "/resource/q2/nodes.csv";
        Q2_CSV_FILE_PATH_EDGES = currentDir + "/resource/q2/edges.csv";
        Q3_CSV_FILE_PATH_NODES = currentDir + "/resource/q3/nodes.csv";
        Q3_CSV_FILE_PATH_EDGES = currentDir + "/resource/q3/edges.csv";
        Q4_CSV_FILE_PATH_NODES = currentDir + "/resource/q4/nodes.csv";
        Q4_CSV_FILE_PATH_EDGES = currentDir + "/resource/q4/edges.csv";
	}

	private static final String Q1_CSV_FILE_PATH_NODES;
	private static final String Q1_CSV_FILE_PATH_EDGES;
	private static final String Q2_CSV_FILE_PATH_NODES;
	private static final String Q2_CSV_FILE_PATH_EDGES;
	private static final String Q3_CSV_FILE_PATH_NODES;
	private static final String Q3_CSV_FILE_PATH_EDGES;
	private static final String Q4_CSV_FILE_PATH_NODES;
	private static final String Q4_CSV_FILE_PATH_EDGES;
	
    public static void main(String[] args) throws IOException {  
    	String inputPlaylist = "July 2013";
    	getRecommendedPlaylist(inputPlaylist);
    }
    
	
	
	@SuppressWarnings("deprecation")
	public static WeightedPseudograph q3Graph() throws IOException {
		// NODES
		CSVReader reader = new CSVReader(new FileReader(Q3_CSV_FILE_PATH_NODES), ',');
		
		HeaderColumnNameMappingStrategy<Node> beanStrategy = new HeaderColumnNameMappingStrategy<Node>();
		beanStrategy.setType(Node.class);
		
		CsvToBean<Node> csvToBean = new CsvToBean<Node>();
		List<Node> playlists = csvToBean.parse(beanStrategy, reader);
		
		WeightedPseudograph<Node, DefaultWeightedEdge> graph = new WeightedPseudograph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		for (Node playlist : playlists) {
			graph.addVertex(playlist);
		}
		
		reader.close();
		
		graph = setEdges(graph, Q3_CSV_FILE_PATH_EDGES);
		
		return graph;	   
	}
	
	public static WeightedPseudograph setEdges(WeightedPseudograph<Node, DefaultWeightedEdge> graph, String path) throws IOException {	
		// EDGES
		CSVReader reader2 = new CSVReader(new FileReader(path), ',');
	
		HeaderColumnNameMappingStrategy<Edge> beanStrategy2 = new HeaderColumnNameMappingStrategy<Edge>();
		beanStrategy2.setType(Edge.class);
		
		CsvToBean<Edge> csvToBean2 = new CsvToBean<Edge>();
		List<Edge> edges = csvToBean2.parse(beanStrategy2, reader2);
		
		for(Edge edge : edges) {
			String sourceID = edge.getSource();
			String targetID = edge.getTarget();
			
			Node source = null;
			Node target = null;
			
			for (Node playlist : graph.vertexSet()) {
				
				if(playlist.getId().equals(sourceID)) {
					source = playlist;
				}
				if(playlist.getId().equals(targetID)) {
					target = playlist;
				}
			}
			
			if(source != null && target != null) {
				DefaultWeightedEdge edgeObj = graph.addEdge(source, target);
				graph.setEdgeWeight(edgeObj, edge.getWeight());
			}
		}
		return graph;
	}

	
	public static String getRecommendedPlaylist(String inputPlaylist) throws IOException {
		WeightedPseudograph<Node, DefaultWeightedEdge> graph = q3Graph();
		
		Node recommendedPlaylist = null;
		
		for(Node playlist : graph.vertexSet()) {
			if(playlist.getLabel().equalsIgnoreCase(inputPlaylist)) {
				int highestEdgeWeight = 0;
				for(DefaultWeightedEdge edge : graph.edgesOf(playlist)) {
					int currentEdgeWeight = (int) graph.getEdgeWeight(edge);
					if(currentEdgeWeight > highestEdgeWeight) {
						highestEdgeWeight = currentEdgeWeight;
						recommendedPlaylist = graph.getEdgeTarget(edge);
					}
				}
			}
		}
		System.out.println("\nQuestão #3");
		System.out.println(recommendedPlaylist);
		
		return recommendedPlaylist.getLabel();
	}
}
