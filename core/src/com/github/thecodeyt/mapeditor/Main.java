package com.github.thecodeyt.mapeditor;

import com.badlogic.gdx.Game;
import com.github.thecodeyt.mapeditor.editor.font.Fonts;
import com.github.thecodeyt.mapeditor.math.Inputf;
import com.github.thecodeyt.mapeditor.screen.EditorScreen;

public class Main extends Game {

	@Override
	public void create() {
		Fonts.initialize();
		setScreen(new EditorScreen());
	}

	@Override
	public void render() {
		super.render();
		Inputf.update();
	}

	@Override
	public void dispose() {
		super.dispose();
		Fonts.dispose();
	}
}
