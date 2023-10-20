package config;

import com.example.myplugin.MyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InvalidObjectException;

public class ConfigValues {
    private final boolean configOverflowRequirements;

    private File file;
    private YamlConfiguration yamlConfiguration;
    public ConfigValues (MyPlugin instance) {
        file = new File(instance.getDataFolder(), "config.yml");
        if (!file.exists()) {
            instance.saveResource("config.yml", false);
        }
        yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to load configurations.");
        }
        yamlConfiguration.options().parseComments(true);
        this.configOverflowRequirements = yamlConfiguration.getBoolean("configOverflowRequirements");
    }

    public boolean getConfigOverflowRequirements() {
        return this.configOverflowRequirements;
    }
}
