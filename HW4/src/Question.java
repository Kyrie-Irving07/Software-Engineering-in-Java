
public class Question {
	public int num;
	public String answer;
	public String question;
	public Question(int num, String answer, String question){
		this.num = num;
		this.answer = answer;
		this.question = question;
	}
	public boolean validate(String ans) {
		return(ans.toLowerCase().equals(this.answer.toLowerCase()));
	}
}
