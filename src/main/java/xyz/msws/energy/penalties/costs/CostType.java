package xyz.msws.energy.penalties.costs;

public enum CostType {
    WALKING(500), SPRINTING(230), JUMPING(220), SPRINT_JUMP(180), SWIMMING(120), DAMAGING(200), HURT(160), ARCHERY(80),
    THROWING(200), CROUCH_WALK(450), CROUCH_JUMP(400), ELYTRA(350), BLOCK_BREAK(210), BLOCK_PLACE(180), COOKING(90),
    CRAFTING(80), ROWING(170), RIDING(250), FALLING(600), REGEN(100);

    private double cost;

    CostType(int inverse) {
        this.cost = 1.0 / inverse;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double inverse) {
        this.cost = 1.0 / inverse;
    }

    public void setRawCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return this.name() + ":" + (1.0 / cost);
    }

    public static CostType fromString(String s) {
        if (!s.contains(":"))
            return CostType.valueOf(s.toUpperCase());
        String name = s.substring(0, s.indexOf(":"));
        double cost = Double.parseDouble(s.substring(s.indexOf(":") + 1));
        CostType type = CostType.valueOf(name);
        type.setCost(cost);
        return type;
    }
}
