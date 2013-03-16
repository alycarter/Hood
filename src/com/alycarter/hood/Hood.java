package com.alycarter.hood;

import javax.swing.JFrame;

import com.alycarter.hood.game.Game;

public class Hood {

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		Game game = new Game(frame);
		game.start();
	}

}
