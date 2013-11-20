public class fsm1 implements common{
public state currentState;

public fsm1() {
    currentState = new circularinitialstatenode0();
}

public void gotocircularinitialstatenode0() {
    currentState = currentState.gotocircularinitialstatenode0();
}
public void gotos1() {
    currentState = currentState.gotos1();
}
public void gotos2() {
    currentState = currentState.gotos2();
}
public void gotocircularfinalstatenode0() {
    currentState = currentState.gotocircularfinalstatenode0();
}
public String getName() {
    return currentState.getName();
}
}

