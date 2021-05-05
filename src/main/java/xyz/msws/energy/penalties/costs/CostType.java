package xyz.msws.energy.penalties.costs;

public enum CostType {
    WALKING(200), SPRINTING(130), JUMPING(130), SPRINT_JUMP(70), SWIMMING(75), DAMAGING(125), HURT(175), CROUCH_WALK(180), CROUCH_JUMP(130),
    ELYTRA(170), BLOCK_BREAK(128), BLOCK_PLACE(100), COOKING(50), CRAFTING(64), ROWING(150), RIDING(155), FALLING(500);

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
