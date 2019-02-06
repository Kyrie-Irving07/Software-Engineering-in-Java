package backend;


public class Attribute {
	String name;
	String value;
	public static Attribute init(String name, String value)
	{
		Attribute attr = new Attribute();
		attr.name = name;
		attr.value = value;
		return attr;
	}
}
