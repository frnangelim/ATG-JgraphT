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
	private static final String Q1_CSV_FILE_PATH = "/home/giuseppe/Documents/ATG-JgraphT/resource/q1/nodes.csv";
	private static final String Q2_CSV_FILE_PATH = "/home/giuseppe/Documents/ATG-JgraphT/resource/q2/nodes.csv";
	private static final String Q4_ARTIST_EDGES_CSV_FILE_PATH = "/home/giuseppe/Documents/ATG-JgraphT/resource/q4/artistsEdges.csv";
	private static final String Q4_ARTIST_NODES_CSV_FILE_PATH = "/home/giuseppe/Documents/ATG-JgraphT/resource/q4/artistsNodes.csv";
	private static final String Q4_PLAYLIST_NODES_CSV_FILE_PATH = "/home/giuseppe/Documents/ATG-JgraphT/resource/q4/playlistNodes.csv";
	
    public static void main(String[] args) throws IOException {  
    	getTop5(); 
    	recomendPlaylisyBySong();
    }
    
	@SuppressWarnings("deprecation")
	public static SimpleWeightedGraph q1Graph() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(Q1_CSV_FILE_PATH), ',');
		
		HeaderColumnNameMappingStrategy<MusicQ1> beanStrategy = new HeaderColumnNameMappingStrategy<MusicQ1>();
		beanStrategy.setType(MusicQ1.class);
		
		CsvToBean<MusicQ1> csvToBean = new CsvToBean<MusicQ1>();
		List<MusicQ1> musics = csvToBean.parse(beanStrategy, reader);
		
		SimpleWeightedGraph<MusicQ1, DefaultWeightedEdge> graph = new SimpleWeightedGraph<MusicQ1, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		for (MusicQ1 music : musics) {
			graph.addVertex(music);
		}
		reader.close();
		
		return graph;	   
	}
	
	@SuppressWarnings("deprecation")
	public static SimpleWeightedGraph q2Graph() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(Q2_CSV_FILE_PATH), ',');
		
		HeaderColumnNameMappingStrategy<Playlist> beanStrategy = new HeaderColumnNameMappingStrategy<Playlist>();
		beanStrategy.setType(Playlist.class);	
		
		CsvToBean<Playlist> csvToBean = new CsvToBean<Playlist>();
		List<Playlist> playlists = csvToBean.parse(beanStrategy, reader);
		
		SimpleWeightedGraph<Playlist, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Playlist, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		for (Playlist playlist : playlists) {
			graph.addVertex(playlist);
		}
		reader.close();
		
		return graph;	   
	}
	
	@SuppressWarnings("deprecation")
	public static SimpleWeightedGraph q4Part1Graph() throws IOException {
		
	}
	
	@SuppressWarnings("deprecation")
	public static SimpleWeightedGraph q4Part2Graph() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(Q4_PLAYLIST_NODES_CSV_FILE_PATH), ',');
		
		HeaderColumnNameMappingStrategy<Playlist> beanStrategy = new HeaderColumnNameMappingStrategy<Playlist>();
		beanStrategy.setType(Playlist.class);	
		
		CsvToBean<Playlist> csvToBean = new CsvToBean<Playlist>();
		List<Playlist> playlists = csvToBean.parse(beanStrategy, reader);
		
		SimpleWeightedGraph<Playlist, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Playlist, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		for (Playlist playlist : playlists) {
			graph.addVertex(playlist);
		}
		reader.close();
		
		return graph;
	}
	
	public static void recomendPlaylistByArtistWithoutTheArtist() throws IOException {
		SimpleWeightedGraph<Playlist, DefaultWeightedEdge> playlistsGraph = q4Part2Graph();
		
		Playlist recomendedPlaylist = null;
		
		for(Playlist playlist : playlistsGraph.vertexSet()) {
			if(recomendedPlaylist==null) 
				recomendedPlaylist = playlist;
			else if(playlist.getWeight() > recomendedPlaylist.getWeight()) {
				recomendedPlaylist = playlist;
			}
		}
		
		System.out.println(recomendedPlaylist.toString() + '\n');
	}
	
	public static void recomendPlaylisyBySong() throws IOException {
		SimpleWeightedGraph<Playlist, DefaultWeightedEdge> graph = q2Graph();
		Playlist recomendedPlaylist = null;
		
		for(Playlist playlist : graph.vertexSet()) {
			if(recomendedPlaylist==null) 
				recomendedPlaylist = playlist;
			else if(playlist.getWeight() > recomendedPlaylist.getWeight()) {
				recomendedPlaylist = playlist;
			}
		}
		System.out.println(recomendedPlaylist.toString() + '\n');
	}
	
	public static void getTop5() throws IOException {
		SimpleWeightedGraph<MusicQ1, DefaultWeightedEdge> graph = q1Graph();
		
		ArrayList<MusicQ1> top5 = new ArrayList<>();
    	for(MusicQ1 v : graph.vertexSet()) {
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
		System.out.print(top5.toString() + '\n');
	}
}