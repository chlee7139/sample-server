package io.github.chlee7139.commands

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.event.Listener


class SetItem : TabExecutor, Listener{

    var tab1 : MutableList<String>? = ArrayList<String>()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            val p : Player = sender
            if(args[0] == "setlimit"){
                if(p.isOp == true) {
                    if (p.itemInHand.type != Material.AIR) {
                        if (args[1] != null && args[2] != null && args[3] != null) {
                            if (args[1].toInt() >= 0) {
                                if(args[3].toInt() > 0) {
                                    val meta = p.itemInHand.itemMeta
                                    var mls = mutableListOf<String>()
                                    meta!!.lore?.let { mls.addAll(it) }
                                    mls.set(args[1].toInt(), "§f* §4요구 " + args[2] + " §f: " + args[3].toInt())
                                    meta!!.setLore(mls)

                                    p.itemInHand.setItemMeta(meta)
                                } else{
                                    p.sendMessage("0보다 큰 값을 입력해주세요.")
                                }
                            }
                            else{
                                p.sendMessage("0 이상을 입력하세요.(0이 첫번째 줄)")
                            }
                        } else{
                            p.sendMessage("제대로 된 값을 입력해주세요")
                        }
                    }
                } else{
                    p.sendMessage("권한이 없습니다.")
                }
            }
            if(args[0] == "addlimit"){
                if(p.isOp == true) {
                    if (p.itemInHand.type != Material.AIR) {
                        if (args[1] != null && args[2] != null) {
                            if (args[2].toInt() > 0) {

                                val meta = p.itemInHand.itemMeta
                                var mls = mutableListOf<String>()
                                meta!!.lore?.let { mls.addAll(it) }
                                mls.add("§f* §4요구 " + args[1] + " §f: " + args[2].toInt())
                                meta!!.setLore(mls)

                                p.itemInHand.setItemMeta(meta)
                                }

                            else{
                                p.sendMessage("0 보다 큰 값을 입력하세요.")
                            }
                        } else{
                            p.sendMessage("제대로 된 값을 입력해주세요")
                        }
                    }
                } else{
                    p.sendMessage("권한이 없습니다.")
                }
            }
            if(args[0] == "setlore"){
                if(p.isOp == true) {
                    if (p.itemInHand.type != Material.AIR) {
                        if (args[1] != null && args[2] != null) {
                            if (args[1].toInt() >= 0) {
                                val meta = p.itemInHand.itemMeta
                                var mls = mutableListOf<String>()
                                meta!!.lore?.let { mls.addAll(it) }
                                mls.set(args[1].toInt(), args[2].replace("_"," ").replace("&", "§"))
                                meta!!.setLore(mls)

                                p.itemInHand.setItemMeta(meta)

                            }
                            else{
                                p.sendMessage("0 이상을 입력하세요.(0이 첫번째 줄)")
                            }
                        } else{
                            p.sendMessage("제대로 된 값을 입력해주세요")
                        }
                    }
                } else{
                    p.sendMessage("권한이 없습니다.")
                }
            }
            if(args[0] == "addlore"){
                if(p.isOp == true) {
                    if (p.itemInHand.type != Material.AIR) {
                        if (args[1] != null) {
                            val meta = p.itemInHand.itemMeta
                            var mls = mutableListOf<String>()
                            meta!!.lore?.let { mls.addAll(it) }
                            mls.add(args[1].replace("_", " ").replace("&","§"))
                            meta!!.setLore(mls)

                            p.itemInHand.setItemMeta(meta)

                        } else{
                            p.sendMessage("제대로 된 값을 입력해주세요")
                        }
                    }
                } else{
                    p.sendMessage("권한이 없습니다.")
                }
            }
            if(args[0] == "setname"){
                if(p.isOp == true) {
                    if (p.itemInHand.type != Material.AIR) {
                        if (args[1] != null) {

                                    val meta = p.itemInHand.itemMeta



                                    meta!!.setDisplayName(args[1].replace("&","§"))
                                    p.itemInHand.setItemMeta(meta)
                                }

                        else{
                            p.sendMessage("제대로 된 값을 입력해주세요")
                        }
                    }
                } else{
                    p.sendMessage("권한이 없습니다.")
                }
            }
        }
        return false

    }

    override fun onTabComplete(
            sender: CommandSender,
            command: Command,
            alias: String,
            args: Array<out String>
    ): MutableList<String>? {
        if(args.size == 1){
            tab1 == null
            tab1?.add("setlimit")
            tab1?.add("addlimit")
            tab1?.add("setlore")
            tab1?.add("addlore")
            tab1?.add("setname")
            return tab1
        }
        return null
    }

}