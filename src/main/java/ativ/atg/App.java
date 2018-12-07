package ativ.atg;

import com.opencsv.CSVReader;

import org.jgrapht.graph.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;

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
//    	getMostFamousAlbum();
//    	getArtistWithMostDistinctSongs();
    	collaborativePlaylistsAreMoreEclectics();
    }
    
    public static void getMostFamousAlbum() throws IOException {
    	WeightedPseudograph<Node, DefaultWeightedEdge> graph = q1Graph();
    	int maxWeight = 0;
    	String album = null;
    	
    	 StrongConnectivityAlgorithm<Node, DefaultWeightedEdge> scAlg = 
    			 new KosarajuStrongConnectivityInspector<>(graph);
        List<Graph<Node, DefaultWeightedEdge>> stronglyConnectedSubgraphs =
            scAlg.getStronglyConnectedComponents();
    	
    	for(Graph<Node, DefaultWeightedEdge> subgraph : stronglyConnectedSubgraphs) {
    		for(Node music : subgraph.vertexSet()) {
    			int currentEdgeWeight = graph.edgesOf(music).size();
    			if(currentEdgeWeight > maxWeight) {
    				maxWeight = currentEdgeWeight;
    				album = music.getAlbum();
    			}
    			break;
    		}
    	}
    	
    	System.out.println("Questão #1");
    	System.out.println(album);
	}
    
	@SuppressWarnings("deprecation")
	public static WeightedPseudograph q1Graph() throws IOException {
		// NODES
		CSVReader reader = new CSVReader(new FileReader(Q1_CSV_FILE_PATH_NODES), ',');
		
		HeaderColumnNameMappingStrategy<Node> beanStrategy = new HeaderColumnNameMappingStrategy<Node>();
		beanStrategy.setType(Node.class);
		
		CsvToBean<Node> csvToBean = new CsvToBean<Node>();
		List<Node> musics = csvToBean.parse(beanStrategy, reader);
		
		WeightedPseudograph<Node, DefaultWeightedEdge> graph = new WeightedPseudograph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		for (Node music : musics) {
			graph.addVertex(music);
		}
		
		reader.close();
		graph = setEdgesQ1(graph, Q1_CSV_FILE_PATH_EDGES);
		
		return graph;	   
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
		
		graph = setEdgesQ3(graph, Q3_CSV_FILE_PATH_EDGES);
		
		return graph;	   
	}

	@SuppressWarnings("deprecation")
	public static WeightedPseudograph q4Graph() throws IOException {
		// NODES
		CSVReader reader = new CSVReader(new FileReader(Q4_CSV_FILE_PATH_NODES), ',');
		
		HeaderColumnNameMappingStrategy<Node> beanStrategy = new HeaderColumnNameMappingStrategy<Node>();
		beanStrategy.setType(Node.class);
		
		CsvToBean<Node> csvToBean = new CsvToBean<Node>();
		List<Node> playlists = csvToBean.parse(beanStrategy, reader);
		
		WeightedPseudograph<Node, DefaultWeightedEdge> graph = new WeightedPseudograph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		for (Node playlist : playlists) {
			graph.addVertex(playlist);
		}
		
		reader.close();
		
		graph = setEdgesQ4(graph, Q4_CSV_FILE_PATH_EDGES);
		
		return graph;	   
	}

	@SuppressWarnings("deprecation")
	public static WeightedPseudograph q2Graph() throws IOException {
		// NODES
		CSVReader reader = new CSVReader(new FileReader(Q2_CSV_FILE_PATH_NODES), ',');
		
		HeaderColumnNameMappingStrategy<Node> beanStrategy = new HeaderColumnNameMappingStrategy<Node>();
		beanStrategy.setType(Node.class);
		
		CsvToBean<Node> csvToBean = new CsvToBean<Node>();
		List<Node> tracks = csvToBean.parse(beanStrategy, reader);
		
		WeightedPseudograph<Node, DefaultWeightedEdge> graph = new WeightedPseudograph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		for (Node track : tracks) {
			graph.addVertex(track);
		}
		
		reader.close();
		
		graph = setEdgesQ2(graph, Q2_CSV_FILE_PATH_EDGES);
		
		return graph;	   
	}
	
	public static WeightedPseudograph setEdgesQ1(WeightedPseudograph<Node, DefaultWeightedEdge> graph, String path) throws IOException {	
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
			
			for (Node music : graph.vertexSet()) {
				
				if(music.getId().equals(sourceID)) {
					source = music;
				}
				if(music.getId().equals(targetID)) {
					target = music;
				}
			}
			
			if(source != null && target != null) {
				DefaultWeightedEdge edgeObj = graph.addEdge(source, target);
			}
		}
		return graph;
	}

	public static WeightedPseudograph setEdgesQ2(WeightedPseudograph<Node, DefaultWeightedEdge> graph, String path) throws IOException {	
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
			
			for (Node track : graph.vertexSet()) {

				if(track.getId().equals(sourceID)) {
					source = track;
				}
				if(track.getId().equals(targetID)) {
					target = track;
				}
			}
			
			if(source != null && target != null) {
				DefaultWeightedEdge edgeObj = graph.addEdge(source, target);
			}
		}
		return graph;
	}
	
	public static WeightedPseudograph setEdgesQ3(WeightedPseudograph<Node, DefaultWeightedEdge> graph, String path) throws IOException {	
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

	public static WeightedPseudograph setEdgesQ4(WeightedPseudograph<Node, DefaultWeightedEdge> graph, String path) throws IOException {	
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
			}
		}
		return graph;
	}

	public static String getArtistWithMostDistinctSongs() throws IOException {
		WeightedPseudograph<Node, DefaultWeightedEdge> graph = q2Graph();
		
		Map<String, Integer> numOfArtistDistinctSongs = new HashMap<String, Integer>();
		
		String artistWithMostDistinctSongs = "";
		int numOfDistinctSongs = 0;
		
		for (Node track1 : graph.vertexSet()) {
			int distinctSongs = 1;
			
			if(numOfArtistDistinctSongs.containsKey(track1.getArtist())) {
				numOfArtistDistinctSongs.put(track1.getArtist(), numOfArtistDistinctSongs.get(track1.getArtist()) + 1);
			}
			else {
				numOfArtistDistinctSongs.put(track1.getArtist(), 1);
			}
		}
		
		for (String key : numOfArtistDistinctSongs.keySet()) {
			if (numOfArtistDistinctSongs.get(key) > numOfDistinctSongs) {
				numOfDistinctSongs = numOfArtistDistinctSongs.get(key);
				artistWithMostDistinctSongs = key;
			}
		}
		
		System.out.println("\nQuestão #2");
		System.out.println(artistWithMostDistinctSongs);
		

		return artistWithMostDistinctSongs;
	}

	public static boolean collaborativePlaylistsAreMoreEclectics() throws IOException {
		WeightedPseudograph<Node, DefaultWeightedEdge> graph = q4Graph();

		int collaborativeDistinctArtists = 0;
		int nonCollaborativeDistinctArtists = 0;

		for (Node playlist: graph.vertexSet()){
			if(playlist.isCollaborative().equals("true")) {
				collaborativeDistinctArtists += playlist.getDistinctArtists();
			} 
			else {
				nonCollaborativeDistinctArtists += playlist.getDistinctArtists();
			}
		}
		
		System.out.println("\nQuestão #4");
		System.out.println(collaborativeDistinctArtists > nonCollaborativeDistinctArtists);

		return collaborativeDistinctArtists > nonCollaborativeDistinctArtists;
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
