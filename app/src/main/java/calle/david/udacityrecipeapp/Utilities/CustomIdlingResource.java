package calle.david.udacityrecipeapp.Utilities;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomIdlingResource implements android.support.test.espresso.IdlingResource {
    private final String mResourceName;
    private final AtomicInteger counter = new AtomicInteger(0);
    private volatile ResourceCallback resourceCallback;

    public CustomIdlingResource(String resourceName){
        mResourceName = resourceName;
    }

    @Override
    public boolean isIdleNow() {
        return counter.get()==0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;

    }

    @Override
    public String getName() {
        return mResourceName;
    }
//    public void setToZero(){counter.set(0);}
//    public void increment(){
//        counter.getAndIncrement();
//    }
//    public  void decrement(){
//        int counterVal = counter.decrementAndGet();
//        if(counterVal == 0){
//            if(resourceCallback != null)resourceCallback.onTransitionToIdle();
//        }
//        if(counterVal<0){
//            throw new IllegalArgumentException("counter is negative check decrements");
//        }
//    }
    public void Lock(){counter.set(1);}
    public void Unlock(){counter.set(0);}
}
