package com.alycarter.hood.game.level.entity;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.sprite.Sprite;


public class Entity {
	
	private Game game;
	private Point2D.Double location =new Point2D.Double();
	private double direction=0;
	private double speed;
	private boolean mapCollision;
	private boolean entityCollision;
	private double health;
	private double maxHealth;
	private double damageResistance;
	private double imageWidth;
	private double hitBoxWidth;
	private boolean showHealthBar = true;
	private boolean removed = false;
	public Sprite sprite = new Sprite();
	public final int entityType;
	
	public static final int TYPE_PLAYER = 0;
	public static final int TYPE_PARTICLE = 1;
	public static final int TYPE_ENEMY = 2;
	public static final int TYPE_ARROW = 3;
	public static final int TYPE_UTILITY = 4;
	public static final int TYPE_TURRET = 5;
	public static final int TYPE_SPAWNER = 6;
	public static final int TYPE_HAIR = 7;
	
	
	
	public Entity(Game game,int entityType,Point2D.Double location,double maxHealth,double damageResistance,double imageWidth,double hitBoxWidth,boolean mapCollision, boolean entityCollision) {
		this.game=game;
		this.entityType=entityType;
		jumpToLocation(location);
		setSpeed(speed);
		this.mapCollision=mapCollision;
		this.entityCollision=entityCollision;
		this.maxHealth=maxHealth;
		setHealth(maxHealth);
		setDamageResistance(damageResistance);
		this.imageWidth=imageWidth;
		this.hitBoxWidth= hitBoxWidth;
	}
	
	public void update(){
		move();
		onUpdate();
		sprite.update();
	}

	public void onUpdate() {
		
	}
	
	public void setDamageResistance(double r){
		if(r<0){
			r=0;
		}
		if(r>1){
			r=1;
		}
	}
	
	private void move(){
		double x = this.getDirectionAsVector().getX();
		double y = this.getDirectionAsVector().getY();
		x*=speed;
		y*=speed;
		x*=game.getDeltaTime();
		y*=game.getDeltaTime();
		x+=this.getLocation().getX();
		y+=this.getLocation().getY();
		this.moveToLocation(x, y);
		
	}
	
	public void moveToLocation(double x, double y){
		Point2D.Double priorLocation = new Point2D.Double(this.getLocation().getX(),this.location.getY());
		jumpToLocation(x,y);
		int collide;
		boolean entityCollide = false;
		collide =checkCollisions();
		if(collide!=-1){
			if(collide!=-2){
				entityCollide=true;
				this.onCollide(getGame().getLevel().entities.get(checkCollisions()));
			}
			this.jumpToLocation(priorLocation.getX(),y);
			collide =checkCollisions();
			if(collide!=-1){
				if(collide!=-2 && !entityCollide){
					entityCollide=true;
					this.onCollide(getGame().getLevel().entities.get(checkCollisions()));
				}
				this.jumpToLocation(x,priorLocation.getY());
				collide =checkCollisions();
				if(collide!=-1){
					if(collide!=-2 && !entityCollide){
						this.onCollide(getGame().getLevel().entities.get(checkCollisions()));
					}
					this.jumpToLocation(priorLocation);
				}
			}
		}
	}
	
	public void onCollide(Entity entity){
		
	}
	
	public int checkCollisions(){
		if(this.entityCollision){
			for(int e =0;e< game.getLevel().entities.size();e++){
				Entity entity = game.getLevel().entities.get(e);
				if(entity != this && entity.entityCollision && !entity.isRemoved()){
					if(this.distanceTo(entity)<(entity.getHitBoxWidth()/2)+(this.getHitBoxWidth()/2)){
						return e;
					}
				}
			}
		}
		if(this.mapCollision){
			if(!getGame().getLevel().getMap().getMovable(getLocation().getX(), getLocation().getY())){
				return -2;
			}
		}
		return -1;
	}
	
	public void hideHealthBar(){
		showHealthBar=false;
	}
	
	public void showHealthBar(){
		showHealthBar=true;
	}
	
