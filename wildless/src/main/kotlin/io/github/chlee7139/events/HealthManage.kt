package io.github.chlee7139.events

import org.bukkit.entity.Damageable
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityRegainHealthEvent


class HealthManage : Listener {
    @EventHandler
    fun onRegenHealth(e : EntityRegainHealthEvent){
        if(e.entity.type == EntityType.PLAYER){
            if(e.regainReason == EntityRegainHealthEvent.RegainReason.REGEN || e.regainReason == EntityRegainHealthEvent.RegainReason.MAGIC
                    || e.regainReason == EntityRegainHealthEvent.RegainReason.MAGIC_REGEN || e.regainReason == EntityRegainHealthEvent.RegainReason.SATIATED
                    || e.regainReason == EntityRegainHealthEvent.RegainReason.EATING){
                val player = e.entity

                val damag: Damageable = player as Player
                val health: Double = damag.maxHealth

                e.amount = e.amount * (health / 20.0)
            }
        }
    }
}