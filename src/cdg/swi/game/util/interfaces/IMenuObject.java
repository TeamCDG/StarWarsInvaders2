package cdg.swi.game.util.interfaces;

public interface IMenuObject 
{
	
	int getId();
	
	void setId(int id);
	
	void drawUI();
	
	void drawSelection();

	IClickListener getClickListener();
	
	ISelectListener getSelectListener();
}
