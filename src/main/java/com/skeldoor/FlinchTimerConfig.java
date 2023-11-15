package com.skeldoor;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("FlinchTimer")
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
		return new Dimension(100, 0);
	}
	@ConfigItem(
		keyName = "flinchBarColour",
		name = "Flinch Bar Colour",
		description = "Colour of the Flinch Bar"
	)
	default Color flinchBarColour() { return Color.yellow; }

	@ConfigItem
			(
					keyName = "showTimerLabel",
					name = "Show Timer Label",
					description = "Show time label on the Flinch Bar"
			)

	default boolean showTimerLabel() { return true; }

	@ConfigItem
			(
					keyName = "showNameLabel",
					name = "Show Name Label",
					description = "Show name label on the Flinch Bar"
			)

	default boolean showNameLabel() { return false; }

	@ConfigItem
			(
					keyName = "sendNotification",
					name = "Send Notification",
					description = "Send notification when flinch timer has expired"
			)

	default boolean sendNotification() { return true; }

}
