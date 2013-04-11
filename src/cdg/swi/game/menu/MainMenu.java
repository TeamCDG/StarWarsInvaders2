package cdg.swi.game.menu;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import cdg.swi.game.util.StaticManager;
import cdg.swi.game.util.Utility;
import cdg.swi.game.util.VertexData;
import cdg.swi.game.util.interfaces.IClickListener;
import cdg.swi.game.util.interfaces.IGameControl;

public class MainMenu extends MenuFrame 
{
	Button newGame;
	Button campaign;
	Button stats;
	Button options;
	Button credits;
	Button close;
	
	Label headLabel;
	Label titleLabel;

	public MainMenu(IGameControl c)
	{
		super(c);
		
		this.newGame = new Button(-0.97142857143f, -0.8f);
		this.newGame.setTextSize(0.7f);
		this.newGame.setText("new game");
		this.add(this.newGame);
		
		this.campaign = new Button(-0.64285714286f, -0.8f);
		this.campaign.setTextSize(0.7f);
		this.campaign.setText("campaign");
		this.add(this.campaign);
		
		this.stats = new Button(-0.31428571429f, -0.8f);
		this.stats.setTextSize(0.7f);
		this.stats.setText("stats");
		this.add(this.stats);
		
		this.options = new Button(0.01428571428f, -0.8f);
		this.options.setTextSize(0.7f);
		this.options.setText("options");
		this.add(this.options);
		
		this.credits = new Button(0.34285714285f, -0.8f);
		this.credits.setTextSize(0.7f);
		this.credits.setText("credits");
		this.credits.addClickListener(new IClickListener(){

			@Override
			public void clicked(int x, int y, int button) 
			{
				getControl().showCredits();
			}

			@Override
			public void unclicked() {
				// TODO Auto-generated method stub
				
			}
		});
		this.add(this.credits);
		
		this.close = new Button(0.67142857142f, -0.8f);
		this.close.setTextSize(0.7f);
		this.close.setText("close");
		this.close.addClickListener(new IClickListener(){

			@Override
			public void clicked(int x, int y, int button) 
			{
				System.exit(1337);
		}

			@Override
			public void unclicked() {
				// TODO Auto-generated method stub
				
			}});
		this.add(this.close);
		
		this.headLabel = new Label(-0.6f, 0.8f, "space empires");
		this.headLabel.setTextSize(2.5f);
		this.headLabel.setTextColor(1.0f, 1.0f, 1.0f, 0.1f);
		this.add(this.headLabel);
		
		Textbox t = new Textbox(-0.5f, 0.0f);
		this.add(t);
		
		this.setDrawBackground(true);
	}
	
	public void setLabelText(String text)
	{
		//((Label)this.getComponentById(5)).setText(text);
	}
}
