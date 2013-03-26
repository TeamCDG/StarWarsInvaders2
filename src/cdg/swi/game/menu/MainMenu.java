package cdg.swi.game.menu;

public class MainMenu extends MenuFrame 
{

	public MainMenu()
	{
		Button b = new Button(-0.5f, 0.2f);
		this.add(b);
		
		Button b2 = new Button(0.0f, 0.0f);
		this.add(b2);
		
		Label l = new Label(-1.0f,1.0f,"E");
		this.add(l);
	}
}
