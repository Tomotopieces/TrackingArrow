package pers.Tomoto.TrackingArrow.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import pers.Tomoto.TrackingArrow.TrackingArrow;

public class onArrowHit implements Listener {
    private TrackingArrow _plugin;

    public onArrowHit(TrackingArrow plugin) {
        this._plugin = plugin;
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if(event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow)event.getEntity();
            if(arrow.getScoreboardTags().contains("Track")) {
                arrow.setGravity(true);
                arrow.removeScoreboardTag("Track");
                Bukkit.getScheduler().cancelTasks(_plugin);
            }
        }
    }
}
