package com.alycarter.hood.game.level.entity;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.sprite.Animation;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;

public class Hair extends Entity{
	private double duration;
	private Entity source;
	private Entity priorStrand;
	public Hair(Game game, Point2D.Double location, double direction, double speed, double maxDistance,Entity source,Entity priorStrand) {
		super(game,Entity.TYPE_HAIR,location,1,1,0.5,0.3,false,false);
		setDirection(direction);
		setSpeed(speed);
		duration = maxDistance/getSpeed();
		sprite.addAnimationLayer(new HairAnimation(game));
		sprite.getAnimationLayer(0).setDirection(direction);
		hideHealthBar();
		this.source=source;
		this.priorStrand=priorStrand;
	}
	
	public void onUpdate(){
		if(priorStrand!=null){
			double x = priorStrand.getLocation().getX()-getLocation().getX();
			double y = priorStrand.getLocation().getY()-getLocation().getY();
			sprite.getAnimationLayer(0).setDirection(new Point2D.Double(x, y));
		}
		duration-=getGame().getDeltaTime();
		if(duration<=0){
			markRemoved();
		}
		Entity entity=null;
		for(int i=0;i<getGame().getLevel().entities.size();i++){
			entity=getGame().getLevel().entities.get(i);
			if(distanceBetweenHitBoxes(entity)<0 && entity.entityType != Entity.TYPE_HAIR &&entity!=source){
				if(entity.entityType==Entity.TYPE_PLAYER||entity.entityType==Entity.TYPE_TURRET){
					entity.damage(this, 0.25);
					this.markRemoved();
				}else{
					if(entity.entityType==Entity.TYPE_ENEMY){
						this.markRemoved();	
					}
				}
			}
		}
	}

	
}

class HairAnimation extends AnimationLayer{
	
	public HairAnimation(Game game){
		addAnimation(new Animation(game,"hair","hair.png",128,1),true);
	}
}



