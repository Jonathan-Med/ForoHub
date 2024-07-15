package com.aluracursos.desafio.forohub.domain.respuesta;

import com.aluracursos.desafio.forohub.domain.topico.Topico;
import com.aluracursos.desafio.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @Setter
    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;
    private LocalDateTime fechaDeCreacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;
    private String solucion;
    private  boolean estatus;

    public void desactivarRespuesta() {this.estatus = false;}

    public void actualizarRespuesta(DatosActualizarRespuesta datos) {
        if (datos.mensaje() != null){
            this.mensaje = datos.mensaje();
        }
        if (datos.solucion() != null){
            this.solucion = datos.solucion();
        }
    }

}
