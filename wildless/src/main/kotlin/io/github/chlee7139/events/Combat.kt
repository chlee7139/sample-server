package io.github.chlee7139.events

import io.github.chlee7139.func.Sound
import io.github.chlee7139.manager.DataManager
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.io.File
import kotlin.random.Random

class Combat : Listener {
    @EventHandler
    fun OnEntityDamagedByEntity(event: EntityDamageByEntityEvent){
        if(event.damager != null && event.damager.type == EntityType.PLAYER){
            var p = event.damager as Player
            var stat = DataManager().getStat(p.uniqueId.toString())
            var sfile = File("plugins/Rpgs/Stat/${p.uniqueId}.yml")
            stat.run {
                event.damage += getInt("str") / 10
                if(getInt("str") >= 40){
                    if(java.util.Random().nextInt(10)+1 <= 3) {
                        if(event.isCancelled == false) {
                            event.entity.setFireTicks(80)
                        }
                    }
                }


            /**var mg = nextDouble()
            if(stat[7] > 0) {
            if (mg <= (0.0005 * stat[7])) {
            event.damage = event.damage * 2
            (event.damager as Player).sendMessage("§4*크리티컬")
            }
            }**/
            var exmp = mapOf("레벨" to "level", "힘" to "str", "집중" to "acc", "손재주" to "def", "민첩" to "agi")
            if((event.damager as Player).itemInHand.type != Material.AIR){
                if((event.damager as Player).itemInHand.itemMeta?.hasLore() == true){
                    for(i in 0..((event.damager as Player).itemInHand.itemMeta?.lore?.size?.minus(1)!!)){
                        if((event.damager as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("추가") == true){
                            if(ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                    if(ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" ")?.get(2) != null) {
                                        exmp[ChatColor.stripColor(
                                            (event.damager as Player).itemInHand.itemMeta?.lore?.get(i)
                                        )?.split(" ")?.get(2)]?.let {
                                            if(exmp[ChatColor.stripColor(
                                                    (event.damager as Player).itemInHand.itemMeta?.lore?.get(i)
                                                )?.split(" ")?.get(2)]?.let { it1 -> getInt(it1) }!! < ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                                event.isCancelled = true
                                                (event.damager as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§c${ChatColor.stripColor(
                                                    (event.damager as Player).itemInHand.itemMeta?.lore?.get(i)
                                                )?.split(" ")?.get(2)}§e스텟§f이 부족합니다."))

                                            }
                                        }
                                    }


                            }
                        }
                        if((event.damager as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("요구 레벨") == true){
                            if(ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                if((event.damager as Player).level < ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                    (event.damager as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4레벨§f이 부족합니다."))
                                    event.setCancelled(true)
                                    event.entity.setFireTicks(0)

                                }
                            }
                        }
                        if((event.damager as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("요구 힘") == true){
                            if(ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                if(getInt("str") < ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                    (event.damager as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4힘 §e스텟§f이 부족합니다."))
                                    event.setCancelled(true)
                                    event.entity.setFireTicks(0)

                                }
                            }
                        }
                        if((event.damager as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("요구 손재주") == true){
                            if(ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                if(getInt("def") < ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                    (event.damager as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§b손재주 §e스텟§f이 부족합니다."))
                                    event.setCancelled(true)
                                    event.entity.setFireTicks(0)

                                }
                            }
                        }
                        if((event.damager as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("요구 집중") == true){
                            if(ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                if(getInt("acc") < ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                    (event.damager as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§c집중 §e스텟§f이 부족합니다."))
                                    event.setCancelled(true)
                                    event.entity.setFireTicks(0)

                                }
                            }
                        }
                        if((event.damager as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("요구 민첩") == true){
                            if(ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                if(getInt("agi") < ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                    (event.damager as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§e민첩 §e스텟§f이 부족합니다."))
                                    event.setCancelled(true)
                                    event.entity.setFireTicks(0)

                                }
                            }
                        }
                        if((event.damager as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("크리티컬") == true){
                            if(ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.replace("%", "")?.toInt()!! > 0){
                                var mg = Random.nextDouble()
                                if(mg <= ChatColor.stripColor((event.damager as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.replace("%", "")?.toInt()!! * 0.01){
                                    event.damage = event.damage * 2
                                    Sound().SPW(event.damager as Player, org.bukkit.Sound.ENTITY_ENDER_DRAGON_HURT, 2.0f, 1.0f)
                                    (event.damager as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4크리티컬!"))

                                }
                            }
                        }
                    }
                }
            }
            }


            if(event.entity.type == EntityType.PLAYER) {
                var stat = DataManager().getStat(event.entity.uniqueId.toString())
                var sfile = File("plugins/Rpgs/Stat/${event.entity.uniqueId}.yml")
                stat.run{
                    if(getInt("def") >= 40){
                        if (java.util.Random().nextInt(10)+1 == 1) {
                            (event.entity as Player).addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 60, 1))
                            (event.entity as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4재생§f이 부여되었습니다."))
                        }
                    }
                }
                /**var mg = nextDouble()
                if(stat[7] > 0) {
                if (mg <= (0.0005 * stat[7])) {
                //event.damage = 0.0
                event.setCancelled(true)
                (event.entity as Player).sendMessage("§e회피!")
                }
                }**/
            }
        }
        if(event.damage != null && event.damager.type != EntityType.PLAYER){
            if(event.entity.type == EntityType.PLAYER) {
                var stat = DataManager().getStat(event.entity.uniqueId.toString())
                var sfile = File("plugins/Rpgs/Stat/${event.entity.uniqueId}.yml")
                stat.run {
                    if (getInt("def") >= 40) {
                        if (java.util.Random().nextInt(10) + 1 == 1) {
                            (event.entity as Player).addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 60, 1))
                            (event.entity as Player).spigot()
                                .sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4재생§f이 부여되었습니다."))
                        }
                    }

                }
                /**var mg = nextDouble()
                if(state[7] > 0) {
                if (mg <= (0.0005 * state[7])) {
                //event.damage = 0.0
                event.setCancelled(true)
                (event.entity as Player).sendMessage("§e회피!")
                }
                }**/
            }
        }
        if(event.damage != null && event.damager is Projectile){
            if(event.damager.type == EntityType.ARROW || event.damager.type == EntityType.EGG || event.damager.type == EntityType.SNOWBALL
                || event.damager.type == EntityType.ENDER_PEARL || event.damager.type == EntityType.FISHING_HOOK || event.damager.type == EntityType.TRIDENT){
                var pr : Projectile = event.damager as Projectile
                if(pr.getShooter() is Player) {
                    var stat = DataManager().getStat((pr.shooter as Player).uniqueId.toString())
                    var sfile = File("plugins/Rpgs/Stat/${(pr.shooter as Player).uniqueId}.yml")
                    stat.run{
                        event.damage += getInt("acc") / 10
                        if(getInt("acc") >= 40){
                            if(java.util.Random().nextInt(10)+1== 1) {
                                event.damage = event.damage * 2
                                Sound().SPW(pr.shooter as Player, org.bukkit.Sound.ENTITY_ENDER_DRAGON_HURT, 2.0f, 1.0f)
                                (pr.shooter as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4명중! *2"))
                            }
                        }


                    if((pr.shooter as Player).itemInHand.type != Material.AIR){
                        if((pr.shooter as Player).itemInHand.itemMeta?.hasLore() == true){
                            for(i in 0..((pr.shooter as Player).itemInHand.itemMeta?.lore?.size?.minus(1)!!)){
                                if((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("레벨") == true){
                                    if(ChatColor.stripColor((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                        if((pr.shooter as Player).level < ChatColor.stripColor((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                            (pr.shooter as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4레벨§f이 부족합니다."))
                                            event.setCancelled(true)


                                        }
                                    }
                                }
                                if((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("요구 힘") == true){
                                    if(ChatColor.stripColor((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                        if(getInt("str") < ChatColor.stripColor((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                            (pr.shooter as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4힘 §e스텟§f이 부족합니다."))
                                            event.setCancelled(true)


                                        }
                                    }
                                }
                                if((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("요구 손재주") == true){
                                    if(ChatColor.stripColor((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                        if(getInt("def") < ChatColor.stripColor((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                            (pr.shooter as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§b손재주 §e스텟§f이 부족합니다."))
                                            event.setCancelled(true)


                                        }
                                    }
                                }
                                if((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("요구 집중") == true){
                                    if(ChatColor.stripColor((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                        if(getInt("acc") < ChatColor.stripColor((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                            (pr.shooter as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§c집중 §e스텟§f이 부족합니다."))
                                            event.setCancelled(true)


                                        }
                                    }
                                }
                                if((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i)?.contains("요구 민첩") == true){
                                    if(ChatColor.stripColor((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!! > 0){
                                        if(getInt("agi") < ChatColor.stripColor((pr.shooter as Player).itemInHand.itemMeta?.lore?.get(i))?.split(" : ")?.get(1)?.toInt()!!){
                                            (pr.shooter as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§e민첩 §e스텟§f이 부족합니다."))
                                            event.setCancelled(true)


                                        }
                                    }
                                }
                            }
                        }
                    }
                    }
                }
                if(event.entity.type == EntityType.PLAYER) {

                    var stat = DataManager().getStat(event.entity.uniqueId.toString())
                    var sfile = File("plugins/Rpgs/Stat/${event.entity.uniqueId}.yml")
                    stat.run{
                        if(getInt("def") >= 40){
                            if (java.util.Random().nextInt(10)+1 == 1) {
                                (event.entity as Player).addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 60, 1))
                                (event.entity as Player).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4재생§f이 부여되었습니다."))
                            }
                        }
                    }
                    /**var mg = nextDouble()
                    if(state[7] > 0) {
                    if (mg <= (0.0005 * state[7])) {
                    //event.damage = 0.0
                    event.setCancelled(true)
                    (event.entity as Player).sendMessage("§e회피!")
                    }
                    }**/

                }
            }
        }
    }

    @EventHandler
    fun onEntityDamage(e : EntityDamageEvent) {
        if (e.damage != null && e.entity.type == EntityType.PLAYER) {
            var p = e.entity
            var stat = DataManager().getStat(p.uniqueId.toString())
            var sfile = File("plugins/Rpgs/Stat/${p.uniqueId}.yml")
            stat.run {
                if (getInt("def") >= 40) {
                    if (java.util.Random().nextInt(10) + 1 == 1) {
                        (p as Player).addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 60, 1))
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§4재생§f이 부여되었습니다."))
                    }
                }
            }

            if (e.cause == EntityDamageEvent.DamageCause.WITHER || e.cause == EntityDamageEvent.DamageCause.POISON
                || e.cause == EntityDamageEvent.DamageCause.FIRE_TICK || e.cause == EntityDamageEvent.DamageCause.FIRE
                || e.cause == EntityDamageEvent.DamageCause.VOID || e.cause == EntityDamageEvent.DamageCause.LAVA
                || e.cause == EntityDamageEvent.DamageCause.LIGHTNING
            ) {
                e.damage = e.damage * ((p as Player).maxHealth / 20.0)
            }

            if (e.cause == EntityDamageEvent.DamageCause.FALL) {
                stat.run {
                    if (getBoolean("cancel_fall")) {
                        e.isCancelled = true
                        set("cancel_fall", false)
                        save(sfile)
                    }
                }
            }
        }
    }
}