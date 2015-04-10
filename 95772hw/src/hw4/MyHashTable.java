package hw4;



import java.math.BigInteger;

/*********************************************************
 * 
 * 95-772 Data Structures for Application Programmers
 * 
 * Homework 4 HashTable Implementation
 * 
 * Andrew ID: jiaqiluo 
 * Name: Jiaqi Luo
 * 
 * 
 * 
 *********************************************************/

public class MyHashTable implements MyHTInterface {
	private final static double LoadFactor = 0.5;
	private final static int DefaultCapacity = 10;
	private int collision;
	private int size;
	private DataItem[] hashArray;
	private DataItem deleted;
	private int numOfItems;

	/**
	 * if no input size, set the length to default capacity 10
	 */
	public MyHashTable() {
		this(DefaultCapacity);
	}

	public MyHashTable(int size) {
		if (size <= 0)
			throw new RuntimeException();
		deleted = new DataItem("#DEL#");
		collision = 0;
		this.size = size;
		hashArray = new DataItem[size];
		this.numOfItems = 0;
	}

	/**
	 * Inserts a new String value (word). Frequency of each word be stored too.
	 * 
	 * @param value
	 *            String value to be added.
	 */
	@Override
	public void insert(String value) {
		if (value == null)
			return;
		int hashVal = hashFunc(value);
		boolean collisionFlag = false;
		while (hashArray[hashVal] != null
				&& !hashArray[hashVal].value.equals(deleted.value)) {
			if (hashArray[hashVal].value.equals(value)) {
				hashArray[hashVal].addFrequency();
				if (collisionFlag)
					this.collision--;

				return;
			}
			if (!collisionFlag) {
				if (hashFunc(hashArray[hashVal].value) == hashFunc(value)) {
					this.collision++;
					collisionFlag = true;
				}
			}
			hashVal++;
			hashVal = hashVal % size;
		}
		this.numOfItems++;
		hashArray[hashVal] = new DataItem(value);

		double loadFactor = (double) this.numOfItems / (double) this.size;
		if (loadFactor >= LoadFactor)
			rehash();

	}

	/**
	 * Returns the size, number of items, of the hashTable
	 * 
	 * @return the number of items in the table.
	 */
	@Override
	public int size() {

		return this.numOfItems;
	}

