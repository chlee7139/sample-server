package io.github.chlee7139

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import io.github.chlee7139.commands.SetItem
import io.github.chlee7139.commands.Timer
import io.github.chlee7139.events.*

import io.github.chlee7139.func.Inventoring
import io.github.chlee7139.func.Sound
import io.github.chlee7139.manager.DataManager
import io.github.chlee7139.manager.InventoryManager
import io.github.chlee7139.skills.FakeEntity
import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import net.kyori.adventure.text.Component.text
import org.bukkit.*
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Damageable
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import java.io.File
import java.util.*
import javax.xml.crypto.Data
import kotlin.math.floor

class Main : JavaPlugin(), Listener {
    public lateinit var protocolManager: ProtocolManager

    //활성화
    fun onUpdate(){
        Bukkit.getOnlinePlayers().forEach {
            if (it.inventory.getItem(17)?.itemMeta?.displayName == "§e§l스텟") {
                var skinv = DataManager().loadSkillbar(it)
                var skfile = File("plugins/Rpgs/skills/${it.uniqueId}.yml")
                skinv.run {
                    if(getBoolean("skillmode")) {
                        DataManager().saveInvWithOutHotbar(it)
                    }
                    else{
                        DataManager().saveInv(it)
                    }
                }

            }
            else{
                var skinv = DataManager().loadSkillbar(it)
                var skfile = File("plugins/Rpgs/skills/${it.uniqueId}.yml")
                skinv.run {
                    if(!getBoolean("skillmode")) {
                        DataManager().saveHotbar(it)
                    }
                }
            }
        }
    }
    override fun onEnable() {

       server.consoleSender.sendMessage("${ChatColor.GREEN}Rpgs 플러그인이 활성화 됐습니다.")
        server.apply{
            pluginManager.apply{
                registerEvents(JoinQuitEvent(), this@Main)
                registerEvents(Combat(), this@Main)
                registerEvents(InventoryClick(), this@Main)
                registerEvents(PlayerDeath(), this@Main)
                registerEvents(HealthManage(), this@Main)
                registerEvents(PlayerExp(), this@Main)
                registerEvents(this@Main, this@Main)
                registerEvents(SkillCast(), this@Main)
                getCommand("itemset")?.setExecutor(SetItem())
                getCommand("timer")?.setExecutor(Timer())


            }
        }



        //닫았을때 무조건 인벤창으로 바꿈
        protocolManager = ProtocolLibrary.getProtocolManager()
        protocolManager.addPacketListener(object : PacketAdapter(
            this,
            ListenerPriority.NORMAL,
            PacketType.Play.Client.CLOSE_WINDOW
        ) {
            override fun onPacketReceiving(event: PacketEvent) {
                if (event.packetType === PacketType.Play.Client.CLOSE_WINDOW) {
                    var p = event.player
                    if(p.inventory.getItem(17)?.itemMeta?.
                        displayName == "§e§l인벤토리") {
                        Inventoring().applyInv(p)
                    }
                }
            }
        })




        kommand{
            register("test"){
                executes {
                    (sender as Player).apply{
                        walkSpeed = 0.2f
                    }
                }
            }
            register("test2"){
                executes {
                    (sender as Player).walkSpeed = 0.2f

                }
            }
            register("shoot") {
                executes {
                    FakeEntity().launchFakeProjectile(sender as Player)
                }
            }
            register("launch"){
                executes {
                    FakeEntity().launches(sender as Player)
                }
            }

            register("spawn"){
                executes {
                    (sender as Player).teleport((sender as Player).world.spawnLocation)
                }
            }
            register("rpg"){
                requires { playerOrNull != null && player.isOp }
                then("set"){
                    then("type" to string()) {
                        then("value" to int()) {
                            executes { context ->
                                val type: String by context
                                val value : Int by context
                                var stat = DataManager().getStat((sender as Player).uniqueId.toString())
                                var sfile = File("plugins/Rpgs/Stat/${(sender as Player).uniqueId}.yml")
                                stat.run{
                                    var p = sender as Player
                                    p.setWalkSpeed(p.walkSpeed - (0.001f * getInt("agi")))
                                    p.setMaxHealth((p.maxHealth - (getInt("def")).toDouble()))
                                    set(type, value)
                                    save(sfile)
                                    sender.sendMessage(text("$type = $value"))
                                    load(sfile)
                                    p.setWalkSpeed(p.walkSpeed + (0.001f * getInt("agi")))
                                    p.setMaxHealth((p.maxHealth + (getInt("def")).toDouble()))
                                }
                            }
                        }
                    }
                }
                then("add"){
                    then("type" to string()) {
                        then("value" to int()) {
                            executes { context ->
                                val type: String by context
                                val value : Int by context
                                var stat = DataManager().getStat((sender as Player).uniqueId.toString())
                                var sfile = File("plugins/Rpgs/Stat/${(sender as Player).uniqueId}.yml")
                                stat.run{
                                    var p = sender as Player
                                    p.setWalkSpeed(p.walkSpeed - (0.001f * getInt("agi")))
                                    p.setMaxHealth((p.maxHealth - (getInt("def")).toDouble()))
                                    set(type, getInt(type) + value)
                                    save(sfile)
                                    sender.sendMessage(text("$type += $value"))
                                    load(sfile)
                                    p.setWalkSpeed(p.walkSpeed + (0.001f * getInt("agi")))
                                    p.setMaxHealth((p.maxHealth + (getInt("def")).toDouble()))
                                }
                            }
                        }
                    }
                }
                then("setb"){
                    then("type" to string()) {
                        then("value" to bool()) {
                            executes { context ->
                                val type: String by context
                                val value : Boolean by context
                                var stat = DataManager().getStat((sender as Player).uniqueId.toString())
                                var sfile = File("plugins/Rpgs/Stat/${(sender as Player).uniqueId}.yml")
                                stat.run{
                                    set(type, value)
                                    save(sfile)
                                    sender.sendMessage(text("$type = $value"))
                                    load(sfile)
                                }
                            }
                        }
                    }
                }
            }

            register("statreset"){
                executes {
                    var p = sender as Player
                    p.level = 0
                    p.exp = 0f
                    var stat = DataManager().getStat((sender as Player).uniqueId.toString())
                    var sfile = File("plugins/Rpgs/Stat/${(sender as Player).uniqueId}.yml")
                    stat.run{
                        p.setWalkSpeed(p.walkSpeed - (0.001f *  getInt("agi")))
                        p.setMaxHealth((p.maxHealth - (getInt("def")).toDouble()))
                        set("sp", 0)
                        set("str", 0)
                        set("acc", 0)
                        set("def", 0)
                        set("agi", 0)
                        set("silent", false)
                        set("cancel_fall", false)
                        p.healthScale = 20.0
                        save(sfile)
                        load(sfile)
                    }
                }
            }

            register("reset"){
                executes {
                    var p = sender as Player
                    p.walkSpeed = 0.2f
                    p.maxHealth =  20.0
                }
            }
            register("cooldown"){
                executes {
                    var p = sender as Player
                    for(i in 0 until 9) p.setCooldown(p.inventory.getItem(i)!!.type, 0)
                }
            }
            register("money"){
                then("check"){
                    executes {
                        var stat = DataManager().getStat((sender as Player).uniqueId.toString())
                        stat.run{
                            sender.sendMessage(text("당신의 잔액은 ${getInt("money")}원 입니다."))
                        }
                    }
                }

                then("see"){
                    requires { playerOrNull != null && player.isOp }
                    then("p" to player()){
                        executes { context ->
                            val p : Player by context
                            var stat = DataManager().getStat(p.uniqueId.toString())
                            stat.run{
                                sender.sendMessage(text("${p.name}님의 잔액은 ${getInt("money")}원 입니다."))
                            }
                        }

                    }
                }
                then("out"){
                    then("value" to int()){
                        executes { context ->
                            var p = sender as Player
                            val value : Int by context
                            var stat = DataManager().getStat(p.uniqueId.toString())
                            var sfile = File("plugins/Rpgs/Stat/${(sender as Player).uniqueId}.yml")
                            stat.run{
                                if(getInt("money") >= value) {

                                    if(p.inventory.firstEmpty() != -1) {
                                        val supyo: ItemStack = ItemStack(Material.PAPER, 1)
                                        val meta = supyo.itemMeta

                                        meta!!.setDisplayName("§f수표§c " + value.toString() + "§f원")

                                        meta.lore = Arrays.asList("수표", value.toString())
                                        supyo.itemMeta = meta
                                        p.inventory.addItem(supyo)
                                        set("money", getInt("money") - value)
                                        save(sfile)
                                        load(sfile)
                                        p.sendMessage("수표를 발행했습니다.")
                                    }
                                    else{

                                        val supyo: ItemStack = ItemStack(Material.PAPER, 1)
                                        val meta = supyo.itemMeta

                                        meta!!.setDisplayName("§f수표§c " + value.toString() + "§f원")

                                        meta.lore = Arrays.asList("수표", value.toString())
                                        supyo.itemMeta = meta
                                        if(p.inventory.containsAtLeast(supyo, 1)){
                                            for(i in 9 until 36){
                                                if(p.inventory.getItem(i)?.itemMeta == supyo.itemMeta && p.inventory.getItem(i)!!.amount < 64){
                                                    p.inventory.addItem(supyo)
                                                    set("money", getInt("money") - value)
                                                    save(sfile)
                                                    load(sfile)
                                                    p.sendMessage("수표를 발행했습니다.")
                                                    break
                                                }
                                            }
                                        }
                                        else {
                                            p.sendMessage("인벤토리가 가득찼습니다.")
                                        }
                                    }
                                }
                                else{
                                    (sender as Player).sendMessage(text("돈이 부족합니다."))
                                }
                            }
                        }
                    }
                }
                then("set"){
                    requires { playerOrNull != null && player.isOp }
                    then("p" to player()){
                        then("value" to int()){
                            executes { context ->
                                val p : Player by context
                                val value : Int by context
                                var stat = DataManager().getStat(p.uniqueId.toString())
                                var sfile = File("plugins/Rpgs/Stat/${(sender as Player).uniqueId}.yml")
                                stat.run{
                                    set("money", value)
                                    save(sfile)
                                    sender.sendMessage(text("money = $value"))
                                    load(sfile)
                                }
                            }
                        }
                    }
                }
                then("add"){
                    requires { playerOrNull != null && player.isOp }
                    then("p" to player()){
                        then("value" to int()){
                            executes { context ->
                                val p : Player by context
                                val value : Int by context
                                var stat = DataManager().getStat(p.uniqueId.toString())
                                var sfile = File("plugins/Rpgs/Stat/${(sender as Player).uniqueId}.yml")
                                stat.run{
                                    set("money", getInt("money") + value)
                                    save(sfile)
                                    sender.sendMessage(text("money += $value"))
                                    load(sfile)
                                }
                            }
                        }
                    }
                }
            }
        }
        var task : BukkitTask? = null
        task = Bukkit.getPluginManager().getPlugin("Rpgs")?.let {
            Bukkit.getScheduler().runTaskTimer(it, Runnable {
                onUpdate()

            }, 0, 1)

    }
    }
    @EventHandler
    fun OnRightClick(event: PlayerInteractEvent) {

        val e = event.hand
        if (e == EquipmentSlot.HAND) {

            if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
                var ih = event.player.itemInHand
                var p = event.player
                if (ih.type == Material.PAPER) {
                    if (ih.itemMeta?.lore != null) {
                        if (ih.itemMeta?.lore?.size == 2) {
                            if (ih.itemMeta?.lore?.get(0) == "수표") {
                                var stat = DataManager().getStat(p.uniqueId.toString())
                                var sfile = File("plugins/Rpgs/Stat/${p.uniqueId}.yml")
                                stat.run {
                                    set("money", getInt("money") + ih.itemMeta?.lore?.get(1)!!.toInt().toLong())
                                    save(sfile)
                                    load(sfile)
                                    event.player.sendMessage("§f수표를 §c사용§f하여 " + ih.itemMeta?.lore?.get(1) + "원을 §e획득§f했습니다.")
                                    Sound().SPW(
                                        event.player,
                                        org.bukkit.Sound.ENTITY_ITEM_PICKUP,
                                        2.0F,
                                        1.0F
                                    )
                                    ih.setAmount(ih.amount - 1)
                                }

                            }
                        }
                    }
                }

            }
        }
    }
    //비활성화
    override fun onDisable() {
        server.consoleSender.sendMessage("${ChatColor.RED}Rpgs 플러그인이 비활성화 됐습니다.")
    }


}