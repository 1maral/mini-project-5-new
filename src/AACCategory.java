import java.util.NoSuchElementException;

import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;

/**
 * Represents the mappings for a single category of items that should
 * be displayed
 * 
 * @author Catie Baker & Maral Bat-Erdene
 *
 */
public class AACCategory implements AACPage {
	// +--------+------------------------------------------------------
	// | Fields |
	// +--------+
	/**
	 * The name of the category. 
	 */
	String category;

	/**
	 * An Associative Array that maps image locations with words.
	 */
	AssociativeArray<String, String> arrWords;

	// +--------------+------------------------------------------------
	// | Constructors |
	// +--------------+

	/**
	 * Creates a new empty category with the given name
	 * @param name the name of the category
	 */
	public AACCategory(String name) {
		this.category = name;
		this.arrWords = new AssociativeArray<String, String>();
	} // AACCategory
	
	// +------------------+--------------------------------------------
	// | Standard Methods |
	// +------------------+

	/**
	 * Adds the image location, text pairing to the category
	 * @param imageLoc the location of the image
	 * @param text the text that image should speak
	 * @throws NullKeyException 
	 */
	public void addItem(String imageLoc, String text) throws NullKeyException {
		this.arrWords.set(imageLoc, text);
	} // addItem()

	/**
	 * Returns an array of all the images in the category
	 * @return the array of image locations; if there are no images,
	 * it should return an empty array
	 * @throws Exception 
	 */
	public String[] getImageLocs() throws Exception {
		// Create an array to hold the image locations
		// sized according to the number of images in the category
		String[] arrImg = new String[this.arrWords.size()];

		// Check if there are any images
		if (this.arrWords.size() == 0) {
			// Return the empty array
			return (new String[] {});
		} else {
			// Iterate through the category to get the image locations
			for (int i = 0; i < this.arrWords.size(); i++){
				arrImg[i] = (String) this.arrWords.getKey(i);
			} // for
			return arrImg;
		} // if/else
	} //getImageLocs()

	/**
	 * Returns the name of the category
	 * @return the name of the category
	 */
	public String getCategory() {
		return this.category;
	} // getCategory()

	/**
	 * Returns the text associated with the given image in this category
	 * @param imageLoc the location of the image
	 * @return the text associated with the image
	 * @throws KeyNotFoundException 
	 * @throws NoSuchElementException if the image provided is not in the current
	 * 		   category
	 */
	public String select(String imageLoc) throws KeyNotFoundException {
		return this.arrWords.get(imageLoc);
	} // select(String)

	/**
	 * Determines if the provided images is stored in the category
	 * @param imageLoc the location of the category
	 * @return true if it is in the category, false otherwise
	 */
	public boolean hasImage(String imageLoc) {
		return this.arrWords.hasKey(imageLoc);
	} // hasImage(String)
} // class AACCategory
