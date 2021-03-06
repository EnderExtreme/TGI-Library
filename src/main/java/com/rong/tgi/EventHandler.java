package com.rong.tgi;

import static com.rong.tgi.DimensionIDs.AETHER;
import static com.rong.tgi.DimensionIDs.END;
import static com.rong.tgi.DimensionIDs.NETHER;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Random;

import com.rong.tgi.entities.EntityManaPearl;

import baubles.api.BaublesApi;
//import muramasa.gtu.api.data.Materials;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thebetweenlands.common.herblore.elixir.ElixirEffectRegistry;
import twilightforest.advancements.TFAdvancements;
import twilightforest.block.TFBlocks;
import vazkii.botania.common.block.BlockFloatingSpecialFlower;
import vazkii.botania.common.block.BlockSpecialFlower;
import vazkii.botania.common.block.tile.TileSpecialFlower;
import vazkii.botania.common.item.ModItems;

@Mod.EventBusSubscriber
public class EventHandler {
    
    @SubscribeEvent
    public static void onItemCrafted(ItemCraftedEvent event) {
        ItemStack stack = event.crafting;
        IInventory inventory = event.craftMatrix;
        EnumRarity rarity = stack.getRarity();
        if (stack.hasEffect()) {
            event.player.addExperience(10);
        }
        else if (rarity == EnumRarity.UNCOMMON) {
            event.player.addExperience(2);
        }
        else if (rarity == EnumRarity.RARE || rarity == EnumRarity.EPIC) {
            event.player.addExperience(10);
        }
        else if (inventory.getDisplayName().getFormattedText() == "gib free xp plox") {
            event.player.addExperience(2);
        }
        else {
            if (inventory.getClass() != InventoryCrafting.class) event.player.addExperience(1);
        }
        
    }

    @SubscribeEvent
    public static void onItemSmelted(ItemSmeltedEvent event) {
        if (FurnaceRecipes.instance().getSmeltingExperience(event.smelting) <= 2.0F) event.player.addExperience(1);
        else event.player.addExperience(2);
    }
    
