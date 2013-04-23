package com.alycarter.hood.game.menu;

import java.awt.Graphics;

import com.alycarter.hood.game.Font;
import com.alycarter.hood.game.Game;

public class ControlsMenu extends Menu {

	public ControlsMenu(Game game) {
		super(game);
		addButton(new Button(game, "back", game.getWidth()/3, game.getHeight()/2, game.getWidth()/3, 25){
			@Override
			public void onClick() {
				game.controlsMenu.hideMenu();
				game.mainMenu.showMenu();
			}
		});
	}
	
	@Override
	public void onRender(Graphics g) {
		Font.drawString(g, "up:          w or up arrow", Game.defaultFont, 15, -2, game.getWidth()/3, game.getHeight()/4);
		Font.drawString(g, "down:        s or down arrow", Game.defaultFont, 15, -2, game.getWidth()/3, game.getHeight()/4+20);
		Font.drawString(g, "left:        a or left arrow", Game.defaultFont, 15, -2, game.getWidth()/3, game.getHeight()/4+40);
		Font.drawString(g, "right:       d or right arrow", Game.defaultFont, 15, -2, game.getWidth()/3, game.getHeight()/4+60);
		Font.drawString(g, "place tower: space", Game.defaultFont, 15, -2, game.getWidth()/3, game.getHeight()/4+80);
		Font.drawString(g, "swing sword: left mouse", Game.defaultFont, 15, -2, game.getWidth()/3, game.getHeight()/4+100);
		Font.drawString(g, "fire arrow:  hold and release right mouse", Game.defaultFont, 15, -2, game.getWidth()/3, game.getHeight()/4+120);
		Font.drawString(g, "aim:         move mouse", Game.defaultFont, 15, -2, game.getWidth()/3, game.getHeight()/4+140);
	}

}
