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
public void gotoloop() {
    currentState = currentState.gotoloop();
}
public String getName() {
    return currentState.getName();
}
}

