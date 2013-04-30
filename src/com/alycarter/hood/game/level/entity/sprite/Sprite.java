package com.alycarter.hood.game.level.entity.sprite;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {
	
	private ArrayList<AnimationLayer> layers = new ArrayList<AnimationLayer>();
	
	public Sprite() {
		// TODO Auto-generated constructor stub
	}
	
	public void update(){
		for (int i = 0;i<layers.size();i++){
			layers.get(i).update();
		}
	}
	
	public void addAnimationLayer(AnimationLayer al){
		layers.add(al);
	}
	
	public BufferedImage getImage(){
		BufferedImage img = null;
		if(layers.size()>=1){
			if(layers.get(0).getImage()!=null){
				img = new BufferedImage(layers.get(0).getImage().getWidth(),layers.get(0).getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
				for (int i=0;i<layers.size();i++){
					if(layers.get(i).getImage()!=null){
						img.getGraphics().drawImage(layers.get(i).getImage(), 0, 0, null);
					}
				}
			}
		}else{
			img = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
			img.setRGB(0, 0, Color.BLUE.getRGB());
		}
		return img;
	}
	
	public AnimationLayer getAnimationLayer(int i){
		if(layers.size()>i&&i>=0){
			return layers.get(i);
		}else{
			return null;
		}
	}

}
