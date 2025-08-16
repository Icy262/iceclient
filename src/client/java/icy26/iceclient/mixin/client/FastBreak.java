package icy26.iceclient.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import icy26.iceclient.IceClientClient;
import net.minecraft.util.math.Direction;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;

@Mixin(ClientPlayerInteractionManager.class)
public class FastBreak {
	@Shadow
	MinecraftClient client;
	@Shadow
	public void sendSequencedPacket(ClientWorld world, SequencedPacketCreator packetCreator) {}
	@Shadow
	public int blockBreakingCooldown;
	@Inject(at = @At("HEAD"), method = "updateBlockBreakingProgress")
	private void modifyBreakingCooldown(BlockPos pos, Direction direction, CallbackInfoReturnable info) {
		if(IceClientClient.fastBreakEnabled) {
			sendSequencedPacket(client.world, sequence -> {
				return new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, pos, direction, sequence);
			});
			blockBreakingCooldown=0;
		}
	}
}