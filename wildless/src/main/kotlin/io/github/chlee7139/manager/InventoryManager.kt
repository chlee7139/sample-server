package io.github.chlee7139.manager

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

class InventoryManager {
    //인벤토리 업데이트
    fun updateInv(p : Player){
        var items = DataManager().loadInv(p)
        items.run {
            for(i in 0 until 36){
                p.inventory.setItem(i, getItemStack("slot${i}"))
            }
        }
    }
    //스텟창 업데이트
    fun updatesGui(p : Player){
        var stat = DataManager().getStat(p.uniqueId.toString())
        var inv : Inventory = p.inventory
        for(i in 9 until 36) {
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
    //파일 지우기
    fun delete(slot : Int, inv : Inventory){
        inv.setItem(slot, ItemStack(Material.BARRIER, 0))
    }
    //여기서만 사용됨
    private fun createGuiItem(material: Material?, amount : Int, name: String?, vararg lore: String?): ItemStack? {
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