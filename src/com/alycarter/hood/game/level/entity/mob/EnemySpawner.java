package com.alycarter.hood.game.level.entity.mob;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.Entity;

public class EnemySpawner extends Mob{
	private double spawnDirection;
	
	private ArrayList<Entity> spawnQueue = new ArrayList<Entity>();
	
	public EnemySpawner(Game game, Point2D.Double location, double spawnDirection, double spawnDelay) {
		super(game,Entity.TYPE_SPAWNER,location,1,0,0,0);
		this.spawnDirection=spawnDirection;
		hideHealthBar();
	}
	
	public void onUpdate(){
		if(spawnQueue.size()>0){
			Entity e = spawnQueue.get(0);
			double x =this.getLocation().getX()+(((e.getHitBoxWidth()+0.1)/2)*angleAsVector(spawnDirection).getX()) ;
			double y =this.getLocation().getY()+(((e.getHitBoxWidth()+0.1)/2)*angleAsVector(spawnDirection).getY()) ;
			e.jumpToLocation(x, y);
			if(e.checkCollisions()<0){
				getGame().getLevel().entities.add(e);
				spawnQueue.remove(0);
			}else{
				if(getGame().getLevel().entities.get(e.checkCollisions()).entityType==Entity.TYPE_TURRET){
					getGame().getLevel().entities.get(e.checkCollisions()).markRemoved();
				}
			}
		}
	}
	
	public void addToSpawnQueue(Entity e){
		spawnQueue.add(e);
	}

}
