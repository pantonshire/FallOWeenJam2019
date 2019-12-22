package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.Main;

public class DesktopLauncher {

	// --- IMPORTANT NOTE ---
	// When running on desktop, use Java 12.
	// Java 14 makes the screen appear blurry.

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 360;
		config.title = "Tiny Escapes";
		new LwjglApplication(new Main(), config);
	}

}
