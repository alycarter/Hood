package com.alycarter.hood.game.level.entity.mob;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.TextureTileLoader;
import com.alycarter.hood.game.level.entity.Entity;
import com.alycarter.hood.game.level.entity.sprite.Animation;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;

public class Player extends Mob{
	public final static double moveSpeed = 3;
	private double faceDirection;
	
	private static final double attackDelay = 0.5;
	private double attackCoolDown = 0;
	
	private int stance = 1;
	
	private double bowTension=0;
	
	public Player(Game game,Point2D.Double location) {
		super(game,Entity.TYPE_PLAYER,location,10,0,1.5,0.5);
		//hideHealthBar();
		sprite.addAnimationLayer(new PlayerAnimation(game));
		faceDirection=getDirectionAsAngle();
		showShadow();
	}
	
	public void onUpdate(){
		double x=0;
		double y=0;
		
		if(getGame().controls.isPressed(KeyEvent.VK_SPACE)){
			getGame().getLevel().cursor.sprite.getAnimationLayer(0).setCurrentAnimation("target", true);
			if(getGame().getLevel().towerPoints>0){
				Turret t=new Turret(getGame(),getGame().getLevel().cursor.getLocation(),3);
				if(t.checkCollisions()==-1){
					getGame().getLevel().cursor.sprite.getAnimationLayer(0).setCurrentAnimation("turret", true);
				}
			}
		}else{
			getGame().getLevel().cursor.sprite.getAnimationLayer(0).setCurrentAnimation("cross", true);
		}
		if(getGame().controls.isTyped(KeyEvent.VK_SPACE)){
			if(getGame().getLevel().towerPoints>0){
				Turret t=new Turret(getGame(),getGame().getLevel().cursor.getLocation(),3);
				if(t.checkCollisions()==-1){
					getGame().getLevel().entities.add(t);
					getGame().getLevel().towerPoints-=1;
				}
			}
		}
		if(getGame().controls.isPressed(KeyEvent.VK_D)){
			x+=1;
		}
		if(getGame().controls.isPressed(KeyEvent.VK_A)){
			x-=1;
		}
		if(getGame().controls.isPressed(KeyEvent.VK_W)){
			y-=1;
		}
		if(getGame().controls.isPressed(KeyEvent.VK_S)){
			y+=1;
		}
		if(x!=0||y!=0){
			this.setDirection(new Point2D.Double(x,y));
			this.setSpeed(moveSpeed);
		}else{
			this.setSpeed(0);
		}
		double xdistance = getGame().getLevel().cursor.getLocation().getX()-this.getLocation().getX();
		double ydistance = getGame().getLevel().cursor.getLocation().getY()-this.getLocation().getY();
		faceDirection = vectorAsAngle(new Point2D.Double(xdistance, ydistance));
		sprite.getAnimationLayer(0).setDirection(faceDirection);
		attackCoolDown-=getGame().getDeltaTime();
		if(attackCoolDown<0){
			if(getGame().controls.leftMousePressed()){
				double distanceFromPlayer = (getImageWidth()/2)-0.32;
				double xl=getLocation().getX()+(angleAsVector(faceDirection).getX()*distanceFromPlayer);
				double yl=getLocation().getY()+(angleAsVector(faceDirection).getY()*distanceFromPlayer);
				getGame().getLevel().entities.add(new SwordSlash(getGame(),new Point2D.Double(xl,yl),faceDirection,moveSpeed,0.4,1));
				attackCoolDown =attackDelay;
				if(stance==1){
					stance=2;
				}else{
					stance=1;
				}
				sprite.getAnimationLayer(0).setCurrentAnimation("sword"+stance, true);
			}
		}
		if(getGame().controls.rightMousePressed()){
			sprite.getAnimationLayer(0).setCurrentAnimation("bow", true);
			bowTension+=getGame().getDeltaTime()*2;
			if(bowTension>1){
				bowTension=1;
			}
		}else{
			sprite.getAnimationLayer(0).setCurrentAnimation("sword"+stance, true);
			if(getGame().controls.rightMouseClicked()){
				double xl=getLocation().getX()+(angleAsVector(faceDirection).getX()*(getImageWidth()/2));
				double yl=getLocation().getY()+(angleAsVector(faceDirection).getY()*(getImageWidth()/2));
				int[] t ={Entity.TYPE_ENEMY}; 
				getGame().getLevel().entities.add(new Arrow(getGame(),new Point2D.Double(xl,yl),faceDirection,5*bowTension,3.5*bowTension,1*bowTension,t));
				bowTension=0;
			}
		}
	}
	
	public void onKill(Entity sender){
		getGame().gameOverMenu.showMenu();
	}
	
}

class PlayerAnimation extends AnimationLayer{
	private static TextureTileLoader bow = new TextureTileLoader("bow.png", 128);
	private static TextureTileLoader sword1 = new TextureTileLoader("sword.png", 288);
	private static TextureTileLoader sword2 = new TextureTileLoader("sword2.png", 128);
	
	public PlayerAnimation(Game game){
		addAnimation(new Animation(game,"bow",bow,1),true);
		addAnimation(new Animation(game,"sword2",sword2,1),true);
		addAnimation(new Animation(game,"sword1",sword1,1),true);
	}
}
