package io.github.chlee7139.events

import io.github.chlee7139.func.Inventoring
import io.github.chlee7139.manager.DataManager
import io.github.chlee7139.skills.FakeEntity
import io.github.chlee7139.skills.Skills
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.io.File
import java.util.*

class SkillCast : Listener {
    @EventHandler
    fun onItemChange(e : PlayerItemHeldEvent){
        var skinv = DataManager().loadSkillbar(e.player)
        var skfile = File("plugins/Rpgs/skills/${e.player.uniqueId}.yml")
        skinv.run {
            if(getBoolean("skillmode")) {
                if (e.newSlot in 0..5) {
                    e.isCancelled = true
                    if(getInt("page") == 1) {
                        Skills().SkillCast(
                            getInt("skillslot${e.newSlot}"),
                            e.player,
                            e.player.inventory.getItem(e.newSlot)!!.type
                        )

                    }
                    if(getInt("page") == 2){
                        Skills().SkillCast(
                            getInt("skillslot${e.newSlot + 6}"),
                            e.player,
                            e.player.inventory.getItem(e.newSlot)!!.type
                        )
                    }

                }
                if(e.newSlot == 6){
                    e.isCancelled = true
                    if(getInt("page") == 2){
                        set("page", 1)
                        save(skfile)
                        load(skfile)
                        Inventoring().skillbar(e.player)
                    }
                }
                if(e.newSlot == 7){
                    e.isCancelled = true
                    if(getInt("page") == 1){
                        set("page", 2)
                        save(skfile)
                        load(skfile)
                        Inventoring().skillbar(e.player)
                    }
                }
            }
        }
    }
    @EventHandler
    fun onSneaking(e : PlayerToggleSneakEvent){
        var oslot = DataManager().loadSkillbar(e.player)
        var skfile = File("plugins/Rpgs/skills/${e.player.uniqueId}.yml")
        oslot.run {
            if (e.player.isSneaking) {

            } else {
                if(!getBoolean("skillmode")) {
                    set("oslot", e.player.inventory.heldItemSlot)
                    save(skfile)
                    load(skfile)
                    e.player.inventory.setHeldItemSlot(8)
                    Inventoring().skillbar(e.player)
                    var invitem = DataManager().loadInv(e.player)
                    e.player.setItemInHand(invitem.getItemStack("slot${getInt("oslot")}"))
                    set("skillmode", true)
                    save(skfile)
                    load(skfile)
                }
                else{
                    var invitem = DataManager().loadInv(e.player)
                    var ifile = File("plugins/Rpgs/Inv/${e.player.uniqueId}.yml")
                    invitem.set("slot${getInt("oslot")}", e.player.itemInHand)
                    invitem.save(ifile)
                    invitem.load(ifile)
                    Inventoring().loadHotbar(e.player)
                    e.player.inventory.setHeldItemSlot(getInt("oslot"))
                    set("skillmode", false)
                    save(skfile)
                    load(skfile)
                }

            }
        }
    }
    @EventHandler
    fun OnInteractEntity(event: PlayerInteractEntityEvent){
        if(event.rightClicked.type == EntityType.PLAYER) {
            if (event.player.isSneaking == true) {
                var inve: Inventory = Bukkit.createInventory(null, 18, "" + event.rightClicked.name + "의 정보")


                inve.setItem(0, (event.rightClicked as Player).inventory.helmet)
                inve.setItem(1, (event.rightClicked as Player).inventory.chestplate)
                inve.setItem(2, (event.rightClicked as Player).inventory.leggings)
                inve.setItem(3, (event.rightClicked as Player).inventory.boots)
                inve.setItem(4, (event.rightClicked as Player).inventory.itemInMainHand)

                fun createGuiItem(material: Material?, name: String?, vararg lore: String?): ItemStack? {
                    val item = ItemStack(material!!, 1)
                    val meta = item.itemMeta

                    // Set the name of the item
                    meta!!.setDisplayName(name)

                    // Set the lore of the item
                    meta.lore = Arrays.asList(*lore)
                    item.itemMeta = meta
                    return item
                }
                inve.setItem(9, createGuiItem(Material.NAME_TAG, "§eRpgs Plugin", "§fMade by chlee7139"))
                inve.setItem(17, createGuiItem(Material.EXPERIENCE_BOTTLE, "§f" + (event.rightClicked as Player).name + "님의 Exp", "§f레벨: " + (event.rightClicked as Player).level, "§f경험치 진행률: " + ((event.rightClicked as Player).exp * 100).toString() + "%"))
                event.player.openInventory(inve)
            }
        }
    }
    @EventHandler
    fun onCast(e : PlayerInteractEvent){
        val item = e.player.itemInHand.type
        when(e.action){
                Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR -> {
                    e.player.apply{
                        if(this.foodLevel< 4) this.sendMessage("마나가 부족합니다.")
                        if(item == Material.STICK && this.foodLevel >= 4) {FakeEntity().launches(this); this.foodLevel-= 4}
                    }
                }
        }
    }
}