package com.alycarter.hood.game.level.entity.sprite;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimationLayer {
	
	private double direction=0;
	
	private Animation currentAnimation= null;
	
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	
	private BufferedImage rotatedCurrentFrame;
	private int loadedFrame=0;
	private double loadedDirection=0;
	private boolean currentlyRotating=false;
	
	public static int transformMode = AffineTransformOp.TYPE_BILINEAR;
	
	public AnimationLayer() {
		// TODO Auto-generated constructor stub
	}
	

	public void update(){
		currentAnimation.update();
		if(currentAnimation!=null&&!currentlyRotating){
			if((rotatedCurrentFrame==null)||(currentAnimation.getCurrentFramePointer()!=loadedFrame)||(loadedDirection!=direction)){
				currentlyRotating=true;
				new Thread(){
					public void run() {
						double rotation = Math.toRadians(direction)*-1;
						BufferedImage img = new BufferedImage(currentAnimation.getCurrentFrame().getWidth(), currentAnimation.getCurrentFrame().getHeight(), BufferedImage.TYPE_INT_ARGB);
						double locationX = img.getWidth() / 2;
						double locationY = img.getHeight() / 2;
						AffineTransform tx = AffineTransform.getRotateInstance(rotation, locationX, locationY);
						AffineTransformOp op = new AffineTransformOp(tx, transformMode);
						img.getGraphics().drawImage(op.filter(currentAnimation.getCurrentFrame(), null), 0, 0, null);
						loadedFrame=currentAnimation.getCurrentFramePointer();
						loadedDirection=direction;
						rotatedCurrentFrame=img;
						currentlyRotating=false;
					};
				}.start();
			}
		}
	}
	
	public void setDirection(double direction){
		this.direction=direction;
	}
	
	public void setDirection(Point2D.Double d){
		direction =(Math.toDegrees(Math.atan2(d.getX(), d.getY())));
	}
	
	public String getAnimationName(){
		return currentAnimation.getName();
	}
	
	public BufferedImage getImage(){
		return rotatedCurrentFrame;
	}
	
	public void addAnimation(Animation animation,boolean current){
		animations.add(animation);
		if(current){
			setCurrentAnimation(animation.getName(), true);
		}
	}

	public void setCurrentAnimation(String animationName, boolean restart){
		for (int i=0; i<animations.size();i++){
			if(animations.get(i).getName().equals(animationName)){
				currentAnimation=animations.get(i);
				if(restart){
					currentAnimation.reset();
				}
			}
		}
	}
}

