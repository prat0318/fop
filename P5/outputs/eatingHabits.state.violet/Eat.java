public class Eat implements state {

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
        return this;
    }

    public state gotoPig() {
        return new Pig();
    }

    public String getName() {
        return "Eat";
    }
	
}
