public class testfsm20_state_violet {

    public static void main(String[] args) {
         fsm20_test(new fsm1());
    }

    public static void fsm20_test(common FSM) {
	FSM.gotocircularinitialstatenode0();
	FSM.gotostatenode0(); 
	FSM.gotostatenode1(); 
	FSM.gotocircularfinalstatenode0();
	System.out.println(FSM.getName());
    }
}
