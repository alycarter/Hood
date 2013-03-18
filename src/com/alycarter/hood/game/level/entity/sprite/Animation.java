package com.alycarter.hood.game.level.entity.sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.TextureTileLoader;

public class Animation {
	
	private ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
	private double currentFramePointer=0;
	private Game game;
	
	private final String name;
	
	public Animation(Game game, String name,String textureFileName, int resolution, int frames) {
		TextureTileLoader t = new TextureTileLoader(textureFileName,resolution);
		for(int i=0;i<frames;i++){
			this.frames.add(t.getTile(i));
		}
		this.game=game;
		this.name=name;
	}
	
	public Animation(Game game, String name,TextureTileLoader spriteSheet, int frames) {
		for(int i=0;i<frames;i++){
			this.frames.add(spriteSheet.getTile(i));
		}
		this.game=game;
		this.name=name;
	}
	
	public void update(){
		currentFramePointer += game.getDeltaTime();
		if(currentFramePointer>=frames.size()){
			reset();
		}
	}
	
	public BufferedImage getCurrentFrame(){
		return(frames.get((int) currentFramePointer));
	}
	
	public void reset(){
		currentFramePointer = 0;
	}
	
	public String getName(){
		return name;
	}

}
