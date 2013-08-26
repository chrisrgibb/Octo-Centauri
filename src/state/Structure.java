package state;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Structure {
	/**
	 * The coordinates of the tile under the bottom corner of the structure.
	 */
	private int x, y;

	/**
	 * Size of the structure, in tiles.
	 */
	private int width, height;

	/**
	 * The structure's image.
	 */
	private Image image;

	public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public Image getImage() {return image;}

	public Structure(int x, int y, int width, int height, String image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = new ImageIcon("Assets/Environment Objects/"+image+".png").getImage();
	}
}
