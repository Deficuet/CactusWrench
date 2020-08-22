package io.github.Deficuet.CactusWrench.mainFunctions;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventPriority;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.Material;
import org.bukkit.Tag;
import java.util.Set;

public class MainHandOnUse implements Listener {
	private Plugin mainClass;
	private Set<Material> orientCycle = GeneralVar.setOrientCycleBlocks();
	private Set<Material> flippinBlocks = GeneralVar.setFlippinBlocks(true);
	private Set<Material> slabsClass = Tag.SLABS.getValues();
	private Set<Material> stairsClass = Tag.STAIRS.getValues();

	public MainHandOnUse(Plugin mainClass) {
		mainClass.getServer().getPluginManager().registerEvents(this, mainClass);
		this.mainClass = mainClass;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK &&
			this.mainClass.getConfig().getBoolean("functions.main-hand") &&
			player.getInventory().getItemInMainHand().getType() == Material.CACTUS && !(player.isSneaking())) {
			
			if (orientCycle.contains(block.getType())) {
				Directional nextFacing = (Directional) block.getBlockData();
				BlockFace original = nextFacing.getFacing();
				if (original != BlockFace.DOWN) {
					nextFacing.setFacing(GeneralVar.getNext(original));
					block.setBlockData(nextFacing, false);
					event.setCancelled(true);
				}
				return;
			}
			if (flippinBlocks.contains(block.getType())) {
				if (GeneralVar.pistonSet.contains(block.getType())) {
					if (((Piston) block.getBlockData()).isExtended()) {
						return;
					}
				}
				Directional backFacing = (Directional) block.getBlockData();
				backFacing.setFacing(backFacing.getFacing().getOppositeFace());
				block.setBlockData(backFacing, false);
				event.setCancelled(true);
				return;
			}
			if (slabsClass.contains(block.getType())) {
				Slab oppType = (Slab) block.getBlockData();
				Type oriType = oppType.getType();
				if (oriType != Type.DOUBLE) {
					oppType.setType(GeneralVar.getOpposite(oriType));
					block.setBlockData(oppType, false);
					event.setCancelled(true);
				}
				return;
			}
			if (stairsClass.contains(block.getType())) {
				BlockFace clickedFace = event.getBlockFace();
				Stairs oppStatus = (Stairs) block.getBlockData();
				if (GeneralVar.surroundFace.contains(clickedFace)) {
					oppStatus.setFacing(GeneralVar.getNext(oppStatus.getFacing()));
					block.setBlockData(oppStatus, true);
					event.setCancelled(true);
				} else if (GeneralVar.axisYFace.contains(clickedFace)) {
					oppStatus.setHalf(GeneralVar.getOpposite(oppStatus.getHalf()));
					block.setBlockData(oppStatus, true);
					event.setCancelled(true);
				}
				return;
			}
		}
	}
}