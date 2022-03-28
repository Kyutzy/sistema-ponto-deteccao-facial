package application;

import javafx.scene.image.Image;

public class Pessoa {
	String nome, data, hora, path;
	Image foto;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public Image getFoto() {
		return foto;
	}
	public void setFoto(Image foto) {
		this.foto = foto;
	}
	public Pessoa(String nome, String data, String hora, String path) {
		super();
		this.nome = nome;
		this.data = data;
		this.hora = hora;
		this.foto = new Image(path, 226, 189, false, false);
	}
}
