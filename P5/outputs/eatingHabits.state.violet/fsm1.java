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
public void gotoReady() {
    currentState = currentState.gotoReady();
}
public void gotoDrink() {
    currentState = currentState.gotoDrink();
}
public void gotoEat() {
    currentState = currentState.gotoEat();
}
public void gotoPig() {
    currentState = currentState.gotoPig();
}
public String getName() {
    return currentState.getName();
}
}

