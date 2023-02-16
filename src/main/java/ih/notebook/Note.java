package ih.notebook;

import java.io.Serializable;

public class Note implements Serializable{

	String title;
	String[] description;

	public void setTitle(String title) {

		this.title = title;

	}

	public String getTitle() {

		return this.title;

	}

	public void setDescription(String[] description) {

		this.description = description;

	}

	public String[] getDescription() {

		return this.description;

	}

}