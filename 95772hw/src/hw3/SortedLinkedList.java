package hw3;
/*************************************************************
 * 
 * 95-772 Data Structures for Application Programmers
 * 
 * Homework 3 SortedLinkedList implementation with Recursion
 * 
 * Implement the SortedLinkedList class and its basic methods
 * such as add, removefirst, removeAt, display and so on using
 * recurtion.
 * 
 * Name: Jiaqi Luo
 * ID : jiaqiluo
 * 
 *************************************************************/
public class SortedLinkedList implements MyListInterface{
	private Node head = null;
	
	public SortedLinkedList (){		
	}
	
	public SortedLinkedList (String[] unsorted){
		head = new Node (null, unsorted[0]);
		recAdd(unsorted);
	}
	
	private class Node{
		private Node next = null;
		private String word = null;
		
		private Node (Node n, String w) {
			word = w;
			next = n;
			
		}
		
	}
	
	
	@Override
	public void add(String value) {
		// TODO Auto-generated method stub
		recAddString (head, value);
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return recSize (head, 0);
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub

		StringBuilder string = new StringBuilder();
		string.append("[");
		string = recBuildString (head, string);
		string.append("]");
		System.out.println (string.toString());
		
	}

	@Override
	public boolean contains(String key) {
		// TODO Auto-generated method stub
		return recContains (head, key);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if (head == null)
			return true;
		return false;
	}

	@Override
	public String removeFirst() {
		// TODO Auto-generated method stub
		Node temp = head;
		head = head.next;
		return temp.word;
	}

	@Override
	public String removeAt(int index) {
		// TODO Auto-generated method stub
		if (index == 0)
			return removeFirst();
		else if (index < 0)
			throw new RuntimeException();
		else
			return reRemoveAt (head, index, 0);
	}

	
	/**
	 * Belows are helper functions 
	 */
	
	/**
	 * using recurtion to add nodes when initialize a sortedlinkedlist
	 * 
	 * @param unsorted string array
	 */
	private void recAdd (String[] unsorted) {
		if (unsorted.length == 1)
			return;
		String[] newUnsorted = new String[unsorted.length - 1];
		System.arraycopy(unsorted, 1, newUnsorted, 0, unsorted.length - 1);
		if (newUnsorted[0].compareTo(head.word) < 0) {
			Node temp = new Node (head, newUnsorted[0]);
			head = temp;		
		} else {
			Node insert = recFind (newUnsorted[0], head);
			if (insert != null) 
				insert.next = new Node (insert.next, newUnsorted[0]);
		
		}
		
		recAdd (newUnsorted);
		
	}
	
	/**
	 * find if there are duplicate words in the added list
	 * 
	 * @param word, search if this word in the list
	 * @param node, search begin from this node
	 * @return the place, which the node should be 
	 * 			inserted.if duplicate, return null
	 */
	private Node recFind (String word, Node node) {
		if (node.word.equals(word))
			return null;
		if (node.next == null)
			return node;
		else if (node.next.word.compareTo(word) > 0)
			return node;
		else 
			return recFind (word, node.next);
	}
	
	/**
	 * add a new string node
	 * @param node
	 * @param value
	 */
	private void recAddString (Node node, String value) {
		if (node.next == null)
			node.next = new Node (null, value);
		else
			recAddString (node.next, value);
		
	}
	
	/**
	 * help to find the size of the list recursively
	 * @param node
	 * @param count
	 * @return
	 */
	private int recSize (Node node, int count) {
		count ++;
		if (node.next != null)
			return recSize (node.next, count);
			
		else
			return count;
		
	}
	
	/**
	 * help to build a string when displaying.
	 * @param node
	 * @param string
	 * @return sttringbuilder
	 */
	private StringBuilder recBuildString (Node node, StringBuilder string) {
		
		if (node.next != null) {
			string.append(node.word + ", ");
			return recBuildString (node.next, string);
		}
		else {
			string.append(node.word);
			return string;
		}
			
	}
	
	/**
	 * help to find if the list contain this key
	 * @param node
	 * @param key
	 * @return
	 */
	private boolean recContains (Node node, String key) {
		if ( node.word.equals(key))
			return true;
		else if (node.next == null)
			return false;
		else return recContains (node.next, key);
	}
	
	/**
	 * the helper function for removeAt()
	 * 
	 * @param node
	 * @param index
	 * @param count
	 * @return
	 */
	private String reRemoveAt (Node node, int index, int count) {
		count ++;
		if (node.next == null)
			throw new RuntimeException();
		else if (count == index) {
			Node temp = node.next;
			node.next = temp.next;
			return temp.word;
		} else 
			return reRemoveAt (node.next, index, count);
		
	}
}
