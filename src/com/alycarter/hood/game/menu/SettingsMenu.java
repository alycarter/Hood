package com.alycarter.hood.game.menu;

import java.awt.image.AffineTransformOp;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;


public class SettingsMenu extends Menu {

	public SettingsMenu(Game game) {
		super(game);
		addButton(new Button(game,"back",game.getWidth()/3,game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				hideMenu();
				if(game.mainMenu.isShown()){
					game.mainMenu.showMenu();
				}else{
					game.pauseMenu.showMenu();
				}
			}
		});
		addButton(new Button(game,String.valueOf("fps limit: "+game.getFpsLimit()),game.getWidth()/3,50+game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				switch(game.getFpsLimit()){
					case 120:{
						game.setFpsLimit(60);
						break;
					}
					case 60:{
						game.setFpsLimit(30);
						break;
					}
					case 30:{
						game.setFpsLimit(15);
						break;
					}
					case 15:{
						game.setFpsLimit(120);
						break;
					}
					default:{
						game.setFpsLimit(120);
						break;
					}
				}
				name=String.valueOf("fps limit: "+game.getFpsLimit());
			}
		});
		addButton(new Button(game,String.valueOf("transform mode: "+AnimationLayer.transformMode+" (default)"),game.getWidth()/3,100+game.getHeight()/3,game.getWidth()/3,25){
			public void onClick(){
				switch(AnimationLayer.transformMode){
					case AffineTransformOp.TYPE_BICUBIC:{
						AnimationLayer.transformMode=AffineTransformOp.TYPE_BILINEAR;
						name=String.valueOf("transform mode: "+AnimationLayer.transformMode+" (default)");
						break;
					}
					case AffineTransformOp.TYPE_BILINEAR:{
						AnimationLayer.transformMode=AffineTransformOp.TYPE_NEAREST_NEIGHBOR;
						name=String.valueOf("transform mode: "+AnimationLayer.transformMode+" (worst)");
						break;
					}
					case AffineTransformOp.TYPE_NEAREST_NEIGHBOR:{
						AnimationLayer.transformMode=AffineTransformOp.TYPE_BICUBIC;
						name=String.valueOf("transform mode: "+AnimationLayer.transformMode+" (best)");
						break;
					}
					default:{
						AnimationLayer.transformMode=AffineTransformOp.TYPE_BILINEAR;
						name=String.valueOf("transform mode: "+AnimationLayer.transformMode+" (default)");
						break;
					}
				}
				
			}
		});
		
	}

}
