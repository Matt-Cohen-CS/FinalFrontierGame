package com.badlogic.lunarproj.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.lunarproj.Lunar;
import com.badlogic.lunarproj.LunarLanding;
import com.badlogic.lunarproj.MainMenuScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;

		new LwjglApplication(new Lunar(),config);
	}
}
