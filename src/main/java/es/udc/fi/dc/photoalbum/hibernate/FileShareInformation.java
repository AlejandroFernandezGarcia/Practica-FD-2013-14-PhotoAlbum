package es.udc.fi.dc.photoalbum.hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FILE_SHARE_INFORMATION")
@SuppressWarnings("serial")
public class FileShareInformation implements Serializable {

	private Integer id;
	private File file;
	private User user;

	public FileShareInformation() {
	}

	public FileShareInformation(Integer id, File file, User user) {
		this.id = id;
		this.file = file;
		this.user = user;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "FILE_ID")
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
