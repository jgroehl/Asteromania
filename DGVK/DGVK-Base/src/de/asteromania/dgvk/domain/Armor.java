package de.asteromania.dgvk.domain;

import java.util.ArrayList;
import java.util.List;

public class Armor
{
	private final int graphicsId;
	private final List<Bonus> boni = new ArrayList<Bonus>();

	public Armor(int graphicsId, Bonus... bonus)
	{
		this.graphicsId = graphicsId;
		if (bonus != null)
			for (Bonus b : bonus)
				boni.add(b);
	}

	public enum ArmorType
	{
		LIGHT, HEAVY;
	}

	public enum ArmorPlace
	{
		HEAD, BREAST, LEGS, SHOES;
	}

	public int getGraphicsId()
	{
		return graphicsId;
	}

}
