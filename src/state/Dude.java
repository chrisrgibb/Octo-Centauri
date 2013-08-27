package state;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Dude {
	/**
	 * The coordinates of the tile under the bottom corner of the dude.
	 */
	private int x, y;
	private int TILE_HEIGHT = 32;
	private int TILE_WIDTH = 64;
	private int NUM_SPRITES = 16;

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

	public Dude(int x, int y, int width, int height, String image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = new ImageIcon(image).getImage();


	}

	public void draw(Graphics g, int width, int camx, int camy){
		int x = this.x - camx;
		int y = this.y - camy;
		int i = (width/2)-(image.getWidth(null)/2) + (x-y) * (TILE_WIDTH/2);
		int j =  (x+y) * (TILE_HEIGHT/ 2) ;
		g.drawImage(image, i, j-image.getHeight(null), image.getWidth(null), image.getWidth(null), null);
	}
}
