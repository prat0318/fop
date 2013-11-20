public class donPats implements state {

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
        return this;
    }

    public state gotodonPats() {
        return this;
    }

    public state gotodogHappy() {
        return new dogHappy();
    }

    public String getName() {
        return "donPats";
    }
	
}
