package com.alycarter.hood.game.menu;

import com.alycarter.hood.game.Game;

public class PauseMenu extends Menu{

	public PauseMenu(Game game) {
		super(game);
		hideMenu();
		addButton(new Button(game,10,10,100,100){
			public void onClick(){
				game.pauseMenu.hideMenu();
			}
		});
		addButton(new Button(game,10,120,100,100){
			public void onClick(){
				game.pauseMenu.hideMenu();
				game.mainMenu.showMenu();
			}
		});
	}

}
