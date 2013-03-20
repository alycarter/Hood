package com.alycarter.hood.game.level.entity;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.TextureTileLoader;
import com.alycarter.hood.game.level.entity.particle.Particle;
import com.alycarter.hood.game.level.entity.sprite.Animation;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;
import com.alycarter.hood.game.level.map.tile.Tile;

public class Cursor extends Entity{

	private double sparkleDelay=0;
	
	public Cursor(Game game) {
		super(game,Entity.TYPE_UTILITY,new Point2D.Double(0, 0),1,1,0.25,0,false,false);
		hideHealthBar();
		sprite.addAnimationLayer(new CursorAnimation(game));
	}
	
	public void onUpdate(){
		double x;
		double y;
		x = ((getGame().windowWidth/2)-getGame().controls.mouseLocation.getX())/Tile.tileResolution;
		y = ((getGame().windowHeight/2)-getGame().controls.mouseLocation.getY())/Tile.tileResolution;
		x = (x-getGame().getLevel().camera.getLocation().getX())*-1;
		y = (y-getGame().getLevel().camera.getLocation().getY())*-1;
		if(x<0||y<0||x>getGame().getLevel().getMap().getMapWidth()||y>getGame().getLevel().getMap().getMapHeight()){
			getGame().showCursor();
		}else{
			getGame().hideCursor();
		}
		this.jumpToLocation(x, y);
		if(sprite.getAnimationLayer(0).getAnimationName().equals("turret")){
			sparkleDelay-=getGame().getDeltaTime();
			if(sparkleDelay<=0){
				x = (Math.random()*getImageWidth())-(getImageWidth()/2)+getLocation().getX();
				y = (Math.random()*getImageWidth())-(getImageWidth()/2)+getLocation().getY();
				Particle p = new Particle(getGame(),new Point2D.Double(x, y),0.15,0.3,Math.random()*360,0.1);
				p.sprite.addAnimationLayer(new ParticleAnimation(getGame()));
				p.sprite.getAnimationLayer(0).setDirection(Math.random()*360);
				getGame().getLevel().entities.add(p);
				sparkleDelay=0.1;
			}
		}
	}
	
	

	

}

class CursorAnimation extends AnimationLayer{
	private static TextureTileLoader target= new TextureTileLoader("target.png", 16);
	private static TextureTileLoader turret= new TextureTileLoader("turret.png", 128);
	private static TextureTileLoader cross= new TextureTileLoader("cross.png", 16);
	
	public CursorAnimation(Game game){
		addAnimation(new Animation(game,"target",target,1),true);
		addAnimation(new Animation(game,"turret",turret,1),true);
		addAnimation(new Animation(game,"cross",cross,1),true);
	}
}

class ParticleAnimation extends AnimationLayer{
	private static TextureTileLoader sparkle= new TextureTileLoader("sparkle.png", 8);
	
	public ParticleAnimation(Game game){
		addAnimation(new Animation(game,"sparkle", sparkle,1),true);
	}
}