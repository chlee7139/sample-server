package io.github.chlee7139.commands

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import kotlin.math.floor


class Timer : TabExecutor, Listener{


    var tab1 : MutableList<String>? = ArrayList<String>()


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            val p : Player = sender
            if(args[0] == "start") {
                if (p.isOp){//관리자 체크
                    if (args[1] != null && args[2] != null && args[3] != null
                            && args[4] != null && args[5] != null) { //null 체크
                        if (args[3].toInt() >= 0 && args[2].toInt() >= 0
                                && args[1].toInt() >= 0){ //값이 유효한지 체크
                            var tm: Int = args[2].toInt() * 60 //분 초로 환산
                            var th: Int = args[1].toInt() * 3600 //시 초로 환산
                            var tt: Int = tm + th + args[3].toInt() // 전부 더하기
                            val pl : Plugin = Bukkit.getPluginManager().getPlugin("Rpgs")  as Plugin
                            val bar = Bukkit.getServer().createBossBar(NamespacedKey(pl , args[5]),
                                    args[4].replace("_", " ") + "까지 남은 시간은 "
                                            + floor((tt / 3600).toDouble()).toInt()
                                            + "시간 " + ((tt / 60) % 60)+ "분 "
                                            + (tt % 60) + "초입니다.", BarColor.BLUE, BarStyle.SEGMENTED_20)
                            val uct = tt.toDouble() // 전체 시간 설정
                            bar.isVisible = true // bar 보이기
                            //서버 내에 있는 모든 플레이어에게 보이기
                            for (i in Bukkit.getServer().onlinePlayers) {
                                bar.addPlayer(i)
                            }
                            bar.progress = 1.0 // 전체로 시작하기
                            var task: BukkitTask? = null // Task 생성
                            fun update() {
                                task = pl.let {
                                    Bukkit.getScheduler().runTaskTimer(it, Runnable {
                                        if (tt != 0) {
                                            bar.progress = tt.toDouble() / uct
                                            bar.setTitle(args[4].replace("_", " ")
                                                    + "까지 남은 시간은 " + floor((tt / 3600).toDouble()).toInt()
                                                    + "시간 " + ((tt / 60) % 60) + "분 "
                                                    + (tt % 60)+ "초입니다.")
                                            tt--
                                            //bar 보인다면 추가
                                            for (i in Bukkit.getServer().onlinePlayers) {
                                                if (!bar.players.contains(i)) {
                                                    if (bar.isVisible) {
                                                        bar.addPlayer(i)
                                                    }
                                                }
                                            }
                                        } else {
                                            //초가 다 된다면 삭제
                                            bar.isVisible = false
                                            bar.removeAll()
                                            task!!.cancel()
                                        } }, 1, 20)
                                }
                            }
                            update()
                        } else {
                            p.sendMessage("시간은 0초 초과해야 합니다.")
                        }
                    } else {
                        p.sendMessage("제대로 된 시간을 입력해주세요")//null 체크의 else
                    }
                } else{
                    p.sendMessage("권한이 없습니다.")//is op의 else
                }
            }
        }
        return false
    }
    //tab 할 때 나오는 거
    override fun onTabComplete(
            sender: CommandSender,
            command: Command,
            alias: String,
            args: Array<out String>
    ): MutableList<String>? {
        if(args.size == 1){
            tab1 == null
            tab1?.add("start")
            return tab1
        }
        return null
    }
}