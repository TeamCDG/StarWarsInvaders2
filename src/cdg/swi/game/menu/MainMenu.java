package cdg.swi.game.menu;

import cdg.swi.game.util.interfaces.IClickListener;
import cdg.swi.game.util.interfaces.IGameControl;

public class MainMenu extends MenuFrame 
{

	public MainMenu(IGameControl c)
	{
		super(c);
		Button b = new Button(-0.25f, 0.2f);
		this.add(b);
		
		Button b2 = new Button(-0.25f, 0.0f);
		b2.setText("Credits");
		b2.addClickListener(new IClickListener(){

			@Override
			public void clicked(int x, int y, int button) 
			{
				getControl().showCredits();
			}
		});
		this.add(b2);
		
		Button b3 = new Button(-0.25f, -0.2f);
		b3.setText("Close");
		b3.addClickListener(new IClickListener(){

			@Override
			public void clicked(int x, int y, int button) 
			{
				System.exit(1337);
			}
		});
		this.add(b3);
		
		Label l = new Label(-1.0f,1.0f,"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		this.add(l);
	}
	
	public void setLabelText(String text)
	{
		((Label)this.getComponentById(4)).setText(text);
	}
}
