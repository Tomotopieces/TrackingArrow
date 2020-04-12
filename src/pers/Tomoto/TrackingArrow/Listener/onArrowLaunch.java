package pers.Tomoto.TrackingArrow.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import pers.Tomoto.TrackingArrow.TrackingArrow;

import java.util.List;

public class onArrowLaunch implements Listener {
    private TrackingArrow _plugin;

    public onArrowLaunch(TrackingArrow plugin) {
        this._plugin = plugin;
    }

    @EventHandler
    public void onArrowLaunch(ProjectileLaunchEvent event) {
        if(event.getEntity().getShooter() instanceof Player) {
            Player shooter = (Player)event.getEntity().getShooter();
            ItemStack handItem = shooter.getInventory().getItemInMainHand();
            if(handItem.getType() == Material.BOW && handItem.getItemMeta().getLore().contains("Track")) {
                shooter.sendMessage("[TrackingArrow]: Tracking.");
                Location target = NearestMonster(shooter.getNearbyEntities(20, 20, 20), shooter.getLocation()).getEyeLocation();
                Arrow arrow = (Arrow)event.getEntity();
                arrow.addScoreboardTag("Track");
                arrow.setGravity(false);
                Bukkit.getScheduler().runTaskTimer(_plugin, () -> {
                    if(arrow.getScoreboardTags().contains("Track")) {
                        Vector velocity = new Vector(
                                target.getX() - arrow.getLocation().getX(),
                                target.getY() - arrow.getLocation().getY(),
                                target.getZ() - arrow.getLocation().getZ());
                        velocity.normalize();
                        velocity.multiply(arrow.getVelocity().length());
                        arrow.setVelocity(velocity);
                    }
                }, 0, 5);
            }
        }
    }

    public Monster NearestMonster(List<Entity> entityList, Location location) {
        Monster result = null;
        for(Entity entity : entityList) {
            if(entity instanceof Monster) {
                result = (Monster)entity;
                break;
            }
        }
        if(result == null) {
            return null;
        }
        double distance = result.getLocation().distance(location);
        for(Entity entity : entityList) {
            if(entity instanceof Monster) {
                if(entity.getLocation().distance(location) < distance) {
                    result = (Monster)entity;
                    distance = entity.getLocation().distance(location);
                }
            }
        }
        return result;
    }
}
