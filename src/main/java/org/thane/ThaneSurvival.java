package org.thane;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.thane.Commands.Surv;
import org.thane.Effects.*;
import org.thane.Recipes.*;
import org.thane.Recipes.Dechanter;

import java.io.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThaneSurvival extends JavaPlugin implements Listener {

    private static ItemStack[] inventory = null;
    private static File switchInventory = null;

    public static Plugin plugin() {
        return ThaneSurvival.getPlugin(ThaneSurvival.class);
    }

    @Override
    public void onEnable() {
        getLogger().info("ThaneSurvival has enabled.");
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new Armor(), this);
        Bukkit.getPluginManager().registerEvents(new Compressor(), this);
        Bukkit.getPluginManager().registerEvents(new org.thane.Effects.Dechanter(), this);
        Bukkit.getPluginManager().registerEvents(new Dechanter(), this);
        Bukkit.getPluginManager().registerEvents(new Surv(), this);
        Bukkit.getPluginManager().registerEvents(new CraftHandler(), this);
        Bukkit.getPluginManager().registerEvents(new MagicTable(), this);
        Bukkit.getPluginManager().registerEvents(new org.thane.Effects.Diversifier(), this);
        Bukkit.getPluginManager().registerEvents(new org.thane.Recipes.Diversifier(), this);
        Bukkit.getPluginManager().registerEvents(new AngelBoots(), this);
        Bukkit.getPluginManager().registerEvents(new FlightPlate(), this);

        Bukkit.addRecipe(MagicTable.recipe());
        Bukkit.addRecipe(Dechanter.recipe());
        Bukkit.addRecipe(RecipeBook.recipe());
        Bukkit.addRecipe(GardenSoup.recipe());
        Bukkit.addRecipe(MinecraftString.recipe());
        Bukkit.addRecipe(GardenSoup.recipe1());
        Bukkit.addRecipe(NameTag.recipe());
        Bukkit.addRecipe(NameTag.recipe1());
        Bukkit.addRecipe(Planks.recipe());
        Bukkit.addRecipe(smelt());
        Bukkit.addRecipe(smelt1());
        Bukkit.addRecipe(smelt2());

        Surv.loadRecipes();
        CraftHandler.loadRecipes();

        switchInventory = new File(getDataFolder().getAbsolutePath() + "/switchinventory");

        loadInventory();

    }

    @Override
    public void onDisable() {
        getLogger().info("ThaneSurvival has disabled.");

        saveInventory();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("surv")) {

            return Surv.handleCommand(sender, args);

        } else if (command.getName().equalsIgnoreCase("switch")) {
            if (sender instanceof Player) {

                if (((Player) sender).getGameMode().equals(GameMode.CREATIVE)) {
                    ((Player) sender).setGameMode(GameMode.SURVIVAL);
                    if (inventory != null) {
                        ((Player) sender).getInventory().setContents(inventory);
                    } else {
                        ((Player) sender).getInventory().clear();
                    }
                } else if (((Player) sender).getGameMode().equals(GameMode.SURVIVAL)) {
                    inventory = ((Player) sender).getInventory().getContents();
                    ((Player) sender).getInventory().clear();
                    ((Player) sender).setGameMode(GameMode.CREATIVE);
                }
                return true;
            }
        }
        return false;
    }

    private static FurnaceRecipe smelt() {
        return new FurnaceRecipe(new ItemStack(Material.BREAD), Material.WHEAT);
    }

    private static FurnaceRecipe smelt1() {
        return new FurnaceRecipe(new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH);
    }

    private static FurnaceRecipe smelt2() {
        ItemStack itemStack = new ItemStack(Material.SLIME_BALL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "Syrup");
        itemStack.setItemMeta(itemMeta);
        return new FurnaceRecipe(itemStack, Material.SUGAR);
    }

    private void loadInventory() {
        if (!getDataFolder().exists()) {
            if (!getDataFolder().mkdir()) {
                throw new RuntimeException("Could not create folder! " + getDataFolder().getAbsolutePath());
            }
        }

        try {
            FileInputStream fileStream = new FileInputStream(switchInventory);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            List<Map<String, Object>> input = (List<Map<String, Object>>) objectStream.readObject();
            ItemStack[] inputStack = new ItemStack[input.size()];
            for (int i = 0; i < input.size(); i++) {
                if(input.get(i) == null) {
                    inputStack[i] = null;
                } else {
                    inputStack[i] = ItemStack.deserialize(input.get(i));
                }
            }
            inventory = inputStack;

            getLogger().info("Loaded inventory from " + switchInventory.getAbsolutePath());
        } catch (FileNotFoundException fnfe) {
            // file isn't there, don't load
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveInventory() {
        if (inventory != null) {
            try {
                List<Map<String, Object>> output = new ArrayList<>();
                for(ItemStack stack : inventory) {
                    if (stack == null) {
                        output.add(null);
                    } else {
                        output.add(stack.serialize());
                    }
                }
                if (switchInventory.exists()) {
                    switchInventory.createNewFile();
                }

                FileOutputStream fileOut = new FileOutputStream(switchInventory, false);
                ObjectOutputStream oos = new ObjectOutputStream(fileOut);
                oos.writeObject(output);
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
    }
}
