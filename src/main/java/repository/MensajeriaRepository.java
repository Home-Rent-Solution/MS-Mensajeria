package repository;
import org.springframework.data.jpa.repository.JpaRepository; // 👈 AQUÍ VA
import model.Mensajeria;

public interface MensajeriaRepository extends JpaRepository<Mensajeria, Long>{
}
