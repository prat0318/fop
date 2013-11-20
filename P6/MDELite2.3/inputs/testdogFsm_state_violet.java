public class testdogFsm_state_violet {

    public static void main(String[] args) {
         dog_test(new fsm1());
    }

    public static void dog_test(common FSM) {
	FSM.gotocircularinitialstatenode0();
	FSM.gotodogAwake();
	FSM.gotodogBowBow();
	FSM.gotodogHungry();
	FSM.gotodogBowBow();
	FSM.gotodogHappy(); //do nothing
	FSM.gotodogHungry();
	FSM.gotodogBowBow();
	FSM.gotodonPats();
	FSM.gotodogHappy();
	FSM.gotodogHungry(); //do nothing
	FSM.gotocircularfinalstatenode0();
	System.out.println(FSM.getName());
    }
}
	
