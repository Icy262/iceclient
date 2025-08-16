package icy26.iceclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

public class IceClientClient implements ClientModInitializer {
	public static boolean fastBreakEnabled=false;
	public static boolean infiniteFlyEnabled=false;
	public static boolean currentlyFlying=false;
	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("fastBreak")
		.executes(context -> {
				fastBreakEnabled=!fastBreakEnabled;
				context.getSource().sendFeedback(Text.literal("fastBreak set to "+fastBreakEnabled));
				return 1;
			}
		)));
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("infiniteFly")
		.executes(context -> {
				infiniteFlyEnabled=!infiniteFlyEnabled;
				context.getSource().sendFeedback(Text.literal("infiniteFly set to "+infiniteFlyEnabled));
				return 1;
			}
		)));
	}
}