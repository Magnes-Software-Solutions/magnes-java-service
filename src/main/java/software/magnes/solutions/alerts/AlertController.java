package software.magnes.solutions.alerts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
public class AlertController {
// EndPoint Http
    @GetMapping("/alerts")
    public List<Alertas> listarAlertas() {
        return AlertService.getAlertass();
    }
}
