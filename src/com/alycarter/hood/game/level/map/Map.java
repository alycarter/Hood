package com.alycarter.hood.game.level.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.alycarter.hood.game.level.TextureTileLoader;
import com.alycarter.hood.game.level.map.tile.Tile;

public class Map {
	private Tile[] tiles;
	private int mapWidth;
	private int mapHeight;
	
	public BufferedImage mapTexture;
	
	public Map() {
		try{
			BufferedReader r = new BufferedReader(new InputStreamReader(Map.class.getResourceAsStream("/0.txt")));
			this.mapHeight=Integer.valueOf(r.readLine());
			this.mapWidth=Integer.valueOf(r.readLine());
			tiles = new Tile[mapWidth*mapHeight];
			r.readLine();
			int xt;
			int yt;
			for (xt=0;xt<mapWidth;xt++){
				for (yt=0;yt<mapHeight;yt++){
					try{
						if(r.readLine().equals("1")){
							tiles[(yt*mapHeight)+xt]=new Tile(true);
						}else{
							tiles[(yt*mapWidth)+xt]=new Tile(false);
						}
					}catch(Exception e){e.printStackTrace();}
				}
			}	
			r.readLine();
			mapTexture = new BufferedImage(mapWidth*Tile.tileResolution,mapHeight*Tile.tileResolution,BufferedImage.TYPE_INT_ARGB);
			Graphics g = mapTexture.getGraphics();
			TextureTileLoader tileMap = new TextureTileLoader("mapTiles.png",192);
			for (xt=0;xt<mapWidth;xt++){
				for (yt=0;yt<mapHeight;yt++){
					try{
						g.drawImage(tileMap.getTile(Integer.valueOf(r.readLine())),xt*Tile.tileResolution, yt*Tile.tileResolution,Tile.tileResolution, Tile.tileResolution, null);
					}catch(Exception e){e.printStackTrace();}
				}
			}	
		}catch(Exception e){}
	}
	
	public boolean getMovable(double x, double y){
		if((mapWidth*(int)y)+(int)x<tiles.length){
			return tiles[(mapWidth*(int)y)+(int)x].getMovable();
		}else{
			return false;
		}
	}
	
	public int getMapWidth(){
		return mapWidth;
	}
	
	public int getMapHeight(){
		return mapHeight;
	}

}
