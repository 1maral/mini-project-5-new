import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Creates a set of mappings of an AAC that has two levels,
 * one for categories and then within each category, it has
 * images that have associated text to be spoken. This class
 * provides the methods for interacting with the categories
 * and updating the set of images that would be shown and handling
 * an interactions.
 * 
 * @author Catie Baker & Maral Bat-Erdene
 *
 */
public class AACMappings implements AACPage {
	// +--------+------------------------------------------------------
	// | Fields |
	// +--------+
	/**
	 * An Associative Array that maps either filenames or names to categories.
	 */
	AssociativeArray<String,AACCategory> arrCat;

	/**
	 * The currently selected category.
	 */
	AACCategory currentCategory;

	/**
	 * The default category / home screen.
	 */
	AACCategory defaultCategory;
	// +--------------+------------------------------------------------
	// | Constructors |
	// +--------------+
	/**
	 * Creates a set of mappings for the AAC based on the provided
	 * file. The file is read in to create categories and fill each
	 * of the categories with initial items. The file is formatted as
	 * the text location of the category followed by the text name of the
	 * category and then one line per item in the category that starts with
	 * > and then has the file name and text of that image
	 * 
	 * for instance:
	 * img/food/plate.png food
	 * >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon
	 * img/clothing/hanger.png clothing
	 * >img/clothing/collaredshirt.png collared shirt
	 * 
	 * represents the file with two categories, food and clothing
	 * and food has french fries and watermelon and clothing has a 
	 * collared shirt
	 * @param filename the name of the file that stores the mapping information
	 * @throws IOException 
	 */
	public AACMappings(String filename) {
		this.arrCat = new AssociativeArray<String,AACCategory>();
		try {
			// Open the file for reading
			FileReader myFile = new FileReader(filename);
			BufferedReader reader = new BufferedReader(myFile);
			String line = reader.readLine();

			// Set the current and default category to home
			this.defaultCategory = new AACCategory("home");
			this.currentCategory = this.defaultCategory;

			while (line != null) {
				String[] lineArr = line.split(" ", 2);
				// Check if the line is a category (does not start with '>')
				if (line.charAt(0) != '>'){
					String imageLoc = lineArr[0];
					String category = lineArr[1];
					
					// Create a new AACCategory object and add it to arrCat
					this.currentCategory = new AACCategory(category);
					try {
							this.arrCat.set(imageLoc, this.currentCategory);
					} catch (NullKeyException e) {
							//Do nothing
					} // try/catch
				} else {
					// If the line starts with '>', it's an item for the current category
					if (this.currentCategory != this.defaultCategory) {
							String itemImgLoc = lineArr[0].substring(1);
							String itemText = lineArr[1];

							// Add the item to the current category
							this.currentCategory.addItem(itemImgLoc, itemText);
					} else {
						System.err.println("Error: Attempted to add item to home");
					} // if/else
				} // if/else
				// read next line
				line = reader.readLine();
			} // while
			this.currentCategory = this.defaultCategory;
			reader.close();
		} catch (Exception ex) {
			// Do nothing
			System.err.println("Error: " + ex.getMessage());
		} // try/catch
	} // AACMappings
	
  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+
	/**
	 * Given the image location selected, it determines the action to be
	 * taken. This can be updating the information that should be displayed
	 * or returning text to be spoken. If the image provided is a category, 
	 * it updates the AAC's current category to be the category associated 
	 * with that image and returns the empty string. If the AAC is currently
	 * in a category and the image provided is in that category, it returns
	 * the text to be spoken.
	 * @param imageLoc the location where the image is stored
	 * @return if there is text to be spoken, it returns that information, otherwise
	 * it returns the empty string
	 * @throws NoSuchElementException if the image provided is not in the current 
	 * category
	 */
public String select(String imageLoc) throws KeyNotFoundException {
	try {
		// Check if the image location corresponds to a category in arrCat
		if (this.currentCategory == this.defaultCategory && arrCat.hasKey(imageLoc)) {
			// If a category, then update the current category
			this.currentCategory = this.arrCat.get(imageLoc);
			// Return an empty string because it's category
			return new String();
		} else if (this.currentCategory != this.defaultCategory && this.currentCategory.arrWords.hasKey(imageLoc)) {
			// Check if we are in a valid category and the image is in that category
			// Return the text associated with the image
			return this.currentCategory.arrWords.get(imageLoc);
		} else {
			// If the image location is not found, throw an exception
			throw new KeyNotFoundException();
		} // if/else
	} catch (KeyNotFoundException e) {
			throw new KeyNotFoundException();
	} // try/catch
} // select(String)

	
	/**
	 * Provides an array of all the images in the current category
	 * @return the array of images in the current category; if there are no images,
	 * it should return an empty array
	 */
	public String[] getImageLocs() {
		// Check if currentCategory is home screen
		if (this.currentCategory == this.defaultCategory) {
			int catSize = this.arrCat.size();
			// Create an array to hold the image locations
			String[] catImgLocs = new String[catSize];
			for (int i = 0; i < catSize; i++){
				try {
					catImgLocs[i] = this.arrCat.getKey(i);
				} catch (Exception e) {
					// Do nothing
				} // try/catch
			} // for
			return catImgLocs;
	  	} // if

		// Set the size as the number of items in the category
		int currentSize = this.currentCategory.arrWords.size();

		// If there are no items, return an empty array
		if (currentSize == 0) {
			return new String[] {};
		} // if

		// Create an array to hold the image locations
		String[] imgLocs = new String[currentSize];

		for (int i = 0; i < currentSize; i++){
			try {
				imgLocs[i] = this.currentCategory.arrWords.getKey(i);
			} catch (Exception e) {
				// Do nothing
			} // try/catch
		} // for
		return imgLocs;
	}
	
