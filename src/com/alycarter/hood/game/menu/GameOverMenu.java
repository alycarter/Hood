package com.alycarter.hood.game.menu;

import com.alycarter.hood.game.Game;

public class GameOverMenu extends Menu{

	public GameOverMenu(Game game) {
		super(game);
		addButton(new Button(game,10,10,100,100){
			public void onClick(){
				game.mainMenu.showMenu();
				game.gameOverMenu.hideMenu();
			}
		});
		addButton(new Button(game,10,120,100,100){
			public void onClick(){
				game.getLevel().loadLevel();
				hideMenu();
			}
		});
	}

}
