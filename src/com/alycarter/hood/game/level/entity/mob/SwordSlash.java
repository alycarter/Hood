package com.alycarter.hood.game.level.entity.mob;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.Entity;
import com.alycarter.hood.game.level.entity.sprite.Animation;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;

public class SwordSlash extends Mob{
	private double duration;
	private double damage;
	public SwordSlash(Game game, Point2D.Double location, double direction, double speed, double maxDistance, double damage) {
		super(game,Entity.TYPE_ARROW,location,1,1,0.75,0.35);
		setDirection(direction);
		setSpeed(speed);
		duration = maxDistance/getSpeed();
		sprite.addAnimationLayer(new SlashAnimation(game));
		sprite.getAnimationLayer(0).setDirection(direction);
		hideHealthBar();
		this.damage=damage;
	}
	
	public void onUpdate(){
		duration-=getGame().getDeltaTime();
		if(duration<=0){
			markRemoved();
		}
	}
	
	public void onCollide(Entity entity){
		if(entity.entityType==Entity.TYPE_ENEMY){
			entity.damage(this, damage);
		}
		this.markRemoved();
	}

}

class SlashAnimation extends AnimationLayer{
	
	public SlashAnimation(Game game){
		addAnimation(new Animation(game,"slash","slash.png",16,1),true);
	}
}
