public class app_part3 {

    public static void main(String[] args) {
         dog_test(new fsm1());
         dog_test(new fsm2());
    }

    public static void dog_test(common FSM) {
	FSM.gotostart();
	FSM.gotoawake();
	FSM.gotobow();
	FSM.gotohungry();
	FSM.gotobow();
	FSM.gotohappy(); //do nothing
	FSM.gotohungry();
	FSM.gotobow();
	FSM.gotopat();
	FSM.gotohappy();
	FSM.gotohungry(); //do nothing
	FSM.gotostop();
	System.out.println(FSM.getName());
    }
}
	
