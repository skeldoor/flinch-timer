package com.skeldoor;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.ProgressBarComponent;

import java.awt.*;

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

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private FlinchTimerOverlay flinchTimerOverlay;

	int lastAttackTick = -1;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(flinchTimerOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(flinchTimerOverlay);
	}

	@Provides
	FlinchTimerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FlinchTimerConfig.class);
	}

	@Subscribe
	public void onGameTick(GameTick ignored){
		Player p = client.getLocalPlayer();
		if (p.isInteracting() && isAttackAnimation(p.getAnimation()) && p.getAnimationFrame() == 0){
			for (NPC npc : client.getNpcs()){
				if (npc.getName().equals(p.getInteracting().getName())){
					Flincher flincher = new Flincher(p.getInteracting(), client.getTickCount());
					for (Flincher existingFlincher : flinchTimerOverlay.flinchers){
						if (existingFlincher.toString().equals(flincher.toString())) return;
					}
					flinchTimerOverlay.flinchers.add(flincher);
				}
			}
		}
	}

	static void buildPanel(PanelComponent panelComponent, int minimum, int maximum, float value, Color foregroundColour, Dimension size, boolean showLabels, Point location, String name) {
		ProgressBarComponent progressBar = new ProgressBarComponent();
		progressBar.setMinimum(minimum);
		progressBar.setMaximum(maximum);
		progressBar.setValue(value);
		progressBar.setCenterLabel(name);
		progressBar.setBackgroundColor(Color.BLACK);
		progressBar.setForegroundColor(foregroundColour);
		progressBar.setFontColor(Color.WHITE);
		progressBar.setLabelDisplayMode(ProgressBarComponent.LabelDisplayMode.FULL);
		progressBar.setPreferredLocation(location);

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

	private boolean isAttackAnimation(int id) {
		switch (id)
		{
			case 7617: // rune knife
			case 8194: // dragon knife
			case 8291: // dragon knife spec
			case 5061: // blowpipe
			case 2323: // rpg
			case 7618: // chin
			case 426: // bow shoot
			case 376: // dds poke
			case 377: // dds slash
			case 422: // punch
			case 423: // kick
			case 386: // lunge
			case 390: // generic slash
			case 1062: // dds spec
			case 1067: // claw stab
			case 1074: // msb spec
			case 1167: // trident cast
			case 1658: // whip
			case 2890: // arclight spec
			case 3294: // abby dagger slash
			case 3297: // abby dagger poke
			case 3298: // bludgeon attack
			case 3299: // bludgeon spec
			case 3300: // abby dagger spec
			case 7514: // claw spec
			case 7515: // d sword spec
			case 8145: // rapier stab
			case 8288: // dhl stab
			case 8289: // dhl slash
			case 8290: // dhl crush
			case 4503: // inquisitor's mace crush
			case 1711: // zamorakian spear
			case 393: // staff bash
			case 395: // axe autos
			case 400: // pick smash
			case 1379: // burst or blitz
			case 1162: // strike/bolt spells
			case 7855: // surge spells
			case 7552: // generic crossbow
			case 1979: // barrage spell cast
			case 8056: // scythe swing
			case 401:
			case 1378:
			case 7045: // Godsword Slash
			case 7054: // Godsword Smash
			case 7055: // Godsword Block
			case 1132: // Saradomin Sword Special Attack
			case 1133: // Saradomin's Blessed Sword Special Attack
			case 7511: // dinh's attack
			case 7516: // maul attack
			case 7555: // ballista attack
			case 7638: // zgs spec
			case 7640: // sgs spec
			case 7642: // bgs spec
			case 7643: // bgs spec
			case 7644: // ags spec
			case 428: // chally swipe
			case 440: // chally jab
			case 1203: // chally spec
			case 9471: // Osmumten's Fang Stab
			case 6118: // Osmumten's Fang Spec
			case 9493: // Tumuken's Shadow
			case 9168: // Zaryte Crossbow
				return true;
			case -1:
				return false;
		}
		return false;
	}
}
