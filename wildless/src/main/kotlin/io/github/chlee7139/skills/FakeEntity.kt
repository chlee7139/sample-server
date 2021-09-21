package io.github.chlee7139.skills

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.entity.*
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.Vector
import java.lang.Double
import kotlin.let

class FakeEntity {
    fun launchFakeProjectile(p : Player){
        //val nearby: List<Entity> = p.getNearbyEntities(9.0, 3.0, 9.0)
        //for (tmp in nearby) {
            //if (tmp is Damageable){
                //val pr = p.launchProjectile(Arrow::class.java)
                //pr.teleport(tmp)
                //pr.velocity = pr.location.direction.subtract(p.location.direction)
                //break
            //}
        //}
        val entity = p.location.world.spawnEntity(p.location, EntityType.ARMOR_STAND, CreatureSpawnEvent.SpawnReason.BUILD_WITHER)
        val t = entity.location
        entity.remove()
        var task : BukkitTask? = null
        var re = 20
        var r = Double.max(1.0, 4.0 * 8.0)
        task = Bukkit.getPluginManager().getPlugin("Rpgs")?.let {
            Bukkit.getScheduler().runTaskTimer(it, Runnable {
                if (re != 0) {
                    entity.world.spawnParticle(
                        Particle.WATER_DROP, t.x, t.y, t.z,
                        (r * r).toInt(), r / 8, r / 8, r / 8, 0.0, null, true
                    )

                    entity.world.playSound(t, org.bukkit.Sound.WEATHER_RAIN, 2.0f, 1.0f)
                    val nearby: List<Entity> = p.getNearbyEntities(9.0, 3.0, 9.0)
                    for (tmp in nearby) {
                    if (tmp is Damageable){ tmp.damage(1.0, p)
                        (tmp as LivingEntity).addPotionEffect(PotionEffect(PotionEffectType.POISON, 100, 3))}}
                    re--
                } else {
                    task!!.cancel()

                }
            }, 1, 5)
        }





    }
    fun launches(p : Player) {


                val startLoc: Location = p.eyeLocation
                val particleLoc = startLoc.clone()
                val world: World = startLoc.world
                val dir: Vector = startLoc.direction
                val vecOffset: Vector = dir.clone().multiply(0.5)
                p.world.playSound(p.location, org.bukkit.Sound.ENTITY_WITHER_SHOOT, 2.0f, 1.0f)
                for( i in 0 until 20) {
                    // Now we add the direction vector offset to the particle's current location
                    particleLoc.add(vecOffset)
                    val entity = particleLoc.world.spawnEntity(particleLoc, EntityType.ARMOR_STAND, CreatureSpawnEvent.SpawnReason.BUILD_WITHER)
                    entity.remove()
                    val nearby: List<Entity> = entity.getNearbyEntities(1.0, 1.0, 1.0)
                    for (tmp in nearby) {
                        if (tmp is Damageable){
                            if(tmp != p){tmp.damage(10.0, p) }}}
                    // Display the particle in the new location
                    world.spawnParticle(Particle.FIREWORKS_SPARK, particleLoc, 0)
                }




                }
            }



