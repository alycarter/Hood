package com.alycarter.hood.game.level;

import java.awt.Color;
import java.awt.Graphics2D;
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
				if(waves.size()>0){
					waves.get(0).startWave();
				}
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
		if(waves.size()>0){
			waves.get(0).startWave();
		}else{
			game.gameOverMenu.showMenu();
		}
	}
	
	public void update(){
		for(int e = 0; e<entities.size();e++){
			if(entities.get(e).isRemoved()){
				entities.remove(e);
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
		g.drawImage(getMap().mapTexture, (int)(game.WIDTH/2-(camera.getLocation().getX()*Tile.TILERESOLUTION)),(int)(game.HEIGHT/2-(camera.getLocation().getY()*Tile.TILERESOLUTION)), null);
		drawEntity(cursor, g);
		for (int e = 0;e<entities.size();e++){
			if(! entities.get(e).isRemoved()){
				drawEntity(entities.get(e),g);
			}
		}
		g.drawString(String.valueOf(towerPoints), game.WIDTH/2, 20);
		if(game.debugMode){
			g.drawString("fps: "+(1/game.getDeltaTime()), 10, 20);
			g.drawString("entities in list: "+entities.size(), 10, 40);
			g.drawString("player x:"+player.getLocation().getX(), 10, 60);
			g.drawString("player y:"+player.getLocation().getY(), 10, 80);
		}
	}
	
	private void drawEntity(Entity e, Graphics2D g){
		g.setColor(Color.BLACK);
		int screenX=(int) (game.WIDTH/2-((camera.getLocation().getX()-e.getLocation().getX())*Tile.TILERESOLUTION));
		int screenY=(int) (game.HEIGHT/2-((camera.getLocation().getY()-e.getLocation().getY())*Tile.TILERESOLUTION));
		g.drawImage(e.getImage(),screenX-(int)((Tile.TILERESOLUTION*e.getImageWidth())/2),screenY-(int)((Tile.TILERESOLUTION*e.getImageWidth())/2),(int) (Tile.TILERESOLUTION*e.getImageWidth()),(int)(Tile.TILERESOLUTION*e.getImageWidth()),null);
		if(e.shouldRenderHealthBar()){
			g.setColor(Color.RED);
			g.fillRect(screenX-(int)((Tile.TILERESOLUTION*e.getImageWidth())/2), screenY-(int)((Tile.TILERESOLUTION*e.getImageWidth())/2),(int)( Tile.TILERESOLUTION*e.getImageWidth()),(int)( Tile.TILERESOLUTION/15*e.getImageWidth()));
			double healthPercent = e.getHealth()/e.getMaxHealth();
			g.setColor(Color.GREEN);
			g.fillRect(screenX-(int)((Tile.TILERESOLUTION*e.getImageWidth())/2), screenY-(int)((Tile.TILERESOLUTION*e.getImageWidth())/2), (int)(Tile.TILERESOLUTION*healthPercent*e.getImageWidth()),(int)( Tile.TILERESOLUTION/15*e.getImageWidth()));
			g.setColor(Color.BLACK);
			g.drawRect(screenX-(int)((Tile.TILERESOLUTION*e.getImageWidth())/2), screenY-(int)((Tile.TILERESOLUTION*e.getImageWidth())/2),(int)( Tile.TILERESOLUTION*e.getImageWidth()),(int)( Tile.TILERESOLUTION/15*e.getImageWidth()));
		}
		if(game.debugMode){
			g.fillRect(screenX,screenY,1,1);
			g.drawOval(screenX-(int)((Tile.TILERESOLUTION*e.getHitBoxWidth())/2), screenY-(int)((Tile.TILERESOLUTION*e.getHitBoxWidth())/2),(int) (Tile.TILERESOLUTION*e.getHitBoxWidth()),(int)(Tile.TILERESOLUTION*e.getHitBoxWidth()));
			g.drawOval(screenX-(int)((Tile.TILERESOLUTION*e.getImageWidth())/2), screenY-(int)((Tile.TILERESOLUTION*e.getImageWidth())/2),(int) (Tile.TILERESOLUTION*e.getImageWidth()),(int)(Tile.TILERESOLUTION*e.getImageWidth()));
			g.drawLine(screenX, screenY, screenX+(int)(e.getDirectionAsVector().getX()*Tile.TILERESOLUTION*e.getHitBoxWidth()), screenY+(int)(e.getDirectionAsVector().getY()*Tile.TILERESOLUTION*e.getHitBoxWidth()));
		}
	}
	
	public Map getMap(){
		return map;
	}

}
