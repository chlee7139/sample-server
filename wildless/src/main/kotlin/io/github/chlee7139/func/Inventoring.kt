package io.github.chlee7139.func

import io.github.chlee7139.manager.DataManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.io.File
import java.util.*

class Inventoring {
    fun Statory(inv : Inventory, p : Player){
        var stat = DataManager().getStat(p.uniqueId.toString())
        for(i in 9..35) {
            inv.setItem(i, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, 1, " ", "§f-----------"))
        }
        stat.run{
            inv.setItem(9, createGuiItem(Material.ENCHANTED_BOOK, 1, "§f[§e현재 스텟 포인트§f] : "  + getInt("sp"), "§f[§5소지금§f] : "  + getInt("money"), "§f-----------"))
            inv.setItem(17, createGuiItem(Material.CHEST, 1, "§e§l인벤토리", "§r§f눌러서 §b인벤토리§b로 §e전환§f하세요.", "§f-----------"))
            inv.setItem(27, createGuiItem(Material.SLIME_BALL, 1, "§8§l스킬", "§r§f눌러서 §c스킬§f을 §e설정§f하세요.","§f-----------"))
            inv.setItem(19, createGuiItem(Material.IRON_SWORD, 1,"§c§l힘" + " §r§f[§8 " + getInt("str") + " §f]", "§8§oStrength", "§4근접§f, §a마법 §f추가 데미지에 관여한다.", "", "§9수치 40 이상 효과:","§830% 확률로 적에게 화염 부여", "§f-----------"))
            inv.setItem(21, createGuiItem(Material.BOW, 1,"§e§l집중" + " §r§f[§8 " + getInt("acc") + " §f]", "§8§oAccuracy", "§6투사체 §f추가 데미지에 관여한다.", "", "§9수치 40 이상 효과:","§810% 확률로 투사체 데미지 2배", "§f-----------"))
            inv.setItem(23, createGuiItem(Material.DIAMOND_CHESTPLATE, 1,"§3§l손재주" + " §r§f[§8 " + getInt("def") + " §f]","§8§oDefensive", "§f피격 §c데미지 §e절감§f에 관여한다.", "", "§9수치 40 이상 효과:","§8피격 시 10% 확률로 재생 II 3초 부여", "§f-----------"))
            inv.setItem(25, createGuiItem(Material.FEATHER, 1,"§b§l민첩" + " §r§f[§8 " + getInt("agi") + " §f]","§8§oAgility", "§f추가 §b속도§f에 관여한다.", "", "§9수치 40 이상 효과:","§810% 확률로 경험치 2배", "§f-----------"))
        }
    }
    fun applyInv(p : Player){
        val invitem = DataManager().loadInv(p)
        var oslot = DataManager().loadSkillbar(p)
        var skfile = File("plugins/Rpgs/skills/${p.uniqueId}.yml")
        if(oslot.getBoolean("skillmode")){
            for(i in 9..35) {
                invitem.run {
                    p.inventory.setItem(i, getItemStack("slot${i}"))
                }
            }
        }
        else{
            for(i in 0..35){
                invitem.run{
                    p.inventory.setItem(i, getItemStack("slot${i}"))
                }
            }
        }

    }
    fun skillselect(inv : Inventory){
        inv.setItem(0, createGuiItem(Material.SNOWBALL, 1, "§c돌진", "§e클릭§f하여서 §c장비§f하세요.", "§f-----------"))
        inv.setItem(1, createGuiItem(Material.SNOWBALL, 1, "§4강타", "§e클릭§f하여서 §c장비§f하세요.", "§f-----------"))
        inv.setItem(2, createGuiItem(Material.SNOWBALL, 1, "§8내려찍기", "§e클릭§f하여서 §c장비§f하세요.", "§f-----------"))
        inv.setItem(3, createGuiItem(Material.SNOWBALL, 1, "§b어퍼컷", "§e클릭§f하여서 §c장비§f하세요.", "§f-----------"))
        inv.setItem(4, createGuiItem(Material.SNOWBALL, 1, "§e침묵", "§e클릭§f하여서 §c장비§f하세요.", "§f-----------"))
        inv.setItem(5, createGuiItem(Material.SNOWBALL, 1, "§3백스텝", "§e클릭§f하여서 §c장비§f하세요.", "§f-----------"))
        inv.setItem(53, createGuiItem(Material.SLIME_BALL, 1, "§4스킬 제거", "§e클릭§f해서 §4스킬§f을 제거하세요.", "§f-----------"))
    }
    fun getSkillCode(item : ItemStack) : Long{
        when(item.itemMeta?.displayName!!){
            "§c돌진" -> {
                return 1
            }
            "§4강타" -> {
                return 2
            }
            "§8내려찍기" -> {
                return 3
            }
            "§b어퍼컷" -> {
                return 4
            }
            "§e침묵" -> {
                return 5
            }
            "§3백스텝" -> {
                return 6
            }
            else -> {
                return 0
            }
        }
    }
    fun setselection(p : Player, item : ItemStack, inv : Inventory){
        var skinv = DataManager().loadSkillbar(p)
        for(i in 0..53){
            inv.setItem(i, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, 1, " ", "§f-----------"))
        }
        var itemlist = arrayOf((Material.BARRIER),
            (Material.CYAN_DYE),
            (Material.ORANGE_DYE),
            (Material.MAGENTA_DYE),
            (Material.GREEN_DYE),
            (Material.GRAY_DYE),
            (Material.LIGHT_GRAY_DYE)
        )
        inv.setItem(13, item)
        inv.setItem(28, createGuiItem(itemlist[skinv.getInt("skillslot0")], 1, "§e스킬바설정_1", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
        inv.setItem(30, createGuiItem(itemlist[skinv.getInt("skillslot1")], 1, "§e스킬바설정_2", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
        inv.setItem(32, createGuiItem(itemlist[skinv.getInt("skillslot2")], 1, "§e스킬바설정_3", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
        inv.setItem(34, createGuiItem(itemlist[skinv.getInt("skillslot3")], 1, "§e스킬바설정_4", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
        inv.setItem(37, createGuiItem(itemlist[skinv.getInt("skillslot4")], 1, "§e스킬바설정_5", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
        inv.setItem(39, createGuiItem(itemlist[skinv.getInt("skillslot5")], 1, "§e스킬바설정_6", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
        inv.setItem(41, createGuiItem(itemlist[skinv.getInt("skillslot6")], 1, "§e스킬바설정_7", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
        inv.setItem(43, createGuiItem(itemlist[skinv.getInt("skillslot7")], 1, "§e스킬바설정_8", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
        inv.setItem(46, createGuiItem(itemlist[skinv.getInt("skillslot8")], 1, "§e스킬바설정_9", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
        inv.setItem(48, createGuiItem(itemlist[skinv.getInt("skillslot9")], 1, "§e스킬바설정_10", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
        inv.setItem(50, createGuiItem(itemlist[skinv.getInt("skillslot10")], 1, "§e스킬바설정_11", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
        inv.setItem(52, createGuiItem(itemlist[skinv.getInt("skillslot11")], 1, "§e스킬바설정_12", "§e클릭§f해서 §4스킬§f을 장착하세요.", "§f-----------"))
    }
    fun setselectionremove(p : Player, item : ItemStack, inv : Inventory){
        var skinv = DataManager().loadSkillbar(p)
        for(i in 0..53){
            inv.setItem(i, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, 1, " ", "§f-----------"))
        }
        var itemlist = arrayOf((Material.BARRIER),
            (Material.CYAN_DYE),
            (Material.ORANGE_DYE),
            (Material.MAGENTA_DYE),
            (Material.GREEN_DYE),
            (Material.GRAY_DYE),
            (Material.LIGHT_GRAY_DYE)
        )
        inv.setItem(13, item)
        inv.setItem(28, createGuiItem(itemlist[skinv.getInt("skillslot0")], 1, "§4스킬바삭제_1", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
        inv.setItem(30, createGuiItem(itemlist[skinv.getInt("skillslot1")], 1, "§4스킬바삭제_2", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
        inv.setItem(32, createGuiItem(itemlist[skinv.getInt("skillslot2")], 1, "§4스킬바삭제_3", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
        inv.setItem(34, createGuiItem(itemlist[skinv.getInt("skillslot3")], 1, "§4스킬바삭제_4", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
        inv.setItem(37, createGuiItem(itemlist[skinv.getInt("skillslot4")], 1, "§4스킬바삭제_5", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
        inv.setItem(39, createGuiItem(itemlist[skinv.getInt("skillslot5")], 1, "§4스킬바삭제_6", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
        inv.setItem(41, createGuiItem(itemlist[skinv.getInt("skillslot6")], 1, "§4스킬바삭제_7", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
        inv.setItem(43, createGuiItem(itemlist[skinv.getInt("skillslot7")], 1, "§4스킬바삭제_8", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
        inv.setItem(46, createGuiItem(itemlist[skinv.getInt("skillslot8")], 1, "§4스킬바삭제_9", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
        inv.setItem(48, createGuiItem(itemlist[skinv.getInt("skillslot9")], 1, "§4스킬바삭제_10", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
        inv.setItem(50, createGuiItem(itemlist[skinv.getInt("skillslot10")], 1, "§4스킬바삭제_11", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
        inv.setItem(52, createGuiItem(itemlist[skinv.getInt("skillslot11")], 1, "§4스킬바삭제_12", "§e클릭§f해서 §4스킬§f을 장착해제하세요.", "§f-----------"))
    }
    fun skillbar(p : Player){
        var skinv = DataManager().loadSkillbar(p)
        var itemlist = arrayOf(createGuiItem(Material.BARRIER, 1, "§4null", "§4드롭금지", "§f-----------"),
            createGuiItem(Material.CYAN_DYE, 1, "§4돌진", "§4드롭금지", "§f-----------"),
            createGuiItem(Material.ORANGE_DYE, 1, "§c강타", "§4드롭금지", "§f-----------"),
            createGuiItem(Material.MAGENTA_DYE, 1, "§8내려찍기", "§4드롭금지", "§f-----------"),
            createGuiItem(Material.GREEN_DYE, 1, "§b어퍼컷", "§4드롭금지", "§f-----------"),
            createGuiItem(Material.GRAY_DYE, 1, "§e침묵", "§4드롭금지","§f-----------"),
            createGuiItem(Material.LIGHT_GRAY_DYE, 1, "§3백스텝", "§4드롭금지", "§f-----------")
        )
        for(i in 0 until 8){
            p.inventory.setItem(i, createGuiItem(Material.ARROW, 0, " "))
        }
        skinv.run{
            if(getInt("page") == 1) {
                for (i in 0..5) {
                    p.inventory.setItem(i, itemlist[getInt("skillslot${i}")])
                }
            }
            if(getInt("page") == 2){
                for (i in 0..5) {
                    p.inventory.setItem(i, itemlist[getInt("skillslot${i+6}")])
                }
            }

        }
        p.inventory.setItem(6, createGuiItem(Material.END_CRYSTAL, 1, "§c이전 §f페이지", "§4드롭금지", "§f-----------"))
        p.inventory.setItem(7, createGuiItem(Material.END_CRYSTAL, 1, "§e다음 §f페이지", "§4드롭금지", "§f-----------"))
    }

    fun loadHotbar(p : Player){
        val invitem = DataManager().loadInv(p)
        for(i in 0 until 9){
            invitem.run{
                p.inventory.setItem(i, getItemStack("slot${i}"))
            }
        }
    }
    fun loadHotbarWithoutSlot(p : Player, slot : Int){
        val invitem = DataManager().loadInv(p)
        for(i in 0 until 9){
            if(i != slot) {
                invitem.run {
                    p.inventory.setItem(i, getItemStack("slot${i}"))
                }
            }
        }
    }
    fun createGuiItem(material: Material?, amount : Int, name: String?, vararg lore: String?): ItemStack? {
        val item = ItemStack(material!!, amount)
        val meta = item.itemMeta

        // Set the name of the item
        meta!!.setDisplayName(name)

        // Set the lore of the item
        meta.lore = Arrays.asList(*lore)
        item.itemMeta = meta
        return item
    }
}