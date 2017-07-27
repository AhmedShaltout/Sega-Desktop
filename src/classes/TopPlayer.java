package classes;

import javafx.scene.layout.Pane;

public class TopPlayer {
	private Pane avatar;
	private String name;
	private int score;
	public TopPlayer(Pane avatar, String name, int score) {
		this.avatar = avatar;
		this.name = name;
		this.score = score;
	}
	public Pane getAvatar() {
		return this.avatar;
	}
	public void setAvatar(Pane avatar) {
		this.avatar = avatar;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return this.score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
