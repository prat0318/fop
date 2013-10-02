public class app {

    public static void main(String[] args) {
       paces(new fsm1() );
       paces(new fsm2() );
    }

    public static void paces( common fsm ) {
        fsm.gotostart();   // do nothing
        fsm.gotoready();
        fsm.gotoready();   // do nothing
        fsm.gotoeat();      
        fsm.gotoeat();
        fsm.gotodrink();
        fsm.gotoeat();
        fsm.gotofamily();
        fsm.gotoeat();     // do nothing
        fsm.gotostart();   // do nothing
        fsm.gotostop();
        fsm.gotoeat();     // do nothing
        System.out.println(fsm.getName());
    }
}
	
