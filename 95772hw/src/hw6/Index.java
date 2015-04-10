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
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Index {

	/**
	 * Build a tree giving a file name
	 * 
	 * @param fileName
	 *            - input file name
	 * @return BST object
	 * @throws IOException
	 */
	public BST<Word> buildIndex(String fileName) throws IOException {
		if (fileName == null)
			return null;
		BST<Word> b = new BST<Word>();
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		String[] words;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		while ((line = br.readLine()) != null) {
			lines.add(line);

		}

		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);

			words = line.split("\\W");

			Pattern p = Pattern.compile("^[a-zA-Z]+$");
			for (int j = 0; j < words.length; j++) {
				Matcher m = p.matcher(words[j]);

				if (!words[j].equals(" ") && !words[j].contains("_")
						&& m.find()) {

					Word fileWord = new Word(words[j], i + 1);
					Word searchedWord = b.search(fileWord);
					if (searchedWord != null) {
						searchedWord.addFrequency();
						searchedWord.getSet().add(i + 1);
					} else {
						b.insert(fileWord);
					}

				}
			}
		}
		br.close();
		return b;

	}

	/**
	 * Build a tree with a file name and comparator object
	 * 
	 * @param fileName
	 *            - input file name
	 * @param comparator
	 *            - comparator to be used
	 * @return BST object
	 * @throws IOException
	 */
	public BST<Word> buildIndex(String fileName, Comparator<Word> comparator)
			throws IOException {
		if (fileName == null || comparator == null)
			return null;
		BST<Word> b = new BST<Word>();
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		String[] words;

		BufferedReader br = new BufferedReader(new FileReader(fileName));
		while ((line = br.readLine()) != null) {
			lines.add(line);

		}

		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			if (comparator instanceof IgnoreCase) {
				line = line.toLowerCase();
			}
			words = line.split("\\W");

			Pattern p = Pattern.compile("^[a-zA-Z]+$");
			for (int j = 0; j < words.length; j++) {
				Matcher m = p.matcher(words[j]);

				if (!words[j].equals(" ") && !words[j].contains("_")
						&& m.find()) {

					Word fileWord = new Word(words[j], i + 1);
					Word searchedWord = b.search(fileWord);
					if (searchedWord != null) {
						searchedWord.addFrequency();
						searchedWord.getSet().add(i + 1);
					} else {
						b.insert(fileWord);
					}

				}
			}
		}
		br.close();
		return b;
	}

	/**
	 * Build a tree with a given list and comparator
	 * 
	 * @param list
	 *            - ArrayList of words
	 * @param comparator
	 *            - comparator to be used
	 * @return BST object
	 */
	public BST<Word> buildIndex(ArrayList<Word> list,
			Comparator<Word> comparator) {
		if (list == null || comparator == null)
			return null;
		BST<Word> b = new BST<Word>();

		Word word;

		for (int i = 0; i < list.size(); i++) {
			word = list.get(i);
			if (comparator instanceof IgnoreCase) {
				word.setWord(word.getWord().toLowerCase());
			}

			b.insert(word);
		}

		return b;
	}

	/**
	 * Sort words alphabetically Note: Should keep the state of the tree
	 * 
	 * @param tree
	 *            - BST tree
	 * @return ArrayList of words sorted alphabetically
	 */
	public ArrayList<Word> sortByAlpha(BST<Word> tree) {
		if (tree == null)
			return null;
		ArrayList<Word> list = new ArrayList<Word>();
		Iterator<Word> ite = tree.iterator();
		Word word;
		while (ite.hasNext()) {
			word = ite.next();
			list.add(word);

		}
		Collections.sort(list);
		return list;
	}

	/**
	 * Sort words by frequency Note: Should keep the state of the tree
	 * 
	 * @param tree
	 *            - BST tree
	 * @return ArrayList of words sorted by frequency
	 */
	public ArrayList<Word> sortByFrequency(BST<Word> tree) {
		if (tree == null)
			return null;
		ArrayList<Word> list = new ArrayList<Word>();
		Iterator<Word> ite = tree.iterator();
		Word word;
		while (ite.hasNext()) {
			word = ite.next();
			list.add(word);

		}
		Collections.sort(list, new Frequency());
		return list;
	}

	/**
	 * Find all words of the highest frequency Note: Should keep the state of
	 * the tree
	 * 
	 * @param tree
	 *            - BST tree
	 * @return ArrayList of words that have highest frequency
	 */
	public ArrayList<Word> getHighestFrequency(BST<Word> tree) {
		if (tree == null)
			return null;
		ArrayList<Word> list = new ArrayList<Word>();
		ArrayList<Word> highestList = new ArrayList<Word>();
		Iterator<Word> ite = tree.iterator();
		Word word;
		int highestFreq;
		while (ite.hasNext()) {
			word = ite.next();
			list.add(word);

		}
		Collections.sort(list, new Frequency());

		highestFreq = list.get(0).getFrequency();
		for (Word aWord : list) {
			if (aWord.getFrequency() < highestFreq)
				break;
			highestList.add(aWord);

		}
		return highestList;
	}

}