package io.robgui09.rhider;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("regex")
            .define("regex", "(Одиночная|Singleplayer)");
			
    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_TARGET_INTRODUCTION = BUILDER
            .comment("target")
            .define("target", "<hidden>");

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static String magicNumberIntroduction;
    public static String magicTargetIntroduction;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
        magicTargetIntroduction = MAGIC_TARGET_INTRODUCTION.get();
    }
	
	public static boolean isMatch(String regex, String target) {
        try {
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(target);
            return matcher.find(); // Returns true if ANY match is found.  Use matcher.matches() for full string match.

        } catch (java.util.regex.PatternSyntaxException e) {
            // Handle invalid regex patterns.  For example, you could re-throw as a more specific exception
            // or return false/null and log the error.  Returning false allows the calling code to continue without
            // crashing.  Throwing an exception forces the caller to handle the bad regex.  The choice depends
            // on the desired behavior.

            // Option 1:  Return false and log (for more robust error handling)
            System.err.println("Invalid regex: " + regex + " - " + e.getMessage());
            return false;

            // Option 2: Throw IllegalArgumentException (if you want the caller to handle the regex error)
            // throw new IllegalArgumentException("Invalid regex: " + regex + " - " + e.getMessage());
        }
    }
}
