package net.imagej.jep.scenarios.scenario5;

import jep.JepException;
import net.imagej.jep.scenarios.IScenario;
import net.imglib2.img.Img;

import java.io.IOException;

/**
 * Class scenario to test if ImageJ is loading and open an image in it
 *
 * @author Amandine Tournay
 */
public class OpenImage extends IScenario {
    @Override
    public void runScenario(boolean firstRun) {
        System.out.println();
        System.out.println("---- Get ImageJ version from Python ----");

        try {
            openJep(firstRun);
            loadImageJ(true);

            getJepInter().exec("blob = 'src/main/resources/scenario4/blobs.png'");
            Img img = (Img) getIj().io().open(getJepInter().getValue("blob", String.class));

            getIj().ui().show(img);

            closeJep();
        }
        catch (JepException e) {
            throwJepException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
