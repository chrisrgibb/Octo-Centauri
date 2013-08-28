package menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * The root frame of the game. Presents all the menus, and can show the game.
 * 
 * @author muruphenr , antunomate , richarhayd
 * 
 */
public class MainFrame extends JFrame {
	Stack<JPanel> frameStack;
	
	public MainFrame() {
		frameStack = new Stack<JPanel>();
		frameStack.add(new MainMenuPanel(this));
		this.add(frameStack.peek());
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = ge.getScreenDevices();
		for (int i = 0; i < 1; i++) {
			if (devices[i].isFullScreenSupported()) {
				this.setUndecorated(true);
				devices[i].setFullScreenWindow(this);
				//this.setSize(this.getToolkit().getScreenSize());
				this.validate();
				//this.setVisible(true);
			}
		}
	}

	public static void main(String args[]) {
		MainFrame f = new MainFrame();
	}

	/**
	 * Add another JPanel. The current JPanel is stored.
	 * 
	 * @param comp
	 */
	public void addMenu(JPanel comp) {
		if (frameStack.size() > 0)
			this.remove(frameStack.peek());
		frameStack.add(comp);
		this.add(comp);
		comp.requestFocus();
		this.validate();
		this.repaint();
	}

	/**
	 * Return to previous JPanel.
	 */
	public void back() {
		if (frameStack.size() == 1)
			return;

		this.remove(frameStack.pop());
		this.add(frameStack.peek());
		this.validate();
		this.repaint();
	}
}
