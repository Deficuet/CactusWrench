package io.github.Deficuet.CactusWrench.mainFunctions;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.block.data.type.Piston;
import java.util.Set;

public class DispenserOnTrigger implements Listener {
	private Plugin mainClass;
	private Set<Material> orientCycle = GeneralVar.setOrientCycleBlocks();
	private Set<Material> flippinBlocks = GeneralVar.setFlippinBlocks(false);
	public DispenserOnTrigger(Plugin mainClass) {
		mainClass.getServer().getPluginManager().registerEvents(this, mainClass);
		this.mainClass = mainClass;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onTrigger(BlockDispenseEvent event) {
		Block block = event.getBlock();
		if (block.getType() == Material.DISPENSER &&
			event.getItem().getType() == Material.CACTUS &&
			mainClass.getConfig().getBoolean("functions.dispenser")) {
			
			event.setCancelled(true);
			BlockFace disFacing = ((Directional) block.getBlockData()).getFacing();
			Location targetLoc = block.getLocation().add(disFacing.getModX(), disFacing.getModY(), disFacing.getModZ());
			Block target = targetLoc.getBlock();
			BlockData data = target.getBlockData();
			Material type = data.getMaterial();
			if (data instanceof Directional) {
				Directional nextFacing = (Directional) data;
				BlockFace original = nextFacing.getFacing();
				if (orientCycle.contains(data.getMaterial())) {
					if (original != BlockFace.DOWN) {
						nextFacing.setFacing(GeneralVar.getNext(original));
						target.setBlockData(nextFacing);
					}
					return;
				}
				if (((type == Material.DISPENSER || type == Material.DROPPER) &&
					(((Dispenser) data).isTriggered())) ||
					((type == Material.PISTON || type == Material.STICKY_PISTON) &&
					((Piston) data).isExtended())) {
					return;
				}
				if (flippinBlocks.contains(data.getMaterial())) {
					if (original == disFacing || original == disFacing.getOppositeFace()) {
						nextFacing.setFacing(original.getOppositeFace());
						target.setBlockData(nextFacing);
					} else {
						nextFacing.setFacing(getNext_Axis(disFacing, original));
						target.setBlockData(nextFacing);
					}
				}
				return;
			}
		}
	}
	private BlockFace getNext_Axis(BlockFace dispenser, BlockFace target) {
		switch (dispenser) {
		case EAST:
			return GeneralVar.getNext_PosX(target);
		case SOUTH:
			return GeneralVar.getNext_PosZ(target);
		case WEST:
			return GeneralVar.getNext_NegX(target);
		case NORTH:
			return GeneralVar.getNext_NegZ(target);
		case UP:
			return GeneralVar.getNext(target);
		case DOWN:
			return GeneralVar.getNext_NegY(target);
		default:
			return BlockFace.SELF;
		}
	}
}