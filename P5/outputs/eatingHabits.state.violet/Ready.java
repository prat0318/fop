public class Ready implements state {

    public state gotocircularinitialstatenode0() {
        return this;
    }

    public state gotocircularfinalstatenode0() {
        return this;
    }

    public state gotoReady() {
        return this;
    }

    public state gotoDrink() {
        return new Drink();
    }

    public state gotoEat() {
        return new Eat();
    }

    public state gotoPig() {
        return this;
    }

    public String getName() {
        return "Ready";
    }
	
}
