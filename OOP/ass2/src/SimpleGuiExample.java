

import biuoop.DrawSurface;
import biuoop.GUI;
import java.awt.Color;
import java.util.Random;

public class SimpleGuiExample {
	public SimpleGuiExample() {
	}

	public void drawRandomCircles() {
		Random var1 = new Random();
		GUI var2 = new GUI("Random Circles Example", 400, 300);
		DrawSurface var3 = var2.getDrawSurface();

		for(int var4 = 0; var4 < 10; ++var4) {
			int var5 = var1.nextInt(400) + 1;
			int var6 = var1.nextInt(300) + 1;
			int var7 = 5 * (var1.nextInt(4) + 1);
			var3.setColor(Color.RED);
			var3.fillCircle(var5, var6, var7);
		}

		var2.show(var3);
	}

	public static void main(String[] var0) {
		SimpleGuiExample var1 = new SimpleGuiExample();
		var1.drawRandomCircles();
	}
}
