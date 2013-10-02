/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author gnanda
 */

class Rest 
{
    public void resting()
    {
        System.out.println("REST");
    }
}

class Rest1 extends Rest
{
    public void resting()
    {
        super.resting();
        System.out.println("REST1");
    }
}

class Rest2 extends Rest1 
{

    public void resting()
    {
        super.resting();
        System.out.println("REST2");   
    }
}

class Resting extends Rest2
{

}

class Test3
{
    public void testing(Rest r1)
    {
        System.out.println("TEST3");
        //r1.resting();
    }
}

class Test1 extends Test3
{
    public void testing(Rest1 r1)
    {
        super.testing(r1);
        System.out.println("TEST1");
        //r1.resting();
    }
}

class Test2 extends Test1
{
    @Override
    public void testing(Rest1 r1)
    {
        super.testing(r1);
        System.out.println("TEST2");
        //r1.resting();
    }
}

class Testing extends Test2
{

}


public class Test {
    public static void main(String[] args) {
        
        //for(int i=0; i < 10; i++) {
        Testing t = new Testing();
        t.testing(new Resting());
//        Resting rt = new Resting();
//        rt.resting();
        //}
    }
}
