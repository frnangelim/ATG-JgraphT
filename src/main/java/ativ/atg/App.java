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

        Q1_CSV_FILE_PATH = currentDir + "/resource/q1/nodes.csv";
        Q2_CSV_FILE_PATH = currentDir + "/resource/q2/nodes.csv";
        Q3_CSV_FILE_PATH_NODES = currentDir + "/resource/q3/nodes.csv";
        Q3_CSV_FILE_PATH_EDGES = currentDir + "/resource/q3/edges.csv";
        Q4_ARTIST_EDGES_CSV_FILE_PATH = currentDir + "/resource/q4/artistsEdges.csv";
        Q4_ARTIST_NODES_CSV_FILE_PATH = currentDir + "/resource/q4/artistsNodes.csv";
        Q4_PLAYLIST_NODES_CSV_FILE_PATH = currentDir + "/resource/q4/playlistsNodes.csv";
	}

	private static final String Q1_CSV_FILE_PATH;
	private static final String Q2_CSV_FILE_PATH;
	private static final String Q3_CSV_FILE_PATH_NODES;
	private static final String Q3_CSV_FILE_PATH_EDGES;
	private static final String Q4_ARTIST_EDGES_CSV_FILE_PATH;
	private static final String Q4_ARTIST_NODES_CSV_FILE_PATH;
	private static final String Q4_PLAYLIST_NODES_CSV_FILE_PATH;
	
    public static void main(String[] args) throws IOException {  
    	getTop5(); 
    	
    	recomendPlaylisyBySong();
    	
    	String inputPlaylist = "July 2013";
    	getRecommendedPlaylist(inputPlaylist);
    	
    	recomendPlaylistByArtistWithoutTheArtist();
    }
    
	@SuppressWarnings("deprecation")
	public static SimpleWeightedGraph q1Graph() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(Q1_CSV_FILE_PATH), ',');
		
		HeaderColumnNameMappingStrategy<Node> beanStrategy = new HeaderColumnNameMappingStrategy<Node>();
		beanStrategy.setType(Node.class);
		
		CsvToBean<Node> csvToBean = new CsvToBean<Node>();
		List<Node> musics = csvToBean.parse(beanStrategy, reader);
		
		SimpleWeightedGraph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		for (Node music : musics) {
			graph.addVertex(music);
		}
		reader.close();
		
		return graph;	   
	}
	
	@SuppressWarnings("deprecation")
	public static SimpleWeightedGraph q2Graph() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(Q2_CSV_FILE_PATH), ',');
		
		HeaderColumnNameMappingStrategy<Node> beanStrategy = new HeaderColumnNameMappingStrategy<Node>();
		beanStrategy.setType(Node.class);	
		
		CsvToBean<Node> csvToBean = new CsvToBean<Node>();
		List<Node> playlists = csvToBean.parse(beanStrategy, reader);
		
		SimpleWeightedGraph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		for (Node playlist : playlists) {
			graph.addVertex(playlist);
		}
		reader.close();
		
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
		
		graph = setEdges(graph, Q3_CSV_FILE_PATH_EDGES);
		
		return graph;	   
	}
	
	@SuppressWarnings("deprecation")
	public static WeightedPseudograph q4Part1Graph() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(Q4_ARTIST_NODES_CSV_FILE_PATH), ',');
		
		HeaderColumnNameMappingStrategy<Node> beanStrategy = new HeaderColumnNameMappingStrategy<Node>();
		beanStrategy.setType(Node.class);
		
		CsvToBean<Node> csvToBean = new CsvToBean<Node>();
		List<Node> artists = csvToBean.parse(beanStrategy, reader);
		
		WeightedPseudograph<Node, DefaultWeightedEdge> graph = new WeightedPseudograph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		for (Node artist : artists) {
			graph.addVertex(artist);
		}
		
		reader.close();
		
		graph = setEdges(graph, Q4_ARTIST_EDGES_CSV_FILE_PATH);
		
		return graph;
	}
	
	@SuppressWarnings("deprecation")
	public static SimpleWeightedGraph q4Part2Graph() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(Q4_PLAYLIST_NODES_CSV_FILE_PATH), ',');
		
		HeaderColumnNameMappingStrategy<Node> beanStrategy = new HeaderColumnNameMappingStrategy<Node>();
		beanStrategy.setType(Node.class);	
		
		CsvToBean<Node> csvToBean = new CsvToBean<Node>();
		List<Node> playlists = csvToBean.parse(beanStrategy, reader);
		
		SimpleWeightedGraph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		for (Node playlist : playlists) {
			graph.addVertex(playlist);
		}
		reader.close();
		
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
	
	public static ArrayList<Node> getTop5() throws IOException {
		SimpleWeightedGraph<Node, DefaultWeightedEdge> graph = q1Graph();

		ArrayList<Node> top5 = new ArrayList<>();
    	for(Node v : graph.vertexSet()) {
    		int currentWeight = v.getWeight();
    		
    		if(top5.size() == 0) {
    			top5.add(v);
    		} else {
    			for (int i = 0; i < top5.size(); i++) {
    				if(i == top5.size() - 1) {
    					top5.add(v);
    				} else {
    					if(currentWeight > top5.get(i).getWeight()) {
            				top5.add(i, v);
            				break;
            			}
    				}
    				if(top5.size() == 6) {
    	    			top5.remove(5);
    	    		}
				}
    		}
		}
    	
    	System.out.println("Questão #1");
    	
    	for(Node song : top5) {
    		System.out.print(song.toString() + "\n");
    	}
    	
    	
    	return top5;
	}
	
	public static void recomendPlaylisyBySong() throws IOException {
		SimpleWeightedGraph<Node, DefaultWeightedEdge> graph = q2Graph();
		Node recomendedPlaylist = null;
		
		for(Node playlist : graph.vertexSet()) {
			if(recomendedPlaylist==null) 
				recomendedPlaylist = playlist;
			else if(playlist.getWeight() > recomendedPlaylist.getWeight()) {
				recomendedPlaylist = playlist;
			}
		}
		
		System.out.println("\nQuestão #2");
		System.out.println(recomendedPlaylist.toString());
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
	
public static void recomendPlaylistByArtistWithoutTheArtist() throws IOException {
		
		SimpleWeightedGraph<Node, DefaultWeightedEdge> playlistsGraph = q4Part2Graph();
		WeightedPseudograph<Node, DefaultWeightedEdge> artistsGraph = q4Part1Graph();
		
		Node recomendedPlaylist = null;
		
		for(Node playlist : playlistsGraph.vertexSet()) {
			if(recomendedPlaylist==null) 
				recomendedPlaylist = playlist;
			else if(playlist.getWeight() > recomendedPlaylist.getWeight()) {
				recomendedPlaylist = playlist;
			}
		}
		
		System.out.println("\nQuestão #4");
		System.out.println(recomendedPlaylist.toString());
	}
}