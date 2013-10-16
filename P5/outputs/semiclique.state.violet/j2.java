public class j2 implements state {

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
        return new j3();
    }

    public String getName() {
        return "j2";
    }
	
}
