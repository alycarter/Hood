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

public class Enemy extends Mob{
	private static final double attackDelay = 2;
	private double attackCoolDown = 0;
	
	private double stunned=0;
	
	private Point2D.Double target= new Point2D.Double(0, 0);
	
	private int stance = 1;
	
	private static double walkSpeed = 1;
	
	public Enemy(Game game, Point2D.Double location) {
		super(game,Entity.TYPE_ENEMY,location,2.5,0,1.5,0.5);
		sprite.addAnimationLayer(new EnemyAnimation(game));
		showShadow();
		setSpeed(walkSpeed);
	}
	
	public void onUpdate(){
		findPath();
		if(stunned<=0){
			setSpeed(walkSpeed);
			if(target==null){//come at me brah
				double x = getGame().getLevel().player.getLocation().getX()-getLocation().getX();
				double y = getGame().getLevel().player.getLocation().getY()-getLocation().getY();
				this.setDirection(new Point2D.Double(x, y));
			}else{//be clever
				double x = target.getX()-getLocation().getX();
				double y = target.getY()-getLocation().getY();
				this.setDirection(new Point2D.Double(x, y));
			}
			sprite.getAnimationLayer(0).setDirection(getDirectionAsAngle());
			attackCoolDown-=getGame().getDeltaTime();
			if(distanceBetweenHitBoxes(getGame().getLevel().player)<((getImageWidth()/2)-getHitBoxWidth())/2 && attackCoolDown<=0){
				getGame().getLevel().player.damage(this, 1);
				attackCoolDown =attackDelay;
				if(stance==1){
					stance=2;
				}else{
					stance=1;
				}
				sprite.getAnimationLayer(0).setCurrentAnimation("sword"+stance, true);
			}
		}else{
			setSpeed(0);
			stunned-=getGame().getDeltaTime();
		}
		
	}
	
	public void onCollide(Entity e){
		if(e.entityType==Entity.TYPE_TURRET){
			double x = e.getLocation().getX()-getLocation().getX();
			double y = e.getLocation().getY()-getLocation().getY();
			this.setDirection(new Point2D.Double(x, y));
			sprite.getAnimationLayer(0).setDirection(getDirectionAsAngle());
			attackCoolDown-=getGame().getDeltaTime();
			if(distanceBetweenHitBoxes(e)<(((getImageWidth()/2)-(getHitBoxWidth()/2))/2) && attackCoolDown<=0){
				e.damage(this, 1);
				attackCoolDown =attackDelay;
			}
		}
	}
	
	public void findPath(){	
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
		Node end=new Node(null,(int)getGame().getLevel().player.getLocation().getX(),(int)getGame().getLevel().player.getLocation().getY(),0,0);
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
					target= new Point2D.Double(parent.x+0.5, parent.y+0.5);
				}
				parent=parent.parent;
			}
		}else{
			target=null;
		}
	}
	
	public boolean blockedTile(int x, int y){
		for (int i=0;i<getGame().getLevel().entities.size();i++){
			Entity e = getGame().getLevel().entities.get(i);
			if(e.entityType==Entity.TYPE_TURRET&&e.getHealth()>getHealth()){
				if ((int)e.getLocation().getX()==x && (int)e.getLocation().getY()==y){
					return true;
				}
			}
		}
		return false;
	}
	
	public void onDamge(Entity sender) {
		stunned=0.5;
	}
	
	public void onKill(Entity sender){
		markRemoved();
		if(Math.random()<0.5){
			getGame().getLevel().entities.add(new Pickup(getGame(),getLocation()));
		}
		new Thread(){
			public void run(){
				for (int i=0;i<EnemyDeathAnimation.chunks;i++){
					double width = getImageWidth()/Math.sqrt(EnemyDeathAnimation.chunks);
					double x= 0-(getImageWidth()/2);
					double y= 0-(getImageWidth()/2);
					y+=width*(int)((double)(i)/Math.sqrt(EnemyDeathAnimation.chunks));
					x+=width*((double)(i)%Math.sqrt(EnemyDeathAnimation.chunks));
					double sin = Math.sin(Math.toRadians(sprite.getAnimationLayer(0).getDirection()-180));
					double cos = Math.cos(Math.toRadians(sprite.getAnimationLayer(0).getDirection()));
					double tempX=x;
					x= (x*cos)-(y*sin);
					y= (tempX*sin) + (y*cos);
					Particle p = new Particle(getGame(), new Point2D.Double(x+getLocation().getX(), y+getLocation().getY()), width,0.25, vectorAsAngle(new Point2D.Double(x, y)), 1);
					p.sprite.addAnimationLayer(new EnemyDeathAnimation(getGame()));
					p.sprite.getAnimationLayer(0).setDirection(sprite.getAnimationLayer(0).getDirection());
					p.sprite.getAnimationLayer(0).setCurrentAnimation("sword"+i, true);
					getGame().getLevel().entities.add(p);
				}
			}
		}.start();
	}

}

class EnemyAnimation extends AnimationLayer{
	private static TextureTileLoader sword1 = new TextureTileLoader("sword.png", 288);
	private static TextureTileLoader sword2 = new TextureTileLoader("sword2.png", 128);
	
	public EnemyAnimation(Game game){
		addAnimation(new Animation(game,"sword2",sword2,1),true);
		addAnimation(new Animation(game,"sword1",sword1,1),true);
	}
}
class EnemyDeathAnimation extends AnimationLayer{
	public final static int chunks=25;
	private static TextureTileLoader sword= new TextureTileLoader("sword.png", 288/(int)Math.sqrt(chunks));
	
	public EnemyDeathAnimation(Game game){
		for(int i=0;i<chunks;i++){
			addAnimation(new Animation(game,"sword"+i,sword,1,i),true);	
		}
	}
}
