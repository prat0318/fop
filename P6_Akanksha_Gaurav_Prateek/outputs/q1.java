public class q1 implements state {

    public state gotocircularinitialstatenode0() {
        return new circularinitialstatenode0();
    }

    public state gotoq1() {
        return this;
    }

    public state gotoq2() {
        return this;
    }

    public state gotocircularfinalstatenode0() {
        return new circularfinalstatenode0();
    }

    public String getName() {
        return "q1";
    }
	
}
