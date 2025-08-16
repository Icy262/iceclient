package icy26.iceclient.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;

import icy26.iceclient.IceClientClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;

@Mixin(ClientPlayerEntity.class)
public class InfiniteFly extends AbstractClientPlayerEntity {
	public InfiniteFly(ClientWorld world, GameProfile profile) {
		super(world, profile);
		//TODO Auto-generated constructor stub
	}

	@Shadow
	public boolean falling; //Currently elytra flying
	@Shadow
	@Final
	public MinecraftClient client;

	@Inject(at = @At("HEAD"), method = "tickMovement")
	private void elytraBoost(CallbackInfo info) {
		if(falling) {
			IceClientClient.currentlyFlying=true;
		} else {
			IceClientClient.currentlyFlying=false;
		}
		if(fallDistance!=0.0F&&client.options.jumpKey.isPressed()) {
			IceClientClient.currentlyFlying=true;
			falling=true;
		}
		if(IceClientClient.infiniteFlyEnabled&&falling) {
			if(client.options.forwardKey.isPressed()&&client.options.backKey.isPressed()) {
				setVelocity(new Vec3d(0, 0.05, 0));
			} else if(client.options.forwardKey.isPressed()&&client.options.sprintKey.isPressed()) {
				setVelocity(getVelocity().multiply(1.2, 1, 1.2));
			} else if(client.options.forwardKey.isPressed()) {
				setVelocity(getVelocity().multiply(1.05, 1, 1.05));
			} else if(client.options.backKey.isPressed()&&client.options.sprintKey.isPressed()) {
				setVelocity(getVelocity().multiply(1/1.2));
			} else if(client.options.backKey.isPressed()) {
				setVelocity(getVelocity().multiply(1/1.05));
			}
			if(client.options.jumpKey.isPressed()&&client.options.sprintKey.isPressed()) {
				setVelocity(getVelocity().x, getVelocity().y+0.5, getVelocity().z);
			} else if(client.options.jumpKey.isPressed()) {
				setVelocity(getVelocity().x, getVelocity().y+0.15, getVelocity().z);
			} else if(client.options.sneakKey.isPressed()&&client.options.sprintKey.isPressed()) {
				setVelocity(getVelocity().x, getVelocity().y-0.5, getVelocity().z);
			} else if(client.options.sneakKey.isPressed()) {
				setVelocity(getVelocity().x, getVelocity().y-0.15, getVelocity().z);
			}
			if(getVelocity().length()>=10) {
				setVelocity(getVelocity().multiply(1/(getVelocity().length()/10)));
			}
		}
	}
}