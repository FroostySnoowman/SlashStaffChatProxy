package frostyservices.org.slashstaffchatproxy;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import frostyservices.org.slashstaffchatproxy.Commands.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public final class Main extends Plugin {

    private File file;
    @Override
    public void onEnable() {
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aLoading &eSlashStaffChat &b(Proxy Edition)&a!"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChat(this));
        file = new File(ProxyServer.getInstance().getPluginsFolder() + "/StaffChatProxy");
        file.mkdirs();
        file = new File(file + "/config.yml");
        generateConfigIfFileDoesntExist("config.yml", file);
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aLoaded &eSlashStaffChat &b(Proxy Edition)&a!"));
    }

    private void generateConfigIfFileDoesntExist(String resourceName, File configFile) {
        if (configFile.exists()) return;
        try {
            getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aCreating &eSlashStaffChat &aconfiguration file!"));
            InputStream stream = Main.class.getClassLoader().getResourceAsStream(resourceName);
            if (stream == null) throw new IllegalStateException("Config file does not exist in resources directory!");
            Files.copy(stream, configFile.toPath());
            getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aCreated &eSlashStaffChat &aconfiguration file!"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&eSlashStaffChat &b(Proxy Edition) &chas disabled!"));
    }
}
