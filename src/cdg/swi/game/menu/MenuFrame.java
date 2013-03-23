package cdg.swi.game.menu;

import java.util.*;

import cdg.swi.game.util.interfaces.IMenuObject;

public abstract class MenuFrame {

	private List<IMenuObject> components;
	
	protected void add(IMenuObject comp)
	{
		
	}
	
	protected void remove(int index)
	{
		
	}
	
	protected void remove(IMenuObject comp)
	{
		
	}
	
	protected void setComponent(int index, IMenuObject comp)
	{
		
	}
	
	protected IMenuObject getComponentById(int id)
	{
		for(int i = 0; i < this.components.size(); i++)
		{
			if(this.components.get(i).getId() == id)
				return this.components.get(i);
		}
		return null;
	}
	
	protected IMenuObject getComponent(int index)
	{
		return this.components.get(index);
	}
	
	protected List<IMenuObject> getComponents()
	{
		return this.components;
	}
	
	protected void draw()
	{
		
	}
	
	public void doSelection()
	{
		doSelection(false,false);
	}
	
	public void doSelection(boolean clearAfter)
	{
		doSelection(false,clearAfter);
	}
	
	public void doSelection(boolean clearBefore, boolean clearAfter)
	{
		
	}

}
