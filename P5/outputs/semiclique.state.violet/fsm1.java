public class fsm1 implements common{
public state currentState;

public fsm1() {
    currentState = new circularinitialstatenode0();
}

public void gotoj1() {
    currentState = currentState.gotoj1();
}
public void gotoj2() {
    currentState = currentState.gotoj2();
}
public void gotoj1() {
    currentState = currentState.gotoj1();
}
public void gotoj3() {
    currentState = currentState.gotoj3();
}
public String getName() {
    return currentState.getName();
}
}

