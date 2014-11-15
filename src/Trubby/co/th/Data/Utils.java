package Trubby.co.th.Data;

import net.minecraft.server.v1_7_R4.EntityFireworks;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;

public class Utils {

	public static void CreateFireworksExplosion(Location location,
			boolean flicker, boolean trail, int type, int[] colors,
			int[] fadeColors, int flightDuration) {
		net.minecraft.server.v1_7_R4.ItemStack item = new net.minecraft.server.v1_7_R4.ItemStack(
				Item.getById(401), 1, 0);

		NBTTagCompound tag = item.tag;
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		NBTTagCompound explTag = new NBTTagCompound();
		explTag.setByte("Flicker", (byte) (flicker ? 1 : 0));
		explTag.setByte("Trail", (byte) (trail ? 1 : 0));
		explTag.setByte("Type", (byte) type);
		explTag.setIntArray("Colors", colors);
		explTag.setIntArray("FadeColors", fadeColors);

		NBTTagCompound fwTag = new NBTTagCompound();
		fwTag.setByte("Flight", (byte) flightDuration);
		NBTTagList explList = new NBTTagList();
		explList.add(explTag);
		fwTag.set("Explosions", explList);
		tag.set("Fireworks", fwTag);

		item.tag = tag;

		EntityFireworks fireworks = new EntityFireworks(
				((CraftWorld) location.getWorld()).getHandle(),
				location.getX(), location.getY(), location.getZ(), item);
		((CraftWorld) location.getWorld()).getHandle().addEntity(fireworks);
		if (flightDuration == 0) {
			((CraftWorld) location.getWorld()).getHandle()
					.broadcastEntityEffect(fireworks, (byte) 17);
			fireworks.die();
		}
	}
}
