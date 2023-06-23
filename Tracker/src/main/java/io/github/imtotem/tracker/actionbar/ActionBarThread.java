package io.github.imtotem.tracker.actionbar;

import io.github.imtotem.tracker.Tracker;
import io.github.imtotem.tracker.module.CalculateDirection;
import io.github.imtotem.tracker.module.CalculateDistance;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static net.kyori.adventure.text.Component.text;

public class ActionBarThread extends BukkitRunnable
{
	@Override
	public void run()
	{
		for ( Player player : Bukkit.getOnlinePlayers() )
		{
			String targeting = Tracker.target.getTarget().get(player.getName());
			Player targetPlayer = Bukkit.getPlayer(targeting);

			String name = player.getName();

			final Tracker instance = Tracker.getPlugin(Tracker.class);
			if ( instance.toggleManager.getConfig().getBoolean(name) ) {
				Location target = player.getLocation();

				String direction = CalculateDirection.getDirection(player, target);

				if ( targetPlayer != null && targetPlayer.isOnline() && !player.getName().equalsIgnoreCase(targeting) ) {
					name = targetPlayer.getName();
					target = targetPlayer.getLocation();

					direction = CalculateDirection.getDirection(player.getEyeLocation(), target);
				}
				else if ( Tracker.getPlugin(Tracker.class).getConfig().getKeys(false).contains(targeting.toLowerCase()) ) {
					name = targeting.toLowerCase();
					target = Tracker.getPlugin(Tracker.class).getConfig().getLocation(targeting.toLowerCase());

					direction = CalculateDirection.getDirection(player.getEyeLocation(), target);
				}
				else if ( targeting.equalsIgnoreCase("dLoc") ) {
					name = "죽은 위치";
					target = Tracker.deadLocation.get(player.getName());

					direction = CalculateDirection.getDirection(player.getEyeLocation(), target);
				}

				int[] distanceAHeight = CalculateDistance.getDistance(player, target);
				int distance = distanceAHeight[0];
				int height = distanceAHeight[1];

				TextComponent message = text("Tracking: ", NamedTextColor.WHITE).append(text(name, NamedTextColor.GREEN, TextDecoration.BOLD))
						.append(text(" - World: ", NamedTextColor.WHITE)).append(text(target.getWorld().getName(), NamedTextColor.GREEN, TextDecoration.BOLD))
						.append(text(" - Distance: ", NamedTextColor.WHITE)).append(text(distance+"m "+direction, NamedTextColor.GREEN, TextDecoration.BOLD))
						.append(text(" - Height: ", NamedTextColor.WHITE)).append(text(height+"m", NamedTextColor.GREEN, TextDecoration.BOLD));

				Tracker.actionBar.sendActionBar(player, message);
			}
		}
	}
}
