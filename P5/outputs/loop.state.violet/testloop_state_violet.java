public class testloop_state_violet {

    public static void main(String[] args) {
       paces(new fsm1() );
    }

    public static void paces( common fsm ) {
        fsm.gotocircularinitialstatenode0();   // do nothing
        fsm.gotoloop();
        fsm.gotocircularfinalstatenode0();
        System.out.println(fsm.getName());
    }
}
	
