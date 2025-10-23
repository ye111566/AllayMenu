
# Menu Plugin

一个用于 Minecraft Bedrock Edition 服务端（如 LiteLoaderBDS）的图形菜单插件。此插件允许玩家通过简单的右键点击钟表来打开可高度自定义的图形用户界面（GUI）菜单，并执行预定义的命令。

A graphical menu plugin for Minecraft Bedrock Edition servers (e.g., LiteLoaderBDS). This plugin allows players to open a highly customizable graphical user interface (GUI) menu by simply right-clicking a clock, and then execute predefined commands.

---

## 安装方法 | Installation

1.  将下载的 `*.jar` 文件放入 BDS 服务端的 `plugins` 文件夹中。
    Place the downloaded `*.jar` file into the `plugins` folder of your BDS server.

2.  在 `plugins` 文件夹内创建一个名为 `menu` 的新文件夹。
    Create a new folder named `menu` inside the `plugins` folder.

3.  在 `menu` 文件夹内，创建您的菜单配置文件，例如 `menu.json`。这个文件将作为右键点击钟时打开的主菜单。
    Inside the `menu` folder, create your menu configuration file, e.g., `menu.json`. This file will serve as the main menu opened when right-clicking the clock.

## 使用方法 | Usage

1.  **打开菜单 | Opening a Menu**
    *   在游戏中手持钟并对准地面右键点击，即可打开在 `menu` 文件夹中定义的默认菜单（通常是 `menu.json`）。
        Hold a clock in the game, aim at the ground, and right-click to open the default menu (usually `menu.json`) defined in the `menu` folder.

2.  **使用命令 | Using Commands**
    *   作为玩家，您也可以使用命令来打开特定的菜单文件：
        As a player, you can also use commands to open specific menu files:
        bash
        menu <filename.json>

    *   例如，要打开 `shop.json`，请输入：
        For example, to open `shop.json`, type:
        bash
        menu shop.json


## 配置说明 | Configuration

菜单的功能通过编辑 `menu` 文件夹内的 JSON 文件来配置。
The menu's functionality is configured by editing JSON files within the `menu` folder.

### 配置示例 | Example Configuration

下面是一个 `menu.json` 的示例配置：
Below is an example configuration for `menu.json`:

json
{
    "Button Display Text 1": {
        "text": "Message text 1 after clicking the button",
        "cmd": "msg @s hi",
        "mode": "player",
        "icon": "textures/ui/op"
    },
    "Button Display Text 2": {
        "text": "Message text 2 after clicking the button",
        "cmd": "title @a title hi",
        "mode": "server"
    },
    "Button Display Text 3": {
        "text": "Message text 3 after clicking the button",
        "cmd": "title @a title hi sender.name",
        "mode": "server"
    },
    "Open shop.json": {
        "text": "Opened successfully",
        "cmd": "menu shop.json",
        "mode": "player"
    }
}


### 参数详解 | Parameter Details

每个按钮都是一个 JSON 对象，包含以下属性：
Each button is a JSON object with the following properties:

*   `"按钮显示文本" | "Button Display Text"` (例如 E.g., `"Button Display Text 1"`): **必填 | Required**
    *   **描述**: 按钮在菜单中显示的文字。
    *   **Description**: The text displayed on the button in the menu.

*   `text`: **必填 | Required**
    *   **描述**: 玩家点击按钮后，聊天栏中显示的反馈信息。
    *   **Description**: The feedback message displayed in the chat when a player clicks the button.

*   `cmd`: **必填 | Required**
    *   **描述**: 点击按钮后要执行的 Minecraft 命令。支持变量（如 `$sender.name$` 表示点击者的名字）。
    *   **Description**: The Minecraft command to be executed when the button is clicked. Supports variables (e.g., `$sender.name$` for the clicker's name).

*   `mode`: **必填 | Required**
    *   **描述**: 命令的执行身份。
    *   **Description**: The identity under which the command is executed.
    *   **可选值 | Options**:
        *   `"player"`: 以点击按钮的玩家身份执行命令。适用于如 `msg @s hi` 等个人命令。
        *   `"server"`: 以服务器控制台身份执行命令。拥有更高权限，适用于如 `title @a title hi` 等全局命令。

*   `icon`: *可选 | Optional*
    *   **描述**: 按钮旁边显示的图标路径。路径相对于游戏资源包。如果不设置，则使用默认图标。
    *   **Description**: The path to the icon image displayed next to the button. The path is relative to the game's resource pack. If not set, a default icon is used.

## 注意事项 | Notes

*   确保 JSON 文件的格式正确，可以使用 [JSON 验证工具](https://jsonlint.com/) 进行检查。
*   Ensure your JSON file is correctly formatted. You can use a [JSON Validator](https://jsonlint.com/).
*   在 `mode` 设置为 `"player"` 时，玩家需要拥有相应命令的执行权限。
*   When `mode` is set to `"player"`, the player must have the necessary permissions to execute the corresponding command.
*   `"server"` 模式通常权限更高，但请谨慎使用高危命令。
*   The `"server"` mode generally has higher privileges, but use it cautiously for high-risk commands.
