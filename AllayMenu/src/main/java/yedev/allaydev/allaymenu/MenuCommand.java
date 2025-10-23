package yedev.allaydev.allaymenu;

import org.allaymc.api.command.Command;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.permission.PermissionGroups;
import org.allaymc.api.server.Server;
public class MenuCommand extends Command {
    public MenuCommand() {
        super("menu", "custom menu for allay");
        getPermissions().forEach(PermissionGroups.MEMBER::addPermission);

    }
    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                .msg("jsonfilename(with.json)")
                .optional()

                .exec(context -> {
                    String filename=context.getResult(0);
                    if(filename.isEmpty()){
                        filename="menu.json";
                    }

                    if(context.getSender().isPlayer()){
                        AllayMenu.jsontoform((EntityPlayer) context.getSender(),filename);
                    }

                    return context.success();
                });
    }

}
