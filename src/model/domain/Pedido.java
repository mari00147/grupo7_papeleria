import java.time.LocalDate;

public class Pedido {
    private LocalDate fecha;
    private String estado;



	public Pedido(LocalDate fecha, String estado) {
		this.fecha = fecha;
		this.estado = estado;

	}



	public LocalDate getFecha() {
		return fecha;
	}



	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}
    

    
    
}
