public class Pig implements state {

    public state gotocircularinitialstatenode0() {
        return this;
    }

    public state gotocircularfinalstatenode0() {
        return new circularfinalstatenode0();
    }

    public state gotoReady() {
        return this;
    }

    public state gotoDrink() {
        return this;
    }

    public state gotoEat() {
        return this;
    }

    public state gotoPig() {
        return this;
    }

    public String getName() {
        return "Pig";
    }
	
}

