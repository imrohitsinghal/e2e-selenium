package tulna.Screens;

import org.im4java.core.IM4JavaException;
import java.io.IOException;
import static tulna.Screens.Validator.isBuildMode;
import static tulna.Screens.Validator.isForceBuildMode;


public class TulnaExecutor {

    private ExecutorService executorService;

    public TulnaExecutor() {
        executorService = new ExecutorService();
    }

    /**
     * @param baseLineImageName * @param threshold
     * @return false if actual and expected images are not similar and generate a difference Image
     */
    public boolean tulnaExecutorNativeCompare(String baseLineImageName, int threshold)
            throws InterruptedException, IOException, IM4JavaException {

        return (isBuildMode() || isForceBuildMode()) ? executorService.buildMode(baseLineImageName)
                : compareModeRetry(baseLineImageName, threshold);
    }

    public boolean compareModeRetry(String baseLineImageName, int threshold)
            throws InterruptedException, IOException, IM4JavaException {
        int retryCount = 3;
        int waitInterval = 1;
        for (int i=0; i < retryCount; i++) {
            if (executorService.compareMode(baseLineImageName, threshold)) {
                return true;
            }
            Thread.sleep(waitInterval * 1000);
        }
        return false;
    }
}
