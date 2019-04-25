
public class Question {
	public int num;
	public String answer;
	public String question;
	public boolean answered = false;
	public Word word;
	public String position;
	public Question() {
		this.num = 0;
		this.answered = false;
	}
	public Question(int num, String answer, String question, String position, Word word){
		this.num = num;
		this.answer = answer;
		this.question = question;
		this.position = position;
		this.word = word;
	}
	public boolean validate(String ans) {
		boolean result = ans.toLowerCase().equals(this.answer.toLowerCase());
		if(result) {
			this.answered = true;
			this.word.answered = true;
		}
		return(result);
	}
}
