package tulna.Platforms;

import tulna.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageConverter {
    public static void main(String[] args) {
        Web web = new Web();
        Utils util = new Utils();
        List<String> result = null;
        try (Stream<Path> walk = Files.walk(Paths.get("/Users/ismieshkov/IdeaProjects/P2PAutomation/Baselines/web_qa/baseline_images"))) {

            result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            result.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
        result.forEach((path) -> {
            if (path.endsWith(".png")) {
                File origFile = new File(path);
                String newPath = path.replace("baseline_images", "baseline_images_converted").replace(".png", ".jpg");
                File newFile = new File(newPath);
                String parentFolder = newFile.getParent();
                if (! new File(parentFolder).exists()) {
                    util.createDirectory(parentFolder);
                    System.out.println("Creating folder " + parentFolder);
                }
                try {
                    web.writeImageToFileAdvanced(origFile, newFile, "jpg");
                    System.out.println("Converting file " + newPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
