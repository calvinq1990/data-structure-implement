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

public class IgnoreCase implements Comparator<Word> {

	@Override
	public int compare(Word o1, Word o2) {
		return o1.getWord().compareTo(o2.getWord());
	}

}