package com.skeldoor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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

    private int flinchTickDuration = 11;

    public List<Flincher> flinchers = new ArrayList<>();


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
        if (config.renderFlinchBar()) renderEnergy(graphics);

        return super.render(graphics);
    }

    void renderEnergy(Graphics2D graphics){
        for (Flincher flincher : flinchers){
            net.runelite.api.Point p = flincher.getActor().getCanvasTextLocation(graphics, "Where should I be placed?", flincher.getActor().getLogicalHeight() / 2);
            if (p == null) return;
            Point barLocation = new Point(p.getX(), p.getY());
            System.out.println(barLocation);
            int timeUntilFlinch = Math.max(11 - (client.getTickCount() - flincher.getLastAttackTick()), 0);
            if (timeUntilFlinch > 0) {
                setPreferredSize(new Dimension(
                        config.flinchBarSize().width,
                        0));
                FlinchTimerPlugin.buildPanel(
                        panelComponent,
                        0,
                        11,
                        timeUntilFlinch,
                        config.flinchBarColour(),
                        config.flinchBarSize(),
                        config.showLabels(),
                        barLocation);
            }else {
                flinchers.remove(flincher);
            }
        }
    }
}
