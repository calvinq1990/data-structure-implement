import java.util.ArrayDeque;
import java.util.LinkedList;

/**********************************************************
 * 
 * 95-772 Data Structures for Application Programmers 
 * 
 * Homework Assignment 2 Solve Josephus problem 
 * with different data structures and compare running time
 * 
 * Andrew ID: Jiaqi Luo
 * Name: jiaqiluo
 * 
 **********************************************************/

public class Josephus {

	/**
	 * This method uses ArrayDeque data structure as Queue/Deque to find the
	 * survivor's position.
	 * 
	 * @param size
	 *            Number of people in the circle that is bigger than 0.
	 * @param rotation
	 *            Elimination order in the circle. The value has to be greater
	 *            than 0.
	 * @return The position value of the survivor.
	 * @throws Exception 
	 * 
	 * Description: 
	 * 		use the arrayDeque to remove the first and add to last as a queue.
	 */
	public int playWithAD(int size, int rotation)  {
		// TODO implement this
		ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
		if (size < 1 || rotation < 1) 
			throw new RuntimeException();
		for(int i = 1; i <= size; i++){
			ad.add(i);
		}
		
		int count = 0;
		while (ad.size() != 1){
			if ((count +1) == rotation) {
				ad.remove();
				count =0;
				continue;
			}
		
			ad.add(ad.remove());
			count++;
			
			
		}
		
		return ad.peek();
		
	}

	/**
	 * This method uses LinkedList data structure as Queue/Deque to find the
	 * survivor's position.
	 * 
	 * @param size
	 *            Number of people in the circle that is bigger than 0.
	 * @param rotation
	 *            Elimination order in the circle. The value has to be greater
	 *            than 0.
	 * @return The position value of the survivor.
	 * 
	 * * Description: 
	 * 		use the linkedlist to remove the first and add to last as a queue.
	 */
	public int playWithLL(int size, int rotation) {
		// TODO implement this
		LinkedList<Integer> ll = new LinkedList<Integer>();
		if (size < 1 || rotation < 1) 
			throw new RuntimeException();
		for(int i = 1; i <= size; i++){
			ll.addLast(i);
		}
		
		int count = 0;
		while (ll.size() != 1){
			if ((count +1) == rotation) {
				ll.removeFirst();
				count =0;
				continue;
			}
		
			ll.addLast(ll.removeFirst());
			count++;
			
			
		}
		
		return ll.getFirst(); 
	}

	/**
	 * This method uses LinkedList data structure to find the survivor's position. 
	 * However, this does not use the LinkedList as Queue/Deque.
	 * Instead, this method uses LinkedList as "Linked List." 
	 * 
	 * That means, it uses index value to find and remove a person to be executed in the
	 * circle.
	 * 
	 * @param size
	 *            Number of people in the circle that is bigger than 0.
	 * @param rotation
	 *            Elimination order in the circle. The value has to be greater
	 *            than 0.
	 * @return The position value of the survivor.
	 * 
	 * Description: 
	 * 		use the index value linkedlist to remove and add
	 */
	public int playWithLLAt(int size, int rotation) {
		// TODO implement this
		LinkedList<Integer> llat = new LinkedList <Integer> ();
		int count = 1;
		int index = 0;
		if (size <1 || rotation < 1)
			throw new RuntimeException();
		for (int i = 1; i <= size; i++)
			llat.add(i);
		while (llat.size() != 1) {
			if (count == rotation){
				index = ( count + index - 1) % llat.size();
				llat.remove(index);
				count = 1;
			} else {
				count++;
			}
		}
		return llat.get(0);
		
		
	}

}