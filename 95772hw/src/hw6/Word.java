package hw6;

/*****************************************************
 * 
 * 95-772 Data Structures for Application Programmers
 * 
 * Homework 6: Building Index using BST
 * 
 * Andrew ID: jiaqiluo
 * Name: Jiaqi Luo
 * 
 *****************************************************/
import java.util.*;

public class Word implements Comparable<Word> {
	
	private String word;
	private Set<Integer> index ; // word's line number in the source text
	private int frequency; // counts occurrences of the word
	
	public Word(String word, int i) {
		this.word = word;
		this.index = new HashSet<Integer>();
		this.index.add(i);
		this.frequency = 1;
	}
	
	@Override
	public int compareTo(Word o) {
		
		return this.word.compareTo(o.word);
	}

	public String getWord() {
		return this.word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public Set<Integer> getSet() {
		return this.index;
	}
	
	public int getFrequency() {
		return this.frequency;
	}
	
	public void addFrequency() {
		this.frequency++;
	}
	
	public void addIndex(int i) {
		index.add(i);
	}
	
	public String toString() {
		return word + " " + frequency + " " + index.toString() ;
	}
	
}