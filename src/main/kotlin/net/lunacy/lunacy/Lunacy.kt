package net.lunacy.lunacy

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.lunacy.lunacy.Lunacy.Companion.LOGGER
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Lunacy : ModInitializer {

    override fun onInitialize() {
        EntitySleepEvents.START_SLEEPING.register { entity, pos ->
            if(entity is ServerPlayer){
                LOGGER.info("SLEEEP player ${entity.name} started")

                val level = entity.level()
                if(level.canSeeSky(entity.blockPosition())){
                    val server = level.server
                    server?.let { server ->
                        val advancement = server.advancements
                            .get(Identifier.fromNamespaceAndPath("lunacy", "sleep_bomz"))

                        if (advancement != null) {
                            val progress = entity.advancements.getOrStartProgress(advancement)
                            if (!progress.isDone) {
                                progress.remainingCriteria.forEach { criterion ->
                                    entity.advancements.award(advancement, criterion)
                                }
                                server.playerList.saveAll()
                            }
                        }
                    }
                }
            }
        }

        ServerTickEvents.END_SERVER_TICK.register { server ->
            server.playerList.players.forEach {
                LOGGER.info("Sending Lunacy Player $it")

                val stack = it.mainHandItem
                val secondStack = it.offhandItem
                val itemType = stack.item
                val coals = Items.COAL
                if (stack.count == 59 && secondStack.count == 59) {

                    val advancement = server.advancements
                        .get(Identifier.fromNamespaceAndPath("lunacy", "fifty_nine"))

                    if (advancement != null) {
                        val progress = it.advancements.getOrStartProgress(advancement)
                        if (!progress.isDone) {
                            progress.remainingCriteria.forEach { criterion ->
                                it.advancements.award(advancement, criterion)
                            }
                            server.playerList.saveAll()
                        }
                    }
                }
            }
        }
    }

    companion object {
        // static final логгер
        val LOGGER: Logger = LoggerFactory.getLogger("lunacy")
    }
}
