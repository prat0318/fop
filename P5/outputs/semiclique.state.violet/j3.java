public class j3 implements state {

    public state gotoj1() {
        return this;
    }

    public state gotoj2() {
        return this;
    }

    public state gotoj1() {
        return new j1();
    }

    public state gotoj3() {
        return this;
    }

    public String getName() {
        return "j3";
    }
	
}

