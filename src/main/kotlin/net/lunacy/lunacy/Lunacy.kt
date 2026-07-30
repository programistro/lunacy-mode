package net.lunacy.lunacy

import com.jcraft.jorbis.Block
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.lunacy.lunacy.Lunacy.Companion.LOGGER
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.StructureTags
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.ProjectileUtil
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.Vec3
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

                val maxDistance = 10.0

                val startPos: Vec3 = it.getEyePosition(1.0f)
                val lookVec: Vec3 = it.getViewVector(1.0f)

                val endPos: Vec3 = startPos.add(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance)
                val searchBox = it.boundingBox.expandTowards(lookVec.scale(maxDistance)).inflate(1.0)
                val entityHit: EntityHitResult? = ProjectileUtil.getEntityHitResult(
                    it,       // Сущность, которую нужно игнорировать (сам игрок)
                    startPos,     // Точка старта луча
                    endPos,       // Точка конца луча
                    searchBox,    // Область поиска
                    { entity -> !entity.isSpectator && entity.isAlive }, // Фильтр (игнорируем наблюдателей и мертвых)
                    maxDistance * maxDistance // Дистанция в квадрате
                )

                if (entityHit != null) {
                    val targetedEntity = entityHit.entity // Это сущность, на которую смотрит игрок!

                    // Пример использования: если игрок смотрит на Зомби
                    if (targetedEntity is net.minecraft.world.entity.monster.EnderMan) {
//                        player.sendSystemMessage(net.minecraft.network.chat.Component.literal("Вы пристально смотрите на Зомби..."))
                        val advancement = server.advancements
                            .get(Identifier.fromNamespaceAndPath("lunacy", "kniga"))

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

//                val serverLevel = it.level()
//                val blockPos = it.blockPosition()
//                val villageStart = serverLevel.structureManager().getStructureWithPieceAt(blockPos, StructureTags.VILLAGE)
//                if (villageStart.isValid) {
//                    LOGGER.info("${it.name} в деревне!")
//
//                }


            }
        }

        UseBlockCallback.EVENT.register { player, level, hand, result ->
           if(!level.isClientSide){
               val serverPlayer = player as ServerPlayer
               val stack = player.getItemInHand(hand)
               val blockPos = result.blockPos
               val blockState = level.getBlockState(blockPos)

               val serverLevel = serverPlayer.level()
               val villageStart = serverLevel.structureManager().getStructureWithPieceAt(blockPos, StructureTags.VILLAGE)
               if (villageStart.isValid) {
                   LOGGER.info("${serverPlayer.name} в деревне!")
                   if (stack.`is`(Items.FLINT_AND_STEEL) && blockState.`is`(Blocks.TNT)) {
                       val server = level.server
                       server?.let { server ->
                           val advancement = server.advancements
                               .get(Identifier.fromNamespaceAndPath("lunacy", "small_tok"))

                           if (advancement != null) {
                               val progress = serverPlayer.advancements.getOrStartProgress(advancement)
                               if (!progress.isDone) {
                                   progress.remainingCriteria.forEach { criterion ->
                                       serverPlayer.advancements.award(advancement, criterion)
                                   }
                                   server.playerList.saveAll()
                               }
                           }
                       }
                   }
               }
           }
            InteractionResult.PASS
        }


    }

    companion object {
        // static final логгер
        val LOGGER: Logger = LoggerFactory.getLogger("lunacy")
    }
}
