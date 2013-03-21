package cdg.swi.game.util.interfaces;

public interface IMenuObject 
{
	
	int getId();
	
	void setId(int id);

	IClickListener getClickListener();
	
	ISelectListener getSelectListener();
}
