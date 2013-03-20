package com.alycarter.hood.game.level.map.tile;

public class Tile {
	private final boolean Movable;
	public static int tileResolution=128;
	
	public Tile(boolean movable) {
		this.Movable=movable;
	}
	
	public boolean getMovable(){
		return this.Movable;
	}

}
