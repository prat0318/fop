public class fsm1 implements common{
public state currentState;

public fsm1() {
    currentState = new circularinitialstatenode0();
}

public void gotocircularinitialstatenode0() {
    currentState = currentState.gotocircularinitialstatenode0();
}
public void goton1() {
    currentState = currentState.goton1();
}
public void goton2() {
    currentState = currentState.goton2();
}
public void goton3() {
    currentState = currentState.goton3();
}
public void goton4() {
    currentState = currentState.goton4();
}
public void gotocircularfinalstatenode0() {
    currentState = currentState.gotocircularfinalstatenode0();
}
public String getName() {
    return currentState.getName();
}
}

