package com.skeldoor;

import java.awt.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.client.Notifier;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

public class FlinchTimerOverlay extends OverlayPanel {

    @Inject
    private Client client;

    @Inject
    private FlinchTimerConfig config;

    @Inject
    private Notifier notifier;

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
        List<Flincher> finishedFlinchers = new ArrayList<>();
        for (Flincher flincher : flinchers) {
            int timeUntilFlinch = Math.max(flinchTickDuration - (client.getTickCount() - flincher.getLastAttackTick()), 0);
            if (timeUntilFlinch > 0) {
                if (config.renderFlinchBar()) renderFlinchBar(graphics, flincher, timeUntilFlinch);
            } else {
                finishedFlinchers.add(flincher);
                if (config.sendNotification()) notifier.notify("Flinch ready for " + flincher.getActor().getName());
            }
        }
        for (Flincher toRemove : finishedFlinchers){
            flinchers.remove(toRemove);
        }
        return super.render(graphics);
    }

    void renderFlinchBar(Graphics2D graphics, Flincher flincher, int timeUntilFlinch){
        net.runelite.api.Point p = flincher.getActor().getCanvasTextLocation(graphics, "Flinch Progress Bar", flincher.getActor().getLogicalHeight() / 2);
        if (p == null) return;
        Point barLocation = new Point(p.getX(), p.getY());
        setPreferredSize(new Dimension(
                config.flinchBarSize().width,
                0));
        setPreferredLocation(barLocation);
        String nameLabel = (config.showNameLabel()) ? flincher.getActor().getName() :  "";
            FlinchTimerPlugin.buildPanel(
                panelComponent,
                0,
                flinchTickDuration,
                timeUntilFlinch,
                config.flinchBarColour(),
                config.flinchBarSize(),
                config.showTimerLabel(),
                barLocation,
                nameLabel);
    }
}
