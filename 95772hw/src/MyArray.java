import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*****************************************************
 * 
 * 95-772 Data Structures for Application Programmers
 * 
 * Homework 1 My Array implementation
 * 
 * Name: Jiaqi Luo 
 * ID: jiaqiluo
 * 
 * Description:
 * 		handling array list problem, such as enlarge 
 * the array, add element, remove element, get size and
 * so on.
 * 
 *****************************************************/
public class MyArray {

	private int capacity;
	private String[] array;

	public MyArray(int length) {

		this.capacity = length;
		array = new String[this.capacity];

	}

	public int size() {
		int count = 0;
		for (int i = 0; i < this.capacity; i++) {
			if (array[i] != null)
				count++;
			else
				break;
		}
		return count;

	}

	public int getCapacity() {
		return this.capacity;
	}
	
	
	// if the string is not a character word, we should neglect it
	public void add(String string) {
		Pattern p = Pattern.compile("[a-zA-Z]");
		Matcher m = p.matcher(string);

		if (m.find() && !string.contains("_")) {

			int size = this.size();
			if (size == this.capacity) {
				if (this.capacity == 0)
					this.capacity = 1;
				else
					this.capacity = 2 * this.capacity;
				String[] newArray = new String[this.capacity];
				System.arraycopy(array, 0, newArray, 0, size);
				array = newArray;
			}
			array[size] = string;

		}
	}

	public boolean search(String string) {
		for (int i = 0; i < this.size(); i++) {
			if (array[i].equals(string))
				return true;
		} 
		return false; 
	}

	public void display() {
		for (int i = 0; i < this.size(); i++)
			System.out.print(array[i] + " ");
		System.out.print("\n");
	}

	// give another empty array, search the old array,
	// if it is a new word, add it to the new array.
	public void removeDups() {
		String[] tempArray = new String[this.size()];
		tempArray[0] = array[0];

		for (int i = 1; i < this.size(); i++) {
			int size = arrayGetSize(tempArray);
			tempArray[size] = array[i];

			for (int k = 0; k < size; k++) {
				if (array[i].equals(tempArray[k])) {
					tempArray[size] = null;
					break;
				}
			}
		}
		array = tempArray;
	}

	// basically use for the removeDups(). 
	// it can return the size of an array.
	private int arrayGetSize(String[] array) {
		int count = 0;
		while (array[count] != null)
			count++;
		return count;
	}

}
