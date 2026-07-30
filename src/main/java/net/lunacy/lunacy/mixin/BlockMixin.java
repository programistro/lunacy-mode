package net.lunacy.lunacy.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TntBlock.class)
public class BlockMixin {

    @Inject(
            method = "playerWillDestroy",
            at = @At("HEAD")
    )
    private void OnFire(Level level, BlockPos pos, BlockState state, Player player, CallbackInfoReturnable<BlockState> cir){
        if (!level.isClientSide()) {
            // Обращаемся к логгеру вашего главного Kotlin-класса Lunacy
            net.lunacy.lunacy.Lunacy.Companion.getLOGGER().info(player.getName().getString() + " активировал ТНТ!");

            // Отправляем сообщение игроку
            player.sendSystemMessage(Component.literal("Вы активировали ТНТ!"));
        }
    }
}