package com.alycarter.hood.game.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.alycarter.hood.game.Game;

public class Menu {
	
	private boolean shown;
	
	public Game game;
	
	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	public Menu(Game game) {
		this.game=game;
	}
	
	public void update(){
		for (int i=0;i<buttons.size();i++){
			buttons.get(i).update();
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.BLACK);
		for (int i=0;i<buttons.size();i++){
			Button b = buttons.get(i);
			int size = (int) (b.rectangle.getHeight()-2);
			g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,size));
			g.clearRect((int)b.rectangle.getX(),(int) b.rectangle.getY(),(int) b.rectangle.getWidth(), (int)b.rectangle.getHeight());
			g.drawRect((int)b.rectangle.getX(),(int) b.rectangle.getY(),(int) b.rectangle.getWidth(), (int)b.rectangle.getHeight());
			g.drawString(b.name,(int)( b.rectangle.getX()+(b.rectangle.width/2)-((size*b.name.length())/4)), (int)(b.rectangle.getY())+size-2);
		}
	}
	
	public void addButton(Button b){
		buttons.add(b);
	}
	
	public boolean isShown(){
		return shown;
	}
	
	public void hideMenu(){
		shown=false;
		game.hideCursor();
	}
	
	public void showMenu(){
		shown=true;
		game.showCursor();
	}

}
