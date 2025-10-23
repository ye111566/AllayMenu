package yedev.allaydev.allaymenu;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.server.Server;
import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.player.PlayerInteractBlockEvent;

import java.util.ArrayList;
import java.util.List;

public class TheEventListener {
    public static List<String> cache = new ArrayList<>();

    @EventHandler
    private void onPlayerInteractBlock(PlayerInteractBlockEvent event) {
        String playername=event.getPlayer().getOriginName();
        if(playername.isEmpty()){
            return;
        }
        if(event.getPlayer().getItemInHand().getItemType().getIdentifier().toString().equals("minecraft:clock")){
            System.out.println("检测到手持钟右键");
            if(!cache.contains(playername)){
                AllayMenu.jsontoform(event.getPlayer(),"menu.json");
                cache.add(playername);

                Server.getInstance().getScheduler().scheduleDelayed(AllayMenu.instance,()->{
                    TheEventListener.cache.remove(playername);
                    return true;
                },20);

            }
        }


    }
}
