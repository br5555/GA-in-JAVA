package hr.fer.zemris.optjava.dz13;

import java.util.ArrayList;
import java.util.Random;

public class Proba {

	public static void main(String[] args) {
		ArrayList<Integer> pp = new ArrayList<>();
		Random rand = new Random();
		for(int i = 0; i< 7; i++){
			while(pp.size()-1 != i){
				Integer iii = rand.nextInt(100);
				if(!pp.contains(iii)){
					pp.add(iii);
				}
			}
		}
		for(Integer i : pp){
			System.out.println(i.intValue());
		}
	}
}
