package it.polito.tdp.itunes.model;



public class Arco {

	private int t1;
	private int t2;
	private String titolo1;
	private String titolo2;
	private double peso;
	public Arco(int t1, int t2, double peso) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.peso = peso;
	}
	public int getT1() {
		return t1;
	}
	public void setT1(int t1) {
		this.t1 = t1;
	}
	public int getT2() {
		return t2;
	}
	public void setT2(int t2) {
		this.t2 = t2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public String getTitolo1() {
		return titolo1;
	}
	public void setTitolo1(String titolo1) {
		this.titolo1 = titolo1;
	}
	public String getTitolo2() {
		return titolo2;
		}
		public void setTitolo2(String titolo2) {
			this.titolo2 = titolo2;
		}
		
		

}


