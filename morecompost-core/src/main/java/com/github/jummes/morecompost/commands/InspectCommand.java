package com.github.jummes.morecompost.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.github.jummes.morecompost.core.MoreCompost;
import com.github.jummes.morecompost.utils.MessageUtils;

public class InspectCommand extends AbstractCommand {

	public InspectCommand(CommandSender sender, String subCommand, String[] arguments, boolean isSenderPlayer) {
		super(sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		Player p = (Player) sender;
		
		String name = getOwner(p.rayTraceBlocks(20).getHitBlock()) != null? getOwner(p.rayTraceBlocks(20).getHitBlock()).getName(): "Nobody :(";

		p.sendMessage(MessageUtils.color(
				"&6The owner of this composter is: &c" + name));

	}

	/**
	 * Returns the owner of this composter, returns null if composter has no owner
	 * 
	 * @param b composter to analize
	 * @return owner of the composter, null if he hasn't one
	 */
	private Player getOwner(Block b) {
		try {
			return Bukkit.getOfflinePlayer((UUID) b.getMetadata("owner").stream()
					.filter(metadata -> metadata.getOwningPlugin() instanceof MoreCompost).findFirst().get().value())
					.getPlayer();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

	@Override
	protected Permission getPermission() {
		return new Permission("morecompost.commands.inspect");
	}

}
