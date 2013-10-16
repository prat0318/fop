public class app_part3 {

    public static void main(String[] args) {
         dog_test(new fsm1());
    }

    public static void dog_test(common FSM) {
	FSM.gotocircularinitialstatenode0();
	FSM.gotodogAwake();
	FSM.gotodogBow-Bow();
	FSM.gotodogHungry();
	FSM.gotodogBow-Bow();
	FSM.gotodogHappy(); //do nothing
	FSM.gotodogHungry();
	FSM.gotodogBow-Bow();
	FSM.gotodonPats();
	FSM.gotodogHappy();
	FSM.gotodogHungry(); //do nothing
	FSM.gotocircularfinalstatenode0();
	System.out.println(FSM.getName());
    }
}
	
