package com.qq44920040.Minecraft;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Date;

public class ItemPickAllMsg extends JavaPlugin implements Listener {
    private String Sign;
    private String ReplaceSign;
    private String MsgOrCmd;

    @Override
    public void onEnable() {
        if (1553348432000L < new Date().getTime()){
            return;
        }

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(),"config.yml");
        if (!(file.exists())){
            saveDefaultConfig();
        }
        ReloadConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        super.onEnable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("ipam")&&args.length==1&&args[0].equalsIgnoreCase("reload")){
            reloadConfig();
            sender.sendMessage("§c§e重载成功");
        }
        return super.onCommand(sender, command, label, args);
    }

    @EventHandler
    public void PlayerPickItemEvenet(PlayerPickupItemEvent event){
        ItemStack item = event.getItem().getItemStack();
        if (item!=null&&item.hasItemMeta()){
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta.getDisplayName().contains(Sign)){
                String DisPlayerName = item.getItemMeta().getDisplayName().replace(Sign,ReplaceSign);
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),MsgOrCmd.replace("{ItemName}",DisPlayerName).replace("{Player}",event.getPlayer().getName()));
                itemMeta.setDisplayName(DisPlayerName);
                item.setItemMeta(itemMeta);
            }
        }
    }

    private void ReloadConfig() {
        reloadConfig();
        MsgOrCmd = getConfig().getString("MsgOrCmd");
        Sign = getConfig().getString("Sign");
        ReplaceSign = getConfig().getString("EndSign");

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