	/**
	 * Displays the values of the table If an index is empty, it shows ** If
	 * previously existed dataitem is deleted, then it should show #DEL#
	 */
	@Override
	public void display() {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (hashArray[i] == null)
				string.append("** ");
			else if (hashArray[i] == deleted)
				string.append(hashArray[i].value + " ");
			else
				string.append("[" + hashArray[i].value + ", "
						+ hashArray[i].frequency + "] ");
		}
		System.out.println(string.toString());
	}

	/**
	 * Returns true if value is contained in the table
	 * 
	 * @param key
	 *            String key value to be searched
	 * @return true if found, false if not found.
	 */
	@Override
	public boolean contains(String key) {
		int hashVal = hashFunc(key);
		while (hashArray[hashVal] != null) {
			if (hashArray[hashVal].value.equals(key))
				return true;
			else {
				hashVal++;
				hashVal = hashVal % size;
			}
		}
		return false;
	}

	/**
	 * Returns the number of collisions in relation to insert and rehash.
	 * 
	 * When rehashing process happens, the number of collisions should be
	 * properly updated.
	 * 
	 * The definition of collision is
	 * "two different keys map to the same hash value." Be careful with the
	 * situation where you could over count. Try to think as if you are using
	 * separate chaining! "How would you count the number of collisions?"
	 * 
	 * @return number of collisions
	 */
	@Override
	public int numOfCollisions() {

		return this.collision;
	}

	/**
	 * Returns the hash value of a String
	 * 
	 * @param value
	 *            value for which the hash value should be calculated
	 * @return int hash value of a String.
	 */
	@Override
	public int hashValue(String value) {
		return hashFunc(value);
	}

	/**
	 * Returns the frequency of a key String
	 * 
	 * @param key
	 *            key string value to find its frequency
	 * @return frequency value if found. If not found, return 0
	 */
	@Override
	public int showFrequency(String key) {
		int hashVal = hashFunc(key);
		while (hashArray[hashVal] != null) {
			if (hashArray[hashVal].value.equals(key))
				return hashArray[hashVal].frequency;
			else {
				hashVal++;
				hashVal = hashVal % size;
			}
		}
		return 0;
	}

	/**
	 * Removes and returns removed value
	 * 
	 * @param value
	 *            String value to be removed
	 * @return value that is removed
	 */
	@Override
	public String remove(String key) {
		int hashVal = hashFunc(key);
		while (hashArray[hashVal] != null) {
			if (hashArray[hashVal].value.equals(key)) {
				hashArray[hashVal] = deleted;
				this.numOfItems--;
				return key;
			}
			hashVal++;
			hashVal = hashVal % size;

		}
		return null;
	}

	/*
	 * Instead of using String's hashCode, you are to implement your own here,
	 * taking the table length into your account.
	 * 
	 * In other words, you are to combine the following two steps into one step
	 * here. 1. converting Object into integer value 2. compress into the table
	 * using modular hashing
	 * 
	 * Helper method to hash a string for English lowercase alphabet and blank,
	 * we have 27 total.
	 * 
	 * For example, "cats" : 3*27^3 + 1*27^2 + 20*27^1 + 19*27^0 = 60,337
	 * 
	 * But, to make the hash process faster, Horner's method should be applied
	 * as follows;
	 * 
	 * var4*n^4 + var3*n^3 + var2*n^2 + var1*n^1 + var0*n^0 can be rewritten as
	 * (((var4*n + var3)*n + var2)*n + var1)*n + var0
	 * 
	 * Note: You must use 27 for this homework. However, if you have time, I
	 * would encourage you to try with other constant values other than 27. And
	 * compare the results but it is not required.
	 */
	private int hashFunc(String input) {
		BigInteger value = BigInteger.valueOf(0);
		BigInteger k2 = new BigInteger("27");
		char c = input.charAt(0);
		long i1 = (int) c - 96;
		BigInteger k1 = new BigInteger("0");
		k1 = k1.add(BigInteger.valueOf(i1));
		value = k1;

		for (int i = 1; i < input.length(); i++) {

			char c2 = input.charAt(i);

			value = value.multiply(k2);

			long i2 = (int) c2 - 96;
			BigInteger k3 = new BigInteger("0");
			k3 = k3.add(BigInteger.valueOf(i2));

			value = value.add(k3);

		}
		BigInteger size = new BigInteger("0");
		size = size.add(BigInteger.valueOf(this.size));
		value = value.mod(size);

		return value.intValue();
	}

	/**
	 * doubles array length and rehash items
	 *  whenever the load factor is reached
	 */

	private void rehash() {
		int oldSize = this.size;
		int newSize = size * 2;
		while (!isPrime(newSize))
			newSize++;
		this.size = newSize;
		DataItem[] newHashArray = new DataItem[newSize];
		String temp;
		this.collision = 0;
		Boolean repeatValue;
		for (int i = 0; i < oldSize; i++) {
			if (hashArray[i] != null && hashArray[i] != deleted)
				temp = hashArray[i].value;
			else
				continue;
			repeatValue = false;
			int hashVal = hashFunc(temp);
			boolean collisionFlag = false;
			while (newHashArray[hashVal] != null
					&& !newHashArray[hashVal].value.equals(deleted.value)) {

				if (!collisionFlag) {

					this.collision++;
					collisionFlag = true;

				}
				hashVal++;
				hashVal = hashVal % size;

			}
			if (repeatValue)
				continue;
			else {
				newHashArray[hashVal] = hashArray[i];
			}
		}

		this.hashArray = newHashArray;
	}

	/**
	 * private static data item nested class
	 */

	private static class DataItem {
		private String value;
		private int frequency;

		public DataItem(String value) {
			this.value = value;
			this.frequency = 1;
		}

		public void addFrequency() {
			this.frequency++;
		}

	}

	/**
	 * To judge if the input argument is a prime
	 * 
	 * @param n
	 * @return true if it is a prime, else false
	 */
	private boolean isPrime(int n) {
		if (n == 1 || n == 2)
			return true;

		for (int i = 2; i < n; i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	public int getSize() {
		return this.size;
	}

}