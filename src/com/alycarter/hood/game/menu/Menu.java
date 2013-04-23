package com.alycarter.hood.game.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.alycarter.hood.game.Font;
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
		onRender(g);
		g.setColor(Color.BLACK);
		int overlap = -2;
		for (int i=0;i<buttons.size();i++){
			Button b = buttons.get(i);
			int size = (int) (b.rectangle.getHeight()-4);
			if((size-overlap)*b.name.length()>b.rectangle.getWidth()){
				size=(int)((double)(b.rectangle.getWidth()+(overlap*b.name.length()))/(double)b.name.length());
			}
			g.clearRect((int)b.rectangle.getX(),(int) b.rectangle.getY(),(int) b.rectangle.getWidth(), (int)b.rectangle.getHeight());
			g.drawRect((int)b.rectangle.getX(),(int) b.rectangle.getY(),(int) b.rectangle.getWidth(), (int)b.rectangle.getHeight());
			Font.drawString(g,b.name,Game.defaultFont,size,overlap,(int)(b.rectangle.getCenterX()-((size-overlap)*b.name.length()/2)), (int)(b.rectangle.getCenterY()-(size/2)));
		}
	}
	
	public void onRender(Graphics g) {
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
