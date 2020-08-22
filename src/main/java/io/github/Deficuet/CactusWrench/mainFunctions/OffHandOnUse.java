package io.github.Deficuet.CactusWrench.mainFunctions;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.FaceAttachable.AttachedFace;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.Material;
import org.bukkit.Tag;

public class OffHandOnUse implements Listener {
	private Plugin mainClass;
	public OffHandOnUse(Plugin mainClass) {
		mainClass.getServer().getPluginManager().registerEvents(this, mainClass);
		this.mainClass = mainClass;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlaceBlock(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (player.getInventory().getItemInOffHand().getType() == Material.CACTUS &&
			mainClass.getConfig().getBoolean("functions.off-hand")) {
			Block block = event.getBlockPlaced();
			
			if (event.canBuild()) {
				BlockData data = block.getBlockData();
				if (data instanceof Directional) {
					if ((data.getMaterial() == Material.LEVER &&
						((FaceAttachable) data).getAttachedFace() == AttachedFace.WALL) ||
						Tag.BEDS.getValues().contains(data.getMaterial())) {
						return;
					}
					Directional oppFacing = (Directional) data;
					if (oppFacing.getFacing() != BlockFace.DOWN) {
						oppFacing.setFacing(oppFacing.getFacing().getOppositeFace());
					}
					block.setBlockData(oppFacing);
					return;
				}
				if (data instanceof Slab) {
					Slab status = (Slab) data;
					Type original = status.getType();
					if (original == Type.BOTTOM) {
						status.setType(GeneralVar.getOpposite(original));
						block.setBlockData(status);
					}
					return;
				}
			}
		}
	}
}