package com.alycarter.hood.game;

import java.awt.Graphics;

import com.alycarter.hood.game.level.TextureTileLoader;

public class Font {
	private static final String letterSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789():";
	
	public static void drawString(Graphics g, String string, TextureTileLoader typeFace, int size, int overlay, int x, int y){
		int xOffset=x;
		int yOffset=y;
		string = string.toUpperCase();
		for(int i=0;i<string.length();i++){
			if(string.charAt(i)!=' '){
				g.drawImage(typeFace.getTile(getLetterTile(string.charAt(i))), xOffset, yOffset, size, size, null);
			}
			xOffset+=size-overlay;
		}
	}
	
	private static int getLetterTile(char letter){
		int i=0;
		boolean found =false;
		while(i<letterSequence.length()&&!found){
			if(letterSequence.charAt(i)==letter){
				found=true;
			}else{
				i++;
			}
		}
		return i;
	}
	
}