	public boolean shouldRenderHealthBar(){
		return showHealthBar;
	}
	
	public double distanceTo(double x, double y){
		x-=this.getLocation().getX();
		y-=this.getLocation().getY();
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	public double distanceTo(Entity e){
		return this.distanceTo(e.getLocation());
	}
	
	public double distanceBetweenHitBoxes(Entity e){
		double d = this.distanceTo(e);
		d-=getHitBoxWidth()/2;
		d-=e.getHitBoxWidth()/2;
		return d;
	}
	
	public double distanceTo(Point2D.Double point){
		return this.distanceTo(point.getX(), point.getY());
	}
	
	public void moveToLocation(Point2D.Double location){
		this.moveToLocation(location.getX(), location.getY());
	}
	
	public void jumpToLocation(Point2D.Double location){
		this.jumpToLocation(location.getX(), location.getY());
	}
	
	public void jumpToLocation(double x, double y) {
		if(x-(this.hitBoxWidth/2)<0){
			x=0+(this.hitBoxWidth/2);
		}
		if(y-(this.hitBoxWidth/2)<0){
			y=0+(this.hitBoxWidth/2);
		}
		if(x+(this.hitBoxWidth/2)>game.getLevel().getMap().getMapWidth()){
			x=game.getLevel().getMap().getMapWidth()-(this.hitBoxWidth/2);
		}
		if(y+(this.hitBoxWidth/2)>game.getLevel().getMap().getMapHeight()){
			y=game.getLevel().getMap().getMapHeight()-(this.hitBoxWidth/2);
		}
		this.location.setLocation(x, y);
	}
	
	public void setSpeed(double speed){
		if (speed<0){
			speed=0;
		}
		this.speed=speed;
	}
	
	public void setHealth(double health){
		if(health>maxHealth){
			health=maxHealth;
		}
		this.health=health;
	}

	public Point2D.Double getLocation(){
		return location;
	}
	
	public Game getGame(){
		return game;
	}
	public void setDirection(Point2D.Double d){
		direction =(Math.toDegrees(Math.atan2(d.getX(), d.getY())));
	}
	
	public void setDirection(double d){
		direction =d;
	}
	
	public Point2D.Double getDirectionAsVector(){
		double xd=Math.sin(Math.toRadians(direction));
		double yd=Math.cos(Math.toRadians(direction));
		return new Point2D.Double(xd,yd);
	}
	
	public double getDirectionAsAngle(){
		return this.direction;
	}
	
	public double vectorAsAngle(Point2D.Double d){
		return Math.toDegrees(Math.atan2(d.getX(), d.getY()));
	}

	public Point2D.Double angleAsVector(double direction){
		double xd=Math.sin(Math.toRadians(direction));
		double yd=Math.cos(Math.toRadians(direction));
		return new Point2D.Double(xd,yd);
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public double getHitBoxWidth(){
		return hitBoxWidth;
	}
	
	public double getImageWidth(){
		return imageWidth;
	}
	
	public BufferedImage getImage(){
		return sprite.getImage();
	}
	
	public double getHealth(){
		return health;
	}
	
	public double getMaxHealth(){
		return maxHealth;
	}
	
	public void damage(Entity sender, double damage){
		setHealth(getHealth()-damage*((this.damageResistance-1)*-1));
		onDamge(sender);
		if(getHealth()<=0){
			onKill(sender);
		}
	}

	public void onKill(Entity sender) {
		markRemoved();
	}

	public void onDamge(Entity sender) {
		
	}
	
	public void markRemoved(){
		this.removed=true;
	}
	
	public boolean isRemoved(){
		return removed;
	}
	
	public boolean anglePointsToEntity(Entity e,double angle, double range){
		double angleToEntity =vectorAsAngle(new Point2D.Double(e.getLocation().getX()-getLocation().getX(), e.getLocation().getY()-getLocation().getY()));
		angleToEntity= Math.abs(angleToEntity-angle)%360;
		return( angleToEntity<range/2 || angleToEntity>360-(range/2));
	}
	
}
