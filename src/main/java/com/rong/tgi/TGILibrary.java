package com.rong.tgi;


import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rong.tgi.dimension.TeleportToNetherEventHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.toposort.ModSorter;

@Mod(modid = TGILibrary.MODID, name = TGILibrary.MODNAME, version = TGILibrary.VERSION, useMetadata = true)

public class TGILibrary {
	
	//@SidedProxy(clientSide = "com.rong.tgi.proxies.ClientProxy", serverSide = "com.rong.tgi.proxies.CommonProxy")
	
	//public static ClientProxy clientProxy;
	//public static CommonProxy commonProxy;
	
	@Mod.Instance
    public static TGILibrary instance;
	public static Logger logger;
	
	public static final String MODID = "tgi_library";
	public static final String MODNAME = "TGI Library";
	public static final String VERSION = "1.0";
	
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
    	MinecraftForge.EVENT_BUS.register(new TeleportToNetherEventHandler());
    }
}

