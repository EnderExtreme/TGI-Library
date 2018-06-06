package com.rong.tgi.dimension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import org.apache.commons.lang3.mutable.MutableInt;

import com.rong.tgi.TGILibrary;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNetherEntrance extends Block {
	
	
	private static final AxisAlignedBB PORTAL_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
	private static final AxisAlignedBB PORTAL_AABB_ITEM = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.4F, 1.0F);
	private static final int PORTAL_SIZE_LIMIT = 64;

	public BlockNetherEntrance() {
		super(Material.PORTAL);
		setHardness(-1F);
		setSoundType(SoundType.GLASS);
		setLightLevel(0.75F);
		setCreativeTab(CreativeTabs.DECORATIONS);
		setUnlocalizedName(TGILibrary.MODID + ".nether_portal");
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return PORTAL_AABB;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return NULL_AABB;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBB, List<AxisAlignedBB> blockBBs, @Nullable Entity entity, boolean isActualState) {
		addCollisionBoxToList(pos, entityBB, blockBBs, entity != null && entity instanceof EntityItem ? PORTAL_AABB_ITEM : state.getCollisionBoundingBox(world, pos));
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (state == this.getDefaultState() && !entity.isRiding() && !entity.isBeingRidden()) {
			attemptSendPlayer(entity);
		}
	}
	
	public static void attemptSendPlayer(Entity entity) {
		if(entity.isDead == entity.world.isRemote) {
			return;
		}
		if(entity.timeUntilPortal > 0) {
			return;
		}
		entity.timeUntilPortal = 10;
		int destination = entity.dimension != -1 ? -1 : 0;
		entity.changeDimension(destination, TGITeleporter.getTeleporterForDimension(entity.getServer(), destination));
		if(destination != 0 && entity instanceof EntityPlayerMP) {
			EntityPlayerMP mpPlayer = (EntityPlayerMP) entity;
			mpPlayer.setSpawnChunk(new BlockPos(mpPlayer), true, -1);
		}
	}

	
	public boolean portalCreationAttempt(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		
		if(state == Blocks.LAVA.getDefaultState() || state.getBlock() == this) {
			HashMap<BlockPos, Boolean> blocksChecked = new HashMap<>();
			blocksChecked.put(pos, true);
			
			MutableInt size = new MutableInt(0);
			
			if(recursionValidation(world, pos, blocksChecked, size, state) && size.get() > 3) {
				for(Map.Entry<BlockPos, Boolean> checkedPos : blocksChecked.entrySet())
					if(checkedPos.getValue()) world.setBlockState(checkedPos.getKey(), TGILibrary.nether_portal.getDefaultState());
				return true;
			}
		}
		return false;
	}
	
	private static boolean recursionValidation(World world, BlockPos pos, HashMap<BlockPos, Boolean> blocksChecked, MutableInt lavaLimit, IBlockState requiredBlock) {
		boolean portalEnclosed = true;
		
		lavaLimit.increment();
		if(lavaLimit.get() > PORTAL_SIZE_LIMIT) return false;
		for(int i = 0; i < EnumFacing.HORIZONTALS.length && lavaLimit.get() <= PORTAL_SIZE_LIMIT; i++) {
			BlockPos posCheck = pos.offset(EnumFacing.HORIZONTALS[i]);
			if(!blocksChecked.containsKey(posCheck)) {
				IBlockState state = world.getBlockState(posCheck);
				if(state == requiredBlock && world.getBlockState(posCheck.down()).isFullCube()) {
					portalEnclosed = portalEnclosed && recursionValidation(world, posCheck, blocksChecked, lavaLimit, requiredBlock);
				}
				else if((isNetherBlocks(state)) && isFire(world.getBlockState(posCheck.up()))) {
					blocksChecked.put(posCheck, false);
				}
				else return false;
			}
		}
		return portalEnclosed;
	}
	
	private static class MutableInt {
		private int anInt;

		MutableInt(int anInt) {
			this.anInt = anInt;
		}

		int get() {
			return anInt;
		}

		void increment() {
			this.anInt++;
		}
	}
	
	private static boolean isFire(IBlockState state) {
		Material mat = state.getMaterial();
		return mat == Material.FIRE;
	}

	private static boolean isNetherBlocks(IBlockState state) {
		Material mat = state.getMaterial();
		return state.isFullCube() && (mat == Material.ROCK || mat == Material.GROUND);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		int random = rand.nextInt(100);
		if (random == 0) {
			worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double xPos = (double) ((float) pos.getX() + rand.nextFloat());
			double yPos = pos.getY()+1D;
			double zPos = (double) ((float) pos.getZ() + rand.nextFloat());
			double xSpeed = ((double) rand.nextFloat() - 0.5D) * 0.5D;
			double ySpeed = rand.nextFloat();
			double zSpeed = ((double) rand.nextFloat() - 0.5D) * 0.5D;

			worldIn.spawnParticle(EnumParticleTypes.PORTAL, xPos, yPos, zPos, xSpeed, ySpeed, zSpeed);
		}
	}
}