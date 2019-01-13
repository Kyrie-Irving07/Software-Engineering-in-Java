
public class attribute {
	String name;
	String value;
	public static attribute init(String name, String value)
	{
		attribute attr = new attribute();
		attr.name = name;
		attr.value = value;
		return attr;
	}
}
