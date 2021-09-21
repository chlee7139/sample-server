package io.github.chlee7139.events

import io.github.chlee7139.func.Inventoring
import io.github.chlee7139.manager.DataManager
import io.papermc.paper.event.world.WorldGameRuleChangeEvent
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.io.File

class JoinQuitEvent : Listener {



    var files : File = File("plugins/Rpgs/config.yml")



    @EventHandler
    fun onPlayerJoin(e : PlayerJoinEvent){
        val p = e.player
        p.healthScale = 20.0

        var configs = DataManager().loadConfig(files)
        DataManager().createStat(p.uniqueId.toString())
        if(e.player.inventory.getItem(17)?.itemMeta?.displayName != "§e§l인벤토리") {
            p.inventory.setItem(
                17, Inventoring().createGuiItem(
                    Material.ENDER_CHEST, 1, "§e§l스텟",
                    "§r§f눌러서 §b스텟창§b으로 §e전환§f하세요.", "§f-----------"
                )
            )
            Inventoring().applyInv(p)

        }
        var sfile = File("plugins/Rpgs/Stat/${p.uniqueId}.yml")
        var stat : FileConfiguration = YamlConfiguration.loadConfiguration(sfile)
        stat.run {
            p.setWalkSpeed(p.walkSpeed - (0.001f * getInt("agi")))
            p.setWalkSpeed(p.walkSpeed + (0.001f * getInt("agi")))
        }
        e.joinMessage = configs.get("joinmessage").toString().replace("player", p.name)
    }
    @EventHandler
    fun onPlayerQuit(e : PlayerQuitEvent){
        var configs = DataManager().loadConfig(files)
        val p = e.player
        e.quitMessage = configs.get("quitmessage").toString().replace("player", p.name)
    }
    //@EventHandler
    //fun onEquipArmor(e : ArmorEquipEvent){
        //e.player.sendMessage("아 왜 갑옷 입누 째째")
    //}
    @EventHandler
    fun onWorldChange(e: WorldGameRuleChangeEvent){
        e.isCancelled = true
    }
}