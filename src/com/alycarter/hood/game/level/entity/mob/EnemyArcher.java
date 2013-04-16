package com.alycarter.hood.game.level.entity.mob;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.TextureTileLoader;
import com.alycarter.hood.game.level.entity.Entity;
import com.alycarter.hood.game.level.entity.particle.Particle;
import com.alycarter.hood.game.level.entity.particle.Pickup;
import com.alycarter.hood.game.level.entity.sprite.Animation;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;

public class EnemyArcher extends Mob{
	private double arrowDuration = 3;
	private double arrowDamage = 0.5;
	private final double attackDelay = 1;
	private double attackCoolDown = 0;
	
	private double walkSpeed = 2.5;

	private Point2D.Double targetTile = null;
	
	public EnemyArcher(Game game ,Point2D.Double location) {
		super(game,Entity.TYPE_ENEMY,location,2.5,0,1.5,0.5);
		sprite.addAnimationLayer(new ArcherAnimation(game));
		showShadow();
	}
	
	public void onUpdate() {
		attackCoolDown-=getGame().getDeltaTime();
		double x = getGame().getLevel().player.getLocation().getX()-getLocation().getX();
		double y = getGame().getLevel().player.getLocation().getY()-getLocation().getY();
		setDirection(new Point2D.Double(x, y));
		sprite.getAnimationLayer(0).setDirection(getDirectionAsAngle());
		if(distanceTo(getGame().getLevel().player)>arrowDuration){
			Point2D.Double closest = null;
			for (x=0;x<getGame().getLevel().getMap().getMapWidth();x++){
				for (y=0;y<getGame().getLevel().getMap().getMapHeight();y++){
					if(getGame().getLevel().player.distanceTo(x+0.5, y+0.5)<arrowDuration){
						if((x!=(int)getLocation().getX())&&(y!=(int)getLocation().getY())){
							if(closest==null||distanceTo(x+0.5, y+0.5)<distanceTo(closest)){
								if(getGame().getLevel().getMap().getMovable(x, y)){
									closest = new Point2D.Double(x+0.5, y+0.5);
								}
							}
						}
					}
				}
			}
			if(closest!=null){
				findPath(closest);
			}
			if(targetTile!=null){
				double xt = targetTile.getX()-getLocation().getX();
				double yt = targetTile.getY()-getLocation().getY();
				this.setDirection(new Point2D.Double(xt, yt));
				setSpeed(walkSpeed);
			}
		}else{
			if(distanceTo(getGame().getLevel().player)<1.5){
				Point2D.Double closest = null;
				for (x=0;x<getGame().getLevel().getMap().getMapWidth();x++){
					for (y=0;y<getGame().getLevel().getMap().getMapHeight();y++){
						if(getGame().getLevel().player.distanceTo(x+0.5, y+0.5)>1.5){
							if((x!=(int)getLocation().getX())&&(y!=(int)getLocation().getY())){
								if(closest==null||distanceTo(x+0.5, y+0.5)<distanceTo(closest)){
									if(getGame().getLevel().getMap().getMovable(x, y)){
										closest = new Point2D.Double(x+0.5, y+0.5);
									}
								}
							}
						}
					}
				}
				if(closest!=null){
					findPath(closest);
				}
				if(targetTile!=null){
					double xt = targetTile.getX()-getLocation().getX();
					double yt = targetTile.getY()-getLocation().getY();
					this.setDirection(new Point2D.Double(xt, yt));
					setSpeed(walkSpeed);
				}
			}else{
				setSpeed(0);
				if(attackCoolDown<=0){
					double xl=getLocation().getX()+(getDirectionAsVector().getX()*(getImageWidth()/2));
					double yl=getLocation().getY()+(getDirectionAsVector().getY()*(getImageWidth()/2));
					int[] t ={TYPE_TURRET,TYPE_PLAYER}; 
					getGame().getLevel().entities.add(new Arrow(getGame(),new Point2D.Double(xl,yl),getDirectionAsAngle(),4,arrowDuration,arrowDamage,t));
					attackCoolDown=attackDelay;
				}
			}
		}
		
	}
	
	public void onKill(Entity sender) {
		markRemoved();
		new Thread(){
			public void run(){
				for (int i=0;i<ArcherDeathAnimation.chunks;i++){
					double width = getImageWidth()/Math.sqrt(ArcherDeathAnimation.chunks);
					double x= 0-(getImageWidth()/2);
					double y= 0-(getImageWidth()/2);
					y+=width*(int)((double)(i)/Math.sqrt(ArcherDeathAnimation.chunks));
					x+=width*((double)(i)%Math.sqrt(ArcherDeathAnimation.chunks));
					double sin = Math.sin(Math.toRadians(sprite.getAnimationLayer(0).getDirection()-180));
					double cos = Math.cos(Math.toRadians(sprite.getAnimationLayer(0).getDirection()));
					double tempX=x;
					x= (x*cos)-(y*sin);
					y= (tempX*sin) + (y*cos);
					System.out.println(i+" "+x+" "+y);
					Particle p = new Particle(getGame(), new Point2D.Double(x+getLocation().getX(), y+getLocation().getY()), width,0.25, vectorAsAngle(new Point2D.Double(x, y)), 1);
					p.sprite.addAnimationLayer(new ArcherDeathAnimation(getGame()));
					p.sprite.getAnimationLayer(0).setDirection(sprite.getAnimationLayer(0).getDirection());
					p.sprite.getAnimationLayer(0).setCurrentAnimation("bow"+i, true);
					getGame().getLevel().entities.add(p);
				}
				if(Math.random()<0.5){
					getGame().getLevel().entities.add(new Pickup(getGame(),getLocation()));
				}
			}
		}.start();
	}
	
