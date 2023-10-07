package com.skeldoor;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("example")
public interface FlinchTimerConfig extends Config
{
	@ConfigItem(
		keyName = "renderFlinchBar",
		name = "Render Flinch Bar",
		description = "Show the flinch timer as a bar on screen"
	)
	default boolean renderFlinchBar()
	{
		return true;
	}

	@ConfigItem(
		keyName = "flinchBarSize",
		name = "Flinch Bar Size",
		description = "Size of the Flinch Bar"
	)
	default Dimension flinchBarSize()
	{
		return new Dimension(100, 1);
	}
	@ConfigItem(
		keyName = "flinchBarColour",
		name = "Flinch Bar Colour",
		description = "Colour of the Flinch Bar"
	)
	default Color flinchBarColour() { return Color.yellow; }

	@ConfigItem
			(
					keyName = "showLabels",
					name = "Show Labels",
					description = "Show labels on the minibars"
			)

	default boolean showLabels() { return true; }

}
