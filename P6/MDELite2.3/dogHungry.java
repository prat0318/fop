public class dogHungry implements state {

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
        return this;
    }

    public state gotodogBowBow() {
        return new dogBowBow();
    }

    public state gotodonPats() {
        return new donPats();
    }

    public state gotodogHappy() {
        return this;
    }

    public String getName() {
        return "dogHungry";
    }
	
}
