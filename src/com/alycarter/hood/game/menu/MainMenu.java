package com.alycarter.hood.game.menu;

import java.awt.Graphics;

import com.alycarter.hood.game.Font;
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
		addButton(new Button(game,"controls",game.getWidth()/3,100+game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				game.controlsMenu.showMenu();
			}
		});
		addButton(new Button(game,"quit",game.getWidth()/3,150+game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				game.endGame();
				hideMenu();
			}
		});
	}
	
	@Override
	public void onRender(Graphics g) {
		Font.drawString(g, "Hood", Game.defaultFont, 100, -20, game.getWidth()/2-220, 100);
	}
	
	

}
