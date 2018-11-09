package ativ.atg;

import com.opencsv.CSVReader;

import org.jgrapht.graph.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public class App {
	private static final String Q1_CSV_FILE_PATH = "/home/francisco/atg/resource/nodes.csv";
    
    public static void main(String[] args) throws IOException {
    	SimpleWeightedGraph<MusicQ1, DefaultWeightedEdge> graph = q1();  
    	System.out.println(graph.vertexSet().size());
    }
   
    
	@SuppressWarnings("deprecation")
	public static SimpleWeightedGraph q1() throws IOException {
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
	
	private void getTop5(SimpleWeightedGraph<MusicQ1, DefaultWeightedEdge> graph) {
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
		System.out.print(top5.toString());
	}
}