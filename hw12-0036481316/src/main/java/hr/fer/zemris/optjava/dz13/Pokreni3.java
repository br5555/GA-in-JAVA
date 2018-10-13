package hr.fer.zemris.optjava.dz13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

public class Pokreni3 {

	public static void main(String[] args) {
		Path myTxt = Paths.get("./src/main/resources/SantFe.txt");
		int[][] mapField = null;
		int i = 0 , j = 0;
		try (Stream<String> stream = Files.lines(myTxt)) {
	        Iterator<String> iterator = stream.iterator();
	        boolean first = true;
	        while(iterator.hasNext()){
	        	String line = iterator.next();
	        	if(first){
	        		first = false;
	        		String[] arraySize = line.split("x");
	        		mapField = new int[Integer.parseInt(arraySize[0].trim())][Integer.parseInt(arraySize[1].trim())];
	        		continue;
	        	}
	        	char[] syms = line.toCharArray();
	        	for(char sym: syms){
	        		if(sym == '1'){
	        			mapField[j][i++] =1;
	        		}else mapField[j][i++] =0;
	        	}
	        	i = 0;
	        	j++;
	        }
	        for(int k = 0; k< 32; k++){
	        	for(int l = 0 ; l <32;l++ ){
	        		System.out.print(mapField[k][l]+" ");
	        	}
	        	System.out.println();
	        }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
}
