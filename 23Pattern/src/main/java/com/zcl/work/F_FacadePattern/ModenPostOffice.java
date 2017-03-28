package com.zcl.work.F_FacadePattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class ModenPostOffice {
    private LetterProcess letterProcess = new LetterProcessImpl();
    Police police = new Police();

    public void sendLetter(String context,String address){
        letterProcess.writeContext(context);
        letterProcess.fillEnvelope(address);

        police.checkLetter(letterProcess);

        letterProcess.letterInotoEnvelope();
        letterProcess.sendLetter();
    }
}