    @SubscribeEvent
    public static void onDimensionChangeClient(PlayerChangedDimensionEvent event) {
        EntityPlayer player = event.player;
        World world = player.world;
        if (world.isRemote) {
            BlockPos pos = player.getPosition();
            if (event.toDim == NETHER) {
                world.playSound(player, pos, CommonProxy.thatsHot, SoundCategory.PLAYERS, 1.0F, 1.0F);
            } else if (event.toDim == AETHER || event.toDim == END) {
                world.playSound(player, pos, CommonProxy.itsReallyReallyCold, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void gateTheNether(EntityTravelToDimensionEvent event) {
        World world = event.getEntity().getEntityWorld();
        if (!(event.getEntity() instanceof EntityPlayer))
            return;
        if (event.getDimension() != NETHER)
            return;
        if (!world.isRemote) {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
            WorldServer worldServer = (WorldServer) world;
            AdvancementManager manager = worldServer.getAdvancementManager();
            AdvancementProgress netherProgress = player.getAdvancements().getProgress(manager.getAdvancement(new ResourceLocation("nether/obtain_blaze_rod")));
            if (!netherProgress.isDone()) {
                player.sendStatusMessage(new TextComponentString(I18n.translateToLocal("tgi.netheroops")), true);
                player.setFire(4);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void toTheNether(PlayerInteractEvent.RightClickBlock event) {
        if (!Helper.isModLoaded("wizardry") && !Helper.isModLoaded("botania") && !Helper.isModLoaded("baubles"))
            return;
        if (event.getFace() == null)
            return;
        if (event.getItemStack().isEmpty())
            return;
        if (!event.getItemStack().isItemEqual(new ItemStack(Item.getByNameOrId("wizardry:devil_dust"))))
            return;
        BlockPos offsetPos = event.getPos().offset(event.getFace());
        World world = event.getWorld();
        EntityPlayer player = event.getEntityPlayer();

        if (!(BaublesApi.isBaubleEquipped(player, (new ItemStack(ModItems.cosmetic, 1, 13).getItem())) == -1) && !(BaublesApi.isBaubleEquipped(player, (new ItemStack(ModItems.cosmetic, 1, 19).getItem())) == -1)) {
            preparePortal(offsetPos, world);
        }
    }

    // Subject to change, get MetaTool from stack -> get Material from MetaTool ->
    // get Material.ToolSpeed
    /*
     * @SubscribeEvent(priority = EventPriority.HIGHEST) public static void
     * onBreakingMachines(BreakSpeed event) { IBlockState state = event.getState();
     * ItemStack stack = event.getEntityPlayer().getHeldItemMainhand(); for(String
     * targetMachines : Helper.enderIOMachines) { if(state ==
     * Block.getBlockFromName(targetMachines)) {
     * if(stack.isItemEqual(MetaItems.WRENCH.getStackForm()) &&
     * stack.isItemEqual(MetaItems.WRENCH_LV.getStackForm()) &&
     * stack.isItemEqual(MetaItems.WRENCH_MV.getStackForm()) &&
     * stack.isItemEqual(MetaItems.WRENCH_HV.getStackForm())) {
     * event.setNewSpeed(stack.getDestroySpeed(state) + 3.0F); } else {
     * event.getEntityPlayer().sendStatusMessage(new
     * TextComponentString("tgi.wrongtool"), true); } } } }
     * 
     * @SubscribeEvent(priority = EventPriority.HIGHEST) public static void
     * onHarvestMachines(HarvestCheck event) { boolean harvestable =
     * event.canHarvest(); IBlockState state = event.getTargetBlock(); ItemStack
     * stack = event.getEntityPlayer().getHeldItemMainhand(); for(String
     * targetMachines : Helper.enderIOMachines) { if(state ==
     * Block.getBlockFromName(targetMachines)) {
     * if(stack.isItemEqual(MetaItems.WRENCH.getStackForm()) &&
     * stack.isItemEqual(MetaItems.WRENCH_LV.getStackForm()) &&
     * stack.isItemEqual(MetaItems.WRENCH_MV.getStackForm()) &&
     * stack.isItemEqual(MetaItems.WRENCH_HV.getStackForm())) {
     * event.setCanHarvest(true); } else { event.setCanHarvest(false); } } } }
     */

    /*
    //TODO: Use LootTweaker!
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingDrop(LivingDropsEvent event) {
        if (!Helper.isModLoaded("gregtech"))
            return;
        World world = event.getEntity().world;
        int x = event.getEntity().chunkCoordX;
        int y = event.getEntity().chunkCoordY;
        int z = event.getEntity().chunkCoordZ;
        Random random = new Random();
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            ItemStack stack = player.inventory.getCurrentItem();
            Map<Enchantment, Integer> currentEnchants = EnchantmentHelper.getEnchantments(stack);
            // Will still return 0
            int looting = (int) Math.ceil(event.getLootingLevel() * 2 / 1 + event.getLootingLevel());
            boolean silkTouch = currentEnchants.get(Enchantments.SILK_TOUCH) != null;
            if (event.getEntity().getClass() == EntityDragon.class) {
                if (silkTouch) {
                    event.getDrops().add(new EntityItem(world, x, y, z, new ItemStack(Items.ELYTRA, 1, Items.ELYTRA.getMaxDamage() - 1)));
                }
            } else if (event.getEntity().getClass() == EntityWither.class) {
                if (silkTouch)
                    return;
                event.getDrops().remove(new EntityItem(world, x, y, z, new ItemStack(Items.NETHER_STAR)));
                if (random.nextInt(3) == 2 && (world.provider.getDimension() == DimensionIDs.NETHER || world.provider.getDimension() == DimensionIDs.BENEATH)) {
                    event.getDrops().add(new EntityItem(world, x, y, z, Materials.NetherStar.getDust(3 + looting)));
                } else if (random.nextInt(10) == 5) {
                    event.getDrops().add(new EntityItem(world, x, y, z, Materials.NetherStar.getDust(1 + looting)));
                    event.getDrops().add(new EntityItem(world, x, y, z, Materials.NetherStar.getDust(2 + looting)));
                } else {
                    event.getDrops().add(new EntityItem(world, x, y, z, Materials.NetherStar.getDust(1 + looting)));
                }
            }
        }
        if (event.getEntity().getClass() == EntityLlama.class) {
            event.getDrops().remove(new EntityItem(world, x, y, z, new ItemStack(Items.MUTTON)));
        }
    }
     */

    @SubscribeEvent
    public static void dontPlaceNearTwilightPortal(BlockEvent.PlaceEvent event) {
        if (!Helper.isModLoaded("twilightforest"))
            return;
        IBlockState up = event.getWorld().getBlockState(event.getPos().up());
        IBlockState down = event.getWorld().getBlockState(event.getPos().down());
        IBlockState north = event.getWorld().getBlockState(event.getPos().north());
        IBlockState east = event.getWorld().getBlockState(event.getPos().east());
        IBlockState south = event.getWorld().getBlockState(event.getPos().south());
        IBlockState west = event.getWorld().getBlockState(event.getPos().west());
        boolean allDirections = up == TFBlocks.twilight_portal.getDefaultState() || down == TFBlocks.twilight_portal.getDefaultState() || north == TFBlocks.twilight_portal.getDefaultState() || east == TFBlocks.twilight_portal.getDefaultState() || south == TFBlocks.twilight_portal.getDefaultState() || west == TFBlocks.twilight_portal.getDefaultState();
        if (event.getPlacedAgainst() == TFBlocks.twilight_portal.getDefaultState() || allDirections) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void dontBreakNearTwilightPortal(BlockEvent.BreakEvent event) {
        if (!Helper.isModLoaded("twilightforest"))
            return;
        IBlockState up = event.getWorld().getBlockState(event.getPos().up());
        IBlockState down = event.getWorld().getBlockState(event.getPos().down());
        IBlockState north = event.getWorld().getBlockState(event.getPos().north());
        IBlockState east = event.getWorld().getBlockState(event.getPos().east());
        IBlockState south = event.getWorld().getBlockState(event.getPos().south());
        IBlockState west = event.getWorld().getBlockState(event.getPos().west());
        boolean allDirections = up == TFBlocks.twilight_portal.getDefaultState() || down == TFBlocks.twilight_portal.getDefaultState() || north == TFBlocks.twilight_portal.getDefaultState() || east == TFBlocks.twilight_portal.getDefaultState() || south == TFBlocks.twilight_portal.getDefaultState() || west == TFBlocks.twilight_portal.getDefaultState();
        if (allDirections) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void makeManaPearlGreatAgain(PlayerInteractEvent.RightClickItem event) {
        if (!Helper.isModLoaded("botania"))
            return;
        ItemStack stack = event.getItemStack();
        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        Random rand = new Random();

        // Sorry Folks
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        double d0 = player.posX;
        double d1 = player.posY + (double) player.getEyeHeight();
        double d2 = player.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d3 = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        Vec3d vec3d1 = vec3d.add((double) f6 * d3, (double) f5 * d3, (double) f7 * d3);
        RayTraceResult raytraceResult = world.rayTraceBlocks(vec3d, vec3d1, false);

        if (stack.isItemEqual(new ItemStack(Item.getByNameOrId("botania:manaresource"), 1, 1))) {
            if (raytraceResult != null && raytraceResult.typeOfHit == RayTraceResult.Type.BLOCK && world.getBlockState(raytraceResult.getBlockPos()).getBlock() == Blocks.END_PORTAL_FRAME) {
            } else {
                player.setActiveHand(event.getHand());
                if (!world.isRemote) {
                    BlockPos pos = ((WorldServer) world).getChunkProvider().getNearestStructurePos(world, "Stronghold", new BlockPos(player), false);
                    if (pos != null && !(((WorldServer) world).getChunkProvider().isInsideStructure(world, "Stronghold", player.getPosition()))) {
                        EntityManaPearl entityManaPearl = new EntityManaPearl(world, player.posX, player.posY + (double) (player.height / 2.0F), player.posZ);
                        entityManaPearl.moveTowards(pos);
                        world.spawnEntity(entityManaPearl);
                        world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
                        world.playEvent((EntityPlayer) null, 1003, new BlockPos(player), 0);
                        if (!player.capabilities.isCreativeMode) {
                            stack.shrink(1);
                        }
                        player.addStat(StatList.getObjectUseStats(stack.getItem()));
                    }
                }
            }
        }
        if (stack.isItemEqual(new ItemStack(Items.ENDER_EYE))) {
            event.setCanceled(true);
            if (event.isCanceled() && raytraceResult != null && raytraceResult.typeOfHit == RayTraceResult.Type.BLOCK && (world.getBlockState(raytraceResult.getBlockPos()).getBlock() == Blocks.END_BRICKS || world.getBlockState(raytraceResult.getBlockPos()).getBlock() == Blocks.PURPUR_BLOCK)) {
            } else {
                player.setActiveHand(event.getHand());
                if (!world.isRemote) {
                    BlockPos exploredPos = ((WorldServer) world).getChunkProvider().getNearestStructurePos(world, "EndCity", new BlockPos(player), false);
                    if (exploredPos != null && !(((WorldServer) world).getChunkProvider().isInsideStructure(world, "EndCity", player.getPosition()))) {
                        EntityEnderEye entityEnderEye = new EntityEnderEye(world, player.posX, player.posY + (double) (player.height / 2.0F), player.posZ);
                        entityEnderEye.moveTowards(exploredPos);
                        world.spawnEntity(entityEnderEye);
                        if (player instanceof EntityPlayerMP) {
                            CriteriaTriggers.USED_ENDER_EYE.trigger((EntityPlayerMP) player, exploredPos);
                        }
                        world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
                        world.playEvent((EntityPlayer) null, 1003, new BlockPos(player), 0);
                        if (!player.capabilities.isCreativeMode) {
                            stack.shrink(1);
                        }
                        player.addStat(StatList.getObjectUseStats(stack.getItem()));
                    }
                    BlockPos unexploredPos = ((WorldServer) world).getChunkProvider().getNearestStructurePos(world, "EndCity", new BlockPos(player), true);
                    if (unexploredPos != null && (((WorldServer) world).getChunkProvider().isInsideStructure(world, "EndCity", player.getPosition()))) {
                        EntityEnderEye entityEnderEye = new EntityEnderEye(world, player.posX, player.posY + (double) (player.height / 2.0F), player.posZ);
                        entityEnderEye.moveTowards(exploredPos);
                        world.spawnEntity(entityEnderEye);
                        if (player instanceof EntityPlayerMP) {
                            CriteriaTriggers.USED_ENDER_EYE.trigger((EntityPlayerMP) player, exploredPos);
                        }
                        world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
                        world.playEvent((EntityPlayer) null, 1003, new BlockPos(player), 0);
                        if (!player.capabilities.isCreativeMode) {
                            stack.shrink(1);
                        }
                        player.addStat(StatList.getObjectUseStats(stack.getItem()));
                    }
                }
            }
        }
    }

    /*
     * @SubscribeEvent public static void
     * carbonCartridge(PlayerInteractEvent.RightClickItem event) {
     * if(event.getItemStack().isItemEqual(new
     * ItemStack(AdvancedRocketryItems.itemCarbonScrubberCartridge, 1,
     * AdvancedRocketryItems.itemCarbonScrubberCartridge.getMaxDamage()))) {
     * event.getItemStack().setItemDamage(0);
     * event.getEntityPlayer().addItemStackToInventory(new ItemStack(Items.COAL, 4,
     * 1)); } }
     */

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!Helper.isModLoaded("botania"))
            return;
        Random itemRand = new Random();
        World world = event.getWorld();
        IBlockState state = world.getBlockState(event.getPos());
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();
        ItemStack mainItem = player.getHeldItemMainhand();

        if (stack.isItemEqual(new ItemStack(Item.getByNameOrId("botania:manaresource"), 1, 1)) && event.getEntityPlayer().canPlayerEdit(pos.offset(event.getFace()), event.getFace(), stack) && state.getBlock() == Blocks.END_PORTAL_FRAME && !(state.getValue(BlockEndPortalFrame.EYE)).booleanValue()) {
            if (world.isRemote) {
            } else {
                world.setBlockState(pos, state.withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(true)), 2);
                world.updateComparatorOutputLevel(pos, Blocks.END_PORTAL_FRAME);
                stack.shrink(1);

                for (int i = 0; i < 16; ++i) {
                    double d0 = (double) ((float) pos.getX() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
                    double d1 = (double) ((float) pos.getY() + 0.8125F);
                    double d2 = (double) ((float) pos.getZ() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
                    double d3 = 0.0D;
                    double d4 = 0.0D;
                    double d5 = 0.0D;
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }

                world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                BlockPattern.PatternHelper blockpattern$patternhelper = BlockEndPortalFrame.getOrCreatePortalShape().match(world, pos);

                if (blockpattern$patternhelper != null) {
                    BlockPos blockpos = blockpattern$patternhelper.getFrontTopLeft().add(-3, 0, -3);

                    for (int j = 0; j < 3; ++j) {
                        for (int k = 0; k < 3; ++k) {
                            world.setBlockState(blockpos.add(j, 0, k), TFBlocks.twilight_portal.getDefaultState());
                        }
                    }
                    TFAdvancements.MADE_TF_PORTAL.trigger((EntityPlayerMP) event.getEntityPlayer());
                    EntityLightningBolt bolt = new EntityLightningBolt(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, true);
                    world.addWeatherEffect(bolt);
                    world.playBroadcastSound(1038, blockpos.add(1, 0, 1), 0);
                }
            }
        }

        if (stack.isItemEqual(new ItemStack(Items.ENDER_EYE))) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void toTheEnd(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (Helper.isModLoaded("thebetweenlands") && ElixirEffectRegistry.EFFECT_FOGGEDMIND.isActive(player) && ElixirEffectRegistry.EFFECT_FOGGEDMIND.getDuration(player) < 40) {
            if (!player.world.isRemote && !player.isRiding()) {
                player.changeDimension(1);
            }
        }
    }

    private static void preparePortal(BlockPos offset, World world) {
        if (world.isRemote)
            return;
        IBlockState lavaBrick = Blocks.STONE.getDefaultState(); //TODO: TEMPORARY
        IBlockState portal = Blocks.PORTAL.getDefaultState().withProperty(BlockPortal.AXIS, EnumFacing.Axis.X);
        if (world.getBlockState(offset.east()) == lavaBrick && world.getBlockState(offset.west()) == lavaBrick) {
            if (world.getBlockState(offset.down()) == lavaBrick && world.isAirBlock(offset.up()) && world.getBlockState(offset.up(2)) == lavaBrick && world.getBlockState(offset.up().west()) == lavaBrick && world.getBlockState(offset.up().east()) == lavaBrick) {
                world.setBlockState(offset, portal);
                world.setBlockState(offset.up(), portal);
            } else if (world.getBlockState(offset.up()) == lavaBrick && world.isAirBlock(offset.down()) && world.getBlockState(offset.down(2)) == lavaBrick && world.getBlockState(offset.down().west()) == lavaBrick && world.getBlockState(offset.down().east()) == lavaBrick) {
                world.setBlockState(offset, portal);
                world.setBlockState(offset.down(), portal);
            }
        }
    }
}