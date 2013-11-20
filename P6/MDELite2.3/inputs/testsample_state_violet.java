public class testsample_state_violet {

    public static void main(String[] args) {
         sample_test(new fsm1());
    }

    public static void sample_test(common FSM) {
	FSM.gotocircularinitialstatenode0();
	FSM.gotoq1(); 
	FSM.gotocircularfinalstatenode0();
	System.out.println(FSM.getName());
    }
}
