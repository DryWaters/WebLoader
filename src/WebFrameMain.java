
public class WebFrameMain {

	public static void main(String[] args) {
		WebFrame frame = null;
		if (args.length == 0) {
			frame = new WebFrame(null);
			
		} else {
			frame = new WebFrame(args[0]);
		}
	}
}
