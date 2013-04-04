package com.alycarter.hood.game.menu;

import com.alycarter.hood.game.Game;

public class GameOverMenu extends Menu{

	public GameOverMenu(Game game) {
		super(game);
		addButton(new Button(game,"quit",game.getWidth()/3,game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				game.gameOverMenu.hideMenu();
				game.mainMenu.showMenu();
			}
		});
		addButton(new Button(game,"restart",game.getWidth()/3,50+game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				game.getLevel().loadLevel();
				hideMenu();
			}
		});
	}

}
