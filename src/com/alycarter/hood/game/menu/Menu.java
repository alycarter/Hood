package com.alycarter.hood.game.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.alycarter.hood.game.Font;
import com.alycarter.hood.game.Game;

public class Menu {
	
	private boolean shown;
	
	public Game game;
	
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private BufferedImage buttonImg;
	public Menu(Game game) {
		this.game=game;
		try {
			buttonImg = ImageIO.read(Menu.class.getResourceAsStream("/buttons.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			//g.clearRect((int)b.rectangle.getX(),(int) b.rectangle.getY(),(int) b.rectangle.getWidth(), (int)b.rectangle.getHeight());
			g.drawImage(buttonImg,(int)b.rectangle.getX()-10,(int) b.rectangle.getY()-10,(int) b.rectangle.getWidth()+10, (int)b.rectangle.getHeight()+17,null);
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
