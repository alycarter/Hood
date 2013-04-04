package com.alycarter.hood.game.menu;

import com.alycarter.hood.game.Game;

public class MainMenu extends Menu{

	public MainMenu(Game game) {
		super(game);
		addButton(new Button(game,"play",game.getWidth()/3,game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				game.getLevel().loadLevel();
				hideMenu();
			}
		});
		addButton(new Button(game,"settings",game.getWidth()/3,50+game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				game.settingsMenu.showMenu();
			}
		});
		addButton(new Button(game,"quit",game.getWidth()/3,100+game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				game.endGame();
				hideMenu();
			}
		});
	}
	
	

}
