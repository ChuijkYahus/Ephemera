package net.beholderface.ephemera.forge;

import at.petrak.hexcasting.forge.ForgeHexClientInitializer;
import dev.architectury.platform.forge.EventBuses;
import net.beholderface.ephemera.Ephemera;
import net.beholderface.ephemera.forge.ForgePacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * This is your loading entrypoint on forge, in case you need to initialize
 * something platform-specific.
 */
@Mod(Ephemera.MOD_ID)
public class EphemeraForge {
    public EphemeraForge() {
        // Submit our event bus to let architectury register our content on the right time
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Ephemera.MOD_ID, bus);
        bus.addListener(EphemeraClientForge::init);
        bus.addListener(EphemeraClientForge::registerRenderers);
        Ephemera.init();
        bus.addListener((FMLCommonSetupEvent evt)-> evt.enqueueWork(ForgePacketHandler::init));
    }
}
