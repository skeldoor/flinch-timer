package com.skeldoor;

import net.runelite.api.Actor;

public class Flincher {
    private Actor actor;
    private int lastAttackTick;

    public Flincher(Actor actor, int lastAttackTick){
        this.actor = actor;
        this.lastAttackTick = lastAttackTick;
    }

    public int getLastAttackTick() {
        return lastAttackTick;
    }

    public Actor getActor() {
        return actor;
    }

    public void setLastAttackTick(int lastAttackTick) {
        this.lastAttackTick = lastAttackTick;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "Flincher{" +
                "actorName=" + actor.getName() +
                ", lastAttackTick=" + lastAttackTick +
                '}';
    }
}
