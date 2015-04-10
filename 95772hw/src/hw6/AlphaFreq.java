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

public class AlphaFreq implements Comparator<Word> {

	@Override
	public int compare(Word o1, Word o2) {
		int alpha = o1.getWord().compareTo(o2.getWord());
		if (alpha == 0)
			return o2.getFrequency() - o1.getFrequency();
		else 
			return alpha;
	}

}