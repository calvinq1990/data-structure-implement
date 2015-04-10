import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class test {
	public static void main(String[] args) {
		String s = "\n";
		Pattern p = Pattern.compile("[^a-zA-Z]"); // this regex uses a block
		Matcher m = p.matcher(s);
		System.out.println(m.find());
		System.out.println(s.replaceAll(p.pattern(), "#"));
	}
}
