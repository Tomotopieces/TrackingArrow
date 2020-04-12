package pers.Tomoto.TrackingArrow;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import pers.Tomoto.TrackingArrow.Listener.onArrowHit;
import pers.Tomoto.TrackingArrow.Listener.onArrowLaunch;

import java.util.ArrayList;
import java.util.List;

public class TrackingArrow extends JavaPlugin implements Listener {

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new onArrowLaunch(this), this);
        Bukkit.getPluginManager().registerEvents(new onArrowHit(this), this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        int empty = event.getPlayer().getInventory().firstEmpty();
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.BLUE + "[TrackingArrow]: Welcome.");
        if(empty != -1) {
            ItemStack item = new ItemStack(Material.BOW, 1);
            ItemMeta meta = item.getItemMeta();
            ArrayList<String> lore = new ArrayList<String>();
            lore.add("Track");
            meta.setLore(lore);
            item.setItemMeta(meta);
            player.getInventory().setItem(empty, item);
            player.sendMessage(ChatColor.BLUE + "[TrackingArrow]: Give you a track bow.");
        }
    }
}
