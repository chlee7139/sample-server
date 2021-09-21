package io.github.chlee7139.manager

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class DataManager {

    //config 로드
    fun loadConfig(file : File) : FileConfiguration {
        var config : FileConfiguration = YamlConfiguration.loadConfiguration(file)
        try {
            config.run {
                if (!file.exists()) {
                    set("joinmessage", "player님이 서버에 참여하셨습니다?")
                    set("quitmessage", "player님이 서버에 나가셨습니다?")
                    set("custom_exp", 50)
                    save(file)
                }
                load(file)
            }
            return config
        } catch (localException: Exception) {
            localException.printStackTrace()
            return config
        }
    }

    //stat 생성
    fun createStat(p : String){
        var sfile = File("plugins/Rpgs/Stat/$p.yml")
        var stat : FileConfiguration = YamlConfiguration.loadConfiguration(sfile)
        try{
            stat.run{
                if(!sfile.exists()){
                    set("sp", 0)
                    set("money", 0)
                    set("str", 0)
                    set("acc", 0)
                    set("def", 0)
                    set("agi", 0)
                    set("silent", false)
                    set("cancel_fall", false)
                    save(sfile)
                }
            }
        } catch(localException : Exception){
            localException.printStackTrace()
        }
    }
    //스텟 값 구하기
    fun getStat(p : String) : FileConfiguration{
        var sfile = File("plugins/Rpgs/Stat/$p.yml")
        var stat : FileConfiguration = YamlConfiguration.loadConfiguration(sfile)
        try{
            stat.run{
                if(!sfile.exists()){
                    set("sp", 0)
                    set("money", 0)
                    set("str", 0)
                    set("acc", 0)
                    set("def", 0)
                    set("agi", 0)
                    set("silent", false)
                    set("cancel_fall", false)
                    save(sfile)
                }
                load(sfile)
            }
            return stat
        } catch(localException : Exception){
            localException.printStackTrace()
            return stat
        }
    }
    //해당 타입의 스텟 값 올리기
    fun statUp(p : Player, type : String){
        var sfile = File("plugins/Rpgs/Stat/${p.uniqueId}.yml")
        var stat : FileConfiguration = YamlConfiguration.loadConfiguration(sfile)
        try{
            if(!sfile.exists()){
                stat.run{
                    set("sp", 0)
                    set("money", 0)
                    set("str", 0)
                    set("acc", 0)
                    set("def", 0)
                    set("agi", 0)
                    set("silent", false)
                    set("cancel_fall", false)
                    save(sfile)
                }
            }
            stat.run{
                if(getInt("sp") > 0) {
                    set(type, getInt(type) + 1)
                    set("sp", getInt("sp") - 1)
                    save(sfile)
                }
                else{
                    p.sendMessage("스텟 포인트가 부족합니다.")
                }
                load(sfile)
            }
        } catch(localException : Exception){
            localException.printStackTrace()
        }
    }
    //인벤토리 로드
    fun loadInv(p : Player) : FileConfiguration{
        var ifile = File("plugins/Rpgs/Inv/${p.uniqueId}.yml")
        var inv : FileConfiguration = YamlConfiguration.loadConfiguration(ifile)
        try{
            inv.run{
                if(!ifile.exists()){
                    for(i in 0..35){
                        set("slot${i}", p.inventory.getItem(i))
                    }
                    save(ifile)
                }
                load(ifile)
            }
            return inv
        } catch(localException : Exception){
            localException.printStackTrace()
            return inv
        }
    }
    //인벤토리 저장
    fun saveInv(p : Player){
        var ifile = File("plugins/Rpgs/Inv/${p.uniqueId}.yml")
        var inv : FileConfiguration = YamlConfiguration.loadConfiguration(ifile)
        try{
            inv.run{
                for(i in 0..35){
                    set("slot${i}", p.inventory.getItem(i))
                }
                save(ifile); load(ifile)
            }
        } catch(localException : Exception){
            localException.printStackTrace()
        }
    }
    fun saveInvWithOutHotbar(p : Player){
        var ifile = File("plugins/Rpgs/Inv/${p.uniqueId}.yml")
        var inv : FileConfiguration = YamlConfiguration.loadConfiguration(ifile)
        try{
            inv.run{
                for(i in 9..35){
                    set("slot${i}", p.inventory.getItem(i))
                }
                save(ifile); load(ifile)
            }
        } catch(localException : Exception){
            localException.printStackTrace()
        }
    }
    //핫바 저장
    fun saveHotbar(p : Player){
        var ifile = File("plugins/Rpgs/Inv/${p.uniqueId}.yml")
        var inv : FileConfiguration = YamlConfiguration.loadConfiguration(ifile)
        try{
            inv.run{
                for(i in 0..8){
                    set("slot${i}", p.inventory.getItem(i))
                }
                save(ifile); load(ifile)
            }
        } catch(localException : Exception){
            localException.printStackTrace()
        }
    }
    //핫바 저장
    fun saveMainhand(p : Player){
        var ifile = File("plugins/Rpgs/Inv/${p.uniqueId}.yml")
        var inv : FileConfiguration = YamlConfiguration.loadConfiguration(ifile)
        try{
            inv.run{
                set("slot8", p.inventory.getItem(8))

                save(ifile); load(ifile)
            }
        } catch(localException : Exception){
            localException.printStackTrace()
        }
    }
    fun saveSkillbar(p : Player){
        var skfile = File("plugins/Rpgs/skills/${p.uniqueId}.yml")
        var sbar : FileConfiguration = YamlConfiguration.loadConfiguration(skfile)
        try{
            sbar.run {
                if (!skfile.exists()) {
                    for (i in 0 until 12) {
                        set("skillslot${i}", 0)
                    }
                    set("oslot", 0)
                    set("skillmode", false)
                    set("page", 1)
                    save(skfile)
                }
                load(skfile)
            }
        }
        catch(localException : Exception){
            localException.printStackTrace()
        }
    }
    fun loadSkillbar(p : Player) : FileConfiguration{
        var skfile = File("plugins/Rpgs/skills/${p.uniqueId}.yml")
        var sbar : FileConfiguration = YamlConfiguration.loadConfiguration(skfile)
        try{
            sbar.run {
                if (!skfile.exists()) {
                    for (i in 0 until 12) {
                        set("skillslot${i}", 0)
                    }
                    set("oslot", 0)
                    set("skillmode", false)
                    set("page", 1)
                    save(skfile)
                }
                load(skfile)
            }
            return sbar
        }
        catch(localException : Exception){
            localException.printStackTrace()
            return sbar
        }
    }
}