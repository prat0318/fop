public class Drink implements state {

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
        return this;
    }

    public state gotoEat() {
        return new Eat();
    }

    public state gotoPig() {
        return new Pig();
    }

    public String getName() {
        return "Drink";
    }
	
}
