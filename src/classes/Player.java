package classes;

public class Player {
	private String name,avatar,password;
	private short gender;
	private int score;
	
	public Player(String name,String password, short gender, int score, String avatar) {
		this.name = name;
		this.avatar = avatar;
		this.gender = gender;
		this.score = score;
		this.password = password;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return this.avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public short getGender() {
		return this.gender;
	}
	public void setGender(short gender) {
		this.gender = gender;
	}
	public int getScore() {
		return this.score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object arg) {
		return this.name.equals(((Player)arg).getName());
	}
	
	
}
