package com.alycarter.hood.game.menu;

import com.alycarter.hood.game.Game;

public class GameOverMenu extends Menu{

	public GameOverMenu(Game game) {
		super(game);
		addButton(new Button(game,"quit",10,10,100,100){
			public void onClick(){
				game.gameOverMenu.hideMenu();
				game.mainMenu.showMenu();
			}
		});
		addButton(new Button(game,"restart",10,120,100,100){
			public void onClick(){
				game.getLevel().loadLevel();
				hideMenu();
			}
		});
	}

}
