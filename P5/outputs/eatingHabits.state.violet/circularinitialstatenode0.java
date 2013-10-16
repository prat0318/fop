public class circularinitialstatenode0 implements state {

    public state gotocircularinitialstatenode0() {
        return this;
    }

    public state gotocircularfinalstatenode0() {
        return this;
    }

    public state gotoReady() {
        return new Ready();
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
        return "circularinitialstatenode0";
    }
	
}
