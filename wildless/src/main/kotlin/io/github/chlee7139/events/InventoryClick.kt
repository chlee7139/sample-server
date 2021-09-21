package io.github.chlee7139.events

import io.github.chlee7139.func.Inventoring
import io.github.chlee7139.func.Sound
import io.github.chlee7139.manager.DataManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.io.File

class InventoryClick : Listener {
    @EventHandler
    fun onInventoryClick(e : InventoryClickEvent){
        var p : HumanEntity = e.whoClicked
        var meta = e.currentItem?.itemMeta
        if(e.action == InventoryAction.HOTBAR_SWAP){
            if(e.hotbarButton < 8){
                var oslot = DataManager().loadSkillbar(p as Player)
                var skfile = File("plugins/Rpgs/skills/${p.uniqueId}.yml")
                oslot.run{
                    if(getBoolean("skillmode")){
                        e.isCancelled = true
                    }
                }

            }
        }
        if(e.clickedInventory?.getItem(9)?.itemMeta?.displayName == "§eRpgs Plugin"){
            e.isCancelled = true
        }
        if(meta?.lore?.contains("§f-----------") == true){
                e.isCancelled = true
                when(meta.displayName){
                    "§e§l인벤토리" -> {
                        Inventoring().applyInv(p as Player) //인벤토리 염
                        Sound().SPW(p, org.bukkit.Sound.BLOCK_CHEST_OPEN,
                            2.0f, 1.0f)
                    }
                    "§e§l스텟" -> {
                        Inventoring().Statory(p.inventory, p as Player) //스텟 창 염
                        Sound().SPW(p, org.bukkit.Sound.BLOCK_ENDER_CHEST_OPEN,
                            2.0f, 1.0f)
                    }
                    "§8§l스킬" -> {
                        if((e.cursor != null) && (e.cursor!!.type != Material.AIR)){
                            p.world.dropItem(p.location, e.cursor!!)
                            e.setCursor(ItemStack(Material.AIR))
                        } else {
                            e.setCursor(ItemStack(Material.AIR))
                        }
                        Sound().SPP(p as Player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        var inv = Bukkit.createInventory(null, 54, "${p.name}님의 스킬 창")
                        Inventoring().skillselect(inv)
                        p.openInventory(inv)
                    }
                    "§c돌진" -> {
                        Sound().SPP(p as Player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        Inventoring().setselection(p as Player, e.currentItem!!, e.inventory)
                    }
                    "§4강타" -> {
                        Sound().SPP(p as Player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        Inventoring().setselection(p as Player, e.currentItem!!, e.inventory)

                    }
                    "§8내려찍기" -> {
                        Sound().SPP(p as Player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        Inventoring().setselection(p as Player, e.currentItem!!, e.inventory)

                    }
                    "§b어퍼컷" -> {
                        Sound().SPP(p as Player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        Inventoring().setselection(p as Player, e.currentItem!!, e.inventory)

                    }
                    "§e침묵" -> {
                        Sound().SPP(p as Player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        Inventoring().setselection(p as Player, e.currentItem!!, e.inventory)

                    }
                    "§3백스텝" -> {
                        Sound().SPP(p as Player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        Inventoring().setselection(p as Player, e.currentItem!!, e.inventory)

                    }
                    "§4스킬 제거" -> {
                        Sound().SPP(p as Player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        Inventoring().setselectionremove(p as Player, e.currentItem!!, e.inventory)
                    }

                }
                var oslot = DataManager().loadSkillbar(p as Player)
                var skfile = File("plugins/Rpgs/skills/${(p as Player).uniqueId}.yml")
                when(meta.displayName.split("_")[0]){
                    "§e스킬바설정" -> {
                        Sound().SPP(p as Player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        oslot.run{
                            set("skillslot${meta.displayName.split("_")[1].toInt() - 1}", Inventoring().getSkillCode(e.inventory.getItem(13)!!))
                            save(skfile)
                            load(skfile)
                            var inv = Bukkit.createInventory(null, 54, "${p.name}님의 스킬 창")
                            Inventoring().skillselect(inv)
                            p.openInventory(inv)
                        }
                    }

                    "§4스킬바삭제" -> {
                        Sound().SPP(p as Player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        oslot.run {
                            set("skillslot${meta.displayName.split("_")[1].toInt() - 1}", 0)
                            save(skfile)
                            load(skfile)
                            var inv = Bukkit.createInventory(null, 54, "${p.name}님의 스킬 창")
                            Inventoring().skillselect(inv)
                            p.openInventory(inv)
                        }
                    }
                }
                var stat = DataManager().getStat((p as Player).uniqueId.toString())
                var sfile = File("plugins/Rpgs/Stat/${p.uniqueId}.yml")

                when(e.currentItem?.itemMeta!!.lore?.get(0)){
                    "§8§oStrength" -> {
                        Sound().SPP(p, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        stat.run{
                            DataManager().statUp(p, "str")
                            load(sfile)
                            Inventoring().Statory(p.inventory, p)
                        }
                        p.updateInventory()
                    }
                    "§8§oAccuracy" -> {
                        Sound().SPP(p, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        stat.run{
                            DataManager().statUp(p, "acc")
                            load(sfile)
                            Inventoring().Statory(p.inventory, p)
                        }
                        p.updateInventory()
                    }
                    "§8§oDefensive" -> {
                        Sound().SPP(p, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        stat.run{
                            if(getInt("sp") > 0) {
                                p.setMaxHealth((p.maxHealth - (getInt("def")).toDouble()))
                                DataManager().statUp(p, "def")
                                load(sfile)
                                Inventoring().Statory(p.inventory, p)
                                p.setMaxHealth((p.maxHealth + (getInt("def")).toDouble()))
                            } else{
                                p.sendMessage("스텟 포인트가 부족합니다.")
                            }
                        }

                        p.setHealthScale(20.0)
                        p.updateInventory()
                }
                    "§8§oAgility" -> {
                        Sound().SPP(p, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F)
                        stat.run{
                            p.setWalkSpeed(p.walkSpeed - (0.001f *  getInt("agi")))
                            DataManager().statUp(p, "agi")
                            load(sfile)
                            Inventoring().Statory(p.inventory, p)
                            p.setWalkSpeed(p.walkSpeed + (0.001f *  getInt("agi")))
                        }
                        p.updateInventory()
                    }
                }
            }
        }
    }