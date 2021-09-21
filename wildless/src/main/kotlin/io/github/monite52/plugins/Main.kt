package io.github.monite52.plugins

import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        server.consoleSender.sendMessage("${ChatColor.GREEN}확장 야생 플러그인이 활성화 되었습니다.")

    }

    override fun onDisable() {
        server.consoleSender.sendMessage("${ChatColor.RED}확장 야생 플러그인이 비활성화 되었습니다.")

    }
}