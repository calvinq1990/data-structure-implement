package hw5;

/*********************************************************
 * 
 * 95-772 Data Structures for Application Programmers
 * 
 * Homework 5 
 * 
 * Andrew ID: jiaqiluo 
 * Name: Jiaqi Luo
 * 
 * 
 * 1.Use regulation expression to split and get the word.
 * 2.Use HashMap to store the word, which can have a good
 *   performance in searching words.
 * 3.Use ArrayList to store lines in file
 * 
 *********************************************************/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Similarity {
	private BufferedReader br;
	private HashMap<String, Integer> map;
	private ArrayList<String> lines;

	public Similarity(File file) {
		try {
			lines = new ArrayList<String>();
			map = new HashMap<String, Integer>();
			br = new BufferedReader(new FileReader(file));
			String temp;
			String parts[];

			while ((temp = br.readLine()) != null)
				lines.add(temp);
			for (int i = 0; i < lines.size(); i++) {
				temp = lines.get(i);
				parts = temp.toLowerCase().split("\\W");

				Pattern p = Pattern.compile("^[a-zA-Z]+$");
				for (int j = 0; j < parts.length; j++) {
					Matcher m = p.matcher(parts[j]);

					if (!parts[j].equals(" ") && !parts[j].contains("_")
							&& m.find()) {
						if (!map.containsKey(parts[j])) {

							map.put(parts[j], 1);
						} else
							map.put(parts[j], map.get(parts[j]) + 1);
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Similarity(String string) {
		lines = new ArrayList<String>();
		lines.add(string);
		map = new HashMap<String, Integer>();
		String parts[];
		parts = string.toLowerCase().split("\\W");
		Pattern p = Pattern.compile("^[a-zA-Z]+$");

		for (int j = 0; j < parts.length; j++) {
			Matcher m = p.matcher(parts[j]);
			if (!parts[j].equals(" ") && !parts[j].contains("_") && m.find()) {

				if (!map.containsKey(parts[j])) {
					map.put(parts[j], 1);
				} else
					map.put(parts[j], map.get(parts[j]) + 1);
			}
		}
	}

	public int numberOfLines() {
		return lines.size();
	}

	public int numOfWords() {
		int count = 0;
		for (String key : map.keySet()) {
			count += map.get(key);
		}
		return count;
	}

	public int numOfWordsNoDups() {
		return map.keySet().size();
	}

	public double euclideanNorm() {
		double norm = 0;
		for (Integer value : map.values())
			norm += Math.pow(value, 2);

		return Math.pow(norm, 0.5);

	}

	public HashMap<String, Integer> getMap() {
		return this.map;
	}

	public double dotProduct(HashMap<String, Integer> outMap) {
		Iterator<String> in = this.map.keySet().iterator();
		String temp;
		double dot = 0;
		while (in.hasNext()) {

			temp = in.next();
			if (outMap.containsKey(temp)) {
				dot += map.get(temp) * outMap.get(temp);
			}
		}

		return dot;
	}

	public double distance(HashMap<String, Integer> outMap) {

		double freq1 = this.euclideanNorm();
		double freq2 = 0;
		for (Integer value : outMap.values())
			freq2 += Math.pow(value, 2);
		freq2 = Math.pow(freq2, 0.5);
		return Math.acos(this.dotProduct(outMap) / (freq1 * freq2));
	}
}
