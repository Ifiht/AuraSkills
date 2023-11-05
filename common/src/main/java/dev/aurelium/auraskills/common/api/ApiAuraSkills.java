package dev.aurelium.auraskills.common.api;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.message.MessageManager;
import dev.aurelium.auraskills.api.registry.NamespacedId;
import dev.aurelium.auraskills.api.registry.NamespacedRegistry;
import dev.aurelium.auraskills.api.skill.XpRequirements;
import dev.aurelium.auraskills.api.user.UserManager;
import dev.aurelium.auraskills.common.AuraSkillsPlugin;
import dev.aurelium.auraskills.common.api.implementation.ApiMessageManager;
import dev.aurelium.auraskills.common.api.implementation.ApiNamespacedRegistry;
import dev.aurelium.auraskills.common.api.implementation.ApiUserManager;
import dev.aurelium.auraskills.common.api.implementation.ApiXpRequirements;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ApiAuraSkills implements AuraSkillsApi {

    private final AuraSkillsPlugin plugin;
    private final UserManager userManager;
    private final MessageManager messageManager;
    private final XpRequirements xpRequirements;
    private final Map<String, NamespacedRegistry> namespacedRegistryMap;

    public ApiAuraSkills(AuraSkillsPlugin plugin) {
        this.plugin = plugin;
        this.userManager = new ApiUserManager(plugin);
        this.messageManager = new ApiMessageManager(plugin);
        this.xpRequirements = new ApiXpRequirements(plugin);
        this.namespacedRegistryMap = new HashMap<>();
    }

    @Override
    public UserManager getUserManager() {
        return userManager;
    }

    @Override
    public MessageManager getMessageManager() {
        return messageManager;
    }

    @Override
    public XpRequirements getXpRequirements() {
        return xpRequirements;
    }

    @Override
    public NamespacedRegistry getRegistry(String namespace, File contentDirectory) {
        namespace = namespace.toLowerCase(Locale.ROOT);
        if (namespace.equals(NamespacedId.AURASKILLS)) {
            throw new IllegalArgumentException("Cannot get a namespaced registry for auraskills, use the name of your plugin!");
        }
        final String finalNamespace = namespace;
        return namespacedRegistryMap.computeIfAbsent(namespace, s -> new ApiNamespacedRegistry(plugin, finalNamespace, contentDirectory));
    }

}
