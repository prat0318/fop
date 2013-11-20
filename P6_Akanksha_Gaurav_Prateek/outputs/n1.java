public class n1 implements state {

    public state gotocircularinitialstatenode0() {
        return this;
    }

    public state goton1() {
        return this;
    }

    public state goton2() {
        return new n2();
    }

    public state goton3() {
        return this;
    }

    public state goton4() {
        return new n4();
    }

    public state gotocircularfinalstatenode0() {
        return this;
    }

    public String getName() {
        return "n1";
    }
	
}
