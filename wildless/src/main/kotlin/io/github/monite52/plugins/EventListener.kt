package io.github.monite52.plugins

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class EventListener : Listener {
    @EventHandler
    fun onPlayerJoin(e : PlayerJoinEvent){}
    @EventHandler
    fun onPlayerQuit(e : PlayerQuitEvent){}
}