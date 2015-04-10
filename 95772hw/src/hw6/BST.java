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
 * 
 * Description: 
 * 	for the Search and insert method, I use recursion 
 * to implement;
 * 	for iterator, I use Arraylist to store the data
 * and use stack to implement it.
 * 	for calculating height, I increment it for each recursion
 * in insert, and return the height of each node. Then compare
 * it to the overall height and update it if the height is greater
 * than the overall height.
 * 	for calculating size, I update it for each inserting.
 *****************************************************/
import java.util.*;

public class BST<T extends Comparable<T>> implements Iterable<T>,
		BSTInterface<T> {

	private Node<T> root;
	private Comparator<T> comparator;
	private int size;
	private int height;

	public BST() {
		this(null);
	}

	public BST(Comparator<T> comparator) {
		this.comparator = comparator;
		root = null;
		this.size = 0;
		this.height = 0;
	}

	/**
	 * Returns the comparator used to order this collection.
	 * 
	 * @return comparator
	 */
	public Comparator<T> comparator() {
		return comparator;
	}

	/**
	 * Returns the root data of this tree.
	 * 
	 * @return root data
	 */
	public T getRoot() {
		return this.root.data;

	}

	/**
	 * Returns the height of this tree. if the tree is empty or tree has only a
	 * root node, then the height of the tree is 0.
	 * 
	 * @return int value of the height
	 */
	public int getHeight() {
		return height;

	}

	/**
	 * Returns the number of ndoes in this tree.
	 * 
	 * @return int value of the number of nodes.
	 */
	public int getNumberOfNodes() {
		return this.size;

	}

	/**
	 * Given the value (object) to be searched, it tries to find it.
	 * 
	 * @param toSearch
	 *            - value to be searched
	 * @return The value (object) of the search result. If nothing found, null.
	 */
	@Override
	public T search(T toSearch) {
		if (toSearch == null)
			return null;
		return recSearch(toSearch, root);
	}

	/**
	 * helper function for recursion of searching 
	 * 
	 * @param toSearch 
	 * 			- value to be searched
	 * @param current
	 * 			- the node we search from
	 * @return The value (object) of the search result. If nothing found, null.
	 */
	private T recSearch(T toSearch, Node<T> current) {
		int comparison;
		if (current == null)
			return null;
		if (this.comparator == null)
			comparison = current.data.compareTo(toSearch);
		else
			comparison = comparator.compare(current.data, toSearch);

		if (comparison < 0)
			return recSearch(toSearch, current.right);
		else if (comparison > 0)
			return recSearch(toSearch, current.left);
		else
			return current.data;

	}

	/**
	 * Inserts a value (object) to the tree. No duplicates allowed.
	 * 
	 * @param toInsert
	 *            - a value (object) to be inserted to the tree.
	 */
	@Override
	public void insert(T toInsert) {
		if (toInsert == null)
			return;
		int temHeight = 0;
		if (root == null)
			root = new Node<T>(toInsert);
		this.size++;

		temHeight = recInsert(toInsert, root, temHeight);
		if (temHeight > height)
			height = temHeight;

	}

	/**
	 * helper function for recursion of inserting 
	 * 
	 * @param toInsert 
	 * 			- value to be inserted
	 * @param current
	 * 			- the node we search from
	 * @param temHeight
	 * 			- the height of last level
	 * @return The height of the level of the inserted node
	 */
	private int recInsert(T toInsert, Node<T> current, int temHeight) {
		int count = 0;
		temHeight++;
		int comparison;
		if (this.comparator == null)
			comparison = toInsert.compareTo(current.data);
		else
			comparison = comparator.compare(toInsert, current.data);
		if (comparison > 0) {
			if (current.right == null) {
				current.right = new Node<T>(toInsert);

				return temHeight;
			}
			count = recInsert(toInsert, current.right, temHeight);
		} else if (comparison < 0) {
			if (current.left == null) {
				current.left = new Node<T>(toInsert);

				return temHeight;
			}
			count = recInsert(toInsert, current.left, temHeight);
		} else
			return 0;
		return count;

	}

	/**
	 * In-order iterator
	 * 
	 * @return iterator object
	 */
	@Override
	public Iterator<T> iterator() {
		Stack<Node<T>> stack = new Stack<Node<T>>();
		ArrayList<T> list = new ArrayList<T>();
		if (root == null)
			return null;
		Node<T> current = root;
		stack.push(current);
		while (!stack.isEmpty()) {
			while (current.left != null) {
				current = current.left;
				stack.push(current);
			}

			while ((current = stack.pop()).right == null && !stack.isEmpty()) {
				list.add(current.data);
			}

			list.add(current.data);

			current = current.right;
			if (current != null)
				stack.push(current);
		}

		return list.iterator();
	}

	// private static nested class for Node
	private static class Node<T> {
		private T data;
		private Node<T> left;
		private Node<T> right;

		public Node(T data) {
			this(data, null, null);
		}

		public Node(T data, Node<T> left, Node<T> right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}

		public String toString() {

			return data.toString();
		}
	}

	/***********************************************************
	 * 
	 * For a very simple debug purpose:
	 * 
	 * Test your BST with this first to make sure your BST works. But, this is 
	 * a starting point. Should test more!
	 * 
	 ***********************************************************/
	public static void main(String[] args) {
		BST<Integer> b = new BST<Integer>();
		int[] ar = { 31, 16, 49, 5, 18, 51, 4, 13, 17, 19, 8 };
		for (Integer x : ar) {
			b.insert(x);
		}

		for (Integer x : b)
			System.out.print(x + " ");

		System.out.println();
		System.out.println(b.search(8));
		System.out.println(b.getHeight());
		System.out.println(b.getNumberOfNodes());
	}

}