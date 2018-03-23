package com.hunter.helloandroid.module.multi;

import com.yolanda.multiasynctask.MultiAsynctask;

/**
 * Created in Aug 3, 2015 2:17:26 PM
 *
 * @author YOLANDA
 */
public class LongAsynctask extends MultiAsynctask<Paramer, Integer, Resulter> {
    @Override
    public void onPrepare() {
        super.onPrepare();
        System.out.println("......onPrepare.......");
    }

    @Override
    public Resulter onTask(Paramer... params) {
        System.out.println("......onTask.......params:" + params);

        Paramer paramer = params[0];
        Logger.i("onTask打印：" + "what:" + paramer.getWhat() + ";value:" + paramer.getValue());
        for (int i = 0; i < 5; i++) {
            paramer.setValue(paramer.getValue() + 1);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Updater updater = new Updater(paramer.getWhat(), paramer.getValue());
            postUpdate(i);
        }
        return new Resulter(paramer.getWhat(), paramer.getValue());
    }

    @Override
    public void onUpdate(Integer update) {
        System.out.println("......onUpdate.......update:" + update);

//        Logger.i("onUpdate打印：" + "what:" + update.getWhat() + ";value:" + update.getValue());
    }

    @Override
    public void onResult(Resulter result) {
        System.out.println("......onResult.......result:" + result);
        Logger.i("onResult打印：" + "what:" + result.getWhat() + ";value:" + result.getValue());
    }




}
