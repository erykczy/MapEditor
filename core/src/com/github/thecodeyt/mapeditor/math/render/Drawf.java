package com.github.thecodeyt.mapeditor.math.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.github.thecodeyt.mapeditor.editor.ui.camera.CanvasCamera;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Drawf {
    @Getter private static Mask currentMask;

    public static boolean isPresent(SpriteBatch spriteBatch) {
        return spriteBatch.isDrawing();
    }
    public static boolean isPresent(ShapeRenderer shapeRenderer) {
        return shapeRenderer.isDrawing();
    }
    public static void drawBorder(ShapeRenderer shapeRenderer, Vector2 position, Vector2 size, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.line(position, new Vector2(position.x+size.x, position.y));
        shapeRenderer.line(position, new Vector2(position.x, position.y+size.y));
        shapeRenderer.line(new Vector2(position.x+size.x, position.y), new Vector2(position.x+size.x, position.y+size.y));
        shapeRenderer.line(new Vector2(position.x, position.y+size.y), new Vector2(position.x+size.x, position.y+size.y));
    }

    /*
    public static Mask applyMask(CanvasCamera camera, Mask mask) {
        camera.shapeRenderer.flush();
        camera.spriteBatch.flush();

        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Vector2 maskP0 = camera.viewport.project(mask.getP0());
        Vector2 maskP1 = camera.viewport.project(mask.getP1());
        Vector2 maskSize = maskP1.sub(maskP0);
        Gdx.gl.glScissor((int)maskP0.x, (int)maskP0.y, (int)maskSize.x, (int)maskSize.y);

        Mask newMask = new Mask();
        newMask.setP0(maskP0);
        newMask.setP0(maskP1);

        currentMask = newMask;
        return newMask;
    }
    public static void disableMask(CanvasCamera camera) {
        camera.shapeRenderer.flush();
        camera.spriteBatch.flush();

        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);

        currentMask = null;
    }
     */
    private static List<MaskResult> maskResultsLayers = new ArrayList<>();
    public static void addMaskLayer(CanvasCamera camera, Mask mask) {
        Vector2 maskP0 = camera.viewport.project(mask.getP0().cpy());
        Vector2 maskP1 = camera.viewport.project(mask.getP1().cpy());
        Vector2 maskSize = mask.getP1().cpy().sub(mask.getP0());

        Matrix4 transform = Drawf.isPresent(camera.spriteBatch) ? camera.spriteBatch.getTransformMatrix() : camera.shapeRenderer.getTransformMatrix();

        MaskResult maskResult = new MaskResult();
        ScissorStack.calculateScissors(camera.viewport.getCamera(), transform, new Rectangle(mask.getP0().x, mask.getP0().y, maskSize.x, maskSize.y), maskResult.getRectangle());

        maskResultsLayers.add(maskResult);
        applyMaskLayers();
    }
    public static void removeLastMaskLayer(CanvasCamera camera) {
        if(Drawf.isPresent(camera.spriteBatch))
            camera.spriteBatch.flush();

        if(Drawf.isPresent(camera.shapeRenderer))
            camera.shapeRenderer.flush();

        for (MaskResult maskResult : maskResultsLayers) {
            if(maskResult.isPushed())
                ScissorStack.popScissors();
        }

        maskResultsLayers.remove(maskResultsLayers.size()-1);
    }
    public static void applyMaskLayers() {
        for (MaskResult maskResult : maskResultsLayers) {
            maskResult.setPushed(ScissorStack.pushScissors(maskResult.getRectangle()));
        }
    }
    /*
    public static void addNewMask(CanvasCamera camera, Mask mask) {
        //camera.shapeRenderer.flush();
        //ScissorStack.popScissors();

        Vector2 maskP0 = camera.viewport.project(mask.getP0().cpy());
        Vector2 maskP1 = camera.viewport.project(mask.getP1().cpy());
        //Vector2 maskSize = maskP1.cpy().sub(maskP0);
        Vector2 maskSize = mask.getP1().cpy().sub(mask.getP0());

        Matrix4 transform = Drawf.isPresent(camera.spriteBatch) ? camera.spriteBatch.getTransformMatrix() : camera.shapeRenderer.getTransformMatrix();

        MaskResult maskResult = new MaskResult();
        ScissorStack.calculateScissors(camera.viewport.getCamera(), transform, new Rectangle(mask.getP0().x, mask.getP0().y, maskSize.x, maskSize.y), maskResult.getRectangle());

        maskResults.add(maskResult);
    }
    public static void applyMask() {
        for (MaskResult maskResult : maskResults) {
            maskResult.setPushed(ScissorStack.pushScissors(maskResult.getRectangle()));
        }
    }
    public static void clearMask(CanvasCamera camera) {
        if(Drawf.isPresent(camera.spriteBatch))
            camera.spriteBatch.flush();

        if(Drawf.isPresent(camera.shapeRenderer))
            camera.shapeRenderer.flush();

        for (MaskResult maskResult : maskResults) {
            if(maskResult.isPushed())
                ScissorStack.popScissors();
        }

        maskResults.clear();
    }
     */
}
