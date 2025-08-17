import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClicker implements NativeKeyListener{
    private static Robot bot;
    public static volatile boolean running = false;
    static {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try{
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex){
            System.out.println("Error on Native Hook registering");
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new AutoClicker());

        try {
            bot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
    public static void delay(long x){
        try{
            Thread.sleep(x);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public static void mouseClick(){
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    public static void autoClickerStart(long clickAmount, long clickDelay, long beforeStartDelay){
        if(running){
            return;
        }
        running = true;
        Thread clickerThread = new Thread(() -> {
            try{
                Thread.sleep(beforeStartDelay);
            } catch (InterruptedException e){
                return;
            }
            for(int i = 0; i < clickAmount; i++){
                if(!running){
                    break;
                }
                mouseClick();
                try {
                    Thread.sleep(clickDelay);
                }
                catch (InterruptedException e){
                    break;
                }
            }
            running = false;
        });
        clickerThread.start();
    }
    public static void infAutoClickerStart(long clickDelay, long beforeStartDelay){
        if(running){
            return;
        }
        running = true;
        Thread clickerThread = new Thread(() -> {
            try{
                Thread.sleep(beforeStartDelay);
            } catch (InterruptedException e){
                return;
            }
            while (running){
                mouseClick();
                try {
                    Thread.sleep(clickDelay);
                } catch(InterruptedException e){
                    break;
                }
            }
            running = false;
        });
        clickerThread.start();
    }
    @Override
    public void nativeKeyPressed(NativeKeyEvent e){
        if(e.getKeyCode() == NativeKeyEvent.VC_F4 && running){
            running = false;
        } else if(e.getKeyCode() == NativeKeyEvent.VC_F4 && !running){
            running = true;
        }
    }
}
