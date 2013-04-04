package com.alycarter.hood.game.menu;

import com.alycarter.hood.game.Game;

public class MainMenu extends Menu{

	public MainMenu(Game game) {
		super(game);
		addButton(new Button(game,"play",10,10,100,25){
			public void onClick(){
				game.getLevel().loadLevel();
				hideMenu();
			}
		});
		addButton(new Button(game,"settings",10,50,100,25){
			public void onClick(){
				game.settingsMenu.showMenu();
			}
		});
		addButton(new Button(game,"quit",10,90,100,25){
			public void onClick(){
				game.endGame();
				hideMenu();
			}
		});
	}
	
	

}
