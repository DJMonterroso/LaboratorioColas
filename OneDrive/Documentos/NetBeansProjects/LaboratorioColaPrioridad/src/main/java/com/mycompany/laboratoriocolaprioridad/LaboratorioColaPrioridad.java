package com.mycompany.laboratoriocolaprioridad;

import java.util.Comparator;
import java.util.PriorityQueue;



// Clase principal para la ejecución y pruebas
public class LaboratorioColaPrioridad {

    
    public static void main(String[] args) {
        SupportCenter centro = new SupportCenter();

        // Cargando los tickets según el enunciado
        centro.addTicket(new Ticket(1, "Ana", 3, 100, "No puedo iniciar sesion"));
        centro.addTicket(new Ticket(2, "Luis", 5, 120, "Sistema caido"));
        centro.addTicket(new Ticket(3, "Marta", 5, 110, "Error de pagos"));
        centro.addTicket(new Ticket(4, "Pedro", 2, 130, "Consulta general"));
        centro.addTicket(new Ticket(5, "Sofia", 3, 90, "Cambio de contraseña"));

        System.out.println("Iniciando atencion de tickets...\n");

        // Atender en ciclo hasta vaciar la cola
        while (!centro.isEmpty()) {
            Ticket actual = centro.attendTicket();
            System.out.println(actual);
        }
    }
}

class Ticket {
    private int id;
    private String cliente;
    private int prioridad;
    private long timestamp;
    private String descripcion;

    public Ticket(int id, String cliente, int prioridad, long timestamp, String descripcion) {
        this.id = id;
        this.cliente = cliente;
        this.prioridad = prioridad;
        this.timestamp = timestamp;
        this.descripcion = descripcion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Sobrescribimos toString para que la salida coincida con lo esperado
    @Override
    public String toString() {
        return "Ticket id=" + id + " (prioridad " + prioridad + ", ts " + timestamp + ")";
    }
}

class SupportCenter {
    private PriorityQueue<Ticket> colaTickets;

    public SupportCenter() {
        // Configuramos la PriorityQueue con un Comparator personalizado usando lambdas
        colaTickets = new PriorityQueue<>((t1, t2) -> {
            if (t1.getPrioridad() != t2.getPrioridad()) {
                // Si la prioridad es distinta, ordenamos descendente (mayor prioridad primero)
                return Integer.compare(t2.getPrioridad(), t1.getPrioridad());
            } else {
                // Si hay empate en prioridad, ordenamos ascendente por timestamp (el más antiguo primero)
                return Long.compare(t1.getTimestamp(), t2.getTimestamp());
            }
        });
    }

    public void addTicket(Ticket t) {
        colaTickets.offer(t);
    }

    public Ticket nextTicket() {
        return colaTickets.peek(); // Retorna sin quitar
    }

    public Ticket attendTicket() {
        return colaTickets.poll(); // Retorna y quita de la cola
    }

    public int pendingCount() {
        return colaTickets.size();
    }

    public boolean isEmpty() {
        return colaTickets.isEmpty();
    }
}