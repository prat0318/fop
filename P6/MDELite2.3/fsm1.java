public class fsm1 implements common{
public state currentState;

public fsm1() {
    currentState = new circularinitialstatenode0();
}

public void gotocircularinitialstatenode0() {
    currentState = currentState.gotocircularinitialstatenode0();
}
public void gotocircularfinalstatenode0() {
    currentState = currentState.gotocircularfinalstatenode0();
}
public void gotodogAwake() {
    currentState = currentState.gotodogAwake();
}
public void gotodogHungry() {
    currentState = currentState.gotodogHungry();
}
public void gotodogBowBow() {
    currentState = currentState.gotodogBowBow();
}
public void gotodonPats() {
    currentState = currentState.gotodonPats();
}
public void gotodogHappy() {
    currentState = currentState.gotodogHappy();
}
public String getName() {
    return currentState.getName();
}
}

