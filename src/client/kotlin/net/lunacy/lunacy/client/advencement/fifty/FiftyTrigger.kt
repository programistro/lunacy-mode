package net.lunacy.lunacy.client.advencement.fifty

import net.fabricmc.api.ModInitializer
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items

data class Conditions(
    val item: Item =  Items.COAL,
    val count: Int = 59
)

class FiftyTrigger : ModInitializer {
    override fun onInitialize() {
           
    }
}

