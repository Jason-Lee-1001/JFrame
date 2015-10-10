package com.example;

/**
 * Created by Jason
 */
public class Test {

    public static void main(String[] args) {
        MyClass classA = new MyClass();
        System.out.println(classA.getI());
        change(classA);
        System.out.println(classA.getI());
    }

    public static void change(MyClass myClass){
        myClass.setI(30);
        System.out.println(myClass.getI());
    }

}
