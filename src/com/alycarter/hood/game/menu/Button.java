package com.alycarter.hood.game.menu;

import java.awt.Rectangle;

import com.alycarter.hood.game.Game;

public class Button {
	public Game game;
	public Rectangle rectangle;
	public String name;
	
	public Button(Game game, String name, int x, int y,int width, int height) {
		this.game=game;
		this.name=name;
		rectangle=new Rectangle(x,y,width,height);
	}
	
	public void update(){
		if(rectangle.contains(game.controls.mouseLocation)&&game.controls.leftMouseClicked()){
			onClick();
		}
	}
	
	public void onClick(){
		
	}

}