	/**
	 * Resets the current category of the AAC back to the default
	 * category
	 */
	public void reset() {
		this.currentCategory = this.defaultCategory;
	}
	
	
	/**
	 * Writes the ACC mappings stored to a file. The file is formatted as
	 * the text location of the category followed by the text name of the
	 * category and then one line per item in the category that starts with
	 * > and then has the file name and text of that image
	 * 
	 * for instance:
	 * img/food/plate.png food
	 * >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon
	 * img/clothing/hanger.png clothing
	 * >img/clothing/collaredshirt.png collared shirt
	 * 
	 * represents the file with two categories, food and clothing
	 * and food has french fries and watermelon and clothing has a 
	 * collared shirt
	 * 
	 * @param filename the name of the file to write the
	 * AAC mapping to
	 */
	public void writeToFile(String filename) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			// Loop through each category
			for (int i = 0; i < this.arrCat.size(); i++) {
				// Get the category location (known as key)
				String catLoc = this.arrCat.getKey(i);
				// Get the corresponding AACCategory object
				AACCategory curCat = this.arrCat.get(catLoc);
				// Write category line (category location + category name)
				String categoryLine = catLoc + " " + curCat.getCategory();
				writer.write(categoryLine);
				writer.newLine();
				// Get the items in the current category
				String[] curImgArr = curCat.getImageLocs();
				
				// Write each item in the category
				for (int w = 0; w < curImgArr.length; w++) {
					writer.write(">" + curImgArr[w] + " " + curCat.select(curImgArr[w]));
					writer.newLine();  // New line after each item
				} // for each item
			} // for each category
		} catch (Exception e) {
				System.err.println("An error occurred while writing to the file.");
		} // try/catch
	} // writeToFile
	
	/**
	 * Adds the mapping to the current category (or the default category if
	 * that is the current category)
	 * @param imageLoc the location of the image
	 * @param text the text associated with the image
	 */
	public void addItem(String imageLoc, String text) {
		// If the current category is the default (home), create a new category
    if (this.currentCategory == this.defaultCategory) {
			// Create a new category
			AACCategory newCat = new AACCategory(text);
			// Add the new category to the associative array
			try {
				this.arrCat.set(imageLoc, newCat);
			} catch (NullKeyException e) {
				// Do Nothing
			} // try/catch
		} // if
		// Add the new item (image location and text) to the current category
    this.currentCategory.addItem(imageLoc, text);
	} // addItem


	/**
	 * Gets the name of the current category
	 * @return returns the current category or the empty string if 
	 * on the default category
	 */
	public String getCategory() {
		if (this.currentCategory == this.defaultCategory) {
			return new String();
		} // if
		return this.currentCategory.getCategory();
	} // getCategory


	/**
	 * Determines if the provided image is in the set of images that
	 * can be displayed and false otherwise
	 * @param imageLoc the location of the category
	 * @return true if it is in the set of images that
	 * can be displayed, false otherwise
	 */
	public boolean hasImage(String imageLoc) {
		// Loop through each category
		for (int i = 0; i < this.arrCat.size(); i++) {
			try {
				// Get the category location
				String catLoc = this.arrCat.getKey(i);
				// Get the corresponding AACCategory object
				AACCategory curCat = this.arrCat.get(catLoc);
				// Get the items in the current category
				String[] curImgArr = curCat.getImageLocs();
				
				// Compare each item in the category
				for (int w = 0; w < curImgArr.length; w++) {
						if (curImgArr[w].equals(imageLoc)) {
								return true;
						} // if
				} // for each item
			} // for each category
			catch (Exception ex) {
				// Do Nothing
			}
		} // for
		return false;
	} // hasImage(String)
} // class AACMappings
