package net.lunacy.lunacy.client

import jdk.javadoc.internal.html.Text
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.ChatFormatting
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.advancements.triggers.InventoryChangeTrigger
import net.minecraft.client.gui.screens.social.PlayerEntry
import net.minecraft.client.multiplayer.chat.ChatAbilities
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.Identifier
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.resources.Resource
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.ResourceProvider
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.TextComponent


class LunacyClient : ClientModInitializer {

    override fun onInitializeClient() {
        LOGGER.info("Initializing Lunacy Client")


//        val tools = HashMap<ItemStack, Int>()
//
        PlayerBlockBreakEvents.AFTER.register { level, player, pos, state, entity ->
//            if(player is ServerPlayer) {
//                val item = player.mainHandItem
//
//                var wrongToolUsedCount: Int = tools.getOrDefault(item, 0)
//                wrongToolUsedCount += 1
//                tools.put(item, wrongToolUsedCount)
//
//
//                player.sendSystemMessage(Component.literal("You've used '" + item + "' as a wrong tool: " + wrongToolUsedCount + " times."))
//            }
            if(player is ServerPlayer) {
                val stack = player.mainHandItem

                val itemType = stack.item

                if(itemType == Items.COAL){
                    player.sendSystemMessage(Component.literal("Вы сломали блок алмазной киркой!"))
                }
            }
        }
    }

    companion object {
        // static final логгер
        val LOGGER: Logger = LoggerFactory.getLogger("lunacy")
    }
}
