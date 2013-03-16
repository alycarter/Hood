package com.alycarter.hood.game.level.entity.particle;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.Entity;

public class Particle extends Entity{
	private double duration;
	public Particle(Game game,Point2D.Double location,double imageWidth, double duration, double direction, double speed) {
		super(game,Entity.TYPE_PARTICLE,location,1,1,imageWidth,0,false,false);
		setSpeed(speed);
		setDirection(direction);
		this.duration=duration;
		hideHealthBar();
	}
	
	public void onUpdate(){
		duration-=getGame().getDeltaTime();
		if(duration<=0){
			markRemoved();
		}
	}

}
