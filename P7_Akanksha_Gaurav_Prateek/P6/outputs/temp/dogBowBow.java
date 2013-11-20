public class dogBowBow implements state {

    public state gotocircularinitialstatenode0() {
        return this;
    }

    public state gotocircularfinalstatenode0() {
        return this;
    }

    public state gotodogAwake() {
        return this;
    }

    public state gotodogHungry() {
        return new dogHungry();
    }

    public state gotodogBowBow() {
        return this;
    }

    public state gotodonPats() {
        return new donPats();
    }

    public state gotodogHappy() {
        return this;
    }

    public String getName() {
        return "dogBowBow";
    }
	
}
