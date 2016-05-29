package writer;

import com.pi4j.io.gpio.*;

public class Writer {
	/*
	public static void main(String[] args) throws InterruptedException {
		boolean message[] = new boolean[]{true, false, false, false, true, true};
		int delay = 50;
		Writer writer = new Writer(RaspiPin.GPIO_27, new Pin[]{RaspiPin.GPIO_28});
		w.write(message, delay);
	}
	*/
	
	final GpioPinDigitalOutput dataPin[];
	final GpioPinDigitalOutput syncPin;
	final GpioController gpio;
	
	public Writer(Pin sync, Pin[] data){
		gpio = GpioFactory.getInstance();
		syncPin = gpio.provisionDigitalOutputPin(sync, "SyncPin", PinState.LOW);
		dataPin = new GpioPinDigitalOutput[data.length];
		for(int i = 0; i<data.length; i++)
			dataPin[i] = gpio.provisionDigitalOutputPin(data[i], ("DataPin #"+i), PinState.LOW);	
	}
	
	public  boolean write( boolean[] message, int delay){
		try{
			for(int i = 0; i<message.length;i+=dataPin.length){
				syncPin.high();
				for(int j = 0; j<dataPin.length; j++)
					if(message[i+j])
						dataPin[j].high();
				Thread.sleep(delay-3);
				syncPin.low();
				Thread.sleep(3);
				for(int j = 0; j<dataPin.length; j++)
					dataPin[j].low();
			}
			gpio.shutdown();
			return true;
		} catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
		
	}
}
