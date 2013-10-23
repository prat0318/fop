public class loop implements state {

    public state gotocircularinitialstatenode0() {
        return this;
    }

    public state gotocircularfinalstatenode0() {
        return new circularfinalstatenode0();
    }

    public state gotoloop() {
        return new loop();
    }

    public String getName() {
        return "loop";
    }
	
}

