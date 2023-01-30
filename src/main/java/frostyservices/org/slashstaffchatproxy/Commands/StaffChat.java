package frostyservices.org.slashstaffchatproxy.Commands;

import frostyservices.org.slashstaffchatproxy.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class StaffChat extends Command {

    private File file;
    private Configuration configuration;
    public StaffChat(Main main) {
        super("StaffChat");
    }


    public void execute(CommandSender sender, String[] args) {
        file = new File(ProxyServer.getInstance().getPluginsFolder() + "/StaffChatProxy");
        file.mkdirs();
        file = new File(file + "/config.yml");
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            sender.sendMessage(new ComponentBuilder("An error occurred. Unable to read the configuration file!").color(ChatColor.RED).create());
            return;
        }
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (p.hasPermission("sc.write")) {
                String message = configuration.getString("Message");
                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    if (player.hasPermission("sc.read")) {
                        player.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', message).replaceAll("<server>", p.getServer().getInfo().getName()).replaceAll("<sender>", p.getName()).replaceAll("<message>", args[0]).replaceAll("<msg>", args[0])).create());
                    }
                }
            } else {
                String no_permission_message = configuration.getString("No-Permission-Message");
                p.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', no_permission_message)).create());
            }
        }
        else {
            String not_player_message = configuration.getString("Not-Player-Message");
            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', not_player_message)).create());
        }
    }
}