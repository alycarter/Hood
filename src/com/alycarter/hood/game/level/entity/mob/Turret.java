package com.alycarter.hood.game.level.entity.mob;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.Entity;
import com.alycarter.hood.game.level.entity.sprite.Animation;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;

public class Turret extends Mob{
	private static final double attackDelay = 1;
	private double attackCoolDown = 0;
	private double range;
	public Turret(Game game, Point2D.Double location, double range) {
		super(game,Entity.TYPE_TURRET,location,5,0,0.8,0.5);
		sprite.addAnimationLayer(new TurretAnimation(game));
		this.range=range;
	}
	
	public void onUpdate(){
		Entity closest = null;
		for(int i= 0;i<getGame().getLevel().entities.size();i++){
			Entity temp=getGame().getLevel().entities.get(i);
			if(temp.entityType==Entity.TYPE_ENEMY&&!temp.isRemoved()){
				if(closest==null){
					closest = getGame().getLevel().entities.get(i);
				}else{
					if(distanceTo(temp)<distanceTo(closest)){
						closest = getGame().getLevel().entities.get(i);
					}
				}
			}
		}
		if(closest !=null){
			if(distanceTo(closest)<range){
				double x = closest.getLocation().getX()-getLocation().getX();
				double y = closest.getLocation().getY()-getLocation().getY();
				this.setDirection(new Point2D.Double(x, y));
				sprite.getAnimationLayer(0).setDirection(getDirectionAsAngle());
				attackCoolDown-=getGame().getDeltaTime();
				if(attackCoolDown<=0){
					double xl=getLocation().getX()+(getDirectionAsVector().getX()*(getImageWidth()/2));
					double yl=getLocation().getY()+(getDirectionAsVector().getY()*(getImageWidth()/2));
					getGame().getLevel().entities.add(new Arrow(getGame(),new Point2D.Double(xl,yl),getDirectionAsAngle(),4,range,0.5));
					attackCoolDown=attackDelay;
				}
			}
		}
	}

}

class TurretAnimation extends AnimationLayer{
	
	public TurretAnimation(Game game){
		addAnimation(new Animation(game,"turret","turret.png",128,1),true);
	}
}
