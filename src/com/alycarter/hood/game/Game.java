package com.alycarter.hood.game;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.alycarter.hood.game.level.Level;
import com.alycarter.hood.game.level.RapunzelLevel;
import com.alycarter.hood.game.level.map.tile.Tile;
import com.alycarter.hood.game.menu.GameOverMenu;
import com.alycarter.hood.game.menu.MainMenu;
import com.alycarter.hood.game.menu.PauseMenu;
import com.alycarter.hood.game.menu.SettingsMenu;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	
	public int windowWidth;
	public int windowHeight;
	private double tileWide = 7;
	public final String NAME = "Hood";
	
	public MainMenu mainMenu;
	public PauseMenu pauseMenu;
	public GameOverMenu gameOverMenu;
	public SettingsMenu settingsMenu;
	
	private Thread gameThread = new Thread(this);
	
	private JFrame frame;
	private GraphicsDevice gd;
	
	private boolean running;
	private double deltaTime=0;
	private int fps = 0;
	private int ups = 0;
	private int fpsLimit = 120;
	
	private Level level;
	
	public Controls controls =new Controls(this);
	
	public boolean debugMode = false;
	
	public Game(JFrame frame) {
		this.frame=frame;
	}

	public void start(){
		intit();
		gameThread.run();
	}
	
	private void intit() {
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		windowWidth = gd.getDisplayMode().getWidth();
		windowHeight = gd.getDisplayMode().getHeight();
		Tile.tileResolution= (int) ((double)windowWidth/tileWide);
		frame.setSize(windowWidth, windowHeight);
		frame.setTitle(NAME);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		setSize(windowWidth,windowHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		gd.setFullScreenWindow(frame);
		this.addKeyListener(controls);
		this.addMouseListener(controls);
		mainMenu=new MainMenu(this);
		pauseMenu= new PauseMenu(this);
		gameOverMenu = new GameOverMenu(this);
		settingsMenu = new SettingsMenu(this);
		mainMenu.showMenu();
		level = new RapunzelLevel(this);
	}

	@Override
	public void run() {
		long start;
		long end;
		long timeTaken;
		long timeSinceRender=0;
		long ns =  1000000000;
		int fps=0;
		int ups = 0;
		long second=0;
		running= true;
		while (running){
			if(frame.isFocused()){
				start = System.nanoTime();
				update();
				ups++;
				if((double)timeSinceRender/(double)ns>1.0/(double)fpsLimit){
					render();
					timeSinceRender=0;
					fps++;
				}
				end = System.nanoTime();
				timeTaken = end - start;
				timeSinceRender+=timeTaken;
				deltaTime = (double)timeTaken/(double)ns;
				second+=timeTaken;
				if(second>ns){
					this.fps=fps;
					this.ups=ups;
					fps=0;
					ups=0;
					second =0;
				}
			}else{
				if(level.loaded){
					pauseMenu.showMenu();
				}
				controls.clearControls();
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
		gd.setFullScreenWindow(null);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs ==null){
			createBufferStrategy(3);
		}else{
			Graphics2D g = (Graphics2D)bs.getDrawGraphics();
			g.clearRect(0, 0, windowWidth, windowHeight);
			if(mainMenu.isShown()){
				if(settingsMenu.isShown()){
					settingsMenu.render(g);
				}else{
					mainMenu.render(g);
				}
			}else{
				if(level.loaded){
					if(gameOverMenu.isShown()){
						level.render(g);
						gameOverMenu.render(g);
					}else{
						if(pauseMenu.isShown()){
							level.render(g);
							if(settingsMenu.isShown()){
								settingsMenu.render(g);
							}else{
								pauseMenu.render(g);
							}
						}else{
							level.render(g);
						}
					}
				}else{
					g.drawString("loading level", getWidth()/2, getHeight()/2);
				}
			}
			bs.show();
		}
	}

	public double getDeltaTime(){
		return deltaTime;
	}
	private void update() {
		if(!this.isFocusOwner()){
			this.requestFocusInWindow();
		}
		controls.update();
		if(controls.isPressed(KeyEvent.VK_CONTROL)&&controls.isTyped(KeyEvent.VK_D)){
			debugMode=!debugMode;
		}
		if(controls.isTyped(KeyEvent.VK_ESCAPE)){
			if(level.loaded){
				pauseMenu.showMenu();
			}
		}
		if(mainMenu.isShown()){
			if(settingsMenu.isShown()){
				settingsMenu.update();
			}else {
				mainMenu.update();
			}
		}else{
			if(level.loaded){
				if(gameOverMenu.isShown()){
					gameOverMenu.update();
				}else{
					if(pauseMenu.isShown()){
						if(settingsMenu.isShown()){
							settingsMenu.update();
						}else{
							pauseMenu.update();
						}
					}else{
						level.update();
					}
				}
			}
		}
	}

	public Level getLevel(){
		return level;
	}
	
	public void showCursor(){
		try {
			BufferedImage c;
			c = ImageIO.read(Game.class.getResourceAsStream("/cross.png"));
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(c, new Point(16, 16), "cursor");
			frame.getContentPane().setCursor(cursor);
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void hideCursor(){
		try {
			BufferedImage c;
			c = ImageIO.read(Game.class.getResourceAsStream("/blank.png"));
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(c, new Point(0, 0), "cursor");
			frame.getContentPane().setCursor(cursor);
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void endGame(){
		running=false;
	}
	
	public int getFps(){
		return fps;
	}
	public int getUps(){
		return ups;
	}
	
	public void setFpsLimit(int limit){
		fpsLimit = limit;
	}
	
	public int getFpsLimit(){
		return fpsLimit;
	}
}
