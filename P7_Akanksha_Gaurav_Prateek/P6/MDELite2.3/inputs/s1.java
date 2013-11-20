public class s1 implements state {

    public state gotocircularinitialstatenode0() {
        return this;
    }

    public state gotos1() {
        return this;
    }

    public state gotos2() {
        return new s2();
    }

    public state gotocircularfinalstatenode0() {
        return new circularfinalstatenode0();
    }

    public String getName() {
        return "s1";
    }
	
}
