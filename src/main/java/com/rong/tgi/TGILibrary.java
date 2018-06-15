package com.rong.tgi;

import java.util.logging.Logger;

import com.rong.tgi.warmth.IWarmth;
import com.rong.tgi.warmth.Warmth;
import com.rong.tgi.warmth.WarmthStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = TGILibrary.MODID, name = TGILibrary.MODNAME, version = TGILibrary.VERSION, useMetadata = true)

public class TGILibrary {
	
	//@SidedProxy(clientSide = "com.rong.tgi.proxies.ClientProxy", serverSide = "com.rong.tgi.proxies.CommonProxy")
	
	//public static ClientProxy clientProxy;
	//public static CommonProxy commonProxy;
	
	@Mod.Instance
    public static TGILibrary instance;
	public static Logger logger;
	
	public static final String MODID = "tgilibrary";
	public static final String MODNAME = "TGI Library";
	public static final String VERSION = "1.1";

	@Mod.EventHandler
	public void construction(FMLConstructionEvent event) {
		//May do mod list stuff here, hehe
	}
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        CapabilityManager.INSTANCE.register(IWarmth.class, new WarmthStorage(), Warmth.class);
    }

    @Mod.EventHandler
    public void loaded(FMLLoadCompleteEvent e) {

    }
}

