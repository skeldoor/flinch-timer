package com.skeldoor;

import java.awt.*;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

public class FlinchTimerOverlay extends OverlayPanel {

    @Inject
    private Client client;

    @Inject
    private FlinchTimerConfig config;

    @Inject
    FlinchTimerOverlay(
            Client client,
            FlinchTimerPlugin plugin)
    {
        super(plugin);
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        setPriority(OverlayPriority.LOW);
        setMovable(true);
        this.client = client;
    }


    @Override
    public Dimension render(Graphics2D graphics) {
        if (config.renderFlinchBar()) renderEnergy();

        return super.render(graphics);
    }

    void renderEnergy(){
        setPreferredSize(new Dimension(
                config.flinchBarSize().width,
                0));
        FlinchTimerPlugin.buildPanel(
                panelComponent,
                0,
                100,
                (int)(client.getEnergy() / 100f),
                config.flinchBarColour(),
                config.flinchBarSize(),
                config.showLabels());
    }
}
