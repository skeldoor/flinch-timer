package com.skeldoor;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.ProgressBarComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@PluginDescriptor(
	name = "Flinch Timer"
)
public class FlinchTimerPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private FlinchTimerConfig config;

	boolean isTargetted = false;
	int ticksSinceLastHit = 0;
	private List<Actor> targettingMe = new ArrayList<>();


	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	private void onGameTick(GameTick gameTick){
		client.getLocalPlayer().
	}


	@Provides
	FlinchTimerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FlinchTimerConfig.class);
	}

	static void buildPanel(PanelComponent panelComponent, int minimum, int maximum, float value, Color foregroundColour, Dimension size, boolean showLabels) {
		ProgressBarComponent progressBar = new ProgressBarComponent();
		progressBar.setMinimum(minimum);
		progressBar.setMaximum(maximum);
		progressBar.setValue(value);
		progressBar.setBackgroundColor(Color.BLACK);
		progressBar.setForegroundColor(foregroundColour);
		progressBar.setFontColor(Color.WHITE);
		progressBar.setLabelDisplayMode(ProgressBarComponent.LabelDisplayMode.FULL);

		if (!showLabels) {
			progressBar.setLabelDisplayMode(ProgressBarComponent.LabelDisplayMode.TEXT_ONLY);
		}


		ProgressBarComponent paddingBar = new ProgressBarComponent();
		paddingBar.setMinimum(minimum);
		paddingBar.setMaximum(maximum);
		paddingBar.setValue(value);
		paddingBar.setBackgroundColor(Color.BLACK);
		paddingBar.setForegroundColor(foregroundColour);
		paddingBar.setFontColor(Color.WHITE);
		paddingBar.setLabelDisplayMode(ProgressBarComponent.LabelDisplayMode.TEXT_ONLY);
		// Set height of bar by padding the top and bottom with empty lines
		for (int i = 0; i < size.height; i++){
			panelComponent.getChildren().add(paddingBar);
		}

		panelComponent.getChildren().add(progressBar);

		for (int i = 0; i < size.height; i++){
			panelComponent.getChildren().add(paddingBar);
		}
	}
}
