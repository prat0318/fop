public class dogAwake implements state {

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
        return new dogBowBow();
    }

    public state gotodonPats() {
        return this;
    }

    public state gotodogHappy() {
        return this;
    }

    public String getName() {
        return "dogAwake";
    }
	
}
