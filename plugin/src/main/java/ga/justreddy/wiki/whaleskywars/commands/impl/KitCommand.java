package ga.justreddy.wiki.whaleskywars.commands.impl;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.commands.CommandRunner;
import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommand;
import ga.justreddy.wiki.whaleskywars.model.kits.Kit;
import ga.justreddy.wiki.whaleskywars.model.kits.exceptions.KitRequestException;
import ga.justreddy.wiki.whaleskywars.model.kits.requests.KitRequest;
import ga.justreddy.wiki.whaleskywars.model.kits.requests.requests.KitCreationRequest;
import ga.justreddy.wiki.whaleskywars.model.kits.requests.requests.KitModificationRequest;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JustReddy
 */
public class KitCommand implements SkyWarsCommand {

    @Override
    public String getName() {
        return "kit";
    }

    @Override
    public String getDescription() {
        return "Configure the kits for the game.";
    }

    @Override
    public String getUsage() {
        return "/ws kit";
    }

    @Override
    public String getPermission() {
        return "whaleskywars.admin.kit";
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public void execute(CommandRunner runner, String[] args) {
        IGamePlayer player = runner.asGamePlayer();

        if (args.length == 1) {
            // TODO
            return;
        }

        switch (args[1].toLowerCase()) {
            case "create":
                createKitCommand(player, args);
                break;
            case "save":
                saveKitCommand(player);
                break;
        }
    }

    private void createKitCommand(IGamePlayer player, String[] args) {

        if (args.length != 3) {
            // TODO
            player.sendMessage("&cUsage: /ws kit create <name>");
            return;
        }

        String name = args[2];

        final Kit kit = WhaleSkyWars.getInstance().getKitManager().getKitByName(name);
        if (kit != null) {
            // TODO
            player.sendMessage("&cA kit with that name already exists.");
            return;
        }

        // We know player exists here lol
        Player bukkitPlayer = player.getPlayer().get();
        
        ItemStack itemInHand = WhaleSkyWars.getInstance().getNms().getItemInHand(bukkitPlayer);
        if (itemInHand == null || itemInHand.getType() == Material.AIR) {
            // TODO
            player.sendMessage("&cYou must be holding an item in your hand.");
            return;
        }

        try {
            WhaleSkyWars.getInstance().getKitRequestManager().makeRequest(player, new KitCreationRequest(player, name, itemInHand));
        }catch (KitRequestException ex) {
            ex.printStackTrace();
        }
    }

    private void saveKitCommand(IGamePlayer player) {

        KitRequest request = WhaleSkyWars.getInstance().getKitRequestManager().getKitRequest(player);

        if (request == null) {
            // TODO
            player.sendMessage("&cYou do not have a kit request.");
            return;
        }

        Player bukkitPlayer = player.getPlayer().get();

        if (request instanceof KitModificationRequest) {
            KitModificationRequest modificationRequest = (KitModificationRequest) request;
            // TODO
        } else if (request instanceof KitCreationRequest) {
            KitCreationRequest creationRequest = (KitCreationRequest) request;
            Kit kitFromPlayer = Kit.fromPlayerInventory(bukkitPlayer, creationRequest.getMaterial(), creationRequest.getKitName());
            bukkitPlayer.getInventory().setArmorContents(null);
            WhaleSkyWars.getInstance().getKitManager().cacheOrReplace(kitFromPlayer);
            // TODO
            player.sendMessage("&aKit " + creationRequest.getKitName() + " has been saved.");
        }

        bukkitPlayer.getInventory().clear();

    }

}
