package com.rong.tgi.dimension;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import com.rong.tgi.TGILibrary;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TGITeleporter extends Teleporter {

	public static TGITeleporter getTeleporterForDimension(MinecraftServer server, int dimension) {
		WorldServer ws = server.getWorld(dimension);
		for(Teleporter t : ws.customTeleporters) {
			if(t instanceof TGITeleporter) {
				return (TGITeleporter) t;
			}
		}
		TGITeleporter tp = new TGITeleporter(ws);
		ws.customTeleporters.add(tp);
		return tp;
	}
	
	private TGITeleporter(WorldServer destination) {
		super(destination);
	}
	
	@Override
	public void placeInPortal(Entity entity, float facing) {
		if(!this.placeInExistingPortal(entity, facing)) {
			this.makePortal(entity);
			this.placeInExistingPortal(entity, facing);
		}
	}
	
	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
		int i = 200;
		double d0 = -1.0D;
		int j = MathHelper.floor(entity.posX);
		int k = MathHelper.floor(entity.posZ);
		boolean flag = true;
		BlockPos pos = BlockPos.ORIGIN;
		long l = ChunkPos.asLong(j, k);
		if(this.destinationCoordinateCache.containsKey(l)) {
			Teleporter.PortalPosition portalPosition = this.destinationCoordinateCache.get(l);
			d0 = 0.0D;
			pos = portalPosition;
			portalPosition.lastUpdateTime = this.world.getTotalWorldTime();
			flag = false;			
		}
		else {
			BlockPos pos3 = new BlockPos(entity);
			for(int i1 = -i; i1 <= i; ++i1) {
				BlockPos pos2;
				for (int j1 = -1; j1 <= i; ++j1) {
					ChunkPos chunkPos = new ChunkPos(pos3.add(i1, 0, j1));
					if(!this.world.isChunkGeneratedAt(chunkPos.x, chunkPos.z)) {
						continue;
					}
					for(BlockPos pos1 = pos3.add(i1, getScanHeight(pos3) - pos3.getY(), j1); pos1.getY() >= 0; pos1 = pos2) {
						pos2 = pos1.down();
						if(d0 >= 0.0D && pos1.distanceSq(pos3) >= d0) {
							continue;
						}
						if(this.world.getBlockState(pos1).getBlock() == TGILibrary.nether_portal) {
							for(pos2 = pos1.down(); this.world.getBlockState(pos2).getBlock() == TGILibrary.nether_portal; pos2 = pos2.down()) {
								pos1 = pos2;
							}
							double d1 = pos1.distanceSq(pos3);
							if(d0 < 0.0D || d1 < d0) {
								d0 = d1;
								pos = pos1;
								i = MathHelper.ceil(MathHelper.sqrt(d1));
							}
						}
					}
				}
			}
		}
		if(d0 >= 0.0D) {
			if(flag) {
				this.destinationCoordinateCache.put(l, new Teleporter.PortalPosition(pos, this.world.getTotalWorldTime()));
			}
			BlockPos[] portalBorder = getBoundaryPositions(pos).toArray(new BlockPos[0]);
			BlockPos borderPos = portalBorder[random.nextInt(portalBorder.length)];
			double portalX = borderPos.getX() + 0.5;
			double portalY = borderPos.getY() + 1.0;
			double portalZ = borderPos.getZ() + 0.5;
			entity.motionX = entity.motionY = entity.motionZ = 0.0D;
			if (entity instanceof EntityPlayerMP) {
				((EntityPlayerMP) entity).connection.setPlayerLocation(portalX, portalY, portalZ, entity.rotationYaw, entity.rotationPitch);
			} 
			else {
				entity.setLocationAndAngles(portalX, portalY, portalZ, entity.rotationYaw, entity.rotationPitch);
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	private int getScanHeight(BlockPos pos) {
		return getScanHeight(pos.getX(), pos.getZ());
	}
	
	private int getScanHeight(int x, int z) {
		int worldHeight = world.getActualHeight() - 1;
		int chunkHeight = world.getChunkFromChunkCoords(x >> 4, z >> 4).getTopFilledSegment() + 15;
		return Math.min(worldHeight, chunkHeight);
	}
	
	private Set<BlockPos> getBoundaryPositions(BlockPos start) {
		Set<BlockPos> result = new HashSet<>(), checked = new HashSet<>();
		checked.add(start);
		checkAdjacent(start, checked, result);
		return result;
	}

	private void checkAdjacent(BlockPos pos, Set<BlockPos> checked, Set<BlockPos> result) {
		for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
			BlockPos offset = pos.offset(facing);
			if (isBlockPortal(world, offset)) {
				if (checked.add(offset)) {
					checkAdjacent(offset, checked, result);
				}
			} else {
				result.add(offset);
			}
		}
	}

	private boolean isBlockPortal(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() == TGILibrary.nether_portal;
	}
	
	@Override
	public boolean makePortal(Entity entity) {
		BlockPos spot = findPortalCoords(entity, true);
		if(spot != null) {
			makePortalAt(world, spot);
			return true;
		}
		else {
			spot = findPortalCoords(entity, false);
			if(spot != null) {
				makePortalAt(world, spot);
				return true;
			}
		}
		double yFactor = world.provider.getDimension() == 0 ? 2 : 0.5;
		makePortalAt(world, new BlockPos(entity.posX, entity.posY * yFactor, entity.posZ));
		return false;
	}
	
	@Nullable
	private BlockPos findPortalCoords(Entity entity, boolean ideal) {
		double yFactor = world.provider.getDimension() == 0 ? 2 : 0.5;
		int entityX = MathHelper.floor(entity.posX);
		int entityZ = MathHelper.floor(entity.posZ);
		double spotWeight = -1D;
		BlockPos spot = null;
		byte range = 16;
		for (int rx = entityX - range; rx <= entityX + range; rx++) {
			double xWeight = (rx + 0.5D) - entity.posX;
			for (int rz = entityZ - range; rz <= entityZ + range; rz++) {
				double zWeight = (rz + 0.5D) - entity.posZ;
				for (int ry = getScanHeight(rx, rz); ry >= 0; ry--) {
					BlockPos pos = new BlockPos(rx, ry, rz);
					if (!world.isAirBlock(pos)) {
						continue;
					}
					while (pos.getY() > 0 && world.isAirBlock(pos.down())) pos = pos.down();
					if (ideal ? isIdealPortal(pos) : isOkayPortal(pos)) {
						double yWeight = (pos.getY() + 0.5D) - entity.posY * yFactor;
						double rPosWeight = xWeight * xWeight + yWeight * yWeight + zWeight * zWeight;
						if (spotWeight < 0.0D || rPosWeight < spotWeight) {
							spotWeight = rPosWeight;
							spot = pos;
						}
					}
				}
			}
		}
		return spot;
	}
	
	private boolean isIdealPortal(BlockPos pos) {
		for (int potentialZ = 0; potentialZ < 4; potentialZ++) {
			for (int potentialX = 0; potentialX < 4; potentialX++) {
				for (int potentialY = -1; potentialY < 3; potentialY++) {
					BlockPos tPos = pos.add(potentialX - 1, potentialY, potentialZ - 1);
					Material material = world.getBlockState(tPos).getMaterial();
					if (potentialY == -1 && material != Material.GRASS
							|| potentialY >= 0 && !material.isReplaceable()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean isOkayPortal(BlockPos pos) {
		for (int potentialZ = 0; potentialZ < 4; potentialZ++) {
			for (int potentialX = 0; potentialX < 4; potentialX++) {
				for (int potentialY = -1; potentialY < 3; potentialY++) {
					BlockPos tPos = pos.add(potentialX - 1, potentialY, potentialZ - 1);
                    Material material = world.getBlockState(tPos).getMaterial();
					if (potentialY == -1 && !material.isSolid() && !material.isLiquid()
							|| potentialY >= 0 && !material.isReplaceable()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private void makePortalAt(World world, BlockPos pos) {
		if (pos.getY() < 30) {
			pos = new BlockPos(pos.getX(), 30, pos.getZ());
		}
		if (pos.getY() > 128 - 10) {
			pos = new BlockPos(pos.getX(), 128 - 10, pos.getZ());
		}
		pos = pos.down();
		world.setBlockState(pos.west().north(), Blocks.SOUL_SAND.getDefaultState());
		world.setBlockState(pos.north(), Blocks.SOUL_SAND.getDefaultState());
		world.setBlockState(pos.east().north(), Blocks.SOUL_SAND.getDefaultState());
		world.setBlockState(pos.east(2).north(), Blocks.SOUL_SAND.getDefaultState());

		world.setBlockState(pos.west(), Blocks.SOUL_SAND.getDefaultState());
		world.setBlockState(pos.east(2), Blocks.SOUL_SAND.getDefaultState());

		world.setBlockState(pos.west().south(), Blocks.SOUL_SAND.getDefaultState());
		world.setBlockState(pos.east(2).south(), Blocks.SOUL_SAND.getDefaultState());

		world.setBlockState(pos.west().south(2), Blocks.SOUL_SAND.getDefaultState());
		world.setBlockState(pos.south(2), Blocks.SOUL_SAND.getDefaultState());
		world.setBlockState(pos.east().south(2), Blocks.SOUL_SAND.getDefaultState());
		world.setBlockState(pos.east(2).south(2), Blocks.SOUL_SAND.getDefaultState());

		world.setBlockState(pos.down(), Blocks.OBSIDIAN.getDefaultState());
		world.setBlockState(pos.east().down(), Blocks.OBSIDIAN.getDefaultState());
		world.setBlockState(pos.south().down(), Blocks.OBSIDIAN.getDefaultState());
		world.setBlockState(pos.east().south().down(), Blocks.OBSIDIAN.getDefaultState());

		IBlockState portal = TGILibrary.nether_portal.getDefaultState();

		world.setBlockState(pos, portal, 2);
		world.setBlockState(pos.east(), portal, 2);
		world.setBlockState(pos.south(), portal, 2);
		world.setBlockState(pos.east().south(), portal, 2);

		for (int dx = -1; dx <= 2; dx++) {
			for (int dz = -1; dz <= 2; dz++) {
				for (int dy = 1; dy <= 5; dy++) {
					world.setBlockToAir(pos.add(dx, dy, dz));
				}
			}
		}
	}
}
