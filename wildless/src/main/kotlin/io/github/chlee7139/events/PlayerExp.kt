package io.github.chlee7139.events

import io.github.chlee7139.func.Inventoring
import io.github.chlee7139.manager.DataManager
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerExpChangeEvent
import org.bukkit.event.player.PlayerLevelChangeEvent
import java.io.File
import java.util.*
import kotlin.math.floor

class PlayerExp : Listener{
    @EventHandler
    fun OnLevelChange(event: PlayerLevelChangeEvent){
        var p = event.player
        var hlv : Int = event.newLevel
        var nlv : Int = event.oldLevel
        if(event.player.openInventory.type == InventoryType.ANVIL || event.player.openInventory.type == InventoryType.ENCHANTING){
            event.player.level = nlv
        }
        if(hlv > nlv) {
            var stat = DataManager().getStat(p.uniqueId.toString())
            var sfile = File("plugins/Rpgs/Stat/${p.uniqueId}.yml")
            stat.run {
                set("sp", getInt("sp") + (4 * (hlv - nlv)))
                var ps: Int = 4 * (hlv - nlv)
                save(sfile)
                load(sfile)
                Inventoring().Statory(p.inventory, p)
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4스텟 §e포인트 §f획득 §f[§c+${ps}§f]"))
            }
        }
    }
    @EventHandler
    fun onGetExperience(event: PlayerExpChangeEvent){
        var p = event.player
        var xm : Int = event.amount
        event.amount = 0
        var stat = DataManager().getStat(p.uniqueId.toString())
        var sfile = File("plugins/Rpgs/Stat/${p.uniqueId}.yml")
        var nlv = event.player.level
        var files : File = File("plugins/Rpgs/config.yml")
        var config = DataManager().loadConfig(files)
        var el : Int = config.getInt("custom_exp") + (2 *(nlv + 1 * nlv + 1))
        stat.run{
            if(getInt("agi") >= 40){
                if(Random().nextInt(10)+1 == 1){
                    xm *= 2
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§f[§e알림§f] $xm §cEXP §f(§42배§f)"))
                    if(p.exp + (xm.toDouble() / el.toDouble()).toFloat() < 1.0f) {
                        p.exp = p.exp + (xm.toDouble() / el.toDouble()).toFloat()
                    }
                    else{
                        var intep = floor(p.exp + (xm.toDouble() / el.toDouble()).toFloat()).toInt()
                        p.giveExpLevels(intep)
                        p.exp = (p.exp + (xm.toDouble() / el.toDouble()).toFloat()) - intep.toFloat()
                    }
                }
                else{
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§f[§e알림§f] $xm §cEXP"))

                    if(p.exp + (xm.toDouble() / el.toDouble()).toFloat() < 1.0f) {
                        p.exp = p.exp + (xm.toDouble() / el.toDouble()).toFloat()
                    }
                    else{
                        var intep = floor(p.exp + (xm.toDouble() / el.toDouble()).toFloat()).toInt()
                        p.giveExpLevels(intep)
                        p.exp = (p.exp + (xm.toDouble() / el.toDouble()).toFloat()) - intep.toFloat()
                    }
                }
            }
            else{
                event.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§f[§e알림§f] $xm §cEXP"))

                if(p.exp + (xm.toDouble() / el.toDouble()).toFloat() < 1.0f) {
                    p.exp = p.exp + (xm.toDouble() / el.toDouble()).toFloat()
                }
                else{
                    var intep = floor(p.exp + (xm.toDouble() / el.toDouble()).toFloat()).toInt()
                    p.giveExpLevels(intep)
                    p.exp = (p.exp + (xm.toDouble() / el.toDouble()).toFloat()) - intep.toFloat()
                }
            }
        }
    }
}