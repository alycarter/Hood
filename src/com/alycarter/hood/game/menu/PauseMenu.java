package com.alycarter.hood.game.menu;

import com.alycarter.hood.game.Game;

public class PauseMenu extends Menu{

	public PauseMenu(Game game) {
		super(game);
		hideMenu();
		addButton(new Button(game,"continue",game.getWidth()/3,game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				game.pauseMenu.hideMenu();
			}
		});
		addButton(new Button(game,"settings",game.getWidth()/3,50+game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				game.settingsMenu.showMenu();
			}
		});
		addButton(new Button(game,"quit",game.getWidth()/3,100+game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				game.pauseMenu.hideMenu();
				game.mainMenu.showMenu();
			}
		});
	}

}
