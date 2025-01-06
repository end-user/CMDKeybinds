package net.kyrptonaught.cmdkeybind.config;

import blue.endless.jankson.Comment;
import net.kyrptonaught.cmdkeybind.CmdKeybindMod;
import net.kyrptonaught.cmdkeybind.MacroTypes.BaseMacro;
import net.kyrptonaught.kyrptconfig.config.AbstractConfigFile;
import net.kyrptonaught.kyrptconfig.config.CustomSerializable;
import net.kyrptonaught.kyrptconfig.keybinding.CustomKeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ConfigOptions implements AbstractConfigFile {

    public boolean enabled = true;

    @Comment("Keybinding to open the Macro editing screen")
    public CustomKeyBinding openMacroScreenKeybind = CustomKeyBinding.configDefault(CmdKeybindMod.MOD_ID, InputUtil.UNKNOWN_KEY.getTranslationKey());

    public List<ConfigMacro> macros = new ArrayList<>();

    public static class ConfigMacro implements CustomSerializable {
        @Comment("Macro keybinding")
        public String keyName;
        @Comment("Key modifier")
        public String keyModName;
        @Comment("Command to execute")
        public String command;
        @Comment("Type of Macro. Delayed, Repeating, RunNTimes, SingleUse, DisplayOnly")
        public BaseMacro.MacroType macroType;
        @Comment("Delay(Milliseconds) used for the Delayed/Repeating/RunNTimes macro.")
        public int delay;
        @Comment("Number of repeats used for the RunNTimes macro.")
        public int repetitions;


        public ConfigMacro() {
            this.keyName = InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_KP_0).getTranslationKey();
            this.keyModName = InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_UNKNOWN).getTranslationKey();
            this.command = "/say Command Macros!";
            this.macroType = BaseMacro.MacroType.SingleUse;
            this.delay = 0;
            this.repetitions = 1;
        }

        @Override
        public boolean shouldSerializeField(String field) {
            if (field.equals("delay"))
                return macroType.isDelayApplicable();
            if (field.equals("repetitions"))
                return macroType.isRepetitionsApplicable();
            return true;
        }
    }
}
