import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * A class that is used to decode messages using a key.
 * A key is a mapping from letter to letter.
 * In a valid key, every letter appears once, so that the process can be reversed.
 */

public class Decoder {
	char[] key = new char[26];
	private static char[] alpha = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
			'n','o','p','q','r','s','t','u','v','w','x','y','z'};

	/**
	 * Constructor for the Decoder
	 * Sets the initial key to the default key
	 * [a,b,c, ... x,y,z]
	 * @param key 
	 */
	public Decoder() {
		//TODO implement this constructor
		this.key = Arrays.copyOf(alpha, alpha.length);
	}

	/**
	 * Contructor for the Decoder
	 * Set the initial key by offsetting the default key by i
	 * @param caesarNum
	 */
	public Decoder(int caesarNum) {
		//TODO implement this constructor
		if (caesarNum < 0) {
			while (caesarNum < 0) {
				caesarNum += 26;
			}
		}
		for (int i = 0; i < key.length; i++) {
			key[i] = toLetter(((toIndex(alpha[i]) + 26 - caesarNum) % 26));
		}
	}

	/**
	 * Contructor for the Decoder
	 * Uses a keyword. See the handout for details.
	 * @param keyword to be used
	 */
	public Decoder(String keyword) {
		//TODO implement this constructor
		int i = 0;
		keyword = keyword.toLowerCase(); //makes lowercase
		keyword = cleanString(keyword); //taking keyword and cleaning it up
		for(; i < keyword.length(); i++){ //loop through keyword to check for letter, lowercase 
			if (isLetter(keyword.charAt(i))) {
				key[i] = keyword.charAt(i);
			}
		}
		for (int j = 0; j < alpha.length; j++) { //loop checking for used character and supplying alphabet
			if (keyword.indexOf(alpha[j]) == -1) {
				key[i] = alpha[j];
				i++;
			} 

		}

	} 

	/**
	 * Read one line from the given file and take the first 26 characters for the key
	 * Throw an IllegalArgumentException if this does not result in a valid key
	 * A valid key should have all the letters from a to z
	 * @param key
	 * @throws IllegalArgumentException if the file or its contents aren't valid
	 */
	public Decoder(File key) {
		//TODO implement this constructor
		try {
			Scanner scr = new Scanner(new File("key.txt")); //opens file
			if(!scr.hasNextLine()) { //checks if file is blank
				throw new IllegalArgumentException ("File was blank.");	
			}

			String line = scr.nextLine(); //reads file
			scr.close(); //close scanner

			line = line.substring(0,26); //takes first 26 characters 
			for (int i = 0; i < line.length(); i++) { //loop to check if all characters are letters
				if (!isLetter(line.charAt(i)));{
					throw new IllegalArgumentException ("Invaild key.");
				}
			}
			for (int i = 0; i < line.length(); i++) { //loop checking for repeating characters
				for (int j = i + 1; j < line.length(); j++) { 
					if (line.charAt(i) == line.charAt(j)) {
						throw new IllegalArgumentException ("Invalid key.");
					}
				}
			}
			for(int i= 0; i < key.length(); i++) { //loop putting file contents into key[]
				this.key[i] = line.charAt(i);
			}
		}
		catch (FileNotFoundException e) {
			throw new IllegalArgumentException ("File cannot be located.");
		}
	}

	/**
	 * return a copy of the key
	 * @return a copy of the key
	 */
	public char[] getKey() {
		return Arrays.copyOf(key, 26);
	}

	@Override
	public String toString() {
		String output = "Key looks like: \n";
		for(int i = 0; i<26; i++) {
			output = output + (toLetter(i)) +"->" + key[i] + "\n";
		}
		return output;
	}


	/**
	 * Take the line and translate it according to the current key
	 * @param lineIn the line to be translated
	 * @return the translated line
	 * @throws IllegalArgumentException if lineIn is null
	 */
	public String translateLine(String lineIn) {
		//TODO translate the line appropriately
		// Make sure you follow the directions in the handout
		String translated = "";
		
		if (lineIn == null) {
			throw new IllegalArgumentException ("Line is empty");
		}
		lineIn = lineIn.toLowerCase();
		int index = 0;

		for (int i = 0; i < lineIn.length(); i++) {
			if (!isLetter(lineIn.charAt(i))) {
				char c = lineIn.charAt(i);
				translated += c;

			}
			else {
				index = lineIn.charAt(i) - 97;
				char ch = key[index];
				translated += ch;

			}
			/*for (int i = 0; i < key.length; i++) {
			char ch = lineIn.charAt(i);
			String str = new String(key);
			ch = str.charAt(i);
			translated += ch; 

		}*/
		}
		return translated;
	}

	/**
	 * Method that stores the key in a file
	 * @param f the file to store the key in
	 */
	public void saveKey(File f) {
		//TODO save the current key to a file
		// Make sure you follow the directions in the handout
		try {
			PrintWriter pw = new PrintWriter("f.txt");
			for (int i = 0; i < this.key.length; i++) {
				pw.print(key[i]);

				pw.close();
			}
		}
		catch (FileNotFoundException e) {
			throw new IllegalArgumentException ("File not found.");
		}
	}

	//some helper methods you can use

	/**
	 * Method that removes duplicate characters from a string.
	 * Also removes all characters that are not lowercase alphabetical.
	 * It retains only the first occurrence of each char value.
	 * @param word the string to remove duplicates from
	 * @return the new string with duplicates removed
	 */
	private static String cleanString(String word) { 
		final StringBuilder output = new StringBuilder();   
		for (int i = 0; i < word.length(); i++) { 
			String character = word.substring(i, i + 1); 
			if (output.indexOf(character) < 0) 
				if(isLetter(character.charAt(0)))
					output.append(character); 
		}
		return output.toString(); 
	}

	private static char toLetter(int index) {
		return (char)(index + 'a');
	}

	private static int toIndex(char letter) {
		return letter - 'a';
	}

	private static boolean isLetter(char c) {
		return c >= 'a' && c < 'a' + 26;
	} 
}


