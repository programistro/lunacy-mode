package net.lunacy.lunacy

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.world.item.Items
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Lunacy : ModInitializer {

    override fun onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register { server ->
            server.playerList.players.forEach {
                LOGGER.info("Sending Lunacy Player $it")

                val stack = it.mainHandItem
                val itemType = stack.item
                val coals = Items.COAL

                if (stack.`is`(Items.COAL) && stack.count == 59) {
                    it.sendSystemMessage(Component.literal("59"))

                    val advancement = server.advancements
                        .get(Identifier.fromNamespaceAndPath("lunacy", "fifty_nine"))

                    if (advancement != null) {
                        val progress = it.advancements.getOrStartProgress(advancement)
//                        it.advancements.award(
//                            advancement,
//                            "java_trigger"
//                        )
                        if (!progress.isDone) {
                            // Перебираем незавершенные критерии (в вашем случае "java_trigger")
                            progress.remainingCriteria.forEach { criterion ->
                                it.advancements.award(advancement, criterion)
                            }
                        }
                    }
                }

//                if(itemType == Items.COAL) {
//                    it.sendSystemMessage(Component.literal("11111"))
//
//                }
            }
        }
    }

    companion object {
        // static final логгер
        val LOGGER: Logger = LoggerFactory.getLogger("lunacy")
    }
}
