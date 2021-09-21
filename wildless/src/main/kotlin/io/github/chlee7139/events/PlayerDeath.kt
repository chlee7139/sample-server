package io.github.chlee7139.events

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent
import io.github.chlee7139.func.Inventoring
import io.github.chlee7139.manager.DataManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack
import java.io.File

class PlayerDeath : Listener {

    @EventHandler
    fun onPlayerDeath(e : PlayerDeathEvent){
        e.keepLevel = true
        e.droppedExp = 0
        var p = e.entity
        if(!e.keepInventory) {
            e.keepInventory = true
            e.drops.clear()
            val invitem = DataManager().loadInv(p)
            var oslot = DataManager().loadSkillbar(p)
            var skfile = File("plugins/Rpgs/skills/${p.uniqueId}.yml")
            oslot.run {
                if (getBoolean("skillmode")) {
                    if (p.inventory.getItem(17)?.itemMeta?.displayName == "§e§l인벤토리") {
                        Inventoring().applyInv(p)
                    }
                    var invitem = DataManager().loadInv(p)
                    var ifile = File("plugins/Rpgs/Inv/${p.uniqueId}.yml")
                    invitem.set("slot${getInt("oslot")}", p.itemInHand)
                    invitem.save(ifile)
                    invitem.load(ifile)
                    Inventoring().loadHotbar(p)
                    p.inventory.setHeldItemSlot(getInt("oslot"))
                    set("skillmode", false)
                    save(skfile)
                    load(skfile)
                } else {
                    if (p.inventory.getItem(17)?.itemMeta?.displayName == "§e§l인벤토리") {
                        Inventoring().applyInv(p)
                    }
                }
            }

            for(i in p.inventory) {
                if(i != null && i.type != Material.AIR) {
                    if (i.itemMeta.displayName != "§e§l스텟") {
                        p.world.dropItem(p.location, i)
                    }
                }
            }
            p.inventory.clear()
            p.inventory.setItem(
                17, Inventoring().createGuiItem(
                    Material.ENDER_CHEST, 1, "§e§l스텟",
                    "§r§f눌러서 §b스텟창§b으로 §e전환§f하세요.", "§f-----------"
                )
            )
            //p.updateInventory()
        }


    }
    @EventHandler
    fun onPlayerRespawn(e: PlayerRespawnEvent){
        var p = e.player
        var skinv = DataManager().loadSkillbar(p)
        var itemlist = arrayOf((Material.BARRIER),
            (Material.CYAN_DYE),
            (Material.ORANGE_DYE),
            (Material.MAGENTA_DYE),
            (Material.GREEN_DYE),
            (Material.GRAY_DYE),
            (Material.LIGHT_GRAY_DYE)
        )
        Bukkit.getPluginManager().getPlugin("Rpgs")?.let {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(it, Runnable
            {
                var cool = IntArray(12)
                for(i in 0..11){
                    cool.set(i, p.getCooldown(itemlist[skinv.getInt("skillslot${i}")]))
                }
                p.resetCooldown()
                for(i in 0..11){
                    if(p.hasCooldown(itemlist[skinv.getInt("skillslot${i}")])){
                        p.setCooldown(itemlist[skinv.getInt("skillslot${i}")], 0)
                        p.setCooldown(itemlist[skinv.getInt("skillslot${i}")], cool[i])

                    }
                }
            },
                1)}

    }
}