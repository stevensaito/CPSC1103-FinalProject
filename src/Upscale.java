import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Upscale {

    Util utility;

    public Upscale(Util util){
        utility = util;
    }

    // Runs a commandline within java to convert an image using the settings the user provided in the GUI
    public void convert() throws IOException
    {
        Runtime runtime = Runtime.getRuntime();

        String flags = utility.getFlags();

        System.out.println(flags);

        Process waifu2x = runtime.exec(
                "cmd /c start waifu2x-converter-cpp.exe " +               // Launches the Waifu2x Wrapper
                flags +                                                             // The commands needed to convert images
                " --processor " + "1"                                               // To stop Waifu2x from using Nvidia CUDA cores and crashing the app
        );

        InputStream is = waifu2x.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

}
