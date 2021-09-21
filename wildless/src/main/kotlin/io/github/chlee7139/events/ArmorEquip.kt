package io.github.chlee7139.events

import com.codingforcookies.armorequip.ArmorEquipEvent
import io.github.chlee7139.manager.DataManager
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.io.File

class ArmorEquip : Listener {
    @EventHandler
    fun onArmorEquip(e : ArmorEquipEvent){
        var nar = e.newArmorPiece
        var oar = e.oldArmorPiece
        var p = e.player
        var stat = DataManager().getStat(p.uniqueId.toString())
        var sfile = File("plugins/Rpgs/Stat/${p.uniqueId}.yml")
        if(nar != null){
            if(nar.type != Material.AIR ){
                if(nar.itemMeta?.hasLore() == true){
                    for(i in 0..(nar.itemMeta?.lore?.size?.minus(1)!!)){
                        if(nar.itemMeta?.lore?.get(i)?.contains("레벨") == true){
                            if(ChatColor.stripColor(nar.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                if(p.level < ChatColor.stripColor(nar.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4레벨§f이 부족합니다."))
                                    e.setCancelled(true)
                                }
                            }
                        }
                        if(nar.itemMeta?.lore?.get(i)?.contains("요구 힘") == true){
                            if(ChatColor.stripColor(nar.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                if(stat.getInt("str") < ChatColor.stripColor(nar.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4힘 §e스텟§f이 부족합니다."))
                                    e.setCancelled(true)

                                }
                            }
                        }
                        if(nar.itemMeta?.lore?.get(i)?.contains("요구 손재주") == true){
                            if(ChatColor.stripColor(nar.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                if(stat.getInt("def") < ChatColor.stripColor(nar.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§b손재주 §e스텟§f이 부족합니다."))
                                    e.setCancelled(true)
                                }
                            }
                        }
                        if(nar.itemMeta?.lore?.get(i)?.contains("요구 집중") == true){
                            if(ChatColor.stripColor(nar.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                if(stat.getInt("acc") < ChatColor.stripColor(nar.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§c집중 §e스텟§f이 부족합니다."))
                                    e.setCancelled(true)


                                }
                            }
                        }
                        if(nar.itemMeta?.lore?.get(i)?.contains("요구 민첩") == true){
                            if(ChatColor.stripColor(nar.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                if(stat.getInt("agi") < ChatColor.stripColor(nar.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§e민첩 §e스텟§f이 부족합니다."))
                                    e.setCancelled(true)


                                }
                            }
                        }

                    }
                }
            }
        }
    }
}
