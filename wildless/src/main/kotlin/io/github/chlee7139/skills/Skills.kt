package io.github.chlee7139.skills

import io.github.chlee7139.func.Sound
import io.github.chlee7139.manager.DataManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask
import java.io.File
import java.lang.Double
import kotlin.Int
import kotlin.let
import kotlin.run


class Skills {


    fun SkillCast(num : Int , p : Player, item : Material){
        var stat = DataManager().getStat(p.uniqueId.toString())
        var sfile = File("plugins/Rpgs/Stat/${p.uniqueId}.yml")
        stat.run{
        if(!getBoolean("silent")){
        when(num){
            1 -> {
                if(p.getCooldown(item) < 1){
                    p.setVelocity(p.location.direction.multiply(4).setY(0))

                    p.location.world?.playSound(p.location, org.bukkit.Sound.ENTITY_ENDER_DRAGON_FLAP, 2.0F, 1.0F)
                    var r = Double.max(1.0, 8.0 * 2.0)
                    val nearby: List<Entity> = p.getNearbyEntities(9.0, 3.0, 9.0)
                    for (tmp in nearby) if (tmp is Damageable) tmp.damage(5.0, p)
                    p.location.world?.spawnParticle(Particle.SMOKE_LARGE, p.location.x, p.location.y, p.location.z, (r * r).toInt(), r / 8, r / 8, r / 8, 0.0, null, true)
                    p.setCooldown(Material.CYAN_DYE, 100)
            }
                else{
            p.sendMessage("§f본 스킬의 쿨타임은 " + p.getCooldown(item) / 20 + "초 남았습니다.")
        }
            }
            2 -> {
                if(p.getCooldown(item) < 1) {

                    var r = Double.max(1.0, 4.0 * 2.0)
                    var rp = 5
                    var task: BukkitTask? = null
                    task = Bukkit.getPluginManager().getPlugin("Rpgs")?.let {
                        Bukkit.getScheduler().runTaskTimer(it, Runnable {
                            if (rp != 0) {
                                val nearby: List<Entity> = p.getNearbyEntities(9.0, 3.0, 9.0)
                                for (tmp in nearby) if (tmp is Damageable) {
                                    tmp.damage(5.0, p)
                                    p.location.world?.playSound(p.location, org.bukkit.Sound.ENTITY_WITHER_SHOOT, 2.0F, 1.0F)
                                    p.location.world?.spawnParticle(Particle.SWEEP_ATTACK, tmp.location.x, tmp.location.y, tmp.location.z, (r * r).toInt(), r / 8, r / 8, r / 8, 0.0, null, true)
                                    p.location.world?.spawnParticle(Particle.CRIT, tmp.location.x, tmp.location.y, tmp.location.z, (r * r).toInt(), r / 8, r / 8, r / 8, 0.0, null, true)
                                    p.location.world?.spawnParticle(Particle.CRIT_MAGIC, tmp.location.x, tmp.location.y, tmp.location.z, (r * r).toInt(), r / 8, r / 8, r / 8, 0.0, null, true)
                                    break;
                                }

                                rp--
                            } else {
                                p.setCooldown(Material.ORANGE_DYE, 600)
                                task!!.cancel()
                            }

                        }, 1, 5)
                    }
                } else{
                    p.sendMessage("§f본 스킬의 쿨타임은 " + p.getCooldown(item) / 20 + "초 남았습니다.")
                }
            }
            3-> {
                if(p.getCooldown(item) < 1) {
                    var r = Double.max(1.0, 8.0 * 2.0)
                    set("cancel_fall", true)
                    save(sfile)
                    load(sfile)
                    p.location.world?.playSound(p.location, org.bukkit.Sound.ENTITY_ENDER_PEARL_THROW, 4.0f, 1.0f)
                    p.location.world?.spawnParticle(Particle.SMOKE_NORMAL, p.location.x, p.location.y, p.location.z, 10, 0.0, -1.0, 0.0, 0.0, null, true)
                    p.setVelocity(p.location.direction.setY(2))
                    p.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 5, 1))
                    p.setCooldown(Material.MAGENTA_DYE, 240)
                    var task1: BukkitTask? = null
                    task1 = Bukkit.getPluginManager().getPlugin("Rpgs")?.let {
                        Bukkit.getScheduler().runTaskTimer(it, Runnable {
                            if(p.isOnGround) {
                                p.location.world?.spawnParticle(Particle.EXPLOSION_NORMAL, p.location.x, p.location.y, p.location.z, (r * r).toInt(), r / 8, r / 8, r / 8, 0.0, null, true)
                                p.location.world?.spawnParticle(Particle.SMOKE_NORMAL, p.location.x, p.location.y, p.location.z, (r * r).toInt(), r / 8, r / 8, r / 8, 0.0, null, true)
                                Sound().SPW(p, org.bukkit.Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 4.0f, 1.0f)
                                val nearby: List<Entity> = p.getNearbyEntities(9.0, 3.0, 9.0)
                                for (tmp in nearby) if (tmp is Damageable) {
                                    val nearby: List<Entity> = p.getNearbyEntities(9.0, 3.0, 9.0)
                                    Sound().SPW(p, org.bukkit.Sound.ENTITY_WITHER_AMBIENT, 4.0f, 1.0f)
                                    p.location.world?.spawnParticle(Particle.FLAME, tmp.location.x, tmp.location.y, tmp.location.z, (r * r).toInt(), r / 8, r / 8, r / 8, 0.0, null, true)

                                    for (tmp in nearby) if (tmp is Damageable) {
                                        tmp.damage(15.0, p)
                                    }
                                }

                                task1!!.cancel()
                            }

                        }, 20, 1)
                    }


                }else{
                    p.sendMessage("§f본 스킬의 쿨타임은 " + p.getCooldown(item) / 20 + "초 남았습니다.")
                }
            }
            4 ->{
                if(p.getCooldown(item) < 1) {
                    var r = Double.max(1.0, 8.0 * 2.0)

                    val nearby: List<Entity> = p.getNearbyEntities(9.0, 3.0, 9.0)
                    p.setCooldown(Material.GREEN_DYE, 200)
                    for (tmp in nearby) if (tmp is Damageable) {
                        val nearby: List<Entity> = p.getNearbyEntities(9.0, 3.0, 9.0)

                        for (tmp in nearby) if (tmp is Damageable) {
                            p.location.world?.playSound(tmp.location, org.bukkit.Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 2.0f, 1.0f)
                            p.location.world?.spawnParticle(Particle.ELECTRIC_SPARK, tmp.location.x, tmp.location.y, tmp.location.z, (r * r).toInt(), r / 8, r / 8, r / 8, 0.0, null, true)
                            (tmp as LivingEntity).addPotionEffect(PotionEffect(PotionEffectType.POISON, 100, 1))
                            tmp.damage(12.0, p)
                            tmp.setVelocity(tmp.location.direction.setY(1))

                            break
                        }
                    }

                }
                else{
                    p.sendMessage("§f본 스킬의 쿨타임은 " + p.getCooldown(item) / 20 + "초 남았습니다.")
                }
            }
            5 -> {
                if(p.getCooldown(item) < 1) {
                    var r = Double.max(1.0, 4.0 * 2.0)
                    val nearby: List<Entity> = p.getNearbyEntities(9.0, 3.0, 9.0)
                    for (tmp in nearby) if (tmp is Damageable) {
                        val nearby: List<Entity> = p.getNearbyEntities(9.0, 3.0, 9.0)


                        for (tmp in nearby) if (tmp is Damageable) {
                            if (tmp.type == EntityType.PLAYER) {
                                var tstat = DataManager().getStat(tmp.uniqueId.toString())
                                var tsfile = File("plugins/Rpgs/Stat/${tmp.uniqueId}.yml")
                                tstat.run{
                                    set("silent", true)
                                    save(tsfile)
                                    load(tsfile)
                                }

                            p.location.world?.playSound(tmp.location, org.bukkit.Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 2.0f, 1.0f)

                            p.location.world?.spawnParticle(Particle.CRIT, tmp.location.x, tmp.location.y, tmp.location.z, (r * r).toInt(), r / 8, r / 8, r / 8, 0.0, null, true)
                            p.setCooldown(Material.GRAY_DYE, 300)
                            Bukkit.getPluginManager().getPlugin("Rpgs")?.let {
                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(it, Runnable {
                                    var tstat = DataManager().getStat(tmp.uniqueId.toString())
                                    var tsfile = File("plugins/Rpgs/Stat/${tmp.uniqueId}.yml")
                                    tstat.run{
                                        set("silent", false)
                                        save(tsfile)
                                        load(tsfile)
                                    }



                                }, 100L)
                            }
                        } else{
                            p.sendMessage("근처에 사람이 없습니다.")
                        }
                        }
                    }


                }
                else{
                    p.sendMessage("§f본 스킬의 쿨타임은 " + p.getCooldown(item) / 20 + "초 남았습니다.")
                }
            }
            6 -> {
                if(p.getCooldown(item) < 1) {
                    p.setVelocity(p.location.direction.multiply(-4).setY(0))

                    p.location.world?.playSound(p.location, org.bukkit.Sound.ENTITY_ENDERMAN_TELEPORT, 2.0F, 1.0F)
                    var r = Double.max(1.0, 8.0 * 2.0)
                    p.location.world?.spawnParticle(Particle.CLOUD, p.location.x, p.location.y, p.location.z, (r * r).toInt(), r / 8, r / 8, r / 8, 0.0, null, true)
                    p.setCooldown(Material.LIGHT_GRAY_DYE, 100)
                }
                else{
                    p.sendMessage("§f본 스킬의 쿨타임은 " + p.getCooldown(item) / 20 + "초 남았습니다.")
                }
            }
            }
        } else{
            p.sendMessage("§f당신은 §b침묵§f 상태여서 §e스킬§f을 사용하실 수 없습니다.")
        }
        }


    }

}
