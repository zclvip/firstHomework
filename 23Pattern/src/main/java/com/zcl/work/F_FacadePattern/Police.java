package com.zcl.work.F_FacadePattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class Police {

    private LetterProcess letterProcess ;

    public void checkLetter(LetterProcess letterProcess){
        letterProcess = new LetterProcessImpl();
        System.out.println("Éó²éÐÅ¼þ¡£¡£¡£¡£");
    }
}