	public void findPath(Point2D.Double t){
		class Node{
			Node parent;
			int x;
			int y;
			double g;
			double h;
			double f;
			
			public Node(Node parent,int x, int y, double g, double h){
				this.parent=parent;
				this.x=x;
				this.y=y;
				this.g=g;
				this.h=h;
				this.f=g+h;
				
			}
		}	
		ArrayList<Node> open = new ArrayList<Node>();
		ArrayList<Node> closed = new ArrayList<Node>();
		Node end=new Node(null,(int)t.getX(),(int)t.getY(),0,0);
		Node start=new Node(null,(int)getLocation().getX(),(int)getLocation().getY(),0,0);
		Node currentNode=start;
		open.add(currentNode);
		while(!closed.contains(end)&&currentNode!=null){
			for (int x=-1;x<=1;x++){
				for (int y=-1;y<=1;y++){
					if(!(x==0&&y==0)){
						int xl = currentNode.x+x;
						int yl = currentNode.y+y;
						if(getGame().getLevel().getMap().getMovable(xl, yl)&&!blockedTile(xl, yl)){
							boolean closedNode = false;
							for (int i=0;i<closed.size();i++){
								Node temp = closed.get(i);
								if(temp.x==xl&&temp.y==yl){
									closedNode=true;
								}
							}
							if(!closedNode){
								boolean openNode = false;
								for (int i=0;i<open.size();i++){
									Node temp = open.get(i);
									if(temp.x==xl&&temp.y==yl){
										openNode=true;
									}
								}
								if(!openNode){
									double sd = currentNode.g+Math.sqrt((Math.abs(x)*Math.abs(x))+(Math.abs(y)*Math.abs(y)));
									double ed = Math.abs(end.x-xl)+Math.abs(end.y-yl);
									if(xl==end.x&&yl==end.y){
										end.parent=currentNode;
										open.add(end);
									}else{
										open.add(new Node(currentNode,xl,yl,sd,ed));
									}
								}else{
									double sd = currentNode.g+Math.abs(x)+Math.abs(y);
									Node target=null;
									for (int i=0;i<open.size();i++){
										Node temp = open.get(i);
										if(temp.x==xl&&temp.y==yl){
											target = open.get(i);
										}
									}
									if(sd<target.g){
										target.g=sd;
										target.parent=currentNode;
										target.f=target.g+target.h;
									}
								}
							}
						}
					}
				}
			}
			closed.add(currentNode);
			open.remove(currentNode);
			
			currentNode=null;
			for (int i=0;i<open.size();i++){
				if(currentNode==null||open.get(i).f<currentNode.f){
					currentNode=open.get(i);
				}
			}
		}
		if (end.parent!=null){
			Node parent = end;
			while(parent!=null){
//				if(getGame().debugMode){
//					getGame().getLevel().entities.add(new Particle(getGame(), new Point2D.Double(parent.x+0.5, parent.y+0.5), 0.1, 0.1, 0, 0));
//				}
				if(parent.parent==start){
					targetTile= new Point2D.Double(parent.x+0.5, parent.y+0.5);
				}
				parent=parent.parent;
			}
		}else{
			targetTile=null;
		}
	}
	
	public boolean blockedTile(int x, int y){
		for (int i=0;i<getGame().getLevel().entities.size();i++){
			Entity e = getGame().getLevel().entities.get(i);
			if(e.entityType==Entity.TYPE_TURRET){
				if ((int)e.getLocation().getX()==x && (int)e.getLocation().getY()==y){
					return true;
				}
			}
		}
		return false;
	}
	
}

class ArcherAnimation extends AnimationLayer{
	private static TextureTileLoader bow= new TextureTileLoader("bow.png", 128);
	
	public ArcherAnimation(Game game){
		addAnimation(new Animation(game,"bow",bow,1),true);
	}
}

class ArcherDeathAnimation extends AnimationLayer{
	public final static int chunks=25;
	private static TextureTileLoader bow= new TextureTileLoader("bow.png", 128/(int)Math.sqrt(chunks));
	
	public ArcherDeathAnimation(Game game){
		for(int i=0;i<chunks;i++){
			addAnimation(new Animation(game,"bow"+i,bow,1,i),true);	
		}
	}
}
