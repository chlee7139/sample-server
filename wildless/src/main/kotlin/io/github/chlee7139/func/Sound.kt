package io.github.chlee7139.func

import org.bukkit.Sound
import org.bukkit.entity.Player

class Sound {
    fun SPW(p : Player, sound: Sound, volume : Float, pitch : Float){
        p.world.playSound(p.location, sound, volume, pitch)
    }
    fun SPP(p : Player, sound : Sound, volume : Float, pitch : Float){
        p.playSound(p.location, sound, volume, pitch)
    }
}