package net.lunacy.lunacy.client

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.advancements.triggers.CriteriaTriggers
import net.minecraft.advancements.triggers.ImpossibleTrigger
import net.minecraft.core.HolderLookup
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer
import kotlin.jvm.optionals.getOrNull

class LunacyDataGenerator : DataGeneratorEntrypoint {

    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider(::LunacyModAdvancementProvider)
    }
}

class LunacyModAdvancementProvider : FabricAdvancementProvider {
    constructor(output: FabricPackOutput, registryLookup: CompletableFuture<HolderLookup.Provider>) : super(output, registryLookup) {

    }

    override fun generateAdvancement(
        registryLookup: HolderLookup.Provider,
        consumer: Consumer<AdvancementHolder>
    ) {
        val fifty = Advancement.Builder.advancement()
            .display(
                // Создаем ItemStack напрямую из безопасного холдера
                ItemStackTemplate(Items.RABBIT),
                Component.literal("595959"),    // Название
                Component.literal("Держать 59 предметов в 2 руках"),   // Описание
                Identifier.fromNamespaceAndPath("lunacy", "fifty_nine"),
                AdvancementType.TASK,
                true, true, false
            )
            .addCriterion("java_trigger", CriteriaTriggers.IMPOSSIBLE.createCriterion(ImpossibleTrigger.TriggerInstance()))
            .build(Identifier.fromNamespaceAndPath("lunacy", "fifty_nine"))

        consumer.accept(fifty)

        val sleepBomz = Advancement.Builder.advancement()
            .display(
                ItemStackTemplate(Items.BED.red),
                Component.literal("Логово бомжей"),    // Название
                Component.literal("Поспать под открытым небом"),   // Описание
                Identifier.fromNamespaceAndPath("lunacy", "fifty_nine"),
                AdvancementType.TASK,
                true, true, false
            )
            .addCriterion("java_trigger", CriteriaTriggers.IMPOSSIBLE.createCriterion(ImpossibleTrigger.TriggerInstance()))
            .build(Identifier.fromNamespaceAndPath("lunacy", "sleep_bomz"))

        consumer.accept(sleepBomz)

        val smallTok = Advancement.Builder.advancement()
            .display(
                ItemStackTemplate(Items.TNT),
                Component.literal("Малая Токмачка"),    // Название
                Component.literal("Ебнуть орешником по малой токмачке"),   // Описание
                Identifier.fromNamespaceAndPath("lunacy", "small_tok"),
                AdvancementType.TASK,
                true, true, false
            )
            .addCriterion("java_trigger", CriteriaTriggers.IMPOSSIBLE.createCriterion(ImpossibleTrigger.TriggerInstance()))
            .build(Identifier.fromNamespaceAndPath("lunacy", "small_tok"))

        consumer.accept(smallTok)
    }
}