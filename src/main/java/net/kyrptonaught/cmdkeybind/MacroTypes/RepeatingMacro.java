package net.kyrptonaught.cmdkeybind.MacroTypes;

import net.minecraft.client.network.ClientPlayerEntity;

public class RepeatingMacro extends BaseMacro {
    private final int delay;
    private long sysTimePressed = 0;
    private long currentTime;

    public RepeatingMacro(String key, String keyMod, String command, int delay) {
        super(key, keyMod, command);
        this.delay = delay;
    }

    @Override
    public void tick(long hndl, ClientPlayerEntity player, long currentTime) {
        this.currentTime = currentTime;
        if (isTriggered(hndl)) {
            if (canExecute())
                execute(player);
        } else {
            sysTimePressed = 0;
        }
    }

    private boolean canExecute() {
        if (delay > 0) {
            if (sysTimePressed == 0) return true;
            return currentTime - sysTimePressed > delay;
        }
        return true;
    }

    protected void execute(ClientPlayerEntity player) {
        sysTimePressed = currentTime;
        super.execute(player);
    }
}
