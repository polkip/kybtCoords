package dev.kybt.kcoords.commands;

import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.gui.ConfigGUI;
import dev.kybt.kcoords.gui.PositionGUI;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class CommandKybtCoords implements ICommand {

    private final List<String> aliases;

    public CommandKybtCoords() {
        aliases = new ArrayList<>();
        aliases.add("coordinates");
        aliases.add("coords");
        aliases.add("kcoords");
    }

    @Override
    public String getCommandName() {
        return "kybtcoords";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/kybtcoords <toggle:config>";
    }

    @Override
    public List<String> getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("toggle")) {
                KybtCoords.isEnabled = !KybtCoords.isEnabled;
            } else if(args[0].equalsIgnoreCase("config")) {
                new ConfigGUI().display();
            } else if(args[0].equalsIgnoreCase("position")) {
                new PositionGUI().display();
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] strings, BlockPos blockPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
