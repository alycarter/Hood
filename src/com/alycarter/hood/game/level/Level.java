package com.alycarter.hood.game.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.Camera;
import com.alycarter.hood.game.level.entity.Cursor;
import com.alycarter.hood.game.level.entity.Entity;
import com.alycarter.hood.game.level.entity.mob.Player;
import com.alycarter.hood.game.level.map.Map;
import com.alycarter.hood.game.level.map.tile.Tile;

public class Level extends Thread{
	public Game game;
	
	private Map map;
	
	public  ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Player player;
	public Cursor cursor;
	public Camera camera;

	public boolean loaded=false;
	public int towerPoints = 0;
	
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	
	public Level(Game game) {
		this.game=game;
	}
	
	public void loadLevel(){
		loaded=false;
		new Thread(){
			public void run(){
				map=new Map();
				entities=new ArrayList<Entity>();
				waves= new ArrayList<Wave>();
				onLoad();
				cursor = new Cursor(game);
				camera = new Camera(game);
				loaded=true;
			}
		}.start();
		
	}
	
	public void onLoad(){
		
	}
	
	public void addWave(Wave w){
		waves.add(w);
	}
	
	public void nextWave(){
		waves.remove(0);
		if(waves.size()==0){
			game.gameOverMenu.showMenu();
		}
	}
	
	public void update(){
		if(game.controls.isTyped(KeyEvent.VK_K)){
			for(int e = 0; e<entities.size();e++){
				if(entities.get(e).entityType==Entity.TYPE_ENEMY){
					entities.remove(e);
					e-=1;
				}
			}
		}
		for(int e = 0; e<entities.size();e++){
			if(entities.get(e).isRemoved()){
				entities.remove(e);
				e-=1;
			}
		}
		cursor.update();
		camera.update();
		for(int e = 0; e<entities.size();e++){
			if(! entities.get(e).isRemoved()){
				entities.get(e).update();
			}
		}
		if(waves.size()>0){
			waves.get(0).update();
		}
		
	}
	
	public void render(Graphics2D g){
		g.drawImage(getMap().mapTexture, (int)(game.windowWidth/2-(camera.getLocation().getX()*Tile.tileResolution)),(int)(game.windowHeight/2-(camera.getLocation().getY()*Tile.tileResolution)), null);
		drawEntity(cursor, g);
		for (int e = 0;e<entities.size();e++){
			if(! entities.get(e).isRemoved()){
				drawEntity(entities.get(e),g);
			}
		}
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(towerPoints), game.windowWidth/2, 20);
		if(game.debugMode){
			g.drawString("fps: "+String.valueOf(game.getFps()), 10, 20);
			g.drawString("ups: "+String.valueOf(game.getUps()), 10, 40);
			g.drawString("entities in list: "+entities.size(), 10, 60);
			g.drawString("player x:"+player.getLocation().getX(), 10, 80);
			g.drawString("player y:"+player.getLocation().getY(), 10, 100);
		}
	}
	
	private void drawEntity(Entity e, Graphics2D g){
		int screenX=(int) (game.windowWidth/2-((camera.getLocation().getX()-e.getLocation().getX())*Tile.tileResolution));
		int screenY=(int) (game.windowHeight/2-((camera.getLocation().getY()-e.getLocation().getY())*Tile.tileResolution));
		g.setColor(new Color(0f,0f,0f,0.4f));
//		if(e.shouldShowShadows()){
//			g.fillOval((int)(screenX-((Tile.tileResolution*e.getHitBoxWidth())/2)), (int)(screenY-((Tile.tileResolution*e.getHitBoxWidth())/2)), (int)(Tile.tileResolution*e.getHitBoxWidth()), (int)(Tile.tileResolution*e.getHitBoxWidth()));
//		}
		g.drawImage(e.getImage(),screenX-(int)((Tile.tileResolution*e.getImageWidth())/2),screenY-(int)((Tile.tileResolution*e.getImageWidth())/2),(int) (Tile.tileResolution*e.getImageWidth()),(int)(Tile.tileResolution*e.getImageWidth()),null);
		if(e.shouldRenderHealthBar()){
			g.setColor(Color.RED);
			g.fillRect(screenX-(int)((Tile.tileResolution*e.getImageWidth())/2), screenY-(int)((Tile.tileResolution*e.getImageWidth())/2),(int)( Tile.tileResolution*e.getImageWidth()),(int)( Tile.tileResolution/15*e.getImageWidth()));
			double healthPercent = e.getHealth()/e.getMaxHealth();
			g.setColor(Color.GREEN);
			g.fillRect(screenX-(int)((Tile.tileResolution*e.getImageWidth())/2), screenY-(int)((Tile.tileResolution*e.getImageWidth())/2), (int)(Tile.tileResolution*healthPercent*e.getImageWidth()),(int)( Tile.tileResolution/15*e.getImageWidth()));
			g.setColor(Color.BLACK);
			g.drawRect(screenX-(int)((Tile.tileResolution*e.getImageWidth())/2), screenY-(int)((Tile.tileResolution*e.getImageWidth())/2),(int)( Tile.tileResolution*e.getImageWidth()),(int)( Tile.tileResolution/15*e.getImageWidth()));
		}
		if(game.debugMode){
			g.setColor(Color.BLACK);
			g.fillRect(screenX,screenY,1,1);
			g.drawOval(screenX-(int)((Tile.tileResolution*e.getHitBoxWidth())/2), screenY-(int)((Tile.tileResolution*e.getHitBoxWidth())/2),(int) (Tile.tileResolution*e.getHitBoxWidth()),(int)(Tile.tileResolution*e.getHitBoxWidth()));
			g.drawOval(screenX-(int)((Tile.tileResolution*e.getImageWidth())/2), screenY-(int)((Tile.tileResolution*e.getImageWidth())/2),(int) (Tile.tileResolution*e.getImageWidth()),(int)(Tile.tileResolution*e.getImageWidth()));
			g.drawLine(screenX, screenY, screenX+(int)(e.getDirectionAsVector().getX()*Tile.tileResolution*e.getHitBoxWidth()), screenY+(int)(e.getDirectionAsVector().getY()*Tile.tileResolution*e.getHitBoxWidth()));
		}
	}
	
	public Map getMap(){
		return map;
	}

}
