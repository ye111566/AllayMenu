package yedev.allaydev.allaymenu;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.allaymc.api.form.element.ImageData;
import org.allaymc.api.plugin.Plugin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import java.util.Objects;
import org.allaymc.api.form.Forms;
import org.allaymc.api.registry.Registries;
import org.allaymc.api.server.Server;
import org.allaymc.api.utils.TextFormat;
import java.util.ArrayList;
import java.util.List;



@Slf4j
public class AllayMenu extends Plugin {

    @Getter
    @Setter
    public static class ButtonConfig {
        private String key;       // 新增：存储按钮标识
        private String text;
        private String cmd;
        private String mode;
        private String icon;

        public ButtonConfig() {}

        @Override
        public String toString() {
            return "ButtonConfig{" +
                    "key='" + key + '\'' +
                    ", text='" + text + '\'' +
                    ", cmd='" + cmd + '\'' +
                    ", mode='" + mode + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }

    public static void jsontoform(EntityPlayer player, String filename) {
        String filePath = "./plugins/menu/" + filename;
        try {
            FileReader reader = new FileReader(filePath);
            Gson gson = new Gson();

            // 使用LinkedHashMap保持顺序，然后转换为有序列表
            Type mapType = new TypeToken<Map<String, ButtonConfig>>() {}.getType();
            Map<String, ButtonConfig> orderedMap = gson.fromJson(reader, mapType);

            // 将Map转换为有序的ArrayList
            List<ButtonConfig> buttonsList = new ArrayList<>();
            for (Map.Entry<String, ButtonConfig> entry : orderedMap.entrySet()) {
                ButtonConfig config = entry.getValue();
                config.setKey(entry.getKey()); // 设置按钮标识
                buttonsList.add(config);
            }

            var simpleform = Forms.simple();
            simpleform.title("菜单").content("选择一个按钮");

            for (ButtonConfig config : buttonsList) {
                var eachbutton = simpleform.button(config.getKey()); // 使用key作为按钮文本

                eachbutton.onClick(button -> {
                    player.sendMessage(config.getText());
                    String oricmd=config.getCmd();
                    String cmd=oricmd.replace("$sender.name$",'"'+player.getOriginName()+'"');
                    System.out.println("debug: oricmd is");
                    System.out.println(oricmd);
                    System.out.println("debug: cmd is");
                    System.out.println(cmd);
                    if (Objects.equals(config.getMode(), "server")) {
                        Registries.COMMANDS.execute(Server.getInstance(), cmd);
                    } else if (Objects.equals(config.getMode(), "player")) {
                        Registries.COMMANDS.execute(player,cmd );
                    } else {
                        player.sendMessage(TextFormat.RED + "无效的按钮指令类型");
                    }
                });

                if (config.getIcon() != null) {
                    ImageData image = new ImageData(ImageData.ImageType.PATH, config.getIcon());
                    eachbutton.setImage(image);
                }

                // 调试输出
                System.out.println("按钮显示文本: " + config.getKey());
                System.out.println(" - 回复文本: " + config.getText());
                System.out.println(" - 执行命令: " + config.getCmd());
                System.out.println(" - 执行模式: " + config.getMode());
                System.out.println(" - 图标路径: " + (config.getIcon() != null ? config.getIcon() : "(未设置)"));
                System.out.println();
            }

            simpleform.sendTo(player);

        } catch (Exception e) {
            log.error("处理JSON文件错误:", e);
        }
    }
    static AllayMenu instance;
    @Override
    public void onLoad() {
        log.info("JavaPluginTemplate loaded!");
        instance=this;
    }

    @Override
    public void onEnable() {
        Registries.COMMANDS.register(new MenuCommand());
        Server.getInstance().getEventBus().registerListener(new TheEventListener());
        log.info("JavaPluginTemplate enabled!");
    }

    @Override
    public void onDisable() {
        log.info("JavaPluginTemplate disabled!");
    }
}