public class testeatinghabits_state_violet {

    public static void main(String[] args) {
       paces(new fsm1() );
    }

    public static void paces( common fsm ) {
        fsm.gotocircularinitialstatenode0();   // do nothing
        fsm.gotoReady();
        fsm.gotoReady();   // do nothing
        fsm.gotoEat();      
        fsm.gotoEat();
        fsm.gotoDrink();
        fsm.gotoEat();
        fsm.gotoPig();
        fsm.gotoEat();     // do nothing
        fsm.gotocircularinitialstatenode0();   // do nothing
        fsm.gotocircularfinalstatenode0();
        fsm.gotoEat();     // do nothing
        System.out.println(fsm.getName());
    }
}
	
