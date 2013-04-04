package com.alycarter.hood.game.menu;

import com.alycarter.hood.game.Game;

public class PauseMenu extends Menu{

	public PauseMenu(Game game) {
		super(game);
		hideMenu();
		addButton(new Button(game,"continue",10,10,100,25){
			public void onClick(){
				game.pauseMenu.hideMenu();
			}
		});
		addButton(new Button(game,"settings",10,50,100,25){
			public void onClick(){
				game.settingsMenu.showMenu();
			}
		});
		addButton(new Button(game,"quit",10,90,100,25){
			public void onClick(){
				game.pauseMenu.hideMenu();
				game.mainMenu.showMenu();
			}
		});
	}

}
