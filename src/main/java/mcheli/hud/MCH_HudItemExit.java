package mcheli.hud;

public class MCH_HudItemExit extends MCH_HudItem {

    public MCH_HudItemExit(int fileLine) {
        super(fileLine);
    }

    public void execute() {
        super.parent.exit = true;
    }
}
