package jlox;
import jlox.A;
import jlox.B;

class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        A.sayA(); // because its static
        B b= new B(); // because it's not static
        b.sayB();

        for (int i = 0; i < args.length; i++) {
            System.out.println("arg " + i + ": " + args[i]);
        }
    }
}
