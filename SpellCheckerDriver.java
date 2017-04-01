
import java.io.IOException;
import java.text.DecimalFormat;


public class SpellCheckerDriver {

    public static void main(String[] args) {
        if (args.length < 2){
            System.out.println("Give 2 arguments:\n <input text path> <dictionary path> ");
            System.exit(-1);
        }

        SpellChecker uv = new SpellChecker();

        try{
            uv.readDictionary(args[0]);
            System.out.print(uv.readInputText(args[1]));
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
            System.exit(-1);
        }

        DecimalFormat sv = new DecimalFormat("###.##");

        System.out.print("\n");
        System.out.print("no_collisions: ");
        System.out.println(uv.getNoOfColl());

        System.out.print("average_chain_length: ");

        System.out.println(sv.format(uv.getAverageChainLength()));

        System.out.print("max_chain_length: ");
        System.out.println(uv.getMaxChainLength());

        System.out.print("load_factor: ");
        System.out.println(sv.format(uv.getLoadFactor()));
    }
}
