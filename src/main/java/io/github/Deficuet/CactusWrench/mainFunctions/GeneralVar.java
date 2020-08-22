package io.github.Deficuet.CactusWrench.mainFunctions;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.block.data.Bisected.Half;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GeneralVar {
	public final static Material[] GlazedBlocksList = {
			Material.WHITE_GLAZED_TERRACOTTA,
			Material.ORANGE_GLAZED_TERRACOTTA,
			Material.MAGENTA_GLAZED_TERRACOTTA,
			Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
			Material.YELLOW_GLAZED_TERRACOTTA,
			Material.LIME_GLAZED_TERRACOTTA,
			Material.PINK_GLAZED_TERRACOTTA,
			Material.GRAY_GLAZED_TERRACOTTA,
			Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
			Material.CYAN_GLAZED_TERRACOTTA,
			Material.PURPLE_GLAZED_TERRACOTTA,
			Material.BLUE_GLAZED_TERRACOTTA,
			Material.BROWN_GLAZED_TERRACOTTA,
			Material.GREEN_GLAZED_TERRACOTTA,
			Material.RED_GLAZED_TERRACOTTA,
			Material.BLACK_GLAZED_TERRACOTTA
		};
	public final static Material[] pistonClass = {Material.PISTON, Material.STICKY_PISTON};
	public final static BlockFace[] sideFace = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
	public final static BlockFace[] UpDownFace = {BlockFace.UP, BlockFace.DOWN};

	public final static List<Material> GlazedTerracotta = Arrays.asList(GlazedBlocksList);
	public final static List<Material> pistonSet = Arrays.asList(pistonClass);
	public final static List<BlockFace> surroundFace = Arrays.asList(sideFace);
	public final static List<BlockFace> axisYFace = Arrays.asList(UpDownFace);
	public static Set<Material> setOrientCycleBlocks() {
		Set<Material> BlocksList = new HashSet<>();
		Material[] MaterialsList = {
			Material.LEVER,
			Material.HOPPER,
			Material.REPEATER,
			Material.COMPARATOR
		};
		BlocksList.addAll(Arrays.asList(MaterialsList));
		BlocksList.addAll(GlazedTerracotta);
		BlocksList.addAll(Tag.TRAPDOORS.getValues());
		BlocksList.addAll(Tag.FENCE_GATES.getValues());
		return BlocksList;
	}
	public static Set<Material> setFlippinBlocks(boolean withBarrel) {
		Set<Material> BlocksList = new HashSet<>();
		Material[] MaterialsList = {
			Material.DISPENSER,
			Material.DROPPER,
			Material.PISTON,
			Material.STICKY_PISTON,
			Material.OBSERVER,
			Material.END_ROD
		};
		BlocksList.addAll(Arrays.asList(MaterialsList));
		if (withBarrel) BlocksList.add(Material.BARREL);
		return BlocksList;
	}
	public static Type getOpposite(Type type) {
		switch (type) {
		case TOP:
			return Type.BOTTOM;
		case BOTTOM:
			return Type.TOP;
		default:
			return type;
		}
	}
	public static Half getOpposite(Half half) {
		switch (half) {
		case BOTTOM:
			return Half.TOP;
		case TOP:
			return Half.BOTTOM;
		default:
			return half;
		}
	}
	public static BlockFace getNext(BlockFace facing) {
		switch (facing) {
		case EAST:
			return BlockFace.SOUTH;
		case SOUTH:
			return BlockFace.WEST;
		case WEST:
			return BlockFace.NORTH;
		case NORTH:
			return BlockFace.EAST;
		default:
			return BlockFace.SELF;
		}
	}
	public static BlockFace getNext_NegY(BlockFace facing) {
		switch (facing) {
		case EAST:
			return BlockFace.NORTH;
		case NORTH:
			return BlockFace.WEST;
		case WEST:
			return BlockFace.SOUTH;
		case SOUTH:
			return BlockFace.EAST;
		default:
			return BlockFace.SELF;
		}
	}
	public static BlockFace getNext_PosX(BlockFace facing) {
		switch (facing) {
		case NORTH:
			return BlockFace.DOWN;
		case DOWN:
			return BlockFace.SOUTH;
		case SOUTH:
			return BlockFace.UP;
		case UP:
			return BlockFace.NORTH;
		default:
			return BlockFace.SELF;
		}
	}
	public static BlockFace getNext_NegX(BlockFace facing) {
		switch (facing) {
		case NORTH:
			return BlockFace.UP;
		case UP:
			return BlockFace.SOUTH;
		case SOUTH:
			return BlockFace.DOWN;
		case DOWN:
			return BlockFace.NORTH;
		default:
			return BlockFace.SELF;
		}
	}
	public static BlockFace getNext_PosZ(BlockFace facing) {
		switch (facing) {
		case EAST:
			return BlockFace.DOWN;
		case DOWN:
			return BlockFace.WEST;
		case WEST:
			return BlockFace.UP;
		case UP:
			return BlockFace.EAST;
		default:
			return BlockFace.SELF;
		}
	}
	public static BlockFace getNext_NegZ(BlockFace facing) {
		switch (facing) {
		case EAST:
			return BlockFace.UP;
		case UP:
			return BlockFace.WEST;
		case WEST:
			return BlockFace.DOWN;
		case DOWN:
			return BlockFace.EAST;
		default:
			return BlockFace.SELF;
		}
	}
}